package com.asimodabas.instagram_clone.view.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.asimodabas.instagram_clone.R
import com.asimodabas.instagram_clone.databinding.BottomKvkkDialogBinding
import com.asimodabas.instagram_clone.databinding.FragmentRegisterBinding
import com.asimodabas.instagram_clone.view.activity.SecondActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

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
        binding.loginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.dateOfBirthDateText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, mYear, mMonth, mDay ->
                    val actvDate = "$mDay-${mMonth + 1}-$mYear"
                    binding.dateOfBirthDateText.text = actvDate
                }, year, month, day
            )
            datePickerDialog.show()
        }

        binding.textViewKvkkText.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                requireContext(),
                R.style.ThemeOverlay_MaterialComponents_BottomSheetDialog
            )
            val bottomSheetBinding =
                BottomKvkkDialogBinding.inflate(LayoutInflater.from(requireContext()))
            bottomSheetDialog.setContentView(bottomSheetBinding.root)
            bottomSheetDialog.show()
        }
    }

    fun signUpClicked() {

        val name = binding.nameEditText.text.toString()
        val surname = binding.surnameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val number = binding.numberEditText.text.toString()
        val dateofBirth = binding.dateOfBirthDateText.text.toString()
        val registrationTime = Timestamp.now()

        if (name.equals("") ||
            surname.equals("") ||
            email.equals("") ||
            password.equals("") ||
            number.equals("") ||
            dateofBirth.equals("")
        ) {
            Toast.makeText(requireContext(), "Lütfen Tüm Boşlukları Doldurunuz", Toast.LENGTH_LONG)
                .show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val dataMap = HashMap<String, Any>()
                    dataMap.put("name", name)
                    dataMap.put("surname", surname)
                    dataMap.put("email", email)
                    dataMap.put("password", password)
                    dataMap.put("number", number)
                    dataMap.put("dateofBirth", dateofBirth)
                    dataMap.put("registrationTime", registrationTime)

                    db.collection("User").add(dataMap).addOnSuccessListener {
                        binding.nameEditText.setText("")
                        binding.surnameEditText.setText("")
                        binding.emailEditText.setText("")
                        binding.passwordEditText.setText("")
                        binding.numberEditText.setText("")
                        binding.dateOfBirthDateText.setText("")
                        activity?.let {
                            val intent = Intent(it, SecondActivity::class.java)
                            it.startActivity(intent)
                            it.finish()
                        }

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                        binding.nameEditText.setText("")
                        binding.surnameEditText.setText("")
                        binding.emailEditText.setText("")
                        binding.passwordEditText.setText("")
                        binding.numberEditText.setText("")
                        binding.dateOfBirthDateText.setText("")
                    }
                }
        }
    }
}