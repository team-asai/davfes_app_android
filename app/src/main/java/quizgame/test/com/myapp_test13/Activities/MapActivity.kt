package quizgame.test.com.myapp_test13.Activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.*
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.firebase.iid.FirebaseInstanceId
import org.altbeacon.beacon.*
import org.altbeacon.beacon.Beacon
import org.json.JSONArray
import org.json.JSONObject
import quizgame.test.com.myapp_test13.R
import java.util.ArrayList

class MapActivity : AppCompatActivity(), BeaconConsumer {

    // ログ出力用のTAG
    val TAG: String = "テストログ"

    // ビーコンのフォーマット設定
    private val IBEACON_FORMAT: String = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"

    // BeaconManager(オプショナル型)型の変数の宣言
    private var beaconManager: BeaconManager? = null

    // uuidの指定
    private val uuidString: String = "00000000-6dc8-1001-b000-001c4d88ed46"
    private val uuid = Identifier.parse(uuidString)

    // get用のurlの指定
    private val url_get = "http://ik1-307-13856.vs.sakura.ne.jp/api/resources/beaconPlace"
    // post用のurlの指定
    private val url_post = "http://ik1-307-13856.vs.sakura.ne.jp/api/post/beacon"

    // デバイストークンを保持するための変数
    private var token: String? = null

    // 通信先から取得した情報を保持する変数
    private var beaconInfoJson: JSONArray = JSONArray()

    // Handlerクラスの変数の宣言
    private val handler: Handler = Handler()

    // 前回検知したビーコンのMajor値を保持する変数
    private var previousMaxMajor: String = ""
    // 前回検知したビーコンのMinor値を保持する変数
    private var previousMaxMinor: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // API 23以上でパーミッシンの確認
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission()
        }

        // マップの画像の初期設定
        var mapImage: ImageView = findViewById(R.id.mapImage)
        mapImage.setImageResource(R.drawable.map)

        // ビーコンマネージャのインスタンスを生成
        beaconManager = BeaconManager.getInstanceForApplication(this)

        // BeaconParseをBeaconManagerに設定
        beaconManager!!.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))

        // 通信先からJson情報を取得
        url_get.httpGet().responseJson { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // 成功した時、変数に保持させる
                    beaconInfoJson = result.value.array()
                }
                is Result.Failure -> {
                    // エラー処理
                }
            }
        }

        // デバイストークンの取得
        token = FirebaseInstanceId.getInstance().getToken()


        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        supportActionBar?.title = "大学内マップ"
    }

    //前の画面に戻る
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    // onResumeのオーバーライド
    override fun onResume() {
        super.onResume()

        // ビーコンサービスの開始
        beaconManager!!.bind(this)
//        Log.d(TAG, "ビーコンサービスの開始")
//        Log.d(TAG, beaconManager.toString())
    }

    override fun onPause() {
        super.onPause()

        // ビーコンサービスの停止
        beaconManager!!.unbind(this)
//        Log.d(TAG, "ビーコンサービスの停止")
    }



    // onBeaconServiceConnectのオーバーライド
    override fun onBeaconServiceConnect() {

        try {
            // ビーコン情報の監視を開始
            beaconManager!!.startMonitoringBeaconsInRegion(Region("unique_id_0723", uuid, null, null))
//            Log.d(TAG, "try成功")
        }
        catch (e: RemoteException) {
            e.printStackTrace()
//            Log.d(TAG, "try失敗")
        }

        // モニタリングの通知受取り処理
        beaconManager!!.addMonitorNotifier(object: MonitorNotifier{

            // 領域内に侵入した時に呼ばれる
            override fun didEnterRegion(region: Region) {

//                Log.d(TAG, "侵入：" + region)

                // レンジングの開始
                beaconManager!!.startRangingBeaconsInRegion(region)
            }

            // 領域外に退出した時に呼ばれる
            override fun didExitRegion(region: Region) {

//                Log.d(TAG, "退出：" + region)

                // レンジングの停止
                beaconManager!!.stopRangingBeaconsInRegion(region)
            }

            // 領域への侵入/退出のステータスが変化した時に呼ばれる
            override fun didDetermineStateForRegion(i: Int, region: Region) {

//                Log.d(TAG, "ステータス変化：" + region)
            }
        })

        // レンジングの通知受け取り処理
        beaconManager!!.addRangeNotifier(object: RangeNotifier{

            // 範囲内のビーコン情報を受け取る
            override fun didRangeBeaconsInRegion(beacons: Collection<Beacon>, region: Region){

                // 範囲内の複数のビーコン情報を保持させる変数
                var catchMajorList: ArrayList<String> = ArrayList()
                var catchMinorList: ArrayList<String> = ArrayList()
                var catchRssiList: ArrayList<Int> = ArrayList()

                // 範囲内にビーコンがある時の処理
                if (beacons.size > 0) {
                    // 範囲内のビーコンの数だけ繰り返す
                    for (beacon in beacons) {

                        // 検出したビーコン全てをログに出力
//                        Log.d(TAG, "UUID:" + beacon.id1 + ", major:" + beacon.id2
//                                + ", minor:" + beacon.id3 + ", Distance:" + beacon.distance
//                                + ",RSSI" + beacon.rssi)

                        // 複数のビーコン情報をArrayListに分割
                        catchMajorList.add(beacon.id2.toString())
                        catchMinorList.add(beacon.id3.toString())
                        catchRssiList.add(beacon.rssi)
                    }

//                    Log.d(TAG, catchRssiList.max().toString())
//                    Log.d(TAG, catchRssiList.indexOf(catchRssiList.max()).toString())

                    // RSSIが最も大きいインデックスを取得
                    var indexRssi: Int = catchRssiList.indexOf(catchRssiList.max())

                    // 取得したインデックスのmajor値・minor値を取得
                    var maxMajor: String = catchMajorList[indexRssi]
                    var maxMinor: String = catchMinorList[indexRssi]

                    // 通信先からのビーコン情報の数だけ繰り返す
                    for (i in 0..beaconInfoJson.length() - 1) {
                        var j = beaconInfoJson[i] as JSONObject

                        // 通信先のビーコン情報と範囲内のビーコン情報を比較して、マッチするものを探す
                        if (j["major"].toString() == maxMajor) {
                            if (j["minor"].toString() == maxMinor) {
//                                Log.d(TAG, j["place"].toString())
//                                Log.d(TAG, j["room"].toString())

                                // この中身はメインスレッドで実行される
                                handler.post {
                                    // マッチする情報を関数に渡して、Viewの更新
                                    update(j["place"].toString(), j["room"].toString())
                                }

                                // 検知した最も近いビーコン情報が変化した時
                                if (!(previousMaxMajor == maxMajor && previousMaxMinor == maxMinor)) {

                                    // パラメータ付きのURLの生成
                                    var url = url_post +
                                            "?os=Android" +
                                            "&deviceToken=" + token +
                                            "&uuid=" + uuidString +
                                            "&major=" + maxMajor +
                                            "&minor=" + maxMinor

                                    // Post
                                    url.httpPost().responseJson { request, response, result ->
                                        when (result) {
                                            is Result.Success -> {
//                                                Log.d(TAG, request.toString())
//                                                Log.d(TAG, response.toString())
//                                                Log.d(TAG, result.toString())
                                            }
                                            is Result.Failure -> {
                                                // エラー処理
                                            }
                                        }
                                    }

                                    // 前回のビーコン情報を更新
                                    previousMaxMajor = maxMajor
                                    previousMaxMinor = maxMinor
                                }
                            }
                        }
                    }
                }
                // 範囲内にビーコンが存在しない場合
                else {
                    // この中身はメインスレッドで実行される
                    handler.post {
                        // 空の引数を渡して、Viewの更新
                        update("", "")
                    }
                }
            }
        })
    }



    // Viewを更新するメソッド
    fun update(place: String, room: String){
        var mapImage : ImageView = findViewById(R.id.mapImage)
//        Log.d(TAG, mapImage.toString())
        var text3: TextView = findViewById(R.id.text3)
        var text4: TextView = findViewById(R.id.text4)

//        Log.d("place",place)
//        Log.d("room", room)

        when(place) {
            "A" -> mapImage.setImageResource(R.drawable.a_map)
            "B" -> mapImage.setImageResource(R.drawable.b_map)
            "C" -> mapImage.setImageResource(R.drawable.c_map)
            "D" -> mapImage.setImageResource(R.drawable.d_map)
            "E" -> mapImage.setImageResource(R.drawable.e_map)
            "F" -> mapImage.setImageResource(R.drawable.f_map)
            "G" -> mapImage.setImageResource(R.drawable.g_map)
            "H" -> mapImage.setImageResource(R.drawable.h_map)
            "K" -> mapImage.setImageResource(R.drawable.k_map)
            "L" -> mapImage.setImageResource(R.drawable.l_map)
            "M" -> mapImage.setImageResource(R.drawable.m_map)
            "W1" -> mapImage.setImageResource(R.drawable.w1_map)
            "W2" -> mapImage.setImageResource(R.drawable.w2_map)
            "W3" -> mapImage.setImageResource(R.drawable.w3_map)

            else -> when(room) {
                "パステル工房" -> mapImage.setImageResource(R.drawable.pastel_map)
                "ステージ" -> mapImage.setImageResource(R.drawable.stage_map)
                "噴水前" -> mapImage.setImageResource(R.drawable.funsui_map)
                "ひまわり畑" -> mapImage.setImageResource(R.drawable.himawari_map)

                else -> mapImage.setImageResource(R.drawable.map)
            }
        }

        // テキストの変更
        if (room != ""){
            text3.text = "現在地を取得しました！"
            text4.text = room
        } else {
            text3.text = "場所がわからなかったときには・・・"
            text4.text = "A棟1階入り口前 受付にてお尋ねください"
        }
    }


    // パーミッションの許可状態の確認
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "パーミッション許可済")
        } else {
//            Log.d(TAG, "パーミッション未許可")
            requestLocationPermission()
        }
    }

    // パーミッションの許可ダイアログの表示
    fun requestLocationPermission() {
        val REQUEST_PERMISSION = 1000
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
        } else {
            val toast = Toast.makeText(this, "許可されないとアプリが実行できません", Toast.LENGTH_SHORT)
            toast.show()
//            Log.d(TAG, "今後は表示しない")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
        }
    }
}
