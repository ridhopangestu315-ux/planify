package com.example.planify

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasVerifikasiOtpBinding

/**
 * AktivitasVerifikasiOtp mengelola tampilan halaman Verifikasi Kode OTP Planify.
 */
class AktivitasVerifikasiOtp : AppCompatActivity() {
    private lateinit var binding: AktivitasVerifikasiOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasVerifikasiOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainVerifikasiOtp) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliOtp.setOnClickListener {
            finish()
        }

        // Tombol Kirim Ulang
        binding.teksKirimUlang.setOnClickListener {
            Toast.makeText(this, "Kode verifikasi baru telah dikirim", Toast.LENGTH_SHORT).show()
        }

        // Tombol Lanjutkan (nonaktif secara visual sesuai ketentuan desain)
        binding.tombolLanjutkan.setOnClickListener {
            Toast.makeText(this, "Verifikasi kode", Toast.LENGTH_SHORT).show()
        }
    }
}
