package com.example.firstmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.firstmvvm.databinding.ItemMainBinding
import com.example.firstmvvm.model.ImageData
import com.example.firstmvvm.util.ImageDiffItemCallback
import com.example.firstmvvm.viewmodel.MainViewModel

class MyListAdapter(val viewModel: MainViewModel, var imageDataList: List<ImageData>, private val glide: RequestManager)
    : ListAdapter<ImageData, MyListAdapter.MyListHolder>(ImageDiffItemCallback()) {

    private lateinit var binding : ItemMainBinding
    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListAdapter.MyListHolder {
        context = parent.context
        binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyListHolder(binding, viewModel, glide)
    }

    override fun onBindViewHolder(holder: MyListAdapter.MyListHolder, position: Int) {
        holder.bind(imageDataList[position])
    }

    class MyListHolder(private val binding: ItemMainBinding, private val viewModel: MainViewModel, private val glide: RequestManager) : RecyclerView.ViewHolder(binding.root){

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

    override fun submitList(list: MutableList<ImageData>?) {
        super.submitList(list)
    }

    fun setData(imageDataList: List<ImageData>) {
        this.imageDataList = imageDataList
    }
}