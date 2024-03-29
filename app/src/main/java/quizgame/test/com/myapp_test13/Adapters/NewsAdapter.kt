package quizgame.test.com.myapp_test13.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import quizgame.test.com.myapp_test13.R

class NewsAdapter(var context: Context, var items: ArrayList<NewsItem>) : BaseAdapter() {
    val inflater: LayoutInflater //指定したレイアウトリソースを利用できる

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: createView(parent)

        val viewHolder = view.tag as NewsViewHolder

        viewHolder.title.text = items.get(position).newsTitle
        viewHolder.time.text = items.get(position).newsTime

        val icon = items[position].newsIcon
        val shardPreferences = context.getSharedPreferences("CommonData" , Context.MODE_PRIVATE)
        val serverName = shardPreferences.getString("ServerName", "")
        val url = serverName + icon
        Picasso.get().load(url).resize(150, 150).into(viewHolder.newsIcon)
        viewHolder.isImage = true


        return view as View
    }

    private fun createView(parent: ViewGroup?) : View {
        val view = inflater.inflate(R.layout.list_news_row, parent, false)
        view.tag = NewsViewHolder(view)
        return view
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    class NewsViewHolder(view: View) {
        val title = view.findViewById<TextView>(R.id.listTitle)
        val time = view.findViewById<TextView>(R.id.listTime)
        val newsIcon = view.findViewById<ImageView>(R.id.newsIcon)
        var isImage = false
    }
}