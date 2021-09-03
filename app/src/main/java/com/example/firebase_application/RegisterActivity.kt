package com.example.firebase_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.user_Description_EditText


class RegisterActivity : AppCompatActivity() {
    var refRoot= FirebaseDatabase.getInstance().reference
//    var list= arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        activity_main_register.setOnClickListener {
            var key=refRoot.push().key.toString()
            var user=User(
                editText_firstname.text.toString(),
                editText_email.text.toString(),
                editText_password.text.toString(),
                user_phone_EditText.text.toString(),
                user_Description_EditText.text.toString(),
            )
//            var checkRef=FirebaseDatabase.getInstance().getReference("users")
//            checkRef.addValueEventListener(object :ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    var child=snapshot.children
//                    for (data in child)
//                    {
//                     var user=
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//
//            })
            refRoot.child("users").child(key).setValue(user)
            val ref= FirebaseDatabase.getInstance().getReference("AuthTable/").child(key)
            ref.setValue(AuthTable(key, user.email, user.password.toString()))
            intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"Check Database", Toast.LENGTH_SHORT).show()
        }
    }

}
