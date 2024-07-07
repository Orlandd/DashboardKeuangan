package com.example.dashboardkeuangan.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.databinding.FragmentDashboardBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.eazegraph.lib.models.BarModel
import org.eazegraph.lib.models.PieModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    
    private val binding get() = _binding!!

    private lateinit var rvData : RecyclerView
    private val listData =  ArrayList<Pemasukan>()
    var db = Firebase.firestore
    private var selectedYear = ""
    private val yearTotalMap = mutableMapOf<String, Float>()
    private val sourceYearTotalMap = mutableMapOf<String, MutableMap<String, Float>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root




        db = Firebase.firestore



        getDataPemasukan()

        initCharts()

        fetchDataAndSetupSpinner()


        binding.pemasukan.setOnClickListener {
            binding.sumber.setVisibility(View.VISIBLE);

            getDataPemasukan()

            initCharts()

            fetchDataAndSetupSpinner()

        }

        binding.pengeluaranbtn.setOnClickListener {

            binding.sumber.setVisibility(View.GONE)

            getDataPengeluaran()

            initCharts()


            fetchDataAndSetupSpinnerPengeluaran()

        }


        return root
    }

    private fun getDataPemasukan() {
        val listData = ArrayList<Pemasukan>()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Fetch data from 'keuangan/pemasukan/data'
        db.collection("keuangan")
            .document("pemasukan")
            .collection("data")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val timestamp = document.getTimestamp("tanggal")
                    val tanggal = timestamp?.toDate()?.let { dateFormat.format(it) } ?: ""
                    val calendar = Calendar.getInstance()
                    calendar.time = timestamp?.toDate()
                    val year = calendar.get(Calendar.YEAR).toString()

                    if (selectedYear == year) {
                        val sumber = document.getString("sumber") ?: ""
                        val jenis = document.getString("jenis") ?: ""
                        val nominal = document.getString("nominal") ?: ""

                        listData.add(Pemasukan(tanggal, sumber, jenis, nominal))
                    }
                }

                // Fetch data  'keuangan/kerjasama/data'
                db.collection("keuangan")
                    .document("kerjasama")
                    .collection("data")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val timestamp = document.getTimestamp("tanggal")
                            val tanggal = timestamp?.toDate()?.let { dateFormat.format(it) } ?: ""
                            val calendar = Calendar.getInstance()
                            calendar.time = timestamp?.toDate()
                            val year = calendar.get(Calendar.YEAR).toString()

                            if (selectedYear == year) {
                                val sumber = document.getString("sumber") ?: ""
                                val jenis = "Kerja Sama"
                                val nominal = document.getString("nominal") ?: ""

                                listData.add(Pemasukan(tanggal, sumber, jenis, nominal))
                            }
                        }


                        listData.sortByDescending {
                            dateFormat.parse(it.tahun)
                        }

                        binding.rcData.setHasFixedSize(true)
                        binding.rcData.layoutManager = LinearLayoutManager(requireContext())
                        val dataAdapter = PemasukanAdapter(requireContext(), listData)
                        binding.rcData.adapter = dataAdapter
                    }
                    .addOnFailureListener { exception ->
                        Log.e("error", "Gagal menampilkan data", exception)
                    }
            }
            .addOnFailureListener { exception ->
                Log.e("error", "Gagal menampilkan data", exception)
            }
    }



    private fun getDataPengeluaran() {
        val listData2 = ArrayList<Pengeluaran>()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Fetch data  "pengeluaran" collection
        db.collection("keuangan")
            .document("pengeluaran")
            .collection("data")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val timestamp = document.getTimestamp("tanggal")
                    val tanggal = timestamp?.toDate()?.let { dateFormat.format(it) } ?: ""
                    val calendar = Calendar.getInstance()
                    calendar.time = timestamp?.toDate()
                    val year = calendar.get(Calendar.YEAR).toString()

                    if (selectedYear == year) {
                        val jenis = document.getString("jenis") ?: ""
                        val nominal = document.getString("nominal") ?: ""

                        listData2.add(Pengeluaran(tanggal, jenis, nominal))
                    }
                }

                // Fetch data  "penelitian" collection
                db.collection("keuangan")
                    .document("penelitian")
                    .collection("data")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val timestamp = document.getTimestamp("tanggal")
                            val tanggal = timestamp?.toDate()?.let { dateFormat.format(it) } ?: ""
                            val calendar = Calendar.getInstance()
                            calendar.time = timestamp?.toDate()
                            val year = calendar.get(Calendar.YEAR).toString()

                            if (selectedYear == year) {
                                val jenis = document.getString("kegiatan") ?: ""
                                val nominal = document.getString("nominal") ?: ""

                                listData2.add(Pengeluaran(tanggal, jenis, nominal))
                            }
                        }


                        listData2.sortByDescending { dateFormat.parse(it.tahun) }

                        // Setup RecyclerView
                        binding.rcData.setHasFixedSize(true)
                        binding.rcData.layoutManager = LinearLayoutManager(requireContext())
                        val dataAdapter = PengeluaranAdapter(requireContext(), listData2)
                        binding.rcData.adapter = dataAdapter
                    }
                    .addOnFailureListener { exception ->
                        Log.e("error", "Gagal menampilkan data dari penelitian", exception)
                    }
            }
            .addOnFailureListener { exception ->
                Log.e("error", "Gagal menampilkan data dari pengeluaran", exception)
            }
    }




    private fun initCharts() {
        binding.barchart.clearChart()
        binding.piechart.clearChart()
    }

    private fun fetchDataAndSetupSpinner() {
        // Fetch initial data for all years
        fetchData(null)
    }
    private fun fetchDataAndSetupSpinnerPengeluaran() {
        // Fetch initial data for all years
        fetchDataPengeluaran(null)
    }

    private fun fetchData(selectedYear: String?) {
        val db = Firebase.firestore
        val collectionRef = db.collection("keuangan")
            .document("pemasukan")
            .collection("data")

        val collectionKerjasama = db.collection("keuangan")
            .document("kerjasama")
            .collection("data")

        yearTotalMap.clear()
        sourceYearTotalMap.clear()

        // Ambil data dari Firestore
        collectionRef.get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val timestamp = document.getTimestamp("tanggal")
                    val tahun = timestamp?.toDate()?.let { getYearFromDate(it) } ?: ""
                    val sumber = document.getString("sumber") ?: ""
                    val nominal = document.getString("nominal")?.toFloatOrNull() ?: 0f

                    // Hitung total nominal per tahun
                    val currentTotal = yearTotalMap.getOrDefault(tahun, 0f)
                    yearTotalMap[tahun] = currentTotal + nominal

                    // Hitung total nominal per sumber dan tahun
                    val currentSourceYearMap = sourceYearTotalMap.getOrPut(sumber) { mutableMapOf() }
                    val currentSourceYearTotal = currentSourceYearMap.getOrDefault(tahun, 0f)
                    currentSourceYearMap[tahun] = currentSourceYearTotal + nominal
                }

                collectionKerjasama.get()
                    .addOnSuccessListener { kerjasamaDocuments ->
                        for (document in kerjasamaDocuments) {
                            val timestamp = document.getTimestamp("tanggal")
                            val tahun = timestamp?.toDate()?.let { getYearFromDate(it) } ?: ""
                            val sumber = document.getString("sumber") ?: ""
                            val nominal = document.getString("nominal")?.toFloatOrNull() ?: 0f

                            val currentTotal = yearTotalMap.getOrDefault(tahun, 0f)
                            yearTotalMap[tahun] = currentTotal + nominal

                            val currentSourceYearMap = sourceYearTotalMap.getOrPut(sumber) { mutableMapOf() }
                            val currentSourceYearTotal = currentSourceYearMap.getOrDefault(tahun, 0f)
                            currentSourceYearMap[tahun] = currentSourceYearTotal + nominal
                        }

                        setupYearSpinnerPemasukan()


                        drawCharts(selectedYear)
                    }
                    .addOnFailureListener { exception ->
                        Log.e("fetchData", "Gagal mengambil data kerjasama", exception)
                    }

            }
            .addOnFailureListener { exception ->
                Log.e("fetchData", "Gagal mengambil data pemasukan", exception)
            }
    }


    private fun fetchDataPengeluaran(selectedYear: String?) {
        val db = Firebase.firestore
        val collectionRef = db.collection("keuangan")
            .document("pengeluaran")
            .collection("data")

        val collectionPenelitian = db.collection("keuangan")
            .document("penelitian")
            .collection("data")

        yearTotalMap.clear()
        sourceYearTotalMap.clear()

        // Ambil data dari Firestore
        collectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val timestamp = document.getTimestamp("tanggal")
                    val tahun = timestamp?.toDate()?.let { getYearFromDate(it) } ?: ""
                    val sumber = document.getString("jenis") ?: ""
                    val nominal = document.getString("nominal")?.toFloatOrNull() ?: 0f

                    // Hitung total nominal per tahun
                    val currentTotal = yearTotalMap.getOrDefault(tahun, 0f)
                    yearTotalMap[tahun] = currentTotal + nominal

                    // Hitung total nominal per sumber dan tahun
                    val currentSourceYearMap = sourceYearTotalMap.getOrPut(sumber) { mutableMapOf() }
                    val currentSourceYearTotal = currentSourceYearMap.getOrDefault(tahun, 0f)
                    currentSourceYearMap[tahun] = currentSourceYearTotal + nominal
                }

                collectionPenelitian.get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val timestamp = document.getTimestamp("tanggal")
                            val tahun = timestamp?.toDate()?.let { getYearFromDate(it) } ?: ""
                            val sumber = document.getString("kegiatan") ?: ""
                            val nominal = document.getString("nominal")?.toFloatOrNull() ?: 0f

                            // Hitung total nominal per tahun
                            val currentTotal = yearTotalMap.getOrDefault(tahun, 0f)
                            yearTotalMap[tahun] = currentTotal + nominal

                            // Hitung total nominal per sumber dan tahun
                            val currentSourceYearMap = sourceYearTotalMap.getOrPut(sumber) { mutableMapOf() }
                            val currentSourceYearTotal = currentSourceYearMap.getOrDefault(tahun, 0f)
                            currentSourceYearMap[tahun] = currentSourceYearTotal + nominal
                        }

                        setupYearSpinnerPengeluaran()

                        drawCharts(selectedYear)
                    }
                    .addOnFailureListener { exception ->
                        Log.e("fetchData", "Gagal mengambil data", exception)
                    }

            }
            .addOnFailureListener { exception ->
                Log.e("fetchData", "Gagal mengambil data", exception)
            }
    }



    private fun setupYearSpinnerPemasukan() {
        val years = yearTotalMap.keys.toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.yearSpinner.adapter = adapter

        binding.yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedYear = parent.getItemAtPosition(position).toString()
                drawCharts(selectedYear)


                getDataPemasukan()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun setupYearSpinnerPengeluaran() {
        val years = yearTotalMap.keys.toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.yearSpinner.adapter = adapter

        binding.yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedYear = parent.getItemAtPosition(position).toString()
                drawCharts(selectedYear)


                getDataPengeluaran()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }


    private fun drawCharts(selectedYear: String?) {

        binding.barchart.clearChart()
        binding.piechart.clearChart()


        yearTotalMap.forEach { (tahun, total) ->
            binding.barchart.addBar(BarModel(tahun, total, getRandomColor()))
        }


        selectedYear?.let { year ->
            val sourceYearMap = sourceYearTotalMap.filterValues { it.containsKey(year) }
            val totalSelectedYear = sourceYearMap.values.sumByDouble { it[year]?.toDouble() ?: 0.0 }.toFloat()

            sourceYearMap.forEach { (sumber, yearMap) ->
                val total = yearMap[year] ?: 0f
                val persentase = (total / totalSelectedYear) * 100.toFloat()
                binding.piechart.addPieSlice(PieModel("$sumber ($year)", persentase, getRandomColor()))
            }
        }


        binding.barchart.startAnimation()
        binding.piechart.startAnimation()
    }

    private fun getYearFromDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.YEAR).toString()
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}