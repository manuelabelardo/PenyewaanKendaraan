package com.test.penyewaankendaraan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, Intent: Intent) {
        val message = Intent.getStringExtra("toastMessage")
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}