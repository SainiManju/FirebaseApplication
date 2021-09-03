package com.example.firebase_application

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.fragment_users_details.*

class UsersDetails : Fragment() {
    var ref = FirebaseDatabase.getInstance().getReference("users")
    var userList = ArrayList<User>()
    var key = ref.push().key
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity:HomeActivity=activity as HomeActivity
        key=activity.readKey()
        return inflater.inflate(R.layout.fragment_users_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//       ref.addValueEventListener(object :ValueEventListener
//       {
//           override fun onDataChange(snapshot: DataSnapshot) {
//               activity_main_TextView_firstname.text=snapshot.child("name").value.toString()
//               activity_main_user_phone_TextView.text=snapshot.child("phoneNumber").value.toString()
//               activity_main_Text_email.text=snapshot.child("email").value.toString()
//               activity_main_user_Description_TextView.text=snapshot.child("description").value.toString()
//           }
//
//           override fun onCancelled(error: DatabaseError) {
//
//           }
//
//       })
        ref.orderByChild("users/").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.value?.let {
                    var data: HashMap<String, String> = it as HashMap<String, String>
                    userList.add(User(
                        data["name"],
                        data["email"],
                        data["password"],
                        data["phoneNumber"],
                        data["description"],
                    ))
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
       add_users.setOnClickListener {
           var key: String = ref.push().key.toString()
           var isExist = false
           for (n in 0 until userList.size) {
               Log.d("TAG", userList[n].name!!)
               Log.d("TAG", (userList[n].name == activity_main_TextView_firstname.text.toString()).toString())
               Log.d("TAG", activity_main_TextView_firstname.text.toString())
               if (userList[n].name == activity_main_TextView_firstname.text.toString()) {
                   Log.d("TAG", userList[n].name!!)
                   Toast.makeText(context, "user already exists", Toast.LENGTH_SHORT).show()
                   isExist = true
               }
           }

//                          if (!isExist) {
//                              val user = User(
//                                  key,activity_main_TextView_firstname.text.toString() ,
//                                  activity_main_user_phone_TextView.text.toString(),
//                                  activity_main_user_Description_TextView.text.toString(),
//                                  activity_main_Text_email.text.toString()
//                                  )
//                              ref.child(key).setValue(user)
//                              Toast.makeText(context, "user Added", Toast.LENGTH_SHORT).show()
//                          }
       }
        val spinner: Spinner = view.findViewById(R.id.users_spinner)
        var list = ArrayList<String>()
//        var key = ref.push().key.toString()
        for(user in userList){
            list.add(user.name!!)
        }
        val spinnerAdapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            list
        )
        spinner.adapter = spinnerAdapter
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (list.isNotEmpty())
                    list.clear()

                for (data in snapshot.children) {
                    list.add(data.child("name").value.toString())
                }
                spinnerAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}

