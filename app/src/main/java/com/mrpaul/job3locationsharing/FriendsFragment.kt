package com.mrpaul.job3locationsharing

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mrpaul.job3locationsharing.Adapter.UserAdapter
import com.mrpaul.job3locationsharing.ViewModel.AuthenticationViewModel
import com.mrpaul.job3locationsharing.ViewModel.FireStoreViewModel
import com.mrpaul.job3locationsharing.ViewModel.LocationViewModel
import com.mrpaul.job3locationsharing.databinding.FragmentFriendsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FriendsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FriendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding
    private lateinit var firestoreViewModel: FireStoreViewModel
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Location Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendsBinding.inflate(inflater,container, false)

        firestoreViewModel = ViewModelProvider(this).get(FireStoreViewModel::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationViewModel.initializeFusedLocationClient(fusedLocationClient)

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Permission is already granted
            getLocation()
        }
        userAdapter = UserAdapter(emptyList())
        binding.userRV.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        fetchUsers()

        binding.locationBtn.setOnClickListener {
            startActivity(Intent(requireContext(),MapsActivity::class.java))
        }


        return binding.root
    }

    private fun fetchUsers() {
        firestoreViewModel.getAllUsers(requireContext()){
            userAdapter.updateData(it)
        }
    }

    private fun getLocation() {
        locationViewModel.getLastLocation {
            authenticationViewModel.getCurrentUserId().let { userId ->
                firestoreViewModel.updateUserLocation(requireContext(),userId, it)
            }
        }
    }

}