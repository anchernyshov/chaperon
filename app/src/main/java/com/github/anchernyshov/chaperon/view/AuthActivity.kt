package com.github.anchernyshov.chaperon.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.github.anchernyshov.chaperon.R
import com.github.anchernyshov.chaperon.databinding.ActivityAuthBinding
import com.github.anchernyshov.chaperon.viewmodel.AuthActivityViewModel

class AuthActivity : AppCompatActivity() {

    val viewModel: AuthActivityViewModel by viewModels()
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.activityAuthUsernameInput.addTextChangedListener((object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setUsername(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }))

        binding.activityAuthPasswordInput.addTextChangedListener((object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setPassword(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }))

        viewModel.isLoginButtonEnabled.observe(this) { flag ->
            binding.activityAuthLoginBtn.isEnabled = flag
        }

        viewModel.loginResponseLiveData.observe(this) { data ->
            data?.let {
                binding.activityAuthLoadingPb.isVisible = false
                binding.activityAuthLoginBtn.isEnabled = true
                binding.activityAuthResultTxt.text = data.token
            }
        }

        binding.activityAuthLoginBtn.setOnClickListener {
            binding.activityAuthLoadingPb.isVisible = true
            binding.activityAuthLoginBtn.isEnabled = false
            viewModel.onLoginButtonClicked()
        }

    }
}