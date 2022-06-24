package com.example.mahasiswa_app_mutia.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mahasiswa_app_mutia.Model.getdata.DataItem
import com.example.mahasiswa_app_mutia.R
import kotlinx.android.synthetic.main.list_item.view.*

class DataAdapter (val data: ArrayList<DataItem>?, val itemClick : OnClickListener) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val mahasiswa_nama = itemView.textNama
        val mahasiswa_nohp = itemView.textNohp
        val Mahasiswa_alamat = itemView.textAlamat
        val btnHapus = itemView.btnHapus

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.mahasiswa_nama.text = item?.mahasiswaNama
        holder.mahasiswa_nohp.text = item?.mahasiswaNohp
        holder.Mahasiswa_alamat.text = item?.mahasiswaAlamat

        holder.itemView.setOnClickListener {
            itemClick.detail(item)
        }

        holder.btnHapus.setOnClickListener {
            itemClick.hapus(item)
        }
    }

    interface OnClickListener{
        fun detail(item: DataItem?)
        fun hapus(item: DataItem?)
    }

}