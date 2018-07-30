package quizgame.test.com.myapp_test13.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import quizgame.test.com.myapp_test13.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main)

        //アクションバーのタイトル
        supportActionBar?.title = "富山県立大学ダ・ヴィンチ祭"


        // Buttonの宣言
        //ここでは、お知らせに画面遷移
        val button1: Button = findViewById(R.id.News)
        // Intent作成
        val intent1 = Intent(this@MainActivity, NewsActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        button1.setOnClickListener {
            startActivity(intent1)
        }

        //ここでは、こども制作教室に画面遷移
        val button2: Button = findViewById(R.id.Workshop)
        // Intent作成
        val intent2 = Intent(this@MainActivity, WorkshopActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        button2.setOnClickListener {
            startActivity(intent2)
        }

        //ここでは、ステージイベントに画面遷移
        val button3: Button = findViewById(R.id.Event)
        // Intent作成
        val intent3 = Intent(this@MainActivity, EventActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        button3.setOnClickListener {
            startActivity(intent3)
        }

        //ここでは、マップに画面遷移
        val button4: Button = findViewById(R.id.Map)
        // Intent作成
        val intent4 = Intent(this@MainActivity, MapActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        button4.setOnClickListener {
            startActivity(intent4)
        }

        //ここでは、企画一覧に画面遷移
        val button5: Button = findViewById(R.id.Plan)
        // Intent作成
        val intent5 = Intent(this@MainActivity, PlanActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        button5.setOnClickListener {
            startActivity(intent5)
        }

        //ここでは、お気に入りに画面遷移
        val button6: Button = findViewById(R.id.Favorite)
        // Intent作成
        val intent6 = Intent(this@MainActivity, FavoriteActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        button6.setOnClickListener {
            startActivity(intent6)
        }
    }

        //onCreateOptionsMenuをoverrideするとメニュー作成時に実行されるので、ここでメニューを作成する
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.main, menu)
            return super.onCreateOptionsMenu(menu)
        }

    //アクションバーのメニューバーの設置
    //Infoに画面遷移
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itm01 -> {
                // 設定ボタン押下処理
                val intent = Intent(this@MainActivity, InfoActivity::class.java)
                startActivity(intent)
            }
            else -> {
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
