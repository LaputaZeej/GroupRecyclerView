package com.laputa.grouprecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laputa.decoration.group.GroupItemAdapter
import com.laputa.decoration.group.Item
import kotlinx.android.synthetic.main.item_animal.view.*

class ItemAdapter(val list: List<Item<Animals>>) : GroupItemAdapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_animal, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.item_text.text = list[position].text
    }

    private class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun isGroup(position: Int): Boolean {
        return if (position == 0) {
            true
        } else {
            list[position].group != list[position - 1].group
        }
    }

    override fun groupName(position: Int): String {
        return list[position].group
    }

}