package com.example.homelayoutprojectpabbaru.ui.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pengeluaran(
    val tanggal : String?, val jenisPengeluaran : String?, val nominal : String?
) : Parcelable
