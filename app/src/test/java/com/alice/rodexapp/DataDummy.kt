package com.alice.rodexapp

import com.alice.rodexapp.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {

        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val storyItem = ListStoryItem(
                i.toString(),
                "author + $i",
                "name $i",
                "description $i",
                0.0,
                "id $i",
                0.0
            )
            items.add(storyItem)
        }
        return items
    }
}