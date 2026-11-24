package com.example.planify

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasAturUlangKataSandiBinding

/**
 * AktivitasAturUlangKataSandi mengelola tampilan halaman Atur Ulang Kata Sandi Planify.
 */
class AktivitasAturUlangKataSandi : AppCompatActivity() {
    private lateinit var binding: AktivitasAturUlangKataSandiBinding
    private var sandiBaruTerlihat = false
    private var konfirmasiSandiTerlihat = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasAturUlangKataSandiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainAturUlangSandi) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliAturUlang.setOnClickListener {
            finish()
        }

        // Toggle visibilitas sandi baru
        binding.btnToggleSandiBaru.setOnClickListener {
            sandiBaruTerlihat = !sandiBaruTerlihat
            if (sandiBaruTerlihat) {
                binding.etKataSandiBaru.transformationMethod = null
                binding.btnToggleSandiBaru.setImageResource(R.drawable.ic_visibility)
            } else {
                binding.etKataSandiBaru.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnToggleSandiBaru.setImageResource(R.drawable.ic_visibility_off)
            }
            binding.etKataSandiBaru.setSelection(binding.etKataSandiBaru.text.length)
        }

        // Toggle visibilitas konfirmasi sandi
        binding.btnToggleKonfirmasiSandi.setOnClickListener {
            konfirmasiSandiTerlihat = !konfirmasiSandiTerlihat
            if (konfirmasiSandiTerlihat) {
                binding.etKonfirmasiSandi.transformationMethod = null
                binding.btnToggleKonfirmasiSandi.setImageResource(R.drawable.ic_visibility)
            } else {
                binding.etKonfirmasiSandi.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnToggleKonfirmasiSandi.setImageResource(R.drawable.ic_visibility_off)
            }
            binding.etKonfirmasiSandi.setSelection(binding.etKonfirmasiSandi.text.length)
        }

        // Tombol Simpan Kata Sandi
        binding.tombolSimpanKataSandi.setOnClickListener {
            Toast.makeText(this, "Kata sandi berhasil disimpan!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
