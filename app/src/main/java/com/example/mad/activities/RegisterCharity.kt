package com.example.mad.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.mad.model.CharityModel
import com.example.mad.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterCharity : AppCompatActivity() {

    private lateinit var submitBtnC: Button
    private lateinit var dbRef:DatabaseReference

    private lateinit var nameEditText: EditText
    private lateinit var nameEditFounder: EditText
    private lateinit var emailEditText : EditText
    private lateinit var nameEditPhone: EditText
    private lateinit var nameEditMotive:EditText
    private lateinit var checkBox: CheckBox




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_charity)

        nameEditText = findViewById(R.id.nameEditText)
        nameEditFounder = findViewById(R.id.nameEditFounder)
        emailEditText = findViewById(R.id.emailEditText)
        nameEditPhone = findViewById(R.id.nameEditPhone)
        nameEditMotive = findViewById(R.id.nameEditMotive)
        checkBox= findViewById(R.id.checkBox)
        submitBtnC=findViewById(R.id.submitBtnC)


        dbRef = FirebaseDatabase.getInstance().getReference("charity")


        submitBtnC.setOnClickListener {
            saveCharityData()
        }


    }

    private fun saveCharityData() {
        val charName = nameEditText.text.toString()
        val charFou = nameEditFounder.text.toString()
        val charEmail = emailEditText.text.toString()
        val charPhone = nameEditPhone.text.toString()
        val charMot = nameEditMotive.text.toString()


        if(charName.isEmpty()){
            nameEditText.error = "Please Enter Charity name"
        }

        if(charFou.isEmpty()){
            nameEditFounder.error = "Please Enter Charity Founder name"
        }

        if(charEmail.isEmpty()){
            emailEditText.error = "Please Enter Email"
        }

        if(charPhone.isEmpty()){
            nameEditPhone.error = "Please Enter Phone Number"
        }
        if(charMot.isEmpty()){
            nameEditMotive.error = "Please enter the motivation of the charity"
        }

        if(!(checkBox.isChecked)){
            Toast.makeText(this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show()
        }


        val charId = dbRef.push().key!!

        val charity = CharityModel(charId ,charName,charFou,charEmail,charPhone,charMot)

        dbRef.child(charId).setValue(charity)
            .addOnCompleteListener {
                Toast.makeText(this,"Data Inserted Successfully", Toast.LENGTH_LONG).show()

                nameEditText.text.clear()
                emailEditText.text.clear()
                nameEditFounder.text.clear()
                nameEditPhone.text.clear()
                nameEditMotive.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()

            }

    }
}