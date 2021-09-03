package com.example.firebase_application

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_add_country.*
import kotlinx.android.synthetic.main.fragment_view_team.*
import java.util.ArrayList

class ViewTeam : Fragment() {
    var teamroot = FirebaseDatabase.getInstance().getReference("ViewTeam")

    //    var countryListFirebase = ArrayList<Country>()
    var teamsplayers = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsplayers.add("player1")
        teamsplayers.add("player2")
        teamsplayers.add("player3")
        teamsplayers.add("player2")
        teamsplayers.add("player3")
        teamsplayers.add("player2")
        teamsplayers.add("player3")
        teamroot.setValue(teamsplayers)
        AddPlayer_btn.setOnClickListener {
            var player = teamroot.push().key
            teamroot.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    teamsplayers.add("player1")
                    teamsplayers.add("player2")
                    teamsplayers.add("player3")
                    teamsplayers.add("player2")
                    teamsplayers.add("player3")
                    teamsplayers.add("player2")
                    teamsplayers.add("player3")
                    Log.d("TAG", teamsplayers.toString())


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}

