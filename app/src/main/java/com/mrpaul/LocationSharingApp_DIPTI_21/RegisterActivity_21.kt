package com.mrpaul.LocationSharingApp_DIPTI_21

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrpaul.LocationSharingApp_DIPTI_21.ViewModel.AuthenticationViewModel
import com.mrpaul.LocationSharingApp_DIPTI_21.ViewModel.FireStoreViewModel
import com.mrpaul.LocationSharingApp_DIPTI_21.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var firestoreViewModel: FireStoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FireStoreViewModel::class.java)

        binding.registerBtn.setOnClickListener {
            val name = binding.displayNameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val confirmPassword = binding.conPasswordEt.text.toString()
            val location = "Don't found any location yet"
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                    .show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            } else {
                authenticationViewModel.register(email, password, {
                    firestoreViewModel.saveUser(this, authenticationViewModel.getCurrentUserId(), name, email, location)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
            }
        }
        binding.loginTxt.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            finish()
        }
    }
}