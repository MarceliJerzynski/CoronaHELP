package com.example.coronahelp.utils

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.coronahelp.MapsActivity

object ScreenUtils {

    fun showMessage(activity: Activity, text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    fun showMissingPermissionError(supportFragmentManager: FragmentManager) {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(supportFragmentManager, "dialog")
    }
}