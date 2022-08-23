package com.example.firstmvvm.util

import androidx.recyclerview.widget.DiffUtil
import com.example.firstmvvm.model.ImageData

class ImageDiffItemCallback : DiffUtil.ItemCallback<ImageData>() {
    override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
        return oldItem == newItem
    }
}