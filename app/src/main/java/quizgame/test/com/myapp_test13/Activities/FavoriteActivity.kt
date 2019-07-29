package quizgame.test.com.myapp_test13.Activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ExpandableListView
import android.widget.ImageView
import quizgame.test.com.myapp_test13.*
import quizgame.test.com.myapp_test13.Adapters.AccordionAdapter
import quizgame.test.com.myapp_test13.DataClasses.Plan
import quizgame.test.com.myapp_test13.Managers.FavoriteManager
import quizgame.test.com.myapp_test13.Managers.PlanManager

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val shardPreferences = getSharedPreferences("CommonData" , Context.MODE_PRIVATE)
        val serverName = shardPreferences.getString("ServerName", "")
        val url = serverName + "api/resources/plan"

        // 最後の引数は関数型オブジェクト
        PlanManager().httpGetPlanJson(url, "全企画", ::adapterToView)


        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        supportActionBar?.title = "お気に入り"
    }

    /**
     * HTTP通信後に行う処理を関数型オブジェクトとしてhttpGetPlanJsonに渡す
     * もっと分かりやすい処理方法があるはず
     */
    private fun adapterToView(plans: ArrayList<Plan>){
        //  お気に入り管理リストでONになっているPlanのみをアダプターに渡す
        val fss = FavoriteManager()
        val favoriteList = fss.loadFavoriteList(this, plans.size)

        // お気に入りリストが空の場合，トップに表示する画像を切り替える
        if (favoriteList.size != 0){
            // お気に入りリストに追加されている企画のみをViewへ送る
            val favoritePlans = ArrayList<Plan>()
            for (value in favoriteList){
                favoritePlans.add(plans[value])
            }

            // Viewにデータを挿入する
            val expListView = findViewById<ExpandableListView>(R.id.expList)
            val adapter = AccordionAdapter(this, favoritePlans)

            expListView.setAdapter(adapter)
        } else{
            val favoritePageTopView = findViewById<ImageView>(R.id.favoritePageTopView)
            favoritePageTopView.setImageResource(R.drawable.no_favorite_top_image)
        }
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
