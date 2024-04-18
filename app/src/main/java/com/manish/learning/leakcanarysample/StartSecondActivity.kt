package com.manish.learning.leakcanarysample

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class StartSecondActivity : ActivityResultContract<Unit?, Unit?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, SecondActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Unit? {
        // Handle the result if needed
        return null
    }
}
