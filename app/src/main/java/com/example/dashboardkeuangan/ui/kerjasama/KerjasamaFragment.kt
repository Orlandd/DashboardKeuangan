package com.example.dashboardkeuangan.ui.kerjasama

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardkeuangan.R


class KerjasamaFragment : Fragment() {

    private lateinit var datatrend: RecyclerView
    private val list = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_kerjasama, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datatrend = view.findViewById(R.id.KerjasamaItemListAwal)
        datatrend.setHasFixedSize(true)

        list.clear()
        list.addAll(getProductList())
        showRecyclerList()
    }

    private fun getProductList(): ArrayList<Data> {
        val productTahun = resources.getStringArray(R.array.data_tahun)
        val productJumlahPerTahun = resources.getStringArray(R.array.data_nominalpertahun)
        val listmasuk = ArrayList<Data>()
        for (i in productTahun.indices) {
            val masuk = Data(productTahun[i], productJumlahPerTahun[i])
            listmasuk.add(masuk)
        }
        return listmasuk
    }

    private fun showRecyclerList() {
        datatrend.layoutManager = LinearLayoutManager(context)
        val listPemasukanAdapter = ListKerjasamaAdapter(list)
        datatrend.adapter = listPemasukanAdapter

        listPemasukanAdapter.setOnItemClickCallback(object : ListKerjasamaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Data) {
                showSelectedData(data)
            }

        })
    }
    private fun showSelectedData(data: Data) {
        val intent = Intent(context, KerjasamaDetailed::class.java)
        intent.putExtra("DATA", data)
        Toast.makeText(context, data.tahun + " is selected", Toast.LENGTH_SHORT).show()
        startActivity(intent)

    }
}
