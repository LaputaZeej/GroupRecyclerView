package com.laputa.decoration.group

import androidx.recyclerview.widget.RecyclerView

abstract class GroupItemAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    /**
     * 当前位置是否是群组头
     *
     * @param position 位置
     */
    abstract fun isGroup(position: Int): Boolean

    abstract fun groupName(position: Int): String
}