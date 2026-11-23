package com.example.planify

import java.util.Calendar

/**
 * Model data untuk merepresentasikan satu sel tanggal pada kalender bulanan.
 */
data class ModelTanggal(
    val tanggalCalendar: Calendar?,
    val teksAngka: String,
    val adalahBulanAktif: Boolean,
    var apakahDipilih: Boolean,
    var memilikiKegiatan: Boolean
)
