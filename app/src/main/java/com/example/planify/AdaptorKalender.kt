package com.example.planify

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.databinding.ItemTanggalKalenderBinding

/**
 * Adaptor RecyclerView untuk menampilkan grid tanggal kalender bulanan (7 kolom).
 */
class AdaptorKalender(
    private var daftarTanggal: List<ModelTanggal>,
    private val ketikaTanggalDitekan: (ModelTanggal) -> Unit
) : RecyclerView.Adapter<AdaptorKalender.ViewHolderTanggal>() {

    inner class ViewHolderTanggal(val binding: ItemTanggalKalenderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTanggal {
        val binding = ItemTanggalKalenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderTanggal(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderTanggal, position: Int) {
        val dataTanggal = daftarTanggal[position]

        holder.binding.teksAngkaTanggal.text = dataTanggal.teksAngka

        if (dataTanggal.tanggalCalendar == null) {
            // Sel kosong (padding hari sebelum awal bulan atau sesudah akhir bulan)
            holder.binding.teksAngkaTanggal.text = ""
            holder.binding.root.setOnClickListener(null)
            holder.binding.indikatorTitikKegiatan.visibility = View.GONE
            holder.binding.teksAngkaTanggal.background = null
        } else {
            // Tampilkan titik indikator jika ada kegiatan
            if (dataTanggal.memilikiKegiatan) {
                holder.binding.indikatorTitikKegiatan.visibility = View.VISIBLE
            } else {
                holder.binding.indikatorTitikKegiatan.visibility = View.GONE
            }

            // Atur gaya jika tanggal dipilih
            if (dataTanggal.apakahDipilih) {
                holder.binding.teksAngkaTanggal.setBackgroundResource(R.drawable.latar_tanggal_terpilih)
                holder.binding.teksAngkaTanggal.setTextColor(Color.WHITE)
            } else {
                holder.binding.teksAngkaTanggal.background = null
                if (dataTanggal.adalahBulanAktif) {
                    holder.binding.teksAngkaTanggal.setTextColor(Color.parseColor("#182B49"))
                } else {
                    holder.binding.teksAngkaTanggal.setTextColor(Color.parseColor("#94A3B8"))
                }
            }

            holder.binding.root.setOnClickListener {
                ketikaTanggalDitekan(dataTanggal)
            }
        }
    }

    override fun getItemCount(): Int = daftarTanggal.size

    fun perbaruiData(dataBaru: List<ModelTanggal>) {
        daftarTanggal = dataBaru
        notifyDataSetChanged()
    }
}
