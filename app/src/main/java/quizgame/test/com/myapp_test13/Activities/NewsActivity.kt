package quizgame.test.com.myapp_test13.Activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ListView
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONArray
import org.json.JSONObject
import quizgame.test.com.myapp_test13.Adapters.NewsAdapter
import quizgame.test.com.myapp_test13.Adapters.NewsItem
import quizgame.test.com.myapp_test13.R

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        var lv = findViewById<ListView>(R.id.listView)

        var newsArray = JSONArray()

        var listArray = ArrayList<NewsItem>()

        val shardPreferences = getSharedPreferences("CommonData" , Context.MODE_PRIVATE)
        val serverName = shardPreferences.getString("ServerName", "")
        val url = serverName + "api/resources/news"

        url.httpGet().responseJson { request, response, result ->
            when (result) {
            // ステータスコード 2xx
                is Result.Success -> {
                    Log.d("Fuel", "ニュース情報の取得に成功しました")
                    Log.d("Fuel", response.toString())
                    val json = result.value.obj()
                    newsArray = json.get("お知らせ") as JSONArray
//                    Log.d("newsArray", newsArray.toString())
                    for (i in 0..newsArray.length() - 1) {
                        var news = newsArray[i] as JSONObject
                        var newsTitle = news["title"]
                        var newsTime = news["time"]
                        var newsId = news["id"]
                        var newsIcon = news["icon"]
                        listArray.add(0, NewsItem(newsId as Int, newsTitle as String, newsTime as String, newsIcon as String))
                    }
                    var adapter = NewsAdapter(this, listArray)
                    lv.adapter = adapter
                }
            // ステータスコード 2xx以外
                is Result.Failure -> {
                    // エラー処理
                    Log.d("Fuel", "ニュース情報の取得に失敗しました")
                    val ex = result.getException()
                    // エラー内容をログに出力
                    Log.d("FuelError", ex.toString())
                }
            }
        }
        // 項目をタップしたときの処理
        lv.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, NewsDetailActivity::class.java)
            val news: JSONObject = newsArray[newsArray.length() - position - 1] as JSONObject
            val id: Int = news["id"] as Int
            val title: String = news["title"] as String
            val time: String = news["time"] as String
            val picture: String = news["picture"] as String
            val content: String = news["content"] as String

            intent.putExtra("id", id.toString())
            intent.putExtra("title", title)
            intent.putExtra("time", time)
            intent.putExtra("picture", picture)
            intent.putExtra("content", content)
            startActivity(intent)
        }

        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        supportActionBar?.title = "お知らせ"
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
