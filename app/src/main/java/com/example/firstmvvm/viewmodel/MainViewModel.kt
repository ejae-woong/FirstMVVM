package com.example.firstmvvm.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstmvvm.GlobalApplication
import com.example.firstmvvm.model.ImageData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    // 입력받은 텍스트
    private val _inputText = MutableLiveData<String>()
    val inputText : LiveData<String> get() = _inputText

    // 타이틀 텍스트
    private val _title = MutableLiveData<String>()
    val title : LiveData<String> get() = _title

    // 중앙 이미지 URI
    private val _imageUri = MutableLiveData<String>()
    val imageUri : LiveData<String> get() = _imageUri

    //JSON 파일로 저장된 데이터를 저장
    fun saveImageData() {
        val imageDataDB = GlobalApplication.appDataBaseInstance.ImageDataInterface()
        val imageDateList = loadImageData()
        imageDataDB.deleteAll()
        imageDateList.forEach {
            imageDataDB.insert(it)
        }
        imageDataDB.getRandom(0)
    }

    // JSON으로 저장된 파일을 데이터 클래스로 치환
    fun loadImageData(): List<ImageData>{
        var jsonString: String = ""
        try {
            jsonString = context.assets.open("data.json").bufferedReader().use {
                it.readText()
            }
        }catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        val gson = Gson()
        val listPostingType = object : TypeToken<List<ImageData>>() {}.type
        var imageDataList: List<ImageData> = gson.fromJson(jsonString, listPostingType)
        imageDataList.forEach {
            Log.e("NUM: ${it.num}", "TITLE: ${it.title}, GENRE: ${it.genre}, URI: ${it.uri}")
        }
        return imageDataList
    }

    // 검색된 키워드로 Room에서 데이터 불러옴
    fun loadImageData(keyword: String): List<ImageData> {
        val imageDataDB = GlobalApplication.appDataBaseInstance.ImageDataInterface()
        Log.e("Searched", imageDataDB.getKeyword(keyword).toString())
        return imageDataDB.getKeyword(keyword)
    }

    fun onTitleChanged(title: String) {
        _title.value = title
    }

    fun onImageChanged(uri: String) {
        _imageUri.value = uri
    }

    fun onInputTextChanged(text: String) {
        _inputText.value = text
    }

}