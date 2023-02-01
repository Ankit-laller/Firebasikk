package com.example.firebasikk.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasikk.Models.EmployeeModel
import com.example.firebasikk.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity(){
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etSalary: EditText
    private lateinit var saveBtn :Button

    private lateinit var dbRef : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etAge = findViewById(R.id.etAge)
        etSalary = findViewById(R.id.etSalary)
        saveBtn = findViewById(R.id.saveBtn)
        etName = findViewById(R.id.fillname)

        dbRef = FirebaseDatabase.getInstance().getReference("Employee")

        saveBtn.setOnClickListener {
            insetData()
        }

    }

    private fun insetData() {
        val name = etName.text.toString()
        val age= etAge.text.toString()
        val salary =etSalary.text.toString()

        if(name.isEmpty()){

            etName.error ="Name is not filled"

        }else if(age.isEmpty()){
            etAge.error ="Age is not filled"
        }else if(salary.isEmpty()){
            etSalary.error ="Salary is not filled"
        }
        else{
            val empId =dbRef.push().key!!

            val employee = EmployeeModel(empId,name,age,salary)

            dbRef.child(empId).setValue(employee)
                .addOnCompleteListener{
                    Toast.makeText(this,"Data inserted", Toast.LENGTH_SHORT).show()
                    etAge.text.clear()
                    etName.text.clear()
                    etSalary.text.clear()
                }.addOnFailureListener{ err->
                    Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
        }



    }


}