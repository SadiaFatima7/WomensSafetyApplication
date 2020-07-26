package com.example.womensafetyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class DataAdapter(val mCtx:Context,val layoutResId:Int,val DataList:List<Data>)
    :ArrayAdapter <Data> (mCtx, layoutResId, DataList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater= LayoutInflater.from(mCtx)
        val view:View=layoutInflater.inflate(layoutResId,null)
        val textViewName=view.findViewById<TextView>(R.id.textViewName)

        val data=DataList[position]
        textViewName.text=data.PhoneNo
        return view
    }
}