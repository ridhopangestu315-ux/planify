package com.example.planify

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasBahasaBinding

/**
 * AktivitasBahasa mengelola halaman Preferensi Bahasa Planify.
 */
class AktivitasBahasa : AppCompatActivity() {
    private lateinit var binding: AktivitasBahasaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasBahasaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainBahasa) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliBahasa.setOnClickListener {
            finish()
        }

        // Set default terpilih: Bahasa Indonesia
        aturPilihanBahasa("id")

        binding.layoutOpsiId.setOnClickListener {
            aturPilihanBahasa("id")
        }
        binding.layoutOpsiEn.setOnClickListener {
            aturPilihanBahasa("en")
        }
    }

    private fun aturPilihanBahasa(pilihan: String) {
        val opsiId = binding.layoutOpsiId
        val opsiEn = binding.layoutOpsiEn

        val centangId = binding.ikonCentangId
        val centangEn = binding.ikonCentangEn

        // Reset semua ke normal
        opsiId.setBackgroundResource(R.drawable.latar_opsi_normal)
        opsiEn.setBackgroundResource(R.drawable.latar_opsi_normal)

        centangId.visibility = View.GONE
        centangEn.visibility = View.GONE

        // Aktifkan pilihan terpilih
        when (pilihan) {
            "id" -> {
                opsiId.setBackgroundResource(R.drawable.latar_opsi_terpilih)
                centangId.visibility = View.VISIBLE
            }
            "en" -> {
                opsiEn.setBackgroundResource(R.drawable.latar_opsi_terpilih)
                centangEn.visibility = View.VISIBLE
            }
        }
    }
}
