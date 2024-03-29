package quizgame.test.com.myapp_test13.Activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import com.squareup.picasso.Picasso
import quizgame.test.com.myapp_test13.R

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        var infoImageView : ImageView = findViewById(R.id.imageInfo)

        val shardPreferences = getSharedPreferences("CommonData" , Context.MODE_PRIVATE)
        val serverName = shardPreferences.getString("ServerName", "")
        val url = serverName + "api/images/others/info.jpg"

        Picasso.get().load(url).into(infoImageView)

        // get reference to ImageView
        val webButton = findViewById(R.id.webButton) as ImageView
        // set on-click listener for ImageView
        webButton.setOnClickListener {
            val urls = "https://www.davinci-fest.net"
            val uris = Uri.parse(urls)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            startActivity(intents)
        }

        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        supportActionBar?.title = "Info"
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
