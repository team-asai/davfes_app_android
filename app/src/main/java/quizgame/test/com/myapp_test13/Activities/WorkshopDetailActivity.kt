package quizgame.test.com.myapp_test13.Activities

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop_detail)

        val baseUrl = "http://ik1-307-13856.vs.sakura.ne.jp/api/resources/seisakuKyoshitsu"

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
        buildingIdImage.layoutParams.height = screenHeight / 10

        val floorNumberImage = findViewById<ImageView>(R.id.flowNumberImage)
        floorNumberImage.layoutParams.height = screenHeight / 10

        // ライブラリを使ってサーバーから画像を読み込む
        val url = "http://ik1-307-13856.vs.sakura.ne.jp/" + plan.imageUrl
        Picasso.get()
                .load(url)
                .resize(450, 450)
                .centerCrop()
                .into(centerImage)

        val sideResourceId = when (pushPosition % 4) {
            0 -> R.drawable.workshop_1
            1 -> R.drawable.workshop_2
            2 -> R.drawable.workshop_3
            else -> R.drawable.workshop_4
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
            "A棟" -> R.drawable.building_a
            "B棟" -> R.drawable.building_b
            "C棟" -> R.drawable.building_c
            "D棟" -> R.drawable.building_d
            "E棟" -> R.drawable.building_e
            "F棟" -> R.drawable.building_f
            "G棟" -> R.drawable.building_g
            "H棟" -> R.drawable.building_h
            "I棟" -> R.drawable.building_i
            "J棟" -> R.drawable.building_j
            "K棟" -> R.drawable.building_k
            "L棟" -> R.drawable.building_l
            "M棟" -> R.drawable.building_m
            "N棟" -> R.drawable.building_n
            "O棟" -> R.drawable.building_o
            "P棟" -> R.drawable.building_p
            "Q棟" -> R.drawable.building_q
            "R棟" -> R.drawable.building_r
            "S棟" -> R.drawable.building_s
            "T棟" -> R.drawable.building_t
            "U棟" -> R.drawable.building_u1
            "V棟" -> R.drawable.building_v
            "W棟" -> R.drawable.building_w
            "X棟" -> R.drawable.building_x
            "Y棟" -> R.drawable.building_y
            else -> R.drawable.building_z
        }
    }

    private fun getFloorNumberResourceId(floorText: String): Int{
        return when(floorText){
            "1F" -> R.drawable.floor_num_1
            "2F" -> R.drawable.floor_num_2
            "3F" -> R.drawable.floor_num_3
            "4F" -> R.drawable.floor_num_4
            "5F" -> R.drawable.floor_num_5
            "6F" -> R.drawable.floor_num_6
            "7F" -> R.drawable.floor_num_7
            "8F" -> R.drawable.floor_num_8
            else -> R.drawable.floor_num_9
        }
    }
}