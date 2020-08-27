package com.example.coronahelp

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.coronahelp.utils.Parameters
import com.example.coronahelp.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val SHARED_PREF = "SHARED_PREF"

    companion object {
        @JvmStatic var preferences: SharedPreferences? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        preferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            //TODO show error
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(
                this, Parameters.LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
    }
}
