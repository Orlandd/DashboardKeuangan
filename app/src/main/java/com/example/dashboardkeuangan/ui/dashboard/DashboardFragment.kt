package com.example.dashboardkeuangan.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.databinding.FragmentDashboardBinding
import org.eazegraph.lib.models.BarModel
import org.eazegraph.lib.models.PieModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var rvData : RecyclerView
    private val listData =  ArrayList<Pemasukan>()

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


        binding.pemasukan.setOnClickListener {
            val listData =  ArrayList<Pemasukan>()
            listData.clear()
            binding.rcData.setHasFixedSize(true)
            binding.rcData.layoutManager = LinearLayoutManager(requireContext())

            listData.add(Pemasukan("2018", "Mahasiswa", "UKT", "190000"))
            listData.add(Pemasukan("2017", "Mahasiswa", "UKT", "190000"))
            listData.add(Pemasukan("2016", "Mahasiswa", "UKT", "180000"))
            listData.add(Pemasukan("2015", "Mahasiswa", "UKT", "140000"))
            listData.add(Pemasukan("2014", "Mahasiswa", "UKT", "150000"))

            val dataAdapter = PemasukanAdapter(requireContext(), listData)

            binding.rcData.adapter = dataAdapter



            //      Chart
            val mPieChart = binding.piechart
            mPieChart.clearChart()

            mPieChart.addPieSlice(PieModel("Mahasiswa", 100f, Color.parseColor("#FE6DA8")))
//        mPieChart.addPieSlice(PieModel("Sleep", 25f, Color.parseColor("#56B7F1")))
//        mPieChart.addPieSlice(PieModel("Work", 35f, Color.parseColor("#CDA67F")))
//        mPieChart.addPieSlice(PieModel("Eating", 9f, Color.parseColor("#FED70E")))

            mPieChart.startAnimation()

            val mBarChart = binding.barchart
            mBarChart.clearChart()


            mBarChart.addBar(BarModel("2014",150000f, -0xedcbaa))
            mBarChart.addBar(BarModel("2015",140000f, -0xcbcbaa))
            mBarChart.addBar(BarModel("2016",180000f, -0xa9cbaa))
            mBarChart.addBar(BarModel("2017",190000f, -0x78c0aa))
            mBarChart.addBar(BarModel("2018",190000f, -0xa9480f))

            mBarChart.startAnimation()
        }

        binding.pengeluaranbtn.setOnClickListener {
            val listData2 =  ArrayList<Pengeluaran>()
            listData.clear()
            binding.rcData.setHasFixedSize(true)
            binding.rcData.layoutManager = LinearLayoutManager(requireContext())

            listData2.add(Pengeluaran("2018",  "Penelitian", "90"))
            listData2.add(Pengeluaran("2018",  "Penelitian", "50"))
            listData2.add(Pengeluaran("2017",  "Pembangunan", "1000"))
            listData2.add(Pengeluaran("2016",  "Pemabngunan", "500"))


            val dataAdapter = PengeluaranAdapter(requireContext(), listData2)

            binding.rcData.adapter = dataAdapter



            //      Chart
            val mPieChart = binding.piechart
            mPieChart.clearChart()

            mPieChart.addPieSlice(PieModel("Penelitian", 20f, Color.CYAN))
            mPieChart.addPieSlice(PieModel("Pembangunan", 80f, Color.RED))


            mPieChart.startAnimation()

            val mBarChart = binding.barchart
            mBarChart.clearChart()


            mBarChart.addBar(BarModel("2018",140f, -0xedcbaa))
            mBarChart.addBar(BarModel("2017",1000f, -0xcbcbaa))
            mBarChart.addBar(BarModel("2016",500f, -0xa9cbaa))


            mBarChart.startAnimation()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}