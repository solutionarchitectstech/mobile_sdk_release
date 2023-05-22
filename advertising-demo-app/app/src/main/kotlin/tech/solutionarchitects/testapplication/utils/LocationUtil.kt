package tech.solutionarchitects.testapplication.utils

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

fun AppCompatActivity.requestLocation() {
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                Timber.d("ACCESS_FINE_LOCATION is accessed")
            }
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                Timber.d("ACCESS_COARSE_LOCATION is accessed")
            }
            else -> {
                Timber.d("No location permission provided")
            }
        }
    }

    locationPermissionRequest.launch(
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
}
