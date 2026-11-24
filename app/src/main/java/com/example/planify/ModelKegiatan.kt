package com.example.planify

/**
 * Model data untuk merepresentasikan entitas Kegiatan Planify.
 */
data class ModelKegiatan(
    val idKegiatan: String,
    val namaKegiatan: String,
    val deskripsiKegiatan: String,
    val tanggalKegiatan: String,
    var statusKegiatan: String,
    var sudahSelesai: Boolean
)
