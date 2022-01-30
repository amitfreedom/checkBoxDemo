package com.days.checkboxdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.days.checkboxdemo.adapter.CityAdapter
import com.days.checkboxdemo.databinding.ActivityMainBinding
import com.days.checkboxdemo.model.CityModel
import android.text.Editable

import android.text.TextWatcher
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CityAdapter
    private lateinit var cityList: ArrayList<CityModel>
    private var selectList = ArrayList<String>()
    private var isSelectAll = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCityList()

        adapter = CityAdapter(this, cityList, isSelectAll, object : CityAdapter.Click {
            override fun setOnAddClickListener(
                cityModel: String,
                position: Int,
                cityModel1: CityModel
            ) {
                if (!selectList.contains(cityModel)) {
                    selectList.add(cityModel)
                    var index = cityList.indexOf(cityModel1)
                    cityList[index].isCitySelected = true
                }
                var listTOComma = selectList.toString().replace("[", "").replace("]", "")
                binding.txtSelectCity.text = listTOComma

            }

            override fun setOnRemoveClickListener(cityModel: String, position: Int) {

                selectList.remove(cityModel)
                cityList[position].isCitySelected = false


                if (selectList.size > 0) {
                    var listTOComma = selectList.toString().replace("[", "").replace("]", "")
                    binding.txtSelectCity.text = listTOComma
                } else {
                    binding.txtSelectCity.text = "Selected City"
                }
            }


        })
        binding.recyclerCity.adapter = adapter

        binding.chSelectAll.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                setAllCitySelected(true)
            } else {
                setAllCitySelected(false)
            }
        }

        searchItem()
    }


    private fun setAllCitySelected(b: Boolean) {
        for (i in 0 until cityList.size) {
            var cityModel = CityModel()
            cityModel.cityName = "$i City Name"
            cityModel.isCitySelected = b
            cityList.set(i, cityModel)
        }

        if (b) {
            selectList.clear()
            for (i in 0 until cityList.size) {
                selectList.add(cityList[i].cityName)
            }
            var listTOComma = selectList.toString().replace("[", "").replace("]", "")
            binding.txtSelectCity.text = listTOComma
        } else {
            selectList.clear()
            binding.txtSelectCity.text = "Show Selected City"
        }

        isSelectAll = b
        adapter.filterList(cityList)

//        adapter.notifyDataSetChanged()
    }

    private fun initCityList() {
        cityList = ArrayList()
        for (i in 0..30) {
            var cityModel = CityModel()
            cityModel.cityName = "$i City Name"
            cityModel.isCitySelected = false
            cityList.add(cityModel)

        }
    }


    private fun searchItem() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(binding.edtSearch.text.toString())
            }
        })
    }

    private fun filter(text: String) {
        val filterList: ArrayList<CityModel> = ArrayList()
        for (items in cityList) {
            if (items.cityName
                    .startsWith(text,true)
            ) {
                filterList.add(items)
            }
        }
        adapter.filterList(filterList)
    }

}