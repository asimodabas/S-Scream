package com.asimodabas.instagram_clone.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.asimodabas.instagram_clone.R
import com.asimodabas.instagram_clone.databinding.FragmentForgotPasswordBinding
import com.asimodabas.instagram_clone.view.viewmodel.ForgotPasswordViewModel

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSend.setOnClickListener {
            if (binding.editTextEMail.text.isNotEmpty()) {
                val email = binding.editTextEMail.text.toString()
                viewModel.forgotPassword(email)
                observeData()
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                Toast.makeText(
                    requireContext(), "Onay Maili Gönderildi.", Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    requireContext(), "Lütfen E-Mail adresinizi giriniz.", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun observeData() {
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(
                    requireContext(), error, Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}