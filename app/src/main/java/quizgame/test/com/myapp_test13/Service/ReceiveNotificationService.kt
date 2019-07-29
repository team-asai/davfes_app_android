package quizgame.test.com.myapp_test13.Service

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import quizgame.test.com.myapp_test13.Activities.DialogActivity

class ReceiveNotificationService: FirebaseMessagingService() {

    // ログ出力用のTAG
    private val TAG = "テスト"

    private val fixedPhraseTitle = "ダヴィンチ祭2018"
    private val fixedPhraseMessage = "引き続きダヴィンチ祭をお楽しみください"

    // onMessageReceived
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        // Intentの生成
        var intent: Intent = Intent(applicationContext, DialogActivity::class.java)

        // スタックにタスクが存在しても新しいタスクとして起動する設定
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        // 通知内のタイトルとメッセージを格納する変数
        var title: String
        var message: String

        // 通知タイトル内容の決定
        if (remoteMessage!!.notification!!.title != null) {
            title = remoteMessage!!.notification!!.title!!
        } else {
            title = fixedPhraseTitle
        }

        // 通知メッセージ内容の決定
        if (remoteMessage!!.notification!!.body != null) {
            message = remoteMessage!!.notification!!.body!!
        } else {
            message = fixedPhraseMessage
        }

        //intentに情報の付加
        intent.putExtra("title", title)
        intent.putExtra("message", message)

        // Activityの起動
        startActivity(intent)
    }
}