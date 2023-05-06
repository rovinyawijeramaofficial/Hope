package com.example.mad.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.R
import com.example.mad.adapter.CharityAdapter
import com.example.mad.model.CharityModel
import com.google.firebase.database.*

class fetchData : AppCompatActivity() {

    private lateinit var charRecycleView: RecyclerView
    private lateinit var charList: ArrayList<CharityModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_data)


        charRecycleView =findViewById(R.id.rvCharity2)
        charRecycleView.layoutManager= LinearLayoutManager(this)
        charRecycleView.setHasFixedSize(true)

        charList= arrayListOf <CharityModel>()


        getCharityData()
    }

    private fun getCharityData() {
        charRecycleView.visibility= View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("charity")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                charList.clear()
                if(snapshot.exists()){
                    for(charSnap in snapshot.children){
                        val charData = charSnap.getValue(CharityModel::class.java)
                        charList.add(charData!!)
                    }
                    val mAdapter = CharityAdapter(charList)
                    charRecycleView.adapter=mAdapter

                    charRecycleView.visibility= View.VISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}