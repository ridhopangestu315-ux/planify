package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planify.databinding.AktivitasKalenderBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * AktivitasKalender mengelola tampilan kalender bulanan interaktif, navigasi bulan, pemilihan tanggal, dan informasi kegiatan.
 */
class AktivitasKalender : AppCompatActivity() {
    private lateinit var binding: AktivitasKalenderBinding
    private lateinit var adaptorKalender: AdaptorKalender
    private val calendarAktif: Calendar = Calendar.getInstance()
    private var tanggalTerpilih: Calendar = Calendar.getInstance()

    // Daftar tanggal yang memiliki kegiatan (contoh tanggal dummy: tanggal 11 dan 19 bulan ini)
    private val daftarTanggalKegiatan = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasKalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainKalender) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Contoh tanggal dummy kegiatan (format: yyyy-MM-dd)
        tambahkanContohKegiatanDummy()

        aturKalenderGrid()
        aturNavigasiBulan()
        aturInteraksiTombol()
        aturNavigasiBawah()
    }

    private fun tambahkanContohKegiatanDummy() {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val cal = Calendar.getInstance()

        // Tanggal 11 bulan ini
        cal.set(Calendar.DAY_OF_MONTH, 11)
        daftarTanggalKegiatan.add(format.format(cal.time))

        // Tanggal 19 bulan ini
        cal.set(Calendar.DAY_OF_MONTH, 19)
        daftarTanggalKegiatan.add(format.format(cal.time))
    }

    private fun aturKalenderGrid() {
        adaptorKalender = AdaptorKalender(emptyList()) { modelTanggal ->
            if (modelTanggal.tanggalCalendar != null) {
                tanggalTerpilih = modelTanggal.tanggalCalendar
                perbaruiTampilanKalender()
            }
        }
        binding.recyclerViewGridKalender.layoutManager = GridLayoutManager(this, 7)
        binding.recyclerViewGridKalender.adapter = adaptorKalender

        perbaruiTampilanKalender()
    }

    private fun perbaruiTampilanKalender() {
        // Perbarui teks Bulan dan Tahun (misal: "November 2026")
        val formatBulanTahun = SimpleDateFormat("MMMM yyyy", Locale("id", "ID"))
        binding.teksBulanTahun.text = formatBulanTahun.format(calendarAktif.time)

        // Perbarui teks judul info tanggal (misal: "Kegiatan • 11 November")
        val formatInfoTanggal = SimpleDateFormat("d MMMM", Locale("id", "ID"))
        binding.teksJudulInfoTanggal.text = "Kegiatan • ${formatInfoTanggal.format(tanggalTerpilih.time)}"

        // Hitung daftar sel tanggal untuk bulan aktif
        val daftarTanggal = buatDaftarTanggalBulanan(calendarAktif)
        adaptorKalender.perbaruiData(daftarTanggal)
    }

    private fun buatDaftarTanggalBulanan(calendar: Calendar): List<ModelTanggal> {
        val hasil = mutableListOf<ModelTanggal>()
        val calTemp = calendar.clone() as Calendar

        calTemp.set(Calendar.DAY_OF_MONTH, 1)
        val hariPertamaDalamMinggu = calTemp.get(Calendar.DAY_OF_WEEK) // 1 = Minggu, 2 = Senin, dst.
        val jumlahHariBulanIni = calTemp.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Indeks awal penanggalan (mengikuti header M S S R K J S di mana Minggu = kolom pertama)
        val offsetKosong = hariPertamaDalamMinggu - 1

        // Tambahkan sel kosong untuk hari-hari sebelum tanggal 1
        for (i in 0 until offsetKosong) {
            hasil.add(ModelTanggal(null, "", false, false, false))
        }

        val formatPengecekan = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Tambahkan tanggal dari 1 sampai akhir bulan
        for (hari in 1..jumlahHariBulanIni) {
            val calHari = calendar.clone() as Calendar
            calHari.set(Calendar.DAY_OF_MONTH, hari)

            val stringTanggal = formatPengecekan.format(calHari.time)
            val apakahDipilih = apakahSamaHari(calHari, tanggalTerpilih)
            val memilikiKegiatan = daftarTanggalKegiatan.contains(stringTanggal)
            val adalahBulanAktif = true

            hasil.add(ModelTanggal(calHari, hari.toString(), adalahBulanAktif, apakahDipilih, memilikiKegiatan))
        }

        return hasil
    }

    private fun apakahSamaHari(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    private fun aturNavigasiBulan() {
        binding.tombolBulanSebelumnya.setOnClickListener {
            calendarAktif.add(Calendar.MONTH, -1)
            perbaruiTampilanKalender()
        }

        binding.tombolBulanBerikutnya.setOnClickListener {
            calendarAktif.add(Calendar.MONTH, 1)
            perbaruiTampilanKalender()
        }
    }

    private fun aturInteraksiTombol() {
        binding.tombolTambahKalender.setOnClickListener {
            val formatTanggal = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
            Toast.makeText(this, "Tambah jadwal untuk tanggal: ${formatTanggal.format(tanggalTerpilih.time)}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun aturNavigasiBawah() {
        binding.menuBerandaKalender.setOnClickListener {
            val intent = Intent(this, AktivitasUtama::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.menuJadwalKalender.setOnClickListener {
            val intent = Intent(this, AktivitasJadwal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.menuCatatanKalender.setOnClickListener {
            val intent = Intent(this, AktivitasCatatan::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.menuKegiatanKalender.setOnClickListener {
            val intent = Intent(this, AktivitasKegiatan::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.menuKalenderKalender.setOnClickListener {
            // Sudah berada di halaman Kalender
        }
    }
}
