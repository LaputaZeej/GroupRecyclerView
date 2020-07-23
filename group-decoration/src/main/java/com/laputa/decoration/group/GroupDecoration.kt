package com.laputa.decoration.group

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.absoluteValue

class GroupDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    private val groupHeight: Float
    private val textPaint: Paint
    private val textRect: Rect

    init {
        groupHeight = dp2px(context, 100f)
        textPaint = Paint().apply {
            textSize = 50F
            color = Color.RED
            isAntiAlias = true
            isDither = true
        }
        textRect = Rect()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        debug { log { "GroupDecoration # onDraw" } }
        parent.adapter?.checkAdapter {
            // 当前屏幕的item个数
            val childCount = parent.childCount
            debug { log { "childCount = $childCount" } }
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            // 遍历每一个可见的ItemView
            (0 until childCount).forEach { index ->
                val childView = parent.getChildAt(index)
                val childAdapterPosition = parent.getChildAdapterPosition(childView)
                val group = it.isGroup(childAdapterPosition)
                if (childView.top < 0 && childView.top.absoluteValue <= childView.height) {
                    return@forEach
                }
                if (group) {
                    // 画群组名称
                    textPaint.color = Color.BLACK
                    c.drawRect(
                        left.toFloat(),
                        childView.top - groupHeight,
                        right.toFloat(),
                        childView.top.toFloat(),
                        textPaint
                    )
                    textPaint.color = Color.WHITE
                    val groupName = it.groupName(childAdapterPosition)
                    textPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                    c.drawText(
                        groupName,
                        left.toFloat(), // 文字绘制 baseline x
                        childView.top - groupHeight / 2 + (textPaint.descent()), // 文字绘制 baseLine y
                        textPaint
                    )
                    debug {
                        textPaint.color = Color.GRAY
                        c.drawLine(
                            left.toFloat(),
                            childView.top / 2.toFloat(),
                            right.toFloat(),
                            childView.top / 2.toFloat(),
                            textPaint
                        )
                        c.drawLine(
                            left.toFloat(),
                            childView.top - groupHeight / 2 + (textPaint.descent()),
                            right.toFloat(),
                            childView.top - groupHeight / 2 + (textPaint.descent()),
                            textPaint
                        )
                    }
                    debug { log { "childView.top - ${childView.hashCode()} ${childView.top} 高：${childView.height}" } }
                    //debug { log { "childView.top2 -  ${childView} ${childView.top - groupHeight / 2 + (textPaint.descent())}" } }
                } else {
                    textPaint.color = Color.GREEN
                    // 普通分割线
                    c.drawRect(
                        left.toFloat(),
                        (childView.top - 1).toFloat(),
                        right.toFloat(),
                        childView.top.toFloat(),
                        textPaint
                    )
                }
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        debug {
            log { "GroupDecoration # onDrawOver " }
        }
        parent.adapter?.checkAdapter {adapter->
            val layoutManager = parent.layoutManager as?LinearLayoutManager
            layoutManager?.let {
                // 可见区域内的第一个item位置
                val firstVisibleItemPosition = it.findFirstVisibleItemPosition()
                debug {
                    log { "firstVisibleItemPosition = $firstVisibleItemPosition" }
                }
                // 获取对应位置的ViewHolder#itemView
                val itemView =parent.findViewHolderForAdapterPosition(firstVisibleItemPosition)?.itemView
                itemView?.let {
                    view->
                    val left = parent.paddingLeft
                    val right = parent.width - parent.paddingRight
                    val top = parent.paddingTop
                    val isGroup: Boolean = adapter.isGroup(firstVisibleItemPosition+1)
                    if (isGroup){

                    }else{
                        textPaint.color = Color.BLACK
                        c.drawRect(left.toFloat(),top.toFloat(),right.toFloat(),top+groupHeight,textPaint)
                        textPaint.color = Color.WHITE
                        val groupName = adapter.groupName(firstVisibleItemPosition)
                        textPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                        c.drawText(
                            groupName,
                            left.toFloat(), // 文字绘制 baseline x
                            itemView.top- groupHeight / 2 + (textPaint.descent()), // 文字绘制 baseLine y
                            textPaint
                        )
                    }
                }
            }

        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.checkAdapter {
            val childLayoutPosition = parent.getChildLayoutPosition(view)
            if (it.isGroup(childLayoutPosition)) {
                outRect.set(0, groupHeight.toInt(), 0, 0)

            } else {
                outRect.set(0, 1, 0, 0)
            }
        }
    }

    private inline fun RecyclerView.Adapter<*>.checkAdapter(block: (GroupItemAdapter<*>) -> Unit) {
        when (this) {
            is GroupItemAdapter<*> -> {
                block(this)
            }
            else -> {
                throw IllegalStateException("Decoration不匹配，请使用合适的Decoration")
            }
        }
    }

    companion object {
        var DEBUG = true

        fun setDebug(debug: Boolean) {
            DEBUG = debug
        }

        private inline fun debug(block: () -> Unit) {
            if (DEBUG) {
                block()
            }
        }

        private fun dp2px(context: Context, dp: Float) =
            (context.resources.displayMetrics.density * dp + 0.5).toFloat()
    }
}