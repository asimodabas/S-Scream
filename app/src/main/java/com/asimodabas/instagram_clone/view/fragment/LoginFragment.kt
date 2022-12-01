package com.asimodabas.instagram_clone.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.asimodabas.instagram_clone.R
import com.asimodabas.instagram_clone.databinding.FragmentLoginBinding
import com.asimodabas.instagram_clone.view.activity.SecondActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            activity?.let {
                val intent = Intent(it, SecondActivity::class.java)
                it.startActivity(intent)
                it.finish()
            }
        }

        binding.signInButton.setOnClickListener {
            signInClicked()
        }

        binding.signUpButton.setOnClickListener {
            signUpClicked()
        }
    }

    fun signInClicked() {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(
                requireContext(),
                "Lütfen Kullanıcı Adı ve Şifre Giriniz!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                activity?.let {
                    val intent = Intent(it, SecondActivity::class.java)
                    it.startActivity(intent)
                    it.finish()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signUpClicked() {

        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

    }
}