package com.mrpaul.job3locationsharing

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.mrpaul.job3locationsharing.ViewModel.AuthenticationViewModel
import com.mrpaul.job3locationsharing.ViewModel.FireStoreViewModel
import com.mrpaul.job3locationsharing.ViewModel.LocationViewModel
import com.mrpaul.job3locationsharing.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var firestoreViewModel: FireStoreViewModel
    private lateinit var locationViewModel: LocationViewModel
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        authViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FireStoreViewModel::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)


        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))

        }

        binding.homeBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        loadUserInfo()
        binding.updateBtn.setOnClickListener {
            val newName = binding.NameEt.text.toString()
            val newLocation = binding.locationEt.text.toString()

            updateBtn(newName, newLocation)
        }

        return binding.root
    }

    private fun updateBtn(newName: String, newLocation: String) {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.uid
            firestoreViewModel.updateUser(requireContext(), userId, newName, newLocation)
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserInfo() {
        val currentUser = authViewModel.getCurrentUser()
        if(currentUser != null) {
            binding.emailEt.setText(currentUser.email)
            firestoreViewModel.getUser(requireContext(), currentUser.uid){ user ->
                if (currentUser.displayName != null) {
                    binding.NameEt.setText(currentUser.displayName)
                }
            }
        }else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }

    }
}