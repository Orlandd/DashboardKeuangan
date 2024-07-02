package com.example.dashboardkeuangan.ui.kerjasama

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataDetailed(
    val keterangan: String = "",
    val sumber: String = "",
    val tanggal: Timestamp? = null,
    val nominal: String = ""


) : Parcelable
