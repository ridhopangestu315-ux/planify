package com.example.planify

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasFormTambahKegiatanBinding
import java.util.Calendar

/**
 * AktivitasFormTambahKegiatan mengelola formulir tambah kegiatan baru Planify.
 */
class AktivitasFormTambahKegiatan : AppCompatActivity() {
    private lateinit var binding: AktivitasFormTambahKegiatanBinding
    private var prioritasAktif = "Sedang"
    private var kategoriAktif = "Akademik"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasFormTambahKegiatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainFormTambahKegiatan) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliFormKegiatan.setOnClickListener {
            finish()
        }

        // Tanggal Deadline Picker
        binding.layoutPilihTanggal.setOnClickListener {
            bukaDatePicker()
        }

        // Waktu Picker
        binding.layoutPilihWaktu.setOnClickListener {
            bukaTimePicker()
        }

        // Prioritas Pills
        binding.pillRendah.setOnClickListener { aturPrioritas("Rendah") }
        binding.pillSedang.setOnClickListener { aturPrioritas("Sedang") }
        binding.pillTinggi.setOnClickListener { aturPrioritas("Tinggi") }

        // Dropdown Kategori
        binding.layoutDropdownKategori.setOnClickListener {
            bukaMenuKategori()
        }

        // Tombol Simpan Kegiatan
        binding.tombolSimpanKegiatan.setOnClickListener {
            simpanKegiatanBaru()
        }
    }

    private fun bukaDatePicker() {
        val kalender = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, tahun, bulan, hari ->
                val tanggalFormat = String.format("%02d/%02d/%d", hari, bulan + 1, tahun)
                binding.teksTanggalTerpilih.text = tanggalFormat
                binding.teksTanggalTerpilih.setTextColor(android.graphics.Color.parseColor("#172443"))
            },
            kalender.get(Calendar.YEAR),
            kalender.get(Calendar.MONTH),
            kalender.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun bukaTimePicker() {
        val kalender = Calendar.getInstance()
        TimePickerDialog(
            this,
            { _, jam, menit ->
                val waktuFormat = String.format("%02d:%02d", jam, menit)
                binding.teksWaktuTerpilih.text = waktuFormat
                binding.teksWaktuTerpilih.setTextColor(android.graphics.Color.parseColor("#172443"))
            },
            kalender.get(Calendar.HOUR_OF_DAY),
            kalender.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun aturPrioritas(prioritas: String) {
        prioritasAktif = prioritas
        binding.pillRendah.setBackgroundResource(R.drawable.latar_prioritas_tidak_aktif)
        binding.pillSedang.setBackgroundResource(R.drawable.latar_prioritas_tidak_aktif)
        binding.pillTinggi.setBackgroundResource(R.drawable.latar_prioritas_tidak_aktif)

        when (prioritas) {
            "Rendah" -> binding.pillRendah.setBackgroundResource(R.drawable.latar_prioritas_rendah)
            "Sedang" -> binding.pillSedang.setBackgroundResource(R.drawable.latar_prioritas_sedang)
            "Tinggi" -> binding.pillTinggi.setBackgroundResource(R.drawable.latar_prioritas_tinggi)
        }
    }

    private fun bukaMenuKategori() {
        val popup = PopupMenu(this, binding.layoutDropdownKategori)
        popup.menu.add("Akademik")
        popup.menu.add("Tugas")
        popup.menu.add("Pribadi")
        popup.menu.add("Lainnya")

        popup.setOnMenuItemClickListener { item ->
            kategoriAktif = item.title.toString()
            binding.teksKategoriTerpilih.text = kategoriAktif
            binding.teksKategoriTerpilih.setTextColor(android.graphics.Color.parseColor("#172443"))
            true
        }
        popup.show()
    }

    private fun simpanKegiatanBaru() {
        val judul = binding.etJudulKegiatan.text.toString().trim()
        val tanggal = binding.teksTanggalTerpilih.text.toString()

        when {
            judul.isEmpty() -> {
                binding.etJudulKegiatan.error = "Judul kegiatan harus diisi"
                binding.etJudulKegiatan.requestFocus()
            }
            tanggal == "Pilih tanggal" -> {
                Toast.makeText(this, "Tanggal deadline harus dipilih", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Kegiatan '$judul' berhasil disimpan!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
