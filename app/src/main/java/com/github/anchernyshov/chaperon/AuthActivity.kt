package com.github.anchernyshov.chaperon

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.github.anchernyshov.chaperon.viewmodel.AuthActivityViewModel

class AuthActivity : AppCompatActivity() {

    val viewModel: AuthActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progressBar : ProgressBar = findViewById(R.id.activity_auth_loading_pb)
        val loginButton : Button = findViewById(R.id.activity_auth_login_btn)
        val usernameEditText : EditText = findViewById(R.id.activity_auth_username_txt)
        val passwordEditText : EditText = findViewById(R.id.activity_auth_password_txt)

        usernameEditText.addTextChangedListener((object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setUsername(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }))

        passwordEditText.addTextChangedListener((object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setPassword(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }))

        viewModel.isLoginButtonEnabled.observe(this) { flag ->
            loginButton.isEnabled = flag
        }

        viewModel.isProgressBarVisible.observe(this) { flag ->
            progressBar.isVisible = flag
        }

        loginButton.setOnClickListener {
            viewModel.onLoginButtonClicked()
        }
    }
}