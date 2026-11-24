package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasPengaturanNotifikasiBinding

/**
 * AktivitasPengaturanNotifikasi mengelola halaman hub pengaturan notifikasi Planify.
 */
class AktivitasPengaturanNotifikasi : AppCompatActivity() {
    private lateinit var binding: AktivitasPengaturanNotifikasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasPengaturanNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainPengaturanNotifikasi) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliPengaturanNotifikasi.setOnClickListener {
            finish()
        }

        // Kartu Notifikasi Jadwal -> Membuka AktivitasNotifikasiJadwal
        binding.kartuNotifikasiJadwal.setOnClickListener {
            val intent = Intent(this, AktivitasNotifikasiJadwal::class.java)
            startActivity(intent)
        }

        // Kartu Notifikasi Kegiatan -> Membuka AktivitasNotifikasiKegiatan
        binding.kartuNotifikasiKegiatan.setOnClickListener {
            val intent = Intent(this, AktivitasNotifikasiKegiatan::class.java)
            startActivity(intent)
        }
    }
}
