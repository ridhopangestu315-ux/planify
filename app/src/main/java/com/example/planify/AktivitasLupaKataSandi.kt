package com.example.planify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasLupaKataSandiBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * AktivitasLupaKataSandi mengelola halaman pemulihan sandi menggunakan Firebase Authentication sendPasswordResetEmail().
 */
class AktivitasLupaKataSandi : AppCompatActivity() {
    private lateinit var binding: AktivitasLupaKataSandiBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasLupaKataSandiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainLupaSandi) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembali.setOnClickListener {
            finish()
        }

        // Tombol Kirim Kode / Tautan Reset
        binding.tombolKirimKode.setOnClickListener {
            val alamatEmail = binding.etEmailLupaSandi.text.toString().trim()
            binding.teksPesanLupaSandi.visibility = View.GONE

            when {
                alamatEmail.isEmpty() -> {
                    tampilkanPesan("Email harus diisi", isError = true)
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(alamatEmail).matches() -> {
                    tampilkanPesan("Format email tidak valid", isError = true)
                }
                else -> {
                    aturStatusMemuat(true)
                    auth.sendPasswordResetEmail(alamatEmail)
                        .addOnCompleteListener { task ->
                            aturStatusMemuat(false)
                            if (task.isSuccessful) {
                                tampilkanPesan("Tautan reset kata sandi telah dikirim ke email Anda.", isError = false)
                                Toast.makeText(this, "Email reset terkirim!", Toast.LENGTH_LONG).show()
                                binding.root.postDelayed({
                                    finish()
                                }, 2000L)
                            } else {
                                tampilkanPesan(task.exception?.localizedMessage ?: "Gagal mengirim email reset. Silakan coba lagi.", isError = true)
                            }
                        }
                }
            }
        }
    }

    private fun tampilkanPesan(pesan: String, isError: Boolean) {
        binding.teksPesanLupaSandi.text = pesan
        binding.teksPesanLupaSandi.setTextColor(
            if (isError) android.graphics.Color.parseColor("#DC2626")
            else android.graphics.Color.parseColor("#16A34A")
        )
        binding.teksPesanLupaSandi.visibility = View.VISIBLE
    }

    private fun aturStatusMemuat(sedangMemuat: Boolean) {
        if (sedangMemuat) {
            binding.tombolKirimKode.text = ""
            binding.tombolKirimKode.isEnabled = false
            binding.progressBarLupaSandi.visibility = View.VISIBLE
        } else {
            binding.tombolKirimKode.text = "Kirim Tautan Reset"
            binding.tombolKirimKode.isEnabled = true
            binding.progressBarLupaSandi.visibility = View.GONE
        }
    }
}
