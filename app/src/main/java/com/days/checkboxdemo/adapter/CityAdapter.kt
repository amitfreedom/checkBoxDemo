package com.days.checkboxdemo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.days.checkboxdemo.databinding.ListCityBinding
import com.days.checkboxdemo.model.CityModel

class CityAdapter(
    val context: Context,
    var cityList: ArrayList<CityModel>,
    val isSelectAll: Boolean,
    val click: Click
) :

    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private var arraList = ArrayList<String>()
    private var selectPosiiton = ArrayList<Int>()

    class ViewHolder(var binding: ListCityBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListCityBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = intArrayOf(0)

        holder.setIsRecyclable(false)
        holder.binding.chCityName.text = cityList[position].cityName

        holder.binding.chCityName.isChecked = cityList[position].isCitySelected

        if (cityList[position].isCitySelected) {
            data[0] = 1
        } else {

        }




        holder.binding.chCityName.setOnClickListener {
            if (data[0] == 0) {
                data[0] = 1
                click.setOnAddClickListener(cityList[position].cityName, position,cityList[position])

                Log.i("onBindViewHolder: ", arraList.toString())
            } else {
                data[0] = 0
                click.setOnRemoveClickListener(cityList[position].cityName, position)

            }
        }


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getItemCount(): Int {

        return cityList.size
    }

    interface Click {
        fun setOnAddClickListener(cityModel: String, position: Int, cityModel1: CityModel)
        fun setOnRemoveClickListener(cityModel: String, position: Int)
    }

    fun filterList(list: ArrayList<CityModel>) {
        cityList = list
        notifyDataSetChanged()
    }


}