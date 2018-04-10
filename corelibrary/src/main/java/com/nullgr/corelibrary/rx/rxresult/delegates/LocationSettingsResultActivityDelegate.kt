package com.nullgr.corelibrary.rx.rxresult.delegates

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.nullgr.corelibrary.rx.rxresult.RxResolveResultActivity

/**
 * Created by Grishko Nikita on 01.02.18.
 */
internal class LocationSettingsResultActivityDelegate(activity: Activity) : BaseResolveResultActivityDelegate(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (activity.intent.hasExtra(RxResolveResultActivity.EXTRA_KEY)) {
            val intentSender = activity.intent.extras[RxResolveResultActivity.EXTRA_KEY] as IntentSender
            activity.startIntentSenderForResult(intentSender, REQUEST_CODE, null, 0, 0, 0)
        } else {
            sendResult(Activity.RESULT_CANCELED, null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            sendResult(resultCode, data)
        }
    }
}