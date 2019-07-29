package quizgame.test.com.myapp_test13.DataClasses

/**
 * 企画（子供制作教室など）がもっている情報（タイトル，内容など）のデータクラス
 */
data class Plan(
        var elementId: Int,
        var titleText: String,
        var targetText: String,
        var contentText: String,
        var implementTimeText: String,
        var timeRequiredText: String,
        var floorText:String,
        var placeText: String,
        var roomText :String,
        var admission: String,
        var imageUrl: String
)