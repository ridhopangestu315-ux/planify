package com.example.planify

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Kelas AktivitasUtama mengelola tampilan Dashboard berbasis XML Layout.
 */
class AktivitasUtama : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.aktivitas_beranda)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mengambil tombol tambah dari layout
        val tombolTambah = findViewById<FloatingActionButton>(R.id.tombolTambah)
        tombolTambah.setOnClickListener {
            Toast.makeText(this, "Tambah kegiatan baru", Toast.LENGTH_SHORT).show()
        }

        // Mengambil komponen menu navigasi bawah dari layout
        val menuBeranda = findViewById<LinearLayout>(R.id.menuBeranda)
        val menuJadwal = findViewById<LinearLayout>(R.id.menuJadwal)
        val menuCatatan = findViewById<LinearLayout>(R.id.menuCatatan)
        val menuKegiatan = findViewById<LinearLayout>(R.id.menuKegiatan)
        val menuKalender = findViewById<LinearLayout>(R.id.menuKalender)

        menuBeranda.setOnClickListener {
            Toast.makeText(this, "Menu Beranda", Toast.LENGTH_SHORT).show()
        }
        menuJadwal.setOnClickListener {
            Toast.makeText(this, "Menu Jadwal", Toast.LENGTH_SHORT).show()
        }
        menuCatatan.setOnClickListener {
            Toast.makeText(this, "Menu Catatan", Toast.LENGTH_SHORT).show()
        }
        menuKegiatan.setOnClickListener {
            Toast.makeText(this, "Menu Kegiatan", Toast.LENGTH_SHORT).show()
        }
        menuKalender.setOnClickListener {
            Toast.makeText(this, "Menu Kalender", Toast.LENGTH_SHORT).show()
        }
    }
}
