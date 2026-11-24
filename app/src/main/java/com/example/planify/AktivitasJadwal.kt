package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasJadwalBinding

/**
 * AktivitasJadwal mengelola tampilan dan interaksi halaman Jadwal Mingguan Planify.
 */
class AktivitasJadwal : AppCompatActivity() {
    private lateinit var binding: AktivitasJadwalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasJadwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainJadwal) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup pemilihan hari mingguan
        aturPemilihanHari()

        // Setup Tombol Tambah Jadwal
        binding.tombolTambahJadwal.setOnClickListener {
            val intent = Intent(this, AktivitasFormTambahJadwal::class.java)
            startActivity(intent)
        }

        // Setup Navigasi Bawah (Bottom Navigation)
        aturNavigasiBawah()
    }

    private fun aturPemilihanHari() {
        val daftarKotakHari = listOf(
            binding.kotakHariSenin to "Senin",
            binding.kotakHariSelasa to "Selasa",
            binding.kotakHariRabu to "Rabu",
            binding.kotakHariKamis to "Kamis",
            binding.kotakHariJumat to "Jumat",
            binding.kotakHariSabtu to "Sabtu",
            binding.kotakHariMinggu to "Minggu"
        )

        for ((kotak, namaHari) in daftarKotakHari) {
            kotak.setOnClickListener {
                // Ubah semua kotak menjadi tidak terpilih
                for ((k, _) in daftarKotakHari) {
                    k.setBackgroundResource(R.drawable.latar_hari_tidak_terpilih)
                    // Ubah warna teks anak di dalam kotak
                    val teksHari = (k.getChildAt(0) as? android.widget.TextView)
                    val teksTanggal = (k.getChildAt(1) as? android.widget.TextView)
                    teksHari?.setTextColor(android.graphics.Color.parseColor("#182B49"))
                    teksTanggal?.setTextColor(android.graphics.Color.parseColor("#182B49"))
                }

                // Tandai kotak yang diklik sebagai terpilih
                kotak.setBackgroundResource(R.drawable.latar_hari_terpilih)
                val teksHariTerpilih = (kotak.getChildAt(0) as? android.widget.TextView)
                val teksTanggalTerpilih = (kotak.getChildAt(1) as? android.widget.TextView)
                teksHariTerpilih?.setTextColor(android.graphics.Color.WHITE)
                teksTanggalTerpilih?.setTextColor(android.graphics.Color.WHITE)

                // Perbarui judul jadwal harian
                binding.teksJudulJadwalHarian.text = "Jadwal Hari $namaHari"
            }
        }
    }

    private fun aturNavigasiBawah() {
        binding.menuBerandaJadwal.setOnClickListener {
            val intent = Intent(this, AktivitasUtama::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.menuJadwalJadwal.setOnClickListener {
            // Sudah berada di halaman Jadwal
        }

        binding.menuCatatanJadwal.setOnClickListener {
            val intent = Intent(this, AktivitasCatatan::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.menuKegiatanJadwal.setOnClickListener {
            val intent = Intent(this, AktivitasKegiatan::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.menuKalenderJadwal.setOnClickListener {
            val intent = Intent(this, AktivitasKalender::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
