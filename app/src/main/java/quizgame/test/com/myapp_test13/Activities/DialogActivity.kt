package quizgame.test.com.myapp_test13.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import quizgame.test.com.myapp_test13.R

class DialogActivity: AppCompatActivity() {

    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 遷移前の情報の受け取り
        val intent: Intent = getIntent()
        val title = intent.getStringExtra("title")
        val message = intent.getStringExtra("message")

        // 表示するダイアログの設定
        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        // タイトルとメッセージ部分の設定
        builder.setTitle(title).setMessage(message)
                // ボタンタップ時の処理
                .setNegativeButton("OK") { dialog, id ->
                    // ダイアログを閉じる
                    dialog.cancel()
                    // 本Activityを終了する
                    this.finish()
                }

        // ダイアログの生成
        var alert: AlertDialog = builder.create()

        // ダイアログの表示
        alert.show()
    }
}