package com.example.firebase_application


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class HomeActivity : AppCompatActivity() {
//    lateinit var key:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if(savedInstanceState==null)
        {
            var transaction=supportFragmentManager.beginTransaction().add(R.id.home_fragment,HomeFragment()).commit()
        }
    }

    fun readKey():String{
        return intent.getStringExtra("key").toString()
    }
}