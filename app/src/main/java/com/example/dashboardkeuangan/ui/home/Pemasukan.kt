package com.example.homelayoutprojectpabbaru.ui.home
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pemasukan(
    val tanggal : String, val jenisPemasukan : String, val nominal : String
) : Parcelable
