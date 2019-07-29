package quizgame.test.com.myapp_test13.Managers

import android.content.Context
import android.widget.ImageButton
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.firebase.iid.FirebaseInstanceId
import quizgame.test.com.myapp_test13.R

/**
 * お気に入りリスト(SharedPreferences)を管理するクラス
 **/
class FavoriteManager{

    fun updateFavoriteList(context: Context, planId: Int, isFavorite: Boolean) {

        val shardPreferences =
                context.getSharedPreferences("favoriteList" ,Context.MODE_PRIVATE)
        val shardPrefEditor = shardPreferences.edit()
        shardPrefEditor.putBoolean(planId.toString(), isFavorite)
        shardPrefEditor.apply()
//        postFavoriteList(context, 100)
    }

    // リストの読み込み
    fun loadFavoriteList(context: Context, arraySize: Int): ArrayList<Int> {

        val shardPreferences =
                context.getSharedPreferences("favoriteList", Context.MODE_PRIVATE)

        val arrayList : ArrayList<Int> = ArrayList()

        for (i in 0..arraySize) {
            if (shardPreferences.getBoolean(i.toString(), false)) {
                arrayList.add(i)
            }
        }

        return arrayList
    }

//    fun postFavoriteList(context: Context, arraySize: Int){
//        // post用のurlの指定
//        val url_post = "https://mini.puc.pu-toyama.ac.jp/davfes_app/api/post/favorite"
//        // デバイストークンの取得
//        val deviceToken = FirebaseInstanceId.getInstance().getToken()
//        val sharedPreferences = context.getSharedPreferences("favoriteList", Context.MODE_PRIVATE)
//
//        var favoStr = ""
//
//        for (i in 0 until arraySize) {
//            if (sharedPreferences.getBoolean(i.toString(), false)) {
//                favoStr+=(i+1).toString()+":"
//            }
//        }
//
//        // パラメータ付きのURLの生成
//        var url = url_post +
//                "?os=Android" +
//                "&deviceToken=" + deviceToken +
//                "&favoIdList=" + favoStr
//
//        // Post
//        url.httpPost().responseJson { request, response, result ->
//            when (result) {
//                is Result.Success -> {
////                    Log.d("result", response.toString())
//                }
//                is Result.Failure -> {
//                    // エラー処理
//                }
//            }
//        }
//
//    }

    fun changeFavoriteButton(context :Context, favoriteButton: ImageButton, elementId: Int){

        val shardPreferences =
                context.getSharedPreferences("favoriteList", Context.MODE_PRIVATE)

        val favoriteListNumber = elementId - 1

        val heartImageId = if (!shardPreferences.getBoolean(favoriteListNumber.toString(), false)){
            R.drawable.heart_off_icon
        } else {
            R.drawable.heart_icon
        }

        favoriteButton.setImageResource(heartImageId)

        // ImageButtonが押されたときにお気に入り管理リストの中身を変更
        favoriteButton.setOnClickListener {
            if (!shardPreferences.getBoolean(favoriteListNumber.toString(), false)) {
                favoriteButton.setImageResource(R.drawable.heart_icon)
//                Log.d("heart : ","on")
//                Log.d("favoriteId : ", favoriteListNumber.toString())
                updateFavoriteList(context, favoriteListNumber, true)
            } else {
                favoriteButton.setImageResource(R.drawable.heart_off_icon)
//                Log.d("heart : ","off")
//                Log.d("favoriteId : ", favoriteListNumber.toString())
                updateFavoriteList(context, favoriteListNumber, false)
            }
//            Log.d("isFavorite : ", shardPreferences.getBoolean(favoriteListNumber.toString(), false).toString())
        }
    }
}