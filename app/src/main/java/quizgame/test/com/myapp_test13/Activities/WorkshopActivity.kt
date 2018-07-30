package quizgame.test.com.myapp_test13.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import quizgame.test.com.myapp_test13.DataClasses.Plan
import quizgame.test.com.myapp_test13.Adapters.PlanAdapter
import quizgame.test.com.myapp_test13.Managers.PlanManager
import quizgame.test.com.myapp_test13.R

class WorkshopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop)

        val baseUrl = "http://ik1-307-13856.vs.sakura.ne.jp/api/resources/seisakuKyoshitsu"

        PlanManager().httpGetPlanJson(baseUrl, "こども製作教室", ::adapterToView)

        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        supportActionBar?.title = "こども科学制作教室"
    }

    //前の画面に戻る
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    /**
     * HTTP通信後に行う処理を関数型オブジェクトとしてhttpGetPlanJsonに渡す
     * もっと分かりやすい処理方法があるはず
     */
    private fun adapterToView(plans: ArrayList<Plan>){
        val workshopListView = findViewById<ListView>(R.id.workshopListView)
        val adapter = PlanAdapter(this, plans)
        workshopListView.adapter = adapter

        workshopListView.setOnItemClickListener { _, _, position, _ ->
            val workshopDetailIntent = Intent(this, WorkshopDetailActivity::class.java)
            // インテントにセット
            workshopDetailIntent.putExtra("position", position)
            // Activity をスイッチする
            startActivity(workshopDetailIntent)
        }
    }
}
