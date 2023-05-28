package com.krutika.bookmyshow.ui.venue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.recyclerview.widget.RecyclerView
import com.krutika.bookmyshow.data.remote.models.VenuesItem
import com.krutika.myapplication.R
import com.krutika.myapplication.databinding.ItemVenueItemBinding

class VenueAdapter(private val dataList: ArrayList<VenuesItem>) :
    RecyclerView.Adapter<VenueAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVenueItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(private val binding: ItemVenueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VenuesItem) {
            binding.venueItem = item
            binding.setClickListener {
                val bundle = Bundle()
                bundle.putSerializable("venue", item)

                Navigation.findNavController(it)
                    .navigate(R.id.action_From_venueList_to_showtimeList, bundle)
            }
            binding.executePendingBindings()
        }
    }
}