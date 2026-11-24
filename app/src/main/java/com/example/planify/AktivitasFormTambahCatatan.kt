package com.example.planify

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasFormTambahCatatanBinding

/**
 * AktivitasFormTambahCatatan mengelola formulir pembuatan catatan baru Planify.
 */
class AktivitasFormTambahCatatan : AppCompatActivity() {
    private lateinit var binding: AktivitasFormTambahCatatanBinding
    private var kategoriAktif = "Akademik"
    private var indeksWarnaAktif = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasFormTambahCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainFormTambahCatatan) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliFormCatatan.setOnClickListener {
            finish()
        }

        // Dropdown Kategori
        binding.layoutDropdownKategoriCatatan.setOnClickListener {
            bukaMenuKategori()
        }

        // Pemilihan Warna / Label
        val daftarKotakWarna = listOf(
            Pair(binding.kotakWarna1, binding.centangWarna1),
            Pair(binding.kotakWarna2, binding.centangWarna2),
            Pair(binding.kotakWarna3, binding.centangWarna3),
            Pair(binding.kotakWarna4, binding.centangWarna4),
            Pair(binding.kotakWarna5, binding.centangWarna5),
            Pair(binding.kotakWarna6, binding.centangWarna6)
        )

        for ((index, pair) in daftarKotakWarna.withIndex()) {
            pair.first.setOnClickListener {
                indeksWarnaAktif = index
                perbaruiTampilanWarna(daftarKotakWarna)
            }
        }
        perbaruiTampilanWarna(daftarKotakWarna)

        // Tombol Simpan Catatan
        binding.tombolSimpanCatatan.setOnClickListener {
            simpanCatatanBaru()
        }
    }

    private fun bukaMenuKategori() {
        val popup = PopupMenu(this, binding.layoutDropdownKategoriCatatan)
        popup.menu.add("Akademik")
        popup.menu.add("Tugas")
        popup.menu.add("Pribadi")
        popup.menu.add("Lainnya")

        popup.setOnMenuItemClickListener { item ->
            kategoriAktif = item.title.toString()
            binding.teksKategoriCatatanTerpilih.text = kategoriAktif
            binding.teksKategoriCatatanTerpilih.setTextColor(android.graphics.Color.parseColor("#172443"))
            true
        }
        popup.show()
    }

    private fun perbaruiTampilanWarna(daftar: List<Pair<View, View>>) {
        for ((i, pair) in daftar.withIndex()) {
            if (i == indeksWarnaAktif) {
                pair.first.setBackgroundResource(R.drawable.latar_opsi_terpilih)
                pair.second.visibility = View.VISIBLE
            } else {
                pair.first.setBackgroundResource(R.drawable.latar_kotak_input)
                pair.second.visibility = View.GONE
            }
        }
    }

    private fun simpanCatatanBaru() {
        val judul = binding.etJudulCatatan.text.toString().trim()
        val isi = binding.etIsiCatatan.text.toString().trim()
        val penting = binding.switchCatatanPenting.isChecked

        when {
            judul.isEmpty() -> {
                binding.etJudulCatatan.error = "Judul catatan harus diisi"
                binding.etJudulCatatan.requestFocus()
            }
            else -> {
                Toast.makeText(this, "Catatan '$judul' berhasil disimpan!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
