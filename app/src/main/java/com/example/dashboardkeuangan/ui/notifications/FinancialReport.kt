package com.example.dashboardkeuangan.ui.notifications

data class FinancialReport(
    val tanggal: String,
    val sumber: String,
    val jenis: String,
    val formattedNominal: String,
    val nominal: Double
)
