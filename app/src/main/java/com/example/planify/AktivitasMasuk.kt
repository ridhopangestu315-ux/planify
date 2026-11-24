package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasMasukBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * AktivitasMasuk mengelola halaman Login (Masuk) dengan XML Layout dan Firebase Authentication.
 */
class AktivitasMasuk : AppCompatActivity() {
    private lateinit var binding: AktivitasMasukBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var sandiTerlihat = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toggle visibility password
        binding.btnTogglePasswordMasuk.setOnClickListener {
            sandiTerlihat = !sandiTerlihat
            if (sandiTerlihat) {
                binding.etPasswordMasuk.transformationMethod = null
                binding.btnTogglePasswordMasuk.setImageResource(R.drawable.ic_visibility)
            } else {
                binding.etPasswordMasuk.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnTogglePasswordMasuk.setImageResource(R.drawable.ic_visibility_off)
            }
            binding.etPasswordMasuk.setSelection(binding.etPasswordMasuk.text.length)
        }

        // Tombol Masuk
        binding.btnMasuk.setOnClickListener {
            val alamatEmail = binding.etEmailMasuk.text.toString().trim()
            val kataSandi = binding.etPasswordMasuk.text.toString()

            binding.teksPesanKesalahanMasuk.visibility = View.GONE

            when {
                alamatEmail.isEmpty() -> {
                    tampilkanPesanKesalahan("Email tidak boleh kosong")
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(alamatEmail).matches() -> {
                    tampilkanPesanKesalahan("Format email tidak valid")
                }
                kataSandi.isEmpty() -> {
                    tampilkanPesanKesalahan("Password tidak boleh kosong")
                }
                else -> {
                    binding.btnMasuk.isEnabled = false
                    auth.signInWithEmailAndPassword(alamatEmail, kataSandi)
                        .addOnCompleteListener(this) { task ->
                            binding.btnMasuk.isEnabled = true
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Berhasil masuk!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, AktivitasUtama::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                tampilkanPesanKesalahan(task.exception?.localizedMessage ?: "Gagal masuk. Periksa kembali email dan password.")
                            }
                        }
                }
            }
        }

        // Tombol Lupa Password
        binding.tvLupaPassword.setOnClickListener {
            val intent = Intent(this, AktivitasLupaKataSandi::class.java)
            startActivity(intent)
        }

        // Tombol ke Pendaftaran (Register)
        binding.tvDaftarSekarang.setOnClickListener {
            val intent = Intent(this, AktivitasDaftar::class.java)
            startActivity(intent)
        }
    }

    private fun tampilkanPesanKesalahan(pesan: String) {
        binding.teksPesanKesalahanMasuk.text = pesan
        binding.teksPesanKesalahanMasuk.visibility = View.VISIBLE
    }
}
