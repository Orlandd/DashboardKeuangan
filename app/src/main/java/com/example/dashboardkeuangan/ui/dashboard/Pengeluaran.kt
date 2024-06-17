package com.example.dashboardkeuangan.ui.dashboard

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Pengeluaran(val tahun: String?, val jenis: String?, val nominal: String?): Parcelable
