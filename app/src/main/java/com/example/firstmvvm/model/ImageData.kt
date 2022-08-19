package com.example.firstmvvm.model

import android.content.Context
import androidx.room.*

@Entity
data class ImageData(
    @PrimaryKey val num: Int,
    val title: String,
    val genre: String,
    val uri: String
)

@Dao
interface ImageDataInterface {

    @Query("SELECT * FROM ImageData")
    fun getAll(): List<ImageData>

    @Insert
    fun insert(ImageDataTable: ImageData)

    @Query("DELETE FROM ImageData")
    fun deleteAll()

    @Query("SELECT * FROM ImageData WHERE num = :n")
    fun getRandom(n: Int): List<ImageData>

    @Query("SELECT * FROM ImageData WHERE genre LIKE '%' || :keyword || '%' OR title LIKE '%' || :keyword || '%'")
    fun getKeyword(keyword: String): List<ImageData>
}

@Database(entities = [ImageData::class], exportSchema = false, version = 1)
abstract class AppDataBase: RoomDatabase() {
    companion object{
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDataBase::class.java, "db")
                    .fallbackToDestructiveMigration()
//                    addCallback(object : Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                        }
//                    })
                    .build()
            }.also {
                instance = it
            }
        }
    }

    abstract fun ImageDataInterface(): ImageDataInterface
}
