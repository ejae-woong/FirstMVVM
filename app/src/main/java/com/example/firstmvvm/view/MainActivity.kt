package com.example.firstmvvm.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.firstmvvm.adapter.MyAdapter
import com.example.firstmvvm.databinding.ActivityMainBinding
import com.example.firstmvvm.model.ImageData
import com.example.firstmvvm.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var imageDataList: List<ImageData>

    private val myAdapter: MyAdapter by lazy {
        MyAdapter(viewModel, imageDataList, Glide.with(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }
        setContentView(binding.root)

        initActivity()
        observeViewModel()
        viewModel.saveImageData()

    }

    // 뷰 이니셜라이징 (어댑터 설정, 클릭리스너)
    private fun initActivity() {
        imageDataList = viewModel.loadImageData()
        binding.rvImg.run {
            adapter = myAdapter
        }

        binding.btnMain.setOnClickListener {
            viewModel.onInputTextChanged(binding.editText.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.inputText.observe(this) {
            searchKeyword(it)
        }
        viewModel.title.observe(this) {
        }
        viewModel.imageUri.observe(this) {
            setImage(it)
        }
    }

    // 입력받은 검색어로 데이터 필터링, 리스트 갱신
    private fun searchKeyword(keyword: String) {
        imageDataList = viewModel.loadImageData(keyword)
        myAdapter.setData(imageDataList)
        binding.rvImg.adapter!!.notifyDataSetChanged()
    }

//    private fun setTitle(title: String) {
//        binding.tvTitle.text = title
//    }

    private fun setImage(uri: String) {
        Glide.with(this)
            .load(uri)
            .into(binding.imgMain)
    }

}