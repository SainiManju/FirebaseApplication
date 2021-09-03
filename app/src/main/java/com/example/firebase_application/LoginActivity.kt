package com.example.firebase_application

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var refRoot: DatabaseReference? = null
    private var ref: DatabaseReference? = null
    lateinit var userNameAuth: String
    var authUserSize: Long? = 0L
    var counter:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        refRoot = FirebaseDatabase.getInstance().reference
        ref = FirebaseDatabase.getInstance().getReference("AuthTable/")
        activity_main_login.setOnClickListener {
            refRoot?.child("AuthTable/")
                ?.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        authUserSize = snapshot.childrenCount
                        ref?.orderByValue()?.addChildEventListener(object : ChildEventListener {
                            override fun onChildAdded(
                                dataSnapshot: DataSnapshot,
                                prevChildKey: String?
                            ) {
                                println(dataSnapshot.value)
                                counter++
                                val userNameAuth: HashMap<String, String> =
                                    dataSnapshot.value as HashMap<String, String>
                                if (userNameAuth["username"]
                                        .equals(editText_username.text.toString()) && userNameAuth["password"]
                                        .equals(login_activity_editText_Password.text.toString())
                                ) {
                                    var intent = Intent(applicationContext,HomeActivity::class.java)
                                    var Id= userNameAuth["Id"].toString()
                                    var id = userNameAuth["id"]
                                    intent.putExtra("key",Id)
                                    intent.putExtra("Key",id)
                                    startActivity(intent)
                                    ref?.orderByValue()?.removeEventListener(this)
                                } else if (counter == authUserSize) {
                                    Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                                }

                            }

                            override fun onChildChanged(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                                TODO("Not yet implemented")
                            }

                            override fun onChildRemoved(snapshot: DataSnapshot) {
                                TODO("Not yet implemented")
                            }

                            override fun onChildMoved(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                                TODO("Not yet implemented")
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
}

