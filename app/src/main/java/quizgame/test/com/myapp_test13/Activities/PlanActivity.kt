package quizgame.test.com.myapp_test13.Activities

import android.content.Intent
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import quizgame.test.com.myapp_test13.Adapters.PlanImageAdapter
import quizgame.test.com.myapp_test13.R

class PlanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)

        val imageUrlList = ArrayList<Int>()

        //企画タイトル画像のResourceIDを読み込む
        for (i in 1..5){
            val imageName = "plan_$i"
            val imageUrl = resources.getIdentifier(imageName, "drawable", this.packageName)
            imageUrlList.add(imageUrl)
        }

        // スクリーンサイズを取得します
        val screen = this.windowManager.defaultDisplay
        var screenHeight: Int = 1920
        val point = Point()
        screen.getSize(point)
        screenHeight = point.y

//        Log.d("height", screenHeight.toString())

        val oneListHeight = screenHeight / 5

        val planList = findViewById<ListView>(R.id.planListView)
        val adapter = PlanImageAdapter(this, imageUrlList, oneListHeight)

        planList.adapter = adapter

        planList.setOnItemClickListener { _, _, position, _ ->
            if (position == 1) {
                val workshopIntent = Intent(this, WorkshopActivity::class.java)
                startActivity(workshopIntent)
            } else {
                val planDetailIntent = Intent(this, PlanDetailActivity::class.java)
                // インテントにセット
                planDetailIntent.putExtra("position", position)
                // Activity をスイッチする
                startActivity(planDetailIntent)
            }
        }

        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        supportActionBar?.title = "企画一覧"
    }
    //前の画面に戻る
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

}
