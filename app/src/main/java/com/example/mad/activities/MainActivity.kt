package com.example.mad.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.mad.R

class MainActivity : AppCompatActivity() {

    //private lateinit var menu: Menu
    private lateinit var btnchar: Button
    private lateinit var btnCharFetch: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        //menu =findViewById(R.id.menu_char)
        btnchar=findViewById(R.id.addChar)
        btnCharFetch=findViewById(R.id.fetchChar)



        btnchar.setOnClickListener{
            val intent = Intent(this, RegisterCharity::class.java)
            startActivity(intent)
        }


        btnCharFetch.setOnClickListener{
            val intent = Intent(this, fetchingActivity::class.java)
            startActivity(intent)
        }





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_char){
            val intent = Intent(this, RegisterCharity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
