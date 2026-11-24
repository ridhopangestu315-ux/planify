package com.example.planify

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planify.databinding.AktivitasKegiatanBinding

/**
 * AktivitasKegiatan mengelola tampilan dan interaksi halaman Kegiatan Planify dengan filter status dan RecyclerView.
 */
class AktivitasKegiatan : AppCompatActivity() {
    private lateinit var binding: AktivitasKegiatanBinding
    private lateinit var adaptorKegiatan: AdaptorKegiatan

    // Data dummy kegiatan (dapat diisi untuk menguji kondisi ada kegiatan / pending / selesai)
    private val daftarKegiatanDummy = mutableListOf<ModelKegiatan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasKegiatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainKegiatan) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        aturRecyclerView()
        aturFilter()
        aturInteraksi()
        terapkanFilter("Semua")
    }

    private fun aturRecyclerView() {
        adaptorKegiatan = AdaptorKegiatan(emptyList()) { kegiatan, sudahSelesai ->
            kegiatan.sudahSelesai = sudahSelesai
            kegiatan.statusKegiatan = if (sudahSelesai) "Selesai" else "Pending"
            terapkanFilter("Semua")
            Toast.makeText(this, "${kegiatan.namaKegiatan} diperbarui", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewKegiatan.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewKegiatan.adapter = adaptorKegiatan
    }

    private fun aturFilter() {
        binding.filterSemuaKegiatan.setOnClickListener {
            aturTombolAktif(0)
            terapkanFilter("Semua")
        }
        binding.filterPendingKegiatan.setOnClickListener {
            aturTombolAktif(1)
            terapkanFilter("Pending")
        }
        binding.filterSelesaiKegiatan.setOnClickListener {
            aturTombolAktif(2)
            terapkanFilter("Selesai")
        }
    }

    private fun aturTombolAktif(index: Int) {
        val tombols = listOf(binding.filterSemuaKegiatan, binding.filterPendingKegiatan, binding.filterSelesaiKegiatan)
        for ((i, tombol) in tombols.withIndex()) {
            if (i == index) {
                tombol.setBackgroundResource(R.drawable.latar_filter_aktif)
                tombol.setTextColor(Color.WHITE)
            } else {
                tombol.setBackgroundResource(R.drawable.latar_filter_tidak_aktif)
                tombol.setTextColor(Color.parseColor("#71819A"))
            }
        }
    }

    private fun terapkanFilter(status: String) {
        val hasilFilter = when (status) {
            "Pending" -> daftarKegiatanDummy.filter { !it.sudahSelesai }
            "Selesai" -> daftarKegiatanDummy.filter { it.sudahSelesai }
            else -> daftarKegiatanDummy
        }
        adaptorKegiatan.perbaruiData(hasilFilter)
        perbaruiTampilanState(hasilFilter)
    }

    private fun aturInteraksi() {
        binding.tombolTambahKegiatan.setOnClickListener {
            val kegiatanBaru = ModelKegiatan(
                idKegiatan = System.currentTimeMillis().toString(),
                namaKegiatan = "Tugas Baru Planify",
                deskripsiKegiatan = "Deskripsi kegiatan baru dari tombol tambah",
                tanggalKegiatan = "13 Nov 2026",
                statusKegiatan = "Pending",
                sudahSelesai = false
            )
            daftarKegiatanDummy.add(kegiatanBaru)
            aturTombolAktif(0)
            terapkanFilter("Semua")
            Toast.makeText(this, "Kegiatan ditambahkan!", Toast.LENGTH_SHORT).show()
        }

        // Bottom Navigation
        binding.menuBerandaKegiatan.setOnClickListener {
            val intent = Intent(this, AktivitasUtama::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        binding.menuJadwalKegiatan.setOnClickListener {
            val intent = Intent(this, AktivitasJadwal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        binding.menuCatatanKegiatan.setOnClickListener {
            val intent = Intent(this, AktivitasCatatan::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        binding.menuKegiatanKegiatan.setOnClickListener {
            // Sudah di halaman Kegiatan
        }
        binding.menuKalenderKegiatan.setOnClickListener {
            val intent = Intent(this, AktivitasKalender::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun perbaruiTampilanState(data: List<ModelKegiatan>) {
        binding.teksJumlahTugasAktif.text = "${daftarKegiatanDummy.count { !it.sudahSelesai }} tugas aktif"
        if (data.isEmpty()) {
            binding.tataLetakKosongKegiatan.visibility = View.VISIBLE
            binding.recyclerViewKegiatan.visibility = View.GONE
        } else {
            binding.tataLetakKosongKegiatan.visibility = View.GONE
            binding.recyclerViewKegiatan.visibility = View.VISIBLE
        }
    }
}
