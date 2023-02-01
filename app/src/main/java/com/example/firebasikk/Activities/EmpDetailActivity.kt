package com.example.firebasikk.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebasikk.Models.EmployeeModel
import com.example.firebasikk.R
import com.google.firebase.database.FirebaseDatabase


class EmpDetailActivity : AppCompatActivity() {

    private lateinit var tvEmpId :TextView
    private lateinit var tvEmpName :TextView
    private lateinit var tvEmpAge :TextView
    private lateinit var tvEmpSalary :TextView
    private lateinit var btnUpdate :Button
    private lateinit var btnDelete :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_detail)

        initView()
        setValuesToViews()
        btnUpdate.setOnClickListener{
            Toast.makeText(this,"only update clicked", Toast.LENGTH_SHORT).show()
            openUpdateDialog(
                intent.getStringExtra("empId"),
                intent.getStringExtra("empName")
            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("empId")
            )
        }
    }

    private fun deleteRecord(id: String?) {
        val dbref= id?.let { FirebaseDatabase.getInstance().getReference("Employee").child(it) }
        val mTask = dbref?.removeValue()
        mTask?.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }?.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpAge = findViewById(R.id.tvEmpAge)
        tvEmpSalary = findViewById(R.id.tvEmpSalary)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun openUpdateDialog(empId: String?, empName: String?) {
        val mDialog = AlertDialog.Builder(this)
        val inflater= layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_box, null)

        mDialog.setView(mDialogView)

        val etEmpName= mDialogView.findViewById<EditText>(R.id.upName)
        val etEmpAge= mDialogView.findViewById<EditText>(R.id.upAge)
        val etEmpSalary= mDialogView.findViewById<EditText>(R.id.upSalary)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.updateDataBtn)

        etEmpName.setText(intent.getStringExtra("empName")).toString()
        etEmpAge.setText(intent.getStringExtra("empAge")).toString()
        etEmpSalary.setText(intent.getStringExtra("empSalary")).toString()

        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            if (empId != null) {
                updateEmpData(
                    empId,
                    etEmpName.text.toString(),
                    etEmpAge.text.toString(),
                    etEmpSalary.text.toString()
                )
            }
        }
        Toast.makeText(this,"Data has updated", Toast.LENGTH_SHORT).show()
        tvEmpName.text = etEmpName.text.toString()
        tvEmpAge.text = etEmpAge.text.toString()
        tvEmpSalary.text =etEmpSalary.text.toString()
        alertDialog.dismiss()
    }

    private fun updateEmpData(
        id:String,
        name:String,
        Age:String,
        Salary:String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employee").child(id)
        val empInfo = EmployeeModel(id,name,Age, Salary)
        dbRef.setValue(empInfo)
    }



    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpAge.text = intent.getStringExtra("empAge")
        tvEmpSalary.text = intent.getStringExtra("empSalary")

    }
}