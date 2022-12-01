package com.asimodabas.instagram_clone.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asimodabas.instagram_clone.databinding.FragmentRegisterBinding
import com.asimodabas.instagram_clone.view.activity.SecondActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        binding.fcreateButton.setOnClickListener {
            signUpClicked()
        }
    }

    fun signUpClicked() {

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(requireContext(), "Lütfen Tüm Boşlukları Doldurunuz", Toast.LENGTH_LONG)
                .show()
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                //Success
                activity?.let {
                    val intent = Intent(it, SecondActivity::class.java)
                    it.startActivity(intent)
                    it.finish()
                }
            }.addOnFailureListener {
                //Failed
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}