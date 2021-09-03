package com.example.firebase_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import java.security.Key


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity_home_viewProfile_btn.setOnClickListener {
            val transaction= parentFragmentManager.beginTransaction()
                ?.replace(R.id.home_fragment,UsersDetails()).commit()
        }
       activity_home_Admin_btn.setOnClickListener {
           val fragmentTransaction = parentFragmentManager?.beginTransaction()?.replace(R.id.home_fragment,AdminFragment())?.commit()

       }
        activity_home_AddCountry_btn.setOnClickListener{
            val fragmentTransaction = parentFragmentManager?.beginTransaction()?.replace(R.id.home_fragment,AddCountry())?.commit()

        }
        activity_home_AddClub_btn.setOnClickListener {
            val fragmentTransaction = parentFragmentManager?.beginTransaction()?.replace(R.id.home_fragment,AddClub())?.commit()

        }
        activity_home_ViewTeam_btn.setOnClickListener {
            val fragmentTransaction = parentFragmentManager?.beginTransaction().replace(R.id.home_fragment,ViewTeam()).commit()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}