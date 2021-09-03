package com.example.firebase_application

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_add_club.*
import kotlinx.android.synthetic.main.fragment_add_country.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList
import java.util.jar.Attributes

class AddClub : Fragment() {
    var refRoot = FirebaseDatabase.getInstance().getReference("ClubData")
    var authUserSize: Long? = 0L
    var counter: Long = 0
    var clubListFirebase = ArrayList<Club>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_club, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refRoot.orderByChild("ClubData").addChildEventListener(object :
            ChildEventListener {
            override fun onChildAdded(
                datasnapshot: DataSnapshot,
                previousChildName: String?
            ) {

                datasnapshot.value?.let {
                    var data: HashMap<String, String> = it as HashMap<String, String>
                    clubListFirebase.add(Club(data["id"].toString(), data["name"].toString())
                    )
                }

            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        addClubBtn.setOnClickListener(object:View.OnClickListener {
            override fun onClick(p0: View?) {
                var key: String = refRoot.push().key.toString()
                var isExist = false
                for (n in 0 until  clubListFirebase.size) {
                    Log.d("Club Name", clubListFirebase.toString())
                    Log.d("Edit Name", EditText_add_club.text.toString())
                    if(clubListFirebase[n].name.equals(EditText_add_club.text.toString())) {
                        Toast.makeText(context, "Club already exists", Toast.LENGTH_SHORT).show()
                        isExist = true
                    }

                }
                if(!isExist) {
                    val club = Club(key, EditText_add_club.text.toString())
                    refRoot.child(key).setValue(club)
                    Toast.makeText(context, "Club Added", Toast.LENGTH_SHORT).show()
                }
            }
            })

        val spinner: Spinner = view.findViewById(R.id.mySpinner)
        var key = refRoot.push().key
        val clubList = ArrayList<String>()
        for(club in clubListFirebase) {
            clubList.add(club.name!!)
        }
        val spinnerAdapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            clubList
        )
        spinner.adapter = spinnerAdapter
        refRoot.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (clubList.isNotEmpty())
                    clubList.clear()

                for (data in snapshot.children) {
                    clubList.add(data.child("name").value.toString())
                }
                spinnerAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
    }









