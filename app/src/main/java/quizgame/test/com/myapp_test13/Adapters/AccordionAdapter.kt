package quizgame.test.com.myapp_test13.Adapters

import android.annotation.SuppressLint
import android.content.Context
//import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.ImageButton
import android.widget.TextView
import quizgame.test.com.myapp_test13.Managers.FavoriteManager
import quizgame.test.com.myapp_test13.DataClasses.Plan
import quizgame.test.com.myapp_test13.R

/**
 * 企画ごとの，個別の一覧ページ(production_detail)用のアダプターを実装したクラス
 * BaseExpandableListAdapterを継承することで，リストをタッチするとリスト下部に子要素を出現するUIを実装
 */
class AccordionAdapter(context: Context, private val plans: ArrayList<Plan>) : BaseExpandableListAdapter() {

    private val mContext = context

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private class CustomViewHolder(view: View){
        // groupView用
        val imageView = view.findViewById<ImageView>(R.id.childClassNumberImage)
        val titleText = view.findViewById<TextView>(R.id.childClassTitle)
        val targetText = view.findViewById<TextView>(R.id.targetAgeView)
        val imageButton = view.findViewById<ImageButton>(R.id.favoriteButton)

        // childView用
        val contentText = view.findViewById<TextView>(R.id.contentView)
        val implementTimeText = view.findViewById<TextView>(R.id.implementationTimeView)
        val timeRequiredText = view.findViewById<TextView>(R.id.timeRequiredView)
        val placeText = view.findViewById<TextView>(R.id.placeView)
    }

    private fun createView(parent: ViewGroup?, layoutId: Int) : View {
        val view = inflater.inflate(layoutId, parent, false)
        view.tag = CustomViewHolder(view)
        return view
    }

    /**
     * リストのViewを生成するメソッド
     */
    override fun getGroupView(
            groupPosition: Int, isExpanded: Boolean,
            convertView: View?, parent: ViewGroup?): View? {

//        Log.d("getGroupView", "リストを読み込みます")

        val view = convertView ?: createView(parent, R.layout.row_exlist_plan_detail)
        val customViewHolder = view.tag as CustomViewHolder

        val plan = getGroup(groupPosition)
        customViewHolder.titleText.text = plan.titleText
        customViewHolder.targetText.text = plan.targetText

        // 番号画像の処理
        val header = if (plan.elementId < 10){ "0" } else { "" }
        val numberStr = Integer.toString(plan.elementId)
        val name = "number_$header$numberStr"
        val url = mContext.resources.getIdentifier(name, "drawable", mContext.packageName)
        customViewHolder.imageView.setImageResource(url)

        // お気に入りボタンの処理
        FavoriteManager().changeFavoriteButton(mContext, customViewHolder.imageButton, plan.elementId)

        return view
    }

    /**
     * リスト下部に出現させるViewを生成するメソッド
     * ListViewの中にimageButtonを実装するとclickListenerが衝突し，getChildViewメソッドが呼び出されない
     * XMLファイル側で「android:descendantFocusability="blocksDescendants"」を設定する
     */
    @SuppressLint("SetTextI18n")
    override fun getChildView(
            groupPosition: Int, childPosition: Int,
            isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {


        val childView = convertView ?: createView(parent, R.layout.element_list_plan_detail)
        val customViewHolder = childView.tag as CustomViewHolder

        val plan = getChild(groupPosition, childPosition)
        customViewHolder.contentText.text = plan.contentText
        customViewHolder.implementTimeText.text = plan.implementTimeText
        customViewHolder.timeRequiredText.text = plan.timeRequiredText
        customViewHolder.placeText.text = plan.placeText + plan.roomText

        return childView
    }

    /**
     * リストに表示するアイテムを取得するメソッド
     * 型推論で返り値をPlan型に修正
     */
    override fun getGroup(groupPosition: Int): Plan {
        return plans[groupPosition]
    }

    /**
     * リスト下部に表示するアイテムを取得するメソッド
     * 型推論で返り値をPlan型に修正
     */
    override fun getChild(groupPosition: Int, childPosition: Int) = plans[groupPosition]

    /**
     * リストの数を取得するメソッド
     * 表示する企画数を返す
     */
    override fun getGroupCount() = plans.count()

    /**
     * リストの子要素の数を取得するメソッド
     * 今回は必ず一つしか表示しないためマジックナンバーを使用
     */
    override fun getChildrenCount(groupPosition: Int) = 1

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun hasStableIds() = true

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = false

}