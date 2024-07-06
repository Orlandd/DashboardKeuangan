package com.example.dashboardkeuangan.ui.penelitian

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val tahun: String,
    val jumlahpertahun: Int,
) : Parcelable
