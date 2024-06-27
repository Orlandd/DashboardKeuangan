package com.example.dashboardkeuangan.ui.kerjasama

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataDetailed(
    val keterangan: String,
    val sumber: String,
    val tanggal: String,
    val nominal: String
) : Parcelable
