package com.example.dashboardkeuangan.ui.kerjasama

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val tahun: String,
    val jumlahpertahun: String
) : Parcelable
