package com.example.planify

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasTampilanBinding

/**
 * AktivitasTampilan mengelola halaman Preferensi Tampilan Planify.
 */
class AktivitasTampilan : AppCompatActivity() {
    private lateinit var binding: AktivitasTampilanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasTampilanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainTampilan) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliTampilan.setOnClickListener {
            finish()
        }

        // Set default terpilih: Terang
        aturPilihanTampilan("terang")

        binding.layoutOpsiTerang.setOnClickListener {
            aturPilihanTampilan("terang")
        }
        binding.layoutOpsiGelap.setOnClickListener {
            aturPilihanTampilan("gelap")
        }
        binding.layoutOpsiSistem.setOnClickListener {
            aturPilihanTampilan("sistem")
        }
    }

    private fun aturPilihanTampilan(pilihan: String) {
        val opsiTerang = binding.layoutOpsiTerang
        val opsiGelap = binding.layoutOpsiGelap
        val opsiSistem = binding.layoutOpsiSistem

        val centangTerang = binding.ikonCentangTerang
        val centangGelap = binding.ikonCentangGelap
        val centangSistem = binding.ikonCentangSistem

        // Reset semua ke normal
        opsiTerang.setBackgroundResource(R.drawable.latar_opsi_normal)
        opsiGelap.setBackgroundResource(R.drawable.latar_opsi_normal)
        opsiSistem.setBackgroundResource(R.drawable.latar_opsi_normal)

        centangTerang.visibility = View.GONE
        centangGelap.visibility = View.GONE
        centangSistem.visibility = View.GONE

        // Aktifkan pilihan terpilih
        when (pilihan) {
            "terang" -> {
                opsiTerang.setBackgroundResource(R.drawable.latar_opsi_terpilih)
                centangTerang.visibility = View.VISIBLE
            }
            "gelap" -> {
                opsiGelap.setBackgroundResource(R.drawable.latar_opsi_terpilih)
                centangGelap.visibility = View.VISIBLE
            }
            "sistem" -> {
                opsiSistem.setBackgroundResource(R.drawable.latar_opsi_terpilih)
                centangSistem.visibility = View.VISIBLE
            }
        }
    }
}
