package quizgame.test.com.myapp_test13.Activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import quizgame.test.com.myapp_test13.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main)
        //アクションバーのタイトル
        supportActionBar?.title = "富山県立大学ダ・ヴィンチ祭"

        val shardPreferences = getSharedPreferences("CommonData" , Context.MODE_PRIVATE)
        val shardPrefEditor = shardPreferences.edit()
        shardPrefEditor.putString("ServerName", "https://mini.puc.pu-toyama.ac.jp/davfes_app/")
        shardPrefEditor.apply()


        // Buttonの宣言
        //ここでは、お知らせに画面遷移
        val toNewsButton: ImageButton = findViewById(R.id.News)
        // Intent作成
        val intent1 = Intent(this@MainActivity, NewsActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        toNewsButton.setOnClickListener {
            startActivity(intent1)
        }

        //ここでは、こども制作教室に画面遷移
        val toWorkshopButton: ImageButton = findViewById(R.id.Workshop)
        // Intent作成
        val intent2 = Intent(this@MainActivity, WorkshopActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        toWorkshopButton.setOnClickListener {
            startActivity(intent2)
        }

        //ここでは、ステージイベントに画面遷移
        val toEventButton: ImageButton = findViewById(R.id.Event)
        // Intent作成
        val intent3 = Intent(this@MainActivity, EventActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        toEventButton.setOnClickListener {
            startActivity(intent3)
        }

        //ここでは、マップに画面遷移
        val toMapButton: ImageButton = findViewById(R.id.Map)
        // Intent作成
        val intent4 = Intent(this@MainActivity, MapActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        toMapButton.setOnClickListener {
            startActivity(intent4)
        }

        //ここでは、企画一覧に画面遷移
        val toPlanButton: ImageButton = findViewById(R.id.Plan)
        // Intent作成
        val intent5 = Intent(this@MainActivity, PlanActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        toPlanButton.setOnClickListener {
            startActivity(intent5)
        }

        //ここでは、お気に入りに画面遷移
        val toFavoButton: ImageButton = findViewById(R.id.Favorite)
        // Intent作成
        val intent6 = Intent(this@MainActivity, FavoriteActivity::class.java)
        // Button（IDがbutton）をタップされた際の処理
        toFavoButton.setOnClickListener {
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
