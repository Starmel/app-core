package com.nullgr.androidcore.keyboardanimator.dialog

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.view.View
import android.view.WindowManager
import com.nullgr.androidcore.R
import com.nullgr.core.ui.keyboardanimator.simple.SimpleKeyboardAnimator

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class SimpleBottomSheetDialog(context: Context, private val enableAnimation: Boolean) : BottomSheetDialog(context) {

    init {
        setContentView(R.layout.dialog_simple_bottom_sheet)
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        BottomSheetBehavior.from(bottomSheet).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
        }
        window?.let { window ->
            if (enableAnimation) {
                SimpleKeyboardAnimator(window).start()
            } else {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
        }
    }
}