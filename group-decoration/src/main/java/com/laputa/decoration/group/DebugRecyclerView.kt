package com.laputa.decoration.group

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class DebugRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    override fun draw(c: Canvas?) {
        super.draw(c)
        log {
            "RecyclerView # draw"
        }
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        log {
            "RecyclerView # onDraw"
        }
    }
}