package quizgame.test.com.myapp_test13.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import quizgame.test.com.myapp_test13.R

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        var titleTextView: TextView = findViewById(R.id.title)
        var timeTextView: TextView = findViewById(R.id.time)
        var pictureImageView: ImageView = findViewById(R.id.picture)
        var contentTextView: TextView = findViewById(R.id.content)

        val intent: Intent = getIntent()
        val id: String = intent.extras.get("id") as String
        val title: String = intent.extras.get("title") as String
        val time: String = intent.extras.get("time") as String
        val picture: String = intent.extras.get("picture") as String
        val content: String = intent.extras.get("content") as String

        val url = "http://ik1-307-13856.vs.sakura.ne.jp/"+picture

        Picasso.get().load(url).into(pictureImageView)



        titleTextView.text = title
        timeTextView.text = time

        contentTextView.text = content

        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        //ここでは、ページのタイトルをアクションバーのタイトルにしている
        supportActionBar?.title = title
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
