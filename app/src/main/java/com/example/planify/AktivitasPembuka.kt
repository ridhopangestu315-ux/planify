package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasPembukaBinding

/**
 * AktivitasPembuka mengelola layar Splash Screen saat aplikasi pertama kali dibuka.
 */
class AktivitasPembuka : AppCompatActivity() {
    private lateinit var binding: AktivitasPembukaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasPembukaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.teksPersentase.text = "70%"

        // Simulasi penundaan 1,5 detik sebelum berpindah ke AktivitasMasuk (Login)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AktivitasMasuk::class.java)
            startActivity(intent)
            finish()
        }, 1500L)
    }
}
