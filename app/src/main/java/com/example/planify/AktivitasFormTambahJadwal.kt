package com.example.planify

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasFormTambahJadwalBinding
import java.util.Calendar

/**
 * AktivitasFormTambahJadwal mengelola formulir penambahan jadwal baru Planify.
 */
class AktivitasFormTambahJadwal : AppCompatActivity() {
    private lateinit var binding: AktivitasFormTambahJadwalBinding
    private var hariAktif = "Senin"
    private var waktuPengingatAktif = "10 menit sebelumnya"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasFormTambahJadwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainFormTambahJadwal) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliFormJadwal.setOnClickListener {
            finish()
        }

        // Pemilihan Hari
        val daftarHariPills = listOf(
            Triple(binding.pillSen, binding.teksSen, binding.centangSen),
            Triple(binding.pillSel, binding.teksSel, binding.centangSel),
            Triple(binding.pillRab, binding.teksRab, binding.centangRab),
            Triple(binding.pillKam, binding.teksKam, binding.centangKam),
            Triple(binding.pillJum, binding.teksJum, binding.centangJum),
            Triple(binding.pillSab, binding.teksSab, binding.centangSab),
            Triple(binding.pillMin, binding.teksMin, binding.centangMin)
        )
        val namaHariList = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")

        for ((index, triple) in daftarHariPills.withIndex()) {
            val nama = namaHariList[index]
            triple.first.setOnClickListener {
                hariAktif = nama
                perbaruiTampilanHari(daftarHariPills, index)
            }
        }
        perbaruiTampilanHari(daftarHariPills, 0) // Default Senin

        // Jam Mulai & Selesai
        binding.layoutJamMulai.setOnClickListener { bukaTimePicker { jam, menit ->
            val fmt = String.format("%02d:%02d", jam, menit)
            binding.teksJamMulaiTerpilih.text = fmt
            binding.teksJamMulaiTerpilih.setTextColor(android.graphics.Color.parseColor("#172443"))
        }}

        binding.layoutJamSelesai.setOnClickListener { bukaTimePicker { jam, menit ->
            val fmt = String.format("%02d:%02d", jam, menit)
            binding.teksJamSelesaiTerpilih.text = fmt
            binding.teksJamSelesaiTerpilih.setTextColor(android.graphics.Color.parseColor("#172443"))
        }}

        // Switch Pengingat
        binding.switchPengingatJadwal.setOnCheckedChangeListener { _, isChecked ->
            aturStatusPengingat(isChecked)
        }

        // Dropdown Waktu Pengingat
        binding.kartuWaktuPengingat.setOnClickListener {
            if (binding.switchPengingatJadwal.isChecked) {
                bukaMenuWaktuPengingat()
            }
        }

        // Tombol Simpan Jadwal
        binding.tombolSimpanJadwal.setOnClickListener {
            simpanJadwalBaru()
        }
    }

    private fun perbaruiTampilanHari(daftar: List<Triple<View, android.widget.TextView, View>>, indeksAktif: Int) {
        for ((i, triple) in daftar.withIndex()) {
            if (i == indeksAktif) {
                triple.first.setBackgroundResource(R.drawable.latar_hari_jadwal_aktif)
                triple.second.setTextColor(android.graphics.Color.parseColor("#3B82F6"))
                triple.third.visibility = View.VISIBLE
            } else {
                triple.first.setBackgroundResource(R.drawable.latar_hari_jadwal_normal)
                triple.second.setTextColor(android.graphics.Color.parseColor("#172443"))
                triple.third.visibility = View.GONE
            }
        }
    }

    private fun bukaTimePicker(onPilih: (Int, Int) -> Unit) {
        val c = Calendar.getInstance()
        TimePickerDialog(
            this,
            { _, h, m -> onPilih(h, m) },
            c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun aturStatusPengingat(aktif: Boolean) {
        val alpha = if (aktif) 1.0f else 0.4f
        binding.kartuWaktuPengingat.isEnabled = aktif
        binding.kartuWaktuPengingat.alpha = alpha
    }

    private fun bukaMenuWaktuPengingat() {
        val popup = PopupMenu(this, binding.kartuWaktuPengingat)
        popup.menu.add("5 menit sebelumnya")
        popup.menu.add("10 menit sebelumnya")
        popup.menu.add("30 menit sebelumnya")
        popup.menu.add("1 jam sebelumnya")

        popup.setOnMenuItemClickListener { item ->
            waktuPengingatAktif = item.title.toString()
            binding.teksWaktuPengingatTerpilih.text = waktuPengingatAktif
            true
        }
        popup.show()
    }

    private fun simpanJadwalBaru() {
        val nama = binding.etNamaJadwal.text.toString().trim()
        val mulai = binding.teksJamMulaiTerpilih.text.toString()

        when {
            nama.isEmpty() -> {
                binding.etNamaJadwal.error = "Nama jadwal harus diisi"
                binding.etNamaJadwal.requestFocus()
            }
            mulai == "Jam Mulai" -> {
                Toast.makeText(this, "Jam mulai harus dipilih", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Jadwal '$nama' berhasil disimpan untuk hari $hariAktif!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
