package quizgame.test.com.myapp_test13.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import quizgame.test.com.myapp_test13.R

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        //アクションバーの設置
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //アクションバーのタイトル
        supportActionBar?.title = "ステージイベント"
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
