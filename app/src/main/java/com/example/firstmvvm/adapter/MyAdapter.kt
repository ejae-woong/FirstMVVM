package com.example.firstmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.firstmvvm.databinding.ItemMainBinding
import com.example.firstmvvm.model.ImageData
import com.example.firstmvvm.viewmodel.MainViewModel

class MyAdapter(val viewModel: MainViewModel, var imageDataList: List<ImageData>, private val glide: RequestManager) : RecyclerView.Adapter<MyAdapter.MyHolder>() {
    private lateinit var binding : ItemMainBinding
    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        context = parent.context
        binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding, viewModel, glide)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(imageDataList[position])
    }

    override fun getItemCount(): Int {
        return imageDataList.size
    }

    class MyHolder(private val binding: ItemMainBinding, private val viewModel: MainViewModel, private val glide: RequestManager) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageData: ImageData) {

            glide.load(imageData.uri)
                .into(binding.imgRv)

            binding.imgRv.setOnClickListener {
                onImageClicked(imageData)
            }
        }

        private fun onImageClicked(imageData: ImageData) {
            viewModel.apply {
                onTitleChanged(imageData.title)
                onImageChanged(imageData.uri)
            }
        }
    }

    fun setData(imageDataList: List<ImageData>) {
        this.imageDataList = imageDataList
    }
}