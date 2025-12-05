package com.cheermateapp.util

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionManager {

    private const val TAG = "PermissionManager"
    const val NOTIFICATION_PERMISSION_CODE = 101

    /**
     * Checks and requests notification permissions if needed (for Android 13+).
     * @return true if permission is already granted, false otherwise and launches request.
     */
    fun checkAndRequestNotificationPermission(
        activity: Activity,
        launcher: ActivityResultLauncher<String>
    ): Boolean {
        Log.d(TAG, "Checking Notification Permission...")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d(TAG, "Notification permission already granted.")
                    return true
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    // Explain to the user why you need the permission, then launch.
                    Log.d(TAG, "Showing rationale and launching notification permission request.")
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    return false
                }
                else -> {
                    // Directly ask for the permission.
                    Log.d(TAG, "Launching notification permission request.")
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    return false
                }
            }
        }
        Log.d(TAG, "Notification permission not required for this Android version.")
        return true // Permission is not needed for older versions.
    }

    /**
     * Checks if the app can schedule exact alarms (for Android 12+).
     * If not, it redirects the user to the system settings screen.
     * @return true if the app can schedule exact alarms, false otherwise.
     */
    fun checkAndRequestExactAlarmPermission(context: Context): Boolean {
        Log.d(TAG, "Checking Exact Alarm Permission...")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                Log.d(TAG, "App cannot schedule exact alarms. Showing rationale dialog.")

                // Show a rationale dialog before redirecting
                androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Permission Required")
                    .setMessage("To ensure task reminders work correctly, Cheermate needs you to enable the 'Alarms & reminders' permission in your system settings.\n\nTap 'Continue' to open settings.")
                    .setPositiveButton("Continue") { _, _ ->
                        Log.d(TAG, "User agreed. Redirecting to settings.")
                        Intent().also { intent ->
                            intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                            intent.data = Uri.fromParts("package", context.packageName, null)
                            context.startActivity(intent)
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()

                return false
            }
            Log.d(TAG, "App can schedule exact alarms.")
        } else {
            Log.d(TAG, "Exact alarm permission not required for this Android version.")
        }
        return true
    }
}
