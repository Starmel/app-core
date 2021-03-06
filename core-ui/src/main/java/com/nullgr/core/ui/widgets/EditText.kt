package com.nullgr.core.ui.widgets

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import android.util.AttributeSet
import com.nullgr.core.ui.widgets.extensions.initTypeface

/**
 * EditText - version of [AppCompatEditText] which can use custom typeface.
 * Typeface can be set by attributes
 * @attr ref com.nullgr.core.ui.R.styleable#TextView_fontPath
 */
class EditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        if (!isInEditMode) {
            initTypeface(this, context, null)
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (!isInEditMode) {
            initTypeface(this, context, attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (!isInEditMode) {
            initTypeface(this, context, attrs)
        }
    }
}