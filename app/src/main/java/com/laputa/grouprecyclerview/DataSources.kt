package com.laputa.grouprecyclerview

import com.laputa.decoration.group.Item

data class Animals(val name: String)

fun createData() = mutableListOf<Item<Animals>>().apply {
    (1..5).map {
        val groupName = "[猫 $it]"
        (1..20).forEach { index ->
            val name = "$groupName:$index"
            add(Item(name, groupName, Animals(name)))
        }
    }
}.toList()