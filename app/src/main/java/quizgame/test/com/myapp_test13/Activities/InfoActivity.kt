package quizgame.test.com.myapp_test13.Activities

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import quizgame.test.com.myapp_test13.R

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        var imageInfo : ImageView = findViewById(R.id.imageInfo)
        imageInfo.setImageResource(R.drawable.info2018)

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
