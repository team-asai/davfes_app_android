package quizgame.test.com.myapp_test13.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import quizgame.test.com.myapp_test13.R


class PlanImageAdapter(private val context: Context,
                       private val imageUrlList: ArrayList<Int>,
                       private val height :Int) : BaseAdapter() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    private class CustomViewHolder(view: View){
        var image = view.findViewById<ImageView>(R.id.planImageView)
    }

    private fun createView(parent: ViewGroup?, layoutId: Int) : View {
        var view = inflater.inflate(layoutId, parent, false)
        view.tag = CustomViewHolder(view)
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView ?: createView(parent, R.layout.row_list_plan)
        var customViewHolder = view.tag as CustomViewHolder

        customViewHolder.image.setImageResource(getItem(position))
        customViewHolder.image.layoutParams.height = height

        return  view
    }

    override fun getItem(position: Int): Int {
        return imageUrlList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return imageUrlList.size
    }
}