package com.github.anchernyshov.chaperon.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.anchernyshov.chaperon.R
import com.github.anchernyshov.chaperon.databinding.ActivityContentBinding

class ContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.activityContentLogoutBtn.setOnClickListener {
            val sharedPreferences = getSharedPreferences("com.github.anchernyshov.chaperon", Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.remove("PREF_AUTHENTICATION_TOKEN")
            editor.apply()

            val intent = Intent(
                this@ContentActivity,
                AuthActivity::class.java
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}