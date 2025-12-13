package com.cheermateapp.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.cheermateapp.R

object ToastManager {

    @Suppress("DEPRECATION")
    fun showCustomToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_toast, null)

        val text: TextView = layout.findViewById(R.id.custom_toast_text)
        text.text = message

        with(Toast(context)) {
            setDuration(duration)
            view = layout
            show()
        }
    }

    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        showCustomToast(context, message, duration)
    }
}