package quizgame.test.com.myapp_test13.Managers

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
//            Log.d("pranJson",planJson.toString())

            val plan = Plan(
                    elementId = planJson["id"] as Int,
                    titleText = if (planJson["name"]!=0) planJson["name"] as String else "",
                    targetText = if (planJson["target"]!=0) planJson["target"] as String else "",
                    contentText = if (planJson["contents"]!=0) planJson["contents"] as String else "",
                    implementTimeText = if (planJson["time"]!=0) planJson["time"] as String else "",
                    timeRequiredText = if (planJson["timeReq"]!=0) planJson["timeReq"] as String else "",
                    floorText = if (spot["floor"]!=0) spot["floor"] as String else "",
                    placeText = if (spot["place"]!=0) spot["place"] as String else "",
                    roomText = if (spot["room"]!=0) spot["room"] as String else "",
                    admission = if (planJson["reserveInfo"]!=0) planJson["reserveInfo"] as String else "",
                    imageUrl = if (planJson["imgUrl"]!=0) planJson["imgUrl"] as String else ""
            )
            plans.add(plan)
        }
        return plans
    }
}