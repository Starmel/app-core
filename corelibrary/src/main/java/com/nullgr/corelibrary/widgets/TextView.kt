package com.nullgr.corelibrary.widgets

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.nullgr.corelibrary.R
import com.nullgr.corelibrary.fonts.init

/**
 * TextView which can use custom font
 */
class TextView : AppCompatTextView {

    private var translateToBottom: Boolean = false

    constructor(context: Context) : super(context) {
        if (!isInEditMode) {
            init(this, context, null)
            initialize(context, null)
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (!isInEditMode) {
            init(this, context, attrs)
            initialize(context, attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (!isInEditMode) {
            init(this, context, attrs)
            initialize(context, attrs)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (translateToBottom) {
            canvas?.translate(0f, paint.fontMetrics.bottom)
        }
        super.onDraw(canvas)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TextView, 0, 0)
        if (ta.hasValue(R.styleable.TextView_translateToBottom)) {
            translateToBottom = ta.hasValue(R.styleable.TextView_translateToBottom)
        }
        ta.recycle()

    }
}