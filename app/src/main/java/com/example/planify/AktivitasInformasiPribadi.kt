package com.example.planify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasInformasiPribadiBinding

/**
 * AktivitasInformasiPribadi mengelola halaman Informasi Pribadi Planify dengan pilihan status interaktif.
 */
class AktivitasInformasiPribadi : AppCompatActivity() {
    private lateinit var binding: AktivitasInformasiPribadiBinding
    private var statusTerbuka = false
    private var statusPilihanAktif: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasInformasiPribadiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainInformasiPribadi) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliInformasi.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Tombol Kamera Profil
        binding.tombolKameraProfil.setOnClickListener {
            Toast.makeText(this, "Ubah foto profil", Toast.LENGTH_SHORT).show()
        }

        // Kotak Dropdown Status
        binding.layoutDropdownStatus.setOnClickListener {
            toggleStatusDropdown()
        }

        // Pilihan Status
        binding.opsiPelajar.setOnClickListener { pilihStatus("Pelajar") }
        binding.opsiPekerja.setOnClickListener { pilihStatus("Pekerja") }
        binding.opsiIrt.setOnClickListener { pilihStatus("Ibu Rumah Tangga") }
        binding.opsiLainnya.setOnClickListener { pilihStatus("Lainnya") }

        // Tombol Simpan Perubahan
        binding.tombolSimpanPerubahan.setOnClickListener {
            val nama = binding.etNamaLengkap.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()

            when {
                nama.isEmpty() -> {
                    binding.etNamaLengkap.error = "Nama lengkap harus diisi"
                    binding.etNamaLengkap.requestFocus()
                }
                email.isEmpty() -> {
                    binding.etEmail.error = "Email harus diisi"
                    binding.etEmail.requestFocus()
                }
                else -> {
                    Toast.makeText(this, "Perubahan berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun toggleStatusDropdown() {
        statusTerbuka = !statusTerbuka
        if (statusTerbuka) {
            binding.kartuDaftarStatus.visibility = View.VISIBLE
            binding.layoutDropdownStatus.setBackgroundResource(R.drawable.latar_kotak_input_aktif)
            binding.ikonPanahStatus.setImageResource(R.drawable.ic_chevron_up)
        } else {
            tutupStatusDropdown()
        }
    }

    private fun tutupStatusDropdown() {
        statusTerbuka = false
        binding.kartuDaftarStatus.visibility = View.GONE
        binding.layoutDropdownStatus.setBackgroundResource(R.drawable.latar_kotak_input)
        binding.ikonPanahStatus.setImageResource(R.drawable.ic_chevron_down)
    }

    private fun pilihStatus(namaStatus: String) {
        statusPilihanAktif = namaStatus
        binding.teksStatusTerpilih.text = namaStatus
        binding.teksStatusTerpilih.setTextColor(android.graphics.Color.parseColor("#172443"))

        // Perbarui tampilan centang pada opsi
        perbaruiStatusOpsi()
        tutupStatusDropdown()
    }

    private fun perbaruiStatusOpsi() {
        val opsi = listOf(
            Triple(binding.opsiPelajar, binding.centangPelajar, "Pelajar"),
            Triple(binding.opsiPekerja, binding.centangPekerja, "Pekerja"),
            Triple(binding.opsiIrt, binding.centangIrt, "Ibu Rumah Tangga"),
            Triple(binding.opsiLainnya, binding.centangLainnya, "Lainnya")
        )

        for ((layout, centang, nama) in opsi) {
            if (nama == statusPilihanAktif) {
                layout.setBackgroundResource(R.drawable.latar_opsi_terpilih)
                centang.visibility = View.VISIBLE
            } else {
                layout.setBackgroundResource(R.drawable.latar_opsi_normal)
                centang.visibility = View.GONE
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (statusTerbuka) {
            tutupStatusDropdown()
        } else {
            super.onBackPressed()
        }
    }
}
