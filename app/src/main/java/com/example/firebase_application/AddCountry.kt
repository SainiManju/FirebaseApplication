package com.example.firebase_application

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_add_country.*
import java.util.ArrayList

class AddCountry : Fragment() {
    var refRoot = FirebaseDatabase.getInstance().getReference("CountriesData")
    var countryListFirebase = ArrayList<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refRoot.orderByChild("CountriesData").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.value?.let {
                    var data: HashMap<String, String> = it as HashMap<String, String>
                    countryListFirebase.add(Country(data["id"].toString(), data["name"].toString()))
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
        btnadd.setOnClickListener(object:View.OnClickListener {
            override fun onClick(p0: View?) {
                var key: String = refRoot.push().key.toString()
                var isExist = false
                for (n in 0 until countryListFirebase.size) {
                    if (countryListFirebase[n].name.equals(editText_add_country.text.toString())) {
                        Log.d("Country Name", countryListFirebase.toString())
                        Log.d("Edit Name", editText_add_country.text.toString())
                        Toast.makeText(context, "Country already exists", Toast.LENGTH_SHORT).show()
                        isExist = true
                    }
                }
                if (!isExist) {
                    val country = Country(key, editText_add_country.text.toString())
                    refRoot.child(key).setValue(country)
                    Toast.makeText(context, "Country Added", Toast.LENGTH_SHORT).show()
                }
            }
        })
        val spinner: Spinner = view.findViewById(R.id.spinnerdata)
        var countryList = ArrayList<String>()
//        var key = refRoot.push().key.toString()
        for(country in countryListFirebase){
            countryList.add(country.name!!)
        }
        val spinnerAdapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            countryList
        )
        spinner.adapter = spinnerAdapter
        refRoot.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (countryList.isNotEmpty())
                    countryList.clear()

                for (data in snapshot.children) {
                    countryList.add(data.child("name").value.toString())
                }
                spinnerAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}