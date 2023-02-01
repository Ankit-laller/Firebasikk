package com.example.firebasikk.Activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasikk.Adapter.EmpAdapter
import com.example.firebasikk.Models.EmployeeModel
import com.example.firebasikk.R
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var empList:ArrayList<EmployeeModel>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        empRecyclerView =findViewById(R.id.emprcv)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.hasFixedSize()
        tvLoadingData =findViewById(R.id.tvLoading)

        empList = arrayListOf()
        getEmployeeData()

    }

    private fun getEmployeeData() {
        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employee")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if(snapshot.exists()){
                    for(empSnap in snapshot.children){
                        val empData = empSnap.getValue(EmployeeModel::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter = EmpAdapter(empList)
                    empRecyclerView.adapter = mAdapter
                    mAdapter.setOnClickListener2(object :EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,EmpDetailActivity::class.java)
                            Toast.makeText(this@FetchingActivity,"$position is clicked",Toast.LENGTH_SHORT).show()
                            intent.putExtra("empId",empList[position].empID)
                            intent.putExtra("empName",empList[position].empName)
                            intent.putExtra("empAge",empList[position].empAge)
                            intent.putExtra("empSalary","90000")
                            startActivity(intent)
                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

    }
}