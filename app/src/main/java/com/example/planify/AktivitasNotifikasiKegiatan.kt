package com.example.planify

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasNotifikasiKegiatanBinding

/**
 * AktivitasNotifikasiKegiatan mengelola pengaturan notifikasi khusus kegiatan Planify.
 */
class AktivitasNotifikasiKegiatan : AppCompatActivity() {
    private lateinit var binding: AktivitasNotifikasiKegiatanBinding
    private var pilihanAktifWaktu = "10 menit sebelum"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasNotifikasiKegiatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainNotifikasiKegiatan) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Muat preferensi tersimpan
        muatPengaturanTersimpan()

        // Tombol Kembali
        binding.tombolKembaliNotifikasiKegiatan.setOnClickListener {
            finish()
        }

        // Switch Toggle Notifikasi
        binding.switchNotifikasi.setOnCheckedChangeListener { _, isChecked ->
            aturStatusAktifPengaturan(isChecked)
        }

        // Dropdown Waktu Pengingat
        binding.layoutDropdownWaktu.setOnClickListener {
            tampilkanMenuDropdownWaktu()
        }

        // Tombol Pill Waktu
        binding.pill5Menit.setOnClickListener { perbaruiPilihanWaktu("5 menit sebelum", 0) }
        binding.pill10Menit.setOnClickListener { perbaruiPilihanWaktu("10 menit sebelum", 1) }
        binding.pill30Menit.setOnClickListener { perbaruiPilihanWaktu("30 menit sebelum", 2) }
        binding.pill1Jam.setOnClickListener { perbaruiPilihanWaktu("1 jam sebelum", 3) }

        // Tombol Simpan Pengingat
        binding.tombolSimpanPengingat.setOnClickListener {
            simpanPengaturan()
        }
    }

    private fun aturStatusAktifPengaturan(aktif: Boolean) {
        val alphaNilai = if (aktif) 1.0f else 0.4f
        binding.layoutPengaturanWaktu.isEnabled = aktif
        binding.layoutPengaturanWaktu.alpha = alphaNilai

        binding.layoutDropdownWaktu.isEnabled = aktif
        binding.pill5Menit.isEnabled = aktif
        binding.pill10Menit.isEnabled = aktif
        binding.pill30Menit.isEnabled = aktif
        binding.pill1Jam.isEnabled = aktif
    }

    private fun tampilkanMenuDropdownWaktu() {
        if (!binding.switchNotifikasi.isChecked) return

        val popup = PopupMenu(this, binding.layoutDropdownWaktu)
        popup.menu.add("5 menit sebelum")
        popup.menu.add("10 menit sebelum")
        popup.menu.add("30 menit sebelum")
        popup.menu.add("1 jam sebelum")

        popup.setOnMenuItemClickListener { item ->
            val teks = item.title.toString()
            val indeksPill = when (teks) {
                "5 menit sebelum" -> 0
                "10 menit sebelum" -> 1
                "30 menit sebelum" -> 2
                else -> 3
            }
            perbaruiPilihanWaktu(teks, indeksPill)
            true
        }
        popup.show()
    }

    private fun perbaruiPilihanWaktu(teksWaktu: String, indeksPill: Int) {
        pilihanAktifWaktu = teksWaktu
        binding.teksWaktuTerpilih.text = teksWaktu

        val daftarPill = listOf(binding.pill5Menit, binding.pill10Menit, binding.pill30Menit, binding.pill1Jam)
        for ((i, pill) in daftarPill.withIndex()) {
            if (i == indeksPill) {
                pill.setBackgroundResource(R.drawable.latar_filter_aktif)
                pill.setTextColor(Color.WHITE)
            } else {
                pill.setBackgroundResource(R.drawable.latar_filter_tidak_aktif)
                pill.setTextColor(Color.parseColor("#64748B"))
            }
        }
    }

    private fun muatPengaturanTersimpan() {
        val sharedPreferences = getSharedPreferences("PlanifyPrefs", Context.MODE_PRIVATE)
        val statusNotif = sharedPreferences.getBoolean("notif_kegiatan_aktif", true)
        val waktuSimpan = sharedPreferences.getString("notif_kegiatan_waktu", "10 menit sebelum") ?: "10 menit sebelum"

        binding.switchNotifikasi.isChecked = statusNotif
        aturStatusAktifPengaturan(statusNotif)

        val indeks = when (waktuSimpan) {
            "5 menit sebelum" -> 0
            "10 menit sebelum" -> 1
            "30 menit sebelum" -> 2
            else -> 3
        }
        perbaruiPilihanWaktu(waktuSimpan, indeks)
    }

    private fun simpanPengaturan() {
        val statusNotif = binding.switchNotifikasi.isChecked
        val sharedPreferences = getSharedPreferences("PlanifyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean("notif_kegiatan_aktif", statusNotif)
            putString("notif_kegiatan_waktu", pilihanAktifWaktu)
            apply()
        }

        Toast.makeText(this, "Pengingat berhasil disimpan!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
