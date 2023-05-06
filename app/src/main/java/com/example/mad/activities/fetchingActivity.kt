package com.example.mad.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.R
import com.example.mad.adapter.CharityAdapter
import com.example.mad.model.CharityModel
import com.google.firebase.database.*

class fetchingActivity : AppCompatActivity() {

    private lateinit var charRecycleView: RecyclerView
    private lateinit var charList: ArrayList<CharityModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        charRecycleView =findViewById(R.id.rvCharity)
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


                    mAdapter.setOnItemClickListener(object : CharityAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent =  Intent(this@fetchingActivity,charityDetails::class.java)


                            intent.putExtra("charId",charList[position].charId)
                            intent.putExtra("charName",charList[position].charName)
                            intent.putExtra("charFou",charList[position].charFou)
                            intent.putExtra("charEmail",charList[position].charEmail)
                            intent.putExtra("charPhone",charList[position].charPhone)
                            intent.putExtra("charMotive",charList[position].charMot)
                            startActivity(intent)

                        }

                    })

                    charRecycleView.visibility= View.VISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}