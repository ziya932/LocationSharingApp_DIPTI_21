package com.mrpaul.UserProfileRegistration_DIPTI_21.ViewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnCompleteListener

class LocationViewModel: ViewModel() {
    private var fusedLocationClient: FusedLocationProviderClient? = null

    fun getLastLocation(callback: (String) -> Unit) {
        fusedLocationClient?.lastLocation
            ?.addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful && it.result != null) {
                    val lastLocation = it.result
                    val latitude = lastLocation.latitude
                    val longitude = lastLocation.longitude
                    val location = "Lat: $latitude, Long: $longitude"
                    callback(location)
                } else {
                    callback("Location not available")
                }
            })
    }

    fun initializeFusedLocationClient(client: FusedLocationProviderClient) {
        fusedLocationClient = client
    }
}
