package com.example.dashboardkeuangan.ui.penelitian

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataDetailed(
    val divisi: String = "",
    val keterangan: String = "",
    val status: String = "",
    val tanggal: Timestamp? = null,
    val nominal: String = "",
    val kegiatan: String = ""
) : Parcelable
