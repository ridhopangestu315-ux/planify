package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planify.databinding.AktivitasCatatanBinding

/**
 * AktivitasCatatan mengelola tampilan dan interaksi halaman Catatan Planify dengan RecyclerView, pencarian, dan filter.
 */
class AktivitasCatatan : AppCompatActivity() {
    private lateinit var binding: AktivitasCatatanBinding
    private lateinit var adaptorCatatan: AdaptorCatatan

    // Data dummy awal kosong (dapat diisi untuk menguji kondisi daftar isi)
    private val daftarCatatanDummy = mutableListOf<ModelCatatan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainCatatan) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        aturRecyclerView()
        aturPencarianDanFilter()
        aturInteraksi()
        perbaruiTampilanState(daftarCatatanDummy)
    }

    private fun aturRecyclerView() {
        adaptorCatatan = AdaptorCatatan(daftarCatatanDummy) { catatan ->
            Toast.makeText(this, "Membuka: ${catatan.judulCatatan}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewCatatan.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCatatan.adapter = adaptorCatatan
    }

    private fun aturPencarianDanFilter() {
        // Tombol Filter "Semua"
        binding.filterSemua.setOnClickListener {
            binding.filterSemua.setBackgroundResource(R.drawable.latar_filter_aktif)
            binding.filterSemua.setTextColor(android.graphics.Color.WHITE)
            binding.filterTerkini.setBackgroundResource(R.drawable.latar_filter_tidak_aktif)
            binding.filterTerkini.setTextColor(android.graphics.Color.parseColor("#182B49"))
            adaptorCatatan.perbaruiData(daftarCatatanDummy)
            perbaruiTampilanState(daftarCatatanDummy)
        }

        // Tombol Filter "Terkini"
        binding.filterTerkini.setOnClickListener {
            binding.filterTerkini.setBackgroundResource(R.drawable.latar_filter_aktif)
            binding.filterTerkini.setTextColor(android.graphics.Color.WHITE)
            binding.filterSemua.setBackgroundResource(R.drawable.latar_filter_tidak_aktif)
            binding.filterSemua.setTextColor(android.graphics.Color.parseColor("#182B49"))
            val dataTerkini = daftarCatatanDummy.sortedByDescending { it.tanggalDibuat }
            adaptorCatatan.perbaruiData(dataTerkini)
            perbaruiTampilanState(dataTerkini)
        }

        // Pencarian Catatan
        binding.etCariCatatan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val kataKunci = s.toString().trim().lowercase()
                val hasilFilter = daftarCatatanDummy.filter {
                    it.judulCatatan.lowercase().contains(kataKunci) ||
                            it.isiCatatan.lowercase().contains(kataKunci)
                }
                adaptorCatatan.perbaruiData(hasilFilter)
                perbaruiTampilanState(hasilFilter)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun aturInteraksi() {
        // Tombol Tambah Catatan (FAB)
        binding.tombolTambahCatatan.setOnClickListener {
            val catatanBaru = ModelCatatan(
                idCatatan = System.currentTimeMillis().toString(),
                judulCatatan = "Catatan Kuliah Pemrograman Mobile",
                isiCatatan = "Mempelajari RecyclerView, ViewBinding, dan navigasi XML di Android Studio.",
                tanggalDibuat = "11 November 2026",
                tanggalDiperbarui = "11 November 2026"
            )
            daftarCatatanDummy.add(catatanBaru)
            adaptorCatatan.perbaruiData(daftarCatatanDummy)
            perbaruiTampilanState(daftarCatatanDummy)
            Toast.makeText(this, "Catatan baru ditambahkan!", Toast.LENGTH_SHORT).show()
        }

        // Navigasi Bawah
        binding.menuBerandaCatatan.setOnClickListener {
            val intent = Intent(this, AktivitasUtama::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.menuJadwalCatatan.setOnClickListener {
            val intent = Intent(this, AktivitasJadwal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.menuCatatanCatatan.setOnClickListener {
            // Sudah berada di halaman Catatan
        }

        binding.menuKegiatanCatatan.setOnClickListener {
            Toast.makeText(this, "Menu Kegiatan", Toast.LENGTH_SHORT).show()
        }

        binding.menuKalenderCatatan.setOnClickListener {
            Toast.makeText(this, "Menu Kalender", Toast.LENGTH_SHORT).show()
        }
    }

    private fun perbaruiTampilanState(data: List<ModelCatatan>) {
        if (data.isEmpty()) {
            binding.tataLetakKosongCatatan.visibility = View.VISIBLE
            binding.recyclerViewCatatan.visibility = View.GONE
        } else {
            binding.tataLetakKosongCatatan.visibility = View.GONE
            binding.recyclerViewCatatan.visibility = View.VISIBLE
        }
    }
}
