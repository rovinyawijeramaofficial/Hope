package com.example.mad.activities

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mad.R
import com.example.mad.model.CharityModel
import com.google.firebase.database.FirebaseDatabase

class charityDetails : AppCompatActivity() {

    private lateinit var tvCharID: TextView
    private lateinit var tvCharName: TextView
    private lateinit var tvCharFou: TextView
    private lateinit var tvCharEmail: TextView
    private lateinit var tvCharPhone: TextView
    private lateinit var tvCharMotive: TextView
    private lateinit var btnCharEdit: Button
    private lateinit var btnCharDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_details)

        initView()
        setValuesToViews()

        btnCharEdit.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("charId").toString(),
                intent.getStringExtra("charName").toString()


            )
        }
        btnCharDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("charId").toString()
            )
        }
    }

    private fun initView() {
        tvCharID= findViewById(R.id.pCharID)
        tvCharName= findViewById(R.id.pCharName)
        tvCharFou= findViewById(R.id.pCharFou)
        tvCharEmail= findViewById(R.id.pCharEmail)
        tvCharPhone= findViewById(R.id.pCharPhone)
        tvCharMotive= findViewById(R.id.pCharMotive)
        btnCharEdit= findViewById(R.id.EditChar)
        btnCharDelete=findViewById(R.id.DeleteChar)
    }

    private fun setValuesToViews(){

        tvCharID.text=intent.getStringExtra("charId")
        tvCharName.text=intent.getStringExtra("charName")
        tvCharFou.text=intent.getStringExtra("charFou")
        tvCharEmail.text=intent.getStringExtra("charEmail")
        tvCharPhone.text=intent.getStringExtra("charPhone")
        tvCharMotive.text=intent.getStringExtra("charMotive")

    }

    private fun openUpdateDialog(
        charId: String,
        charName:String
    ){

        val mDialog = AlertDialog.Builder(this)
        val inflater= layoutInflater
        val mDialogView =inflater.inflate(R.layout.edit_profile,null)

        mDialog.setView(mDialogView)

        val etCharName = mDialogView.findViewById<EditText>(R.id.eCharName)
        val etCharEmail = mDialogView.findViewById<EditText>(R.id.eCharEmail)
        val etCharPhone= mDialogView.findViewById<EditText>(R.id.eCharPhone)
        val etCharMotive = mDialogView.findViewById<EditText>(R.id.eCharMotive)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.EditButton)

        etCharName.setText(intent.getStringExtra("charName").toString())
        etCharEmail.setText(intent.getStringExtra("charEmail").toString())
        etCharPhone.setText(intent.getStringExtra("charPhone").toString())
        etCharMotive.setText(intent.getStringExtra("charMotive").toString())


        mDialog.setTitle("Updating $charName Record")

        val alertDialog= mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            updateCharData(
                charId,
                etCharName.text.toString(),
                etCharEmail.text.toString(),
                etCharPhone.text.toString(),
                etCharMotive.text.toString()


            )
            Toast.makeText(applicationContext,"Employee Data Updated", Toast.LENGTH_LONG).show()

            tvCharName.text = etCharName.text.toString()
            tvCharEmail.text = etCharEmail.text.toString()
            tvCharPhone.text = etCharPhone.text.toString()
            tvCharMotive.text = etCharMotive.text.toString()


            alertDialog.dismiss()
        }


    }

    private fun updateCharData(
        id:String,
        name:String,
        email:String,
        phone:String,
        motive:String


    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Charity").child(id)
        val charInfo = CharityModel(id,name,email,phone,motive)
        dbRef.setValue(charInfo)
    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Charity").child(id)
        val mTask =dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Charity Data Deleted",Toast.LENGTH_LONG).show()
            val intent =Intent(this, fetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()
        }
    }

}