package com.example.dashboardkeuangan.ui.notifications

data class FinancialReport(
    val tanggal: String,
    val category: String,
    val jenis: String,
    val nominal: Int
)
