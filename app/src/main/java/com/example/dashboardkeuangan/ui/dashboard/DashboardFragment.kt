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

    // This property is only valid between onCreateView and
    // onDestroyView.
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

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


        db = Firebase.firestore


//        val listData =  ArrayList<Pemasukan>()
//        listData.clear()
//        binding.rcData.setHasFixedSize(true)
//        binding.rcData.layoutManager = LinearLayoutManager(requireContext())
//
//        listData.add(Pemasukan("2018", "Mahasiswa", "UKT", "190000"))
//        listData.add(Pemasukan("2017", "Mahasiswa", "UKT", "190000"))
//        listData.add(Pemasukan("2016", "Mahasiswa", "UKT", "180000"))
//        listData.add(Pemasukan("2015", "Mahasiswa", "UKT", "140000"))
//        listData.add(Pemasukan("2014", "Mahasiswa", "UKT", "150000"))
//
//        val dataAdapter = PemasukanAdapter(requireContext(), listData)
//
//        binding.rcData.adapter = dataAdapter



        //      Chart
//        val mPieChart = binding.piechart
//        mPieChart.clearChart()
//
//        mPieChart.addPieSlice(PieModel("Mahasiswa", 100f, Color.parseColor("#FE6DA8")))
////        mPieChart.addPieSlice(PieModel("Sleep", 25f, Color.parseColor("#56B7F1")))
////        mPieChart.addPieSlice(PieModel("Work", 35f, Color.parseColor("#CDA67F")))
////        mPieChart.addPieSlice(PieModel("Eating", 9f, Color.parseColor("#FED70E")))
//
//        mPieChart.startAnimation()
//
//        val mBarChart = binding.barchart
//        mBarChart.clearChart()
//
//
//        mBarChart.addBar(BarModel("2014",150000f, -0xedcbaa))
//        mBarChart.addBar(BarModel("2015",140000f, -0xcbcbaa))
//        mBarChart.addBar(BarModel("2016",180000f, -0xa9cbaa))
//        mBarChart.addBar(BarModel("2017",190000f, -0x78c0aa))
//        mBarChart.addBar(BarModel("2018",190000f, -0xa9480f))
//
//        mBarChart.startAnimation()

        // Assuming you call this method to fetch data initially
        getDataPemasukan()

        initCharts()

        // Fetch data and then setup spinner
        fetchDataAndSetupSpinner()


        binding.pemasukan.setOnClickListener {
//            val listData =  ArrayList<Pemasukan>()
//            listData.clear()
//            binding.rcData.setHasFixedSize(true)
//            binding.rcData.layoutManager = LinearLayoutManager(requireContext())
//
//            listData.add(Pemasukan("2018", "Mahasiswa", "UKT", "190000"))
//            listData.add(Pemasukan("2017", "Mahasiswa", "UKT", "190000"))
//            listData.add(Pemasukan("2016", "Mahasiswa", "UKT", "180000"))
//            listData.add(Pemasukan("2015", "Mahasiswa", "UKT", "140000"))
//            listData.add(Pemasukan("2014", "Mahasiswa", "UKT", "150000"))
//
//            val dataAdapter = PemasukanAdapter(requireContext(), listData)
//
//            binding.rcData.adapter = dataAdapter

            getDataPemasukan()

            initCharts()

            // Fetch data and then setup spinner
            fetchDataAndSetupSpinner()



//            //      Chart
//            val mPieChart = binding.piechart
//            mPieChart.clearChart()
//
//            mPieChart.addPieSlice(PieModel("Mahasiswa", 100f, Color.parseColor("#FE6DA8")))
////        mPieChart.addPieSlice(PieModel("Sleep", 25f, Color.parseColor("#56B7F1")))
////        mPieChart.addPieSlice(PieModel("Work", 35f, Color.parseColor("#CDA67F")))
////        mPieChart.addPieSlice(PieModel("Eating", 9f, Color.parseColor("#FED70E")))
//
//            mPieChart.startAnimation()
//
//            val mBarChart = binding.barchart
//            mBarChart.clearChart()
//
//
//            mBarChart.addBar(BarModel("2014",150000f, -0xedcbaa))
//            mBarChart.addBar(BarModel("2015",140000f, -0xcbcbaa))
//            mBarChart.addBar(BarModel("2016",180000f, -0xa9cbaa))
//            mBarChart.addBar(BarModel("2017",190000f, -0x78c0aa))
//            mBarChart.addBar(BarModel("2018",190000f, -0xa9480f))
//
//            mBarChart.startAnimation()
        }

        binding.pengeluaranbtn.setOnClickListener {
//            val listData2 =  ArrayList<Pengeluaran>()
//            listData.clear()
//            binding.rcData.setHasFixedSize(true)
//            binding.rcData.layoutManager = LinearLayoutManager(requireContext())
//
//            listData2.add(Pengeluaran("2018",  "Penelitian", "90"))
//            listData2.add(Pengeluaran("2018",  "Penelitian", "50"))
//            listData2.add(Pengeluaran("2017",  "Pembangunan", "1000"))
//            listData2.add(Pengeluaran("2016",  "Pemabngunan", "500"))
//
//
//            val dataAdapter = PengeluaranAdapter(requireContext(), listData2)
//
//            binding.rcData.adapter = dataAdapter

            getDataPengeluaran()

            initCharts()

            // Fetch data and then setup spinner
            fetchDataAndSetupSpinnerPengeluaran()


//            //      Chart
//            val mPieChart = binding.piechart
//            mPieChart.clearChart()
//
//            mPieChart.addPieSlice(PieModel("Penelitian", 20f, Color.CYAN))
//            mPieChart.addPieSlice(PieModel("Pembangunan", 80f, Color.RED))
//
//
//            mPieChart.startAnimation()
//
//            val mBarChart = binding.barchart
//            mBarChart.clearChart()
//
//
//            mBarChart.addBar(BarModel("2018",140f, -0xedcbaa))
//            mBarChart.addBar(BarModel("2017",1000f, -0xcbcbaa))
//            mBarChart.addBar(BarModel("2016",500f, -0xa9cbaa))
//
//
//            mBarChart.startAnimation()
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

                // Fetch data from 'keuangan/kerjasama/data'
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

                        // Sort listData by date in descending order
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
        db.collection("keuangan")
            .document("pengeluaran")
            .collection("data")
            .get()
            .addOnSuccessListener { documents ->
                val listData2 = ArrayList<Pengeluaran>()
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

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

                // Sort listData2 by date in descending order
                listData2.sortByDescending {
                    dateFormat.parse(it.tahun)
                }

                binding.rcData.setHasFixedSize(true)
                binding.rcData.layoutManager = LinearLayoutManager(requireContext())
                val dataAdapter = PengeluaranAdapter(requireContext(), listData2)
                binding.rcData.adapter = dataAdapter
            }
            .addOnFailureListener { exception ->
                Log.e("error", "Gagal menampilkan data", exception)
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

                // Set up spinner with years


                // Sekarang ambil data dari collectionKerjasama setelah collectionRef selesai
                collectionKerjasama.get()
                    .addOnSuccessListener { kerjasamaDocuments ->
                        for (document in kerjasamaDocuments) {
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

                        // Setelah kedua koleksi berhasil diambil, lanjutkan ke pengolahan data lainnya
                        // atau operasi yang diperlukan.
                        setupYearSpinnerPemasukan()

                        // Draw initial charts with all data
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

                // Set up spinner with years
                setupYearSpinnerPengeluaran()

                // Draw initial charts with all data
                drawCharts(selectedYear)
            }
            .addOnFailureListener { exception ->
                Log.e("fetchData", "Gagal mengambil data", exception)
            }
    }

//    private fun setupYearSpinner() {
//        val years = yearTotalMap.keys.toList()
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.yearSpinner.adapter = adapter
//
//        binding.yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                selectedYear = parent.getItemAtPosition(position).toString()
//                drawCharts(selectedYear)
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Do nothing
//            }
//        }
//    }

    private fun setupYearSpinnerPemasukan() {
        val years = yearTotalMap.keys.toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.yearSpinner.adapter = adapter

        binding.yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedYear = parent.getItemAtPosition(position).toString()
                drawCharts(selectedYear)

                // Update data when year changes
                getDataPemasukan()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
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

                // Update data when year changes
                getDataPengeluaran()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }


    private fun drawCharts(selectedYear: String?) {
        // Clear existing charts
        binding.barchart.clearChart()
        binding.piechart.clearChart()

        // Draw bar chart for total nominal per year
        yearTotalMap.forEach { (tahun, total) ->
            binding.barchart.addBar(BarModel(tahun, total, getRandomColor()))
        }

        // Filter and draw pie chart for selected year
        selectedYear?.let { year ->
            val sourceYearMap = sourceYearTotalMap.filterValues { it.containsKey(year) }
            val totalSelectedYear = sourceYearMap.values.sumByDouble { it[year]?.toDouble() ?: 0.0 }.toFloat()

            sourceYearMap.forEach { (sumber, yearMap) ->
                val total = yearMap[year] ?: 0f
                val persentase = (total / totalSelectedYear) * 100.toFloat()
                binding.piechart.addPieSlice(PieModel("$sumber ($year)", persentase, getRandomColor()))
            }
        }

        // Animate charts
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