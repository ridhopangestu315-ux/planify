package com.example.planify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.databinding.ItemKartuCatatanBinding

/**
 * AdaptorRecyclerView untuk menampilkan daftar kartu catatan di halaman Catatan.
 */
class AdaptorCatatan(
    private var daftarCatatan: List<ModelCatatan>,
    private val ketikaItemDitekan: (ModelCatatan) -> Unit
) : RecyclerView.Adapter<AdaptorCatatan.ViewHolderCatatan>() {

    inner class ViewHolderCatatan(val binding: ItemKartuCatatanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCatatan {
        val binding = ItemKartuCatatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCatatan(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderCatatan, position: Int) {
        val catatan = daftarCatatan[position]
        holder.binding.teksJudulItemCatatan.text = catatan.judulCatatan
        holder.binding.teksIsiItemCatatan.text = catatan.isiCatatan
        holder.binding.teksTanggalCatatan.text = catatan.tanggalDibuat

        holder.itemView.setOnClickListener {
            ketikaItemDitekan(catatan)
        }
    }

    override fun getItemCount(): Int = daftarCatatan.size

    fun perbaruiData(dataBaru: List<ModelCatatan>) {
        daftarCatatan = dataBaru
        notifyDataSetChanged()
    }
}
