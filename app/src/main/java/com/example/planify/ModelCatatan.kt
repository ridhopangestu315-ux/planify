package com.example.planify

/**
 * Model data untuk merepresentasikan entitas Catatan Planify.
 */
data class ModelCatatan(
    val idCatatan: String,
    val judulCatatan: String,
    val isiCatatan: String,
    val tanggalDibuat: String,
    val tanggalDiperbarui: String
)
