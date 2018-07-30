package quizgame.test.com.myapp_test13.Adapters


import android.content.Context
//import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import quizgame.test.com.myapp_test13.Managers.FavoriteManager
import quizgame.test.com.myapp_test13.DataClasses.Plan
import quizgame.test.com.myapp_test13.R

class PlanAdapter(context: Context, private val plans: ArrayList<Plan>): BaseAdapter() {
    private val mContext = context

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private class CustomViewHolder(view: View){
        val imageView = view.findViewById<ImageView>(R.id.childClassNumberImage)
        val titleText = view.findViewById<TextView>(R.id.childClassTitle)
        val targetText = view.findViewById<TextView>(R.id.targetAgeView)
        val imageButton = view.findViewById<ImageButton>(R.id.favoriteButton)
    }

    private fun createView(parent: ViewGroup?, layoutId: Int) : View {
        val view = inflater.inflate(layoutId, parent, false)
        view.tag = CustomViewHolder(view)
        return view
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: createView(parent, R.layout.row_exlist_plan_detail)
        val customViewHolder = view.tag as CustomViewHolder

        val plan = getItem(position)
        customViewHolder.titleText.text = plan.titleText
        customViewHolder.targetText.text = plan.targetText

        // 番号画像の処理
        val header = if (plan.elementId < 10){ "0" } else { "" }
        val numberStr = Integer.toString(plan.elementId)
        val name = "number_$header$numberStr"
        val url = mContext.resources.getIdentifier(name, "drawable", mContext.packageName)
        customViewHolder.imageView.setImageResource(url)

        // お気に入りボタンの処理
        val fss = FavoriteManager()
        val shardPreferences =
                mContext.getSharedPreferences("favoriteList", Context.MODE_PRIVATE)

        val favoriteListNumber = plan.elementId - 1

        val heartImageId = if (!shardPreferences.getBoolean(favoriteListNumber.toString(), false)){
            R.drawable.heart_off_icon
        } else {
            R.drawable.heart_icon
        }

        customViewHolder.imageButton.setImageResource(heartImageId)

        // ImageButtonが押されたときにお気に入り管理リストの中身を変更
        customViewHolder.imageButton.setOnClickListener {
            if (!shardPreferences.getBoolean(favoriteListNumber.toString(), false)) {
                customViewHolder.imageButton.setImageResource(R.drawable.heart_icon)
//                Log.d("heart : ","on")
//                Log.d("elementId : ", favoriteListNumber.toString())
                fss.updateFavoriteList(mContext, favoriteListNumber, true)
            } else {
                customViewHolder.imageButton.setImageResource(R.drawable.heart_off_icon)
//                Log.d("heart : ","off")
//                Log.d("elementId : ", favoriteListNumber.toString())
                fss.updateFavoriteList(mContext, favoriteListNumber, false)
            }
//            Log.d("isFavorite : ", shardPreferences.getBoolean(favoriteListNumber.toString(), false).toString())
        }

        return view
    }

    override fun getItem(position: Int): Plan {
        return plans[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return plans.count()
    }

}