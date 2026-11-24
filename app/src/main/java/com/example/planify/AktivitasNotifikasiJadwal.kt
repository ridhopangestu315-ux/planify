package com.example.planify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.planify.databinding.AktivitasNotifikasiJadwalBinding
import com.example.planify.databinding.ItemNadaDeringBinding

/**
 * AktivitasNotifikasiJadwal mengelola pengaturan notifikasi dan nada dering jadwal Planify.
 */
class AktivitasNotifikasiJadwal : AppCompatActivity() {
    private lateinit var binding: AktivitasNotifikasiJadwalBinding
    private var nadaDeringAktifIndex = 0

    private val daftarNadaDering = listOf(
        "Default (Planify)",
        "Notification 1",
        "Notification 2",
        "Notification 3",
        "Alarm Ring",
        "Gentle Bell"
    )

    private val launcherPilihAudio = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                validasiDanTampilkanFileAudio(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AktivitasNotifikasiJadwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainNotifikasiJadwal) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Kembali
        binding.tombolKembaliNotifikasi.setOnClickListener {
            finish()
        }

        aturDaftarNadaDering()
        aturPilihAudioKustom()
        aturSliderVolume()
        aturTombolSimpan()
    }

    private fun aturDaftarNadaDering() {
        val itemBindings = listOf(
            binding.opsiNada1,
            binding.opsiNada2,
            binding.opsiNada3,
            binding.opsiNada4,
            binding.opsiNada5,
            binding.opsiNada6
        )

        for ((index, itemBinding) in itemBindings.withIndex()) {
            itemBinding.teksNamaNada.text = daftarNadaDering[index]

            // Setup state awal (Index 0 aktif default)
            perbaruiTampilanItemNada(itemBinding, index == nadaDeringAktifIndex)

            // Klik item
            itemBinding.root.setOnClickListener {
                nadaDeringAktifIndex = index
                for ((i, ib) in itemBindings.withIndex()) {
                    perbaruiTampilanItemNada(ib, i == nadaDeringAktifIndex)
                }
                Toast.makeText(this, "Nada dering dipilih: ${daftarNadaDering[index]}", Toast.LENGTH_SHORT).show()
            }

            // Tombol Play preview
            itemBinding.tombolPlay.setOnClickListener {
                Toast.makeText(this, "Memutar preview: ${daftarNadaDering[index]}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun perbaruiTampilanItemNada(itemBinding: ItemNadaDeringBinding, aktif: Boolean) {
        if (aktif) {
            itemBinding.ikonRadio.setImageResource(R.drawable.ic_radio_checked)
            itemBinding.ikonEqualizer.visibility = View.VISIBLE
        } else {
            itemBinding.ikonRadio.setImageResource(R.drawable.ic_radio_unchecked)
            itemBinding.ikonEqualizer.visibility = View.GONE
        }
    }

    private fun aturPilihAudioKustom() {
        binding.kartuPilihAudio.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "audio/*"
            }
            launcherPilihAudio.launch(intent)
        }
    }

    private fun validasiDanTampilkanFileAudio(uri: Uri) {
        var ukuranFile: Long = 0
        var namaFile = "Audio Kustom"

        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val namaIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val ukuranIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst()) {
                if (namaIndex != -1) namaFile = cursor.getString(namaIndex)
                if (ukuranIndex != -1) ukuranFile = cursor.getLong(ukuranIndex)
            }
        }

        val batasMaksimal = 10 * 1024 * 1024 // 10 MB
        if (ukuranFile > batasMaksimal) {
            Toast.makeText(this, "Ukuran file melebihi batas maksimal 10 MB", Toast.LENGTH_LONG).show()
        } else {
            binding.teksPilihAudio.text = namaFile
            binding.teksFormatAudio.text = "File audio berhasil dimuat"
            Toast.makeText(this, "Audio kustom dipilih: $namaFile", Toast.LENGTH_SHORT).show()
        }
    }

    private fun aturSliderVolume() {
        binding.sliderVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.teksPersentaseVolume.text = "$progress%"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun aturTombolSimpan() {
        binding.tombolSimpanNotifikasi.setOnClickListener {
            Toast.makeText(this, "Pengaturan notifikasi berhasil disimpan!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
