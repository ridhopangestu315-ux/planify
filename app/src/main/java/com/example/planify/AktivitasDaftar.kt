package com.example.planify

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasDaftarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * AktivitasDaftar mengelola halaman Pendaftaran Akun (Register) menggunakan XML Layout dan PengelolaAutentikasi.
 */
class AktivitasDaftar : AppCompatActivity() {
    private lateinit var binding: AktivitasDaftarBinding
    private val pengelolaAutentikasi = PengelolaAutentikasi()
    private var sandiTerlihat = false
    private var konfirmasiSandiTerlihat = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toggle password visibility
        binding.btnTogglePasswordDaftar.setOnClickListener {
            sandiTerlihat = !sandiTerlihat
            if (sandiTerlihat) {
                binding.etPasswordDaftar.transformationMethod = null
                binding.btnTogglePasswordDaftar.setImageResource(R.drawable.ic_visibility)
            } else {
                binding.etPasswordDaftar.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnTogglePasswordDaftar.setImageResource(R.drawable.ic_visibility_off)
            }
            binding.etPasswordDaftar.setSelection(binding.etPasswordDaftar.text.length)
        }

        // Toggle confirm password visibility
        binding.btnToggleConfirmPasswordDaftar.setOnClickListener {
            konfirmasiSandiTerlihat = !konfirmasiSandiTerlihat
            if (konfirmasiSandiTerlihat) {
                binding.etConfirmPasswordDaftar.transformationMethod = null
                binding.btnToggleConfirmPasswordDaftar.setImageResource(R.drawable.ic_visibility)
            } else {
                binding.etConfirmPasswordDaftar.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnToggleConfirmPasswordDaftar.setImageResource(R.drawable.ic_visibility_off)
            }
            binding.etConfirmPasswordDaftar.setSelection(binding.etConfirmPasswordDaftar.text.length)
        }

        // Tombol Daftar
        binding.btnDaftar.setOnClickListener {
            val namaLengkap = binding.etNamaLengkap.text.toString().trim()
            val alamatEmail = binding.etEmailDaftar.text.toString().trim()
            val kataSandi = binding.etPasswordDaftar.text.toString()
            val konfirmasiKataSandi = binding.etConfirmPasswordDaftar.text.toString()

            binding.teksPesanKesalahanDaftar.visibility = View.GONE
            binding.teksPesanBerhasilDaftar.visibility = View.GONE

            when {
                namaLengkap.isEmpty() -> {
                    tampilkanPesanKesalahan("Nama lengkap tidak boleh kosong")
                }
                alamatEmail.isEmpty() -> {
                    tampilkanPesanKesalahan("Email tidak boleh kosong")
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(alamatEmail).matches() -> {
                    tampilkanPesanKesalahan("Format email tidak valid")
                }
                kataSandi.length < 6 -> {
                    tampilkanPesanKesalahan("Password minimal 6 karakter")
                }
                kataSandi != konfirmasiKataSandi -> {
                    tampilkanPesanKesalahan("Password dan konfirmasi password tidak sama")
                }
                else -> {
                    aturStatusMemuat(true)
                    CoroutineScope(Dispatchers.IO).launch {
                        val hasil = pengelolaAutentikasi.daftarkanPengguna(namaLengkap, alamatEmail, kataSandi)
                        withContext(Dispatchers.Main) {
                            aturStatusMemuat(false)
                            if (hasil.isSuccess) {
                                binding.teksPesanBerhasilDaftar.text = "Akun berhasil dibuat! Silakan masuk."
                                binding.teksPesanBerhasilDaftar.visibility = View.VISIBLE
                                Toast.makeText(this@AktivitasDaftar, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()

                                // Kembali ke halaman Login setelah 1.5 detik
                                binding.root.postDelayed({
                                    finish()
                                }, 1500L)
                            } else {
                                tampilkanPesanKesalahan(hasil.exceptionOrNull()?.localizedMessage ?: "Registrasi gagal. Silakan coba lagi.")
                            }
                        }
                    }
                }
            }
        }

        // Tombol kembali ke Login
        binding.tvMasukSekarang.setOnClickListener {
            finish()
        }
    }

    private fun tampilkanPesanKesalahan(pesan: String) {
        binding.teksPesanKesalahanDaftar.text = pesan
        binding.teksPesanKesalahanDaftar.visibility = View.VISIBLE
    }

    private fun aturStatusMemuat(sedangMemuat: Boolean) {
        if (sedangMemuat) {
            binding.btnDaftar.text = ""
            binding.btnDaftar.isEnabled = false
            binding.progressBarDaftar.visibility = View.VISIBLE
        } else {
            binding.btnDaftar.text = "Daftar"
            binding.btnDaftar.isEnabled = true
            binding.progressBarDaftar.visibility = View.GONE
        }
    }
}
