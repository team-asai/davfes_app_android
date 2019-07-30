package quizgame.test.com.myapp_test13.Activities

import android.app.usage.EventStats
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import com.squareup.picasso.Picasso
import quizgame.test.com.myapp_test13.R

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        supportActionBar?.title = "ステージイベント"

        var eventImageView : ImageView = findViewById(R.id.imageEvent)

        val shardPreferences = getSharedPreferences("CommonData" , Context.MODE_PRIVATE)
        val serverName = shardPreferences.getString("ServerName", "")
        val url = serverName + "api/images/others/stageevent.jpg"
        Picasso.get().load(url).into(eventImageView)
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
