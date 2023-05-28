package com.krutika.bookmyshow.ui.showlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krutika.bookmyshow.data.remote.models.ShowtimesItem
import com.krutika.myapplication.databinding.ItemTimeslotItemBinding

class ShowListAdapter(private val dataList: ArrayList<ShowtimesItem?>?) :
    RecyclerView.Adapter<ShowListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimeslotItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList?.get(position)
        item?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(private val binding: ItemTimeslotItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShowtimesItem) {
            binding.showtimeItem = item


            binding.executePendingBindings()

        }
    }
}