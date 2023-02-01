package com.example.firebasikk.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.firebasikk.Models.EmployeeModel
import com.example.firebasikk.R
import org.w3c.dom.Text

class EmpAdapter(private val empList:ArrayList<EmployeeModel>):
    RecyclerView.Adapter<EmpAdapter.ViewHolder>(){

    private lateinit var mListener :onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnClickListener2(clickListener:onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.emp_list_item,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = empList[position]
        holder.tvEmpName.text = currentEmp.empName
    }

    override fun getItemCount(): Int {
        return empList.size
    }
    class ViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val tvEmpName :TextView = itemView.findViewById(R.id.tvEmpName)
        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
    }