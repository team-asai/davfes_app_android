package quizgame.test.com.myapp_test13.Activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import android.view.MenuItem
import android.widget.ExpandableListView
import quizgame.test.com.myapp_test13.Adapters.AccordionAdapter
import quizgame.test.com.myapp_test13.DataClasses.Plan
import quizgame.test.com.myapp_test13.Managers.PlanManager
import quizgame.test.com.myapp_test13.R

class PlanDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_detail)

        // 企画一覧アクティビティで押されたボタンの位置を取得
        val pushPosition = intent.getIntExtra("position", 0)
        val shardPreferences = getSharedPreferences("CommonData" , Context.MODE_PRIVATE)
        val serverName = shardPreferences.getString("ServerName", "")
        val baseUrl = serverName + "api/resources/"

        val endUrl = when(pushPosition.toString()){
            "0" -> "tokubetsuKikaku"
            "1" -> "seisakuKyoshitsu"
            "2" -> "daigakuTankentai"
            "3" -> "kagakuEnnichi"
            "4" -> "sonota"
            else -> "plan"
        }

        val topKey = when(pushPosition.toString()){
            "0" -> "特別コラボ企画"
            "1" -> "こども製作教室"
            "2" -> "大学探検隊"
            "3" -> "おもしろ科学縁日"
            "4" -> "その他いろいろ"
            else -> "全企画"
        }

//        Log.d("pus_hPosition", pushPosition.toString())
//        Log.d("endUrl", endUrl)
//        Log.d("topKey", topKey)

        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        //ここでは、topKeyをタイトルとしている
        supportActionBar?.title =topKey


        PlanManager().httpGetPlanJson(baseUrl + endUrl, topKey, ::adapterToView)
    }

    /**
     * HTTP通信後に行う処理を関数型オブジェクトとしてhttpGetPlanJsonに渡す
     * もっと分かりやすい処理方法があるはず
     */
    private fun adapterToView(plans: ArrayList<Plan>){
        val expListView = findViewById<ExpandableListView>(R.id.expList)
        val adapter = AccordionAdapter(this, plans)
        expListView.setAdapter(adapter)
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
