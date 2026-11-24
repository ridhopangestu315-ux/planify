package com.example.planify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.databinding.ItemKartuKegiatanBinding

/**
 * Adaptor RecyclerView untuk menampilkan daftar kartu kegiatan di halaman Kegiatan.
 */
class AdaptorKegiatan(
    private var daftarKegiatan: List<ModelKegiatan>,
    private val ketikaCheckboxDiubah: (ModelKegiatan, Boolean) -> Unit
) : RecyclerView.Adapter<AdaptorKegiatan.ViewHolderKegiatan>() {

    inner class ViewHolderKegiatan(val binding: ItemKartuKegiatanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderKegiatan {
        val binding = ItemKartuKegiatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderKegiatan(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderKegiatan, position: Int) {
        val kegiatan = daftarKegiatan[position]
        holder.binding.teksNamaItemKegiatan.text = kegiatan.namaKegiatan
        holder.binding.teksDeskripsiItemKegiatan.text = kegiatan.deskripsiKegiatan
        holder.binding.teksTanggalKegiatan.text = kegiatan.tanggalKegiatan
        holder.binding.teksStatusKegiatan.text = kegiatan.statusKegiatan

        holder.binding.checkboxSelesai.setOnCheckedChangeListener(null)
        holder.binding.checkboxSelesai.isChecked = kegiatan.sudahSelesai

        holder.binding.checkboxSelesai.setOnCheckedChangeListener { _, isChecked ->
            ketikaCheckboxDiubah(kegiatan, isChecked)
        }
    }

    override fun getItemCount(): Int = daftarKegiatan.size

    fun perbaruiData(dataBaru: List<ModelKegiatan>) {
        daftarKegiatan = dataBaru
        notifyDataSetChanged()
    }
}
