package quizgame.test.com.myapp_test13.Activities

import android.content.Context
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import quizgame.test.com.myapp_test13.Managers.FavoriteManager
import quizgame.test.com.myapp_test13.DataClasses.Plan
import quizgame.test.com.myapp_test13.Managers.PlanManager
import quizgame.test.com.myapp_test13.R

class WorkshopDetailActivity : AppCompatActivity() {
    private var serverName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop_detail)

        val shardPreferences = getSharedPreferences("CommonData" , Context.MODE_PRIVATE)
        serverName = shardPreferences.getString("ServerName", "")
        val baseUrl = serverName + "api/resources/seisakuKyoshitsu"

        PlanManager().httpGetPlanJson(baseUrl, "こども製作教室", ::dataToView)

    }

    //前の画面に戻る
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
    private fun dataToView(plans: ArrayList<Plan>) {

        // 画面の高さを取得
        val screen = this.windowManager.defaultDisplay
        var screenHeight: Int = 1920
        val point = Point()
        screen.getSize(point)
        screenHeight = point.y

        val pushPosition = intent.getIntExtra("position", 0)

        //TextViewにテキストデータを挿入
        val titleText = findViewById<TextView>(R.id.workshopDetailTitle)

        val contentText = findViewById<TextView>(R.id.workshopDetailContent)
        contentText.layoutParams.height = screenHeight / 4

        val timeText = findViewById<TextView>(R.id.workshopImplimantationTime)

        val targetText = findViewById<TextView>(R.id.workshopTargetText)

        val admissionText = findViewById<TextView>(R.id.needApplicationText)

        val plan = plans[pushPosition]

        titleText.text = plan.titleText
        contentText.text = plan.contentText
        timeText.text = plan.implementTimeText
        targetText.text = plan.targetText

        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        //ここでは、企画のタイトルをアクションバーのタイトルとしている
        supportActionBar?.title =plan.titleText

        admissionText.text = when (plan.admission) {
            "当日申込" -> "事前申し込み 必要"
            "事前申込" -> "事前申し込み 不要"
            else -> plan.admission
        }

        //ImageViewに画像データ挿入
        val centerImage = findViewById<ImageView>(R.id.wdCenterImage)
        centerImage.layoutParams.height = screenHeight / 4

        val sideImage = findViewById<ImageView>(R.id.workshopSideImage)
        sideImage.layoutParams.height = screenHeight / 3

        val buildingIdImage = findViewById<ImageView>(R.id.buldingIdImage)
        buildingIdImage.layoutParams.height = screenHeight / 15

        val floorNumberImage = findViewById<ImageView>(R.id.flowNumberImage)
        floorNumberImage.layoutParams.height = screenHeight / 15

        // ライブラリを使ってサーバーから画像を読み込む
        val url = "https://mini.puc.pu-toyama.ac.jp/davfes_app/" + plan.imageUrl
        Picasso.get()
                .load(url)
                .resize(450, 450)
                .centerCrop()
                .into(centerImage)

        val sideResourceId = when (pushPosition % 3) {
            0 -> R.drawable.workshop_1
            1 -> R.drawable.workshop_2
            2 -> R.drawable.workshop_3
            else -> R.drawable.workshop_1
        }
        sideImage.setImageResource(sideResourceId)

        val bId = getBuildingResourceId(plan.placeText)
        buildingIdImage.setImageResource(bId)

        val fId = getFloorNumberResourceId(plan.floorText)
        floorNumberImage.setImageResource(fId)

        //お気に入りボタンの処理
        val favoriteButton = findViewById<ImageButton>(R.id.wdFavoriteButton)
        FavoriteManager().changeFavoriteButton(this, favoriteButton, plan.elementId)
    }

    private fun getBuildingResourceId(placeText: String): Int {
        return when(placeText){
            "A" -> R.drawable.building_a
            "B" -> R.drawable.building_b
            "C" -> R.drawable.building_c
            "D" -> R.drawable.building_d
            "E" -> R.drawable.building_e
            "F" -> R.drawable.building_f
            "G" -> R.drawable.building_g
            "H" -> R.drawable.building_h
            "I" -> R.drawable.building_i
            "J" -> R.drawable.building_j
            "K" -> R.drawable.building_k
            "L" -> R.drawable.building_l
            "M" -> R.drawable.building_m
            "N" -> R.drawable.building_n
            "O" -> R.drawable.building_o
            "P" -> R.drawable.building_p
            "Q" -> R.drawable.building_q
            "R" -> R.drawable.building_r
            "S" -> R.drawable.building_s
            "T" -> R.drawable.building_t
            "U" -> R.drawable.building_u1
            "V" -> R.drawable.building_v
            "W1" -> R.drawable.building_w1
            "W2" -> R.drawable.building_w2
            "W3" -> R.drawable.building_w3
            "W4" -> R.drawable.building_w4
            "X" -> R.drawable.building_x
            "Y" -> R.drawable.building_y
            else -> R.drawable.building_z
        }
    }

    private fun getFloorNumberResourceId(floorText: String): Int{
        return when(floorText){
            "1" -> R.drawable.floor_num_1
            "2" -> R.drawable.floor_num_2
            "3" -> R.drawable.floor_num_3
            "4" -> R.drawable.floor_num_4
            "5" -> R.drawable.floor_num_5
            "6" -> R.drawable.floor_num_6
            "7" -> R.drawable.floor_num_7
            "8" -> R.drawable.floor_num_8
            else -> R.drawable.floor_num_9
        }
    }
}
