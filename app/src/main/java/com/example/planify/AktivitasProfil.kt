package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasProfilBinding

/**
 * AktivitasProfil mengelola tampilan halaman Profil Saya Planify dengan XML Layout dan ViewBinding.
 */
class AktivitasProfil : AppCompatActivity() {
    private lateinit var binding: AktivitasProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainProfil) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliProfil.setOnClickListener {
            finish()
        }

        // Tombol Keluar / Logout
        binding.tombolKeluarProfil.setOnClickListener {
            Toast.makeText(this, "Keluar akun", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Aksi klik kartu menu akun dan preferensi
        binding.kartuInformasiPribadi.setOnClickListener {
            val intent = Intent(this, AktivitasInformasiPribadi::class.java)
            startActivity(intent)
        }
        binding.kartuUbahPassword.setOnClickListener {
            Toast.makeText(this, "Ubah Password", Toast.LENGTH_SHORT).show()
        }
        binding.kartuNotifikasi.setOnClickListener {
            val intent = Intent(this, AktivitasPengaturanNotifikasi::class.java)
            startActivity(intent)
        }
        binding.kartuTampilan.setOnClickListener {
            val intent = Intent(this, AktivitasTampilan::class.java)
            startActivity(intent)
        }
        binding.kartuBahasa.setOnClickListener {
            val intent = Intent(this, AktivitasBahasa::class.java)
            startActivity(intent)
        }
    }
}
