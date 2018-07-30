package quizgame.test.com.myapp_test13.Managers

//import android.util.Log
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONArray
import org.json.JSONObject
import quizgame.test.com.myapp_test13.DataClasses.Plan

class PlanManager {
    fun httpGetPlanJson(url: String, topKey: String, onPostExecute: (ArrayList<Plan>) -> Unit){
        url.httpGet().responseJson { _, _, result ->
            when (result) {
            // ステータスコード 2xx
                is Result.Success -> {
//                    Log.d("Fuel", "企画情報の取得に成功しました")

                    val json = result.value.obj()
                    val planArray: JSONArray = json.get(topKey) as JSONArray

//                    Log.d("planArray", planArray[0].toString())

                    val planList = makePlanList(planArray)

                    onPostExecute(planList)
                }
            // ステータスコード 2xx以外
                is Result.Failure -> {
                    // エラー処理
//                    Log.d("Fuel", "企画情報の取得に失敗しました")
                    val ex = result.getException()
                    // エラー内容をログに出力
//                    Log.d("FuelError", ex.toString())
                }
            }
        }
    }

    private fun makePlanList(planArray: JSONArray): ArrayList<Plan>{
        val plans = ArrayList<Plan>()

        for (i in 0 until planArray.length()) {
            val planJson = planArray[i] as JSONObject

            // 開催場所情報の統合
            val spot = planJson["spot"] as JSONObject

            val plan = Plan(
                    elementId = planJson["id"] as Int,
                    titleText = planJson["name"] as String,
                    targetText = planJson["target"] as String,
                    contentText = planJson["contents"] as String,
                    implementTimeText = planJson["time"] as String,
                    timeRequiredText = planJson["timeReq"] as String,
                    floorText = spot["floor"] as String,
                    placeText = spot["place"] as String,
                    roomText = spot["room"] as String,
                    admission = planJson["reserveInfo"] as String,
                    imageUrl = planJson["imgUrl"] as String)

            plans.add(plan)
        }

        return plans
    }
}