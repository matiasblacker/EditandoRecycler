package com.example.eje11_editandorecycler

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eje11_editandorecycler.model.UserData
import com.example.eje11_editandorecycler.view.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList = ArrayList()
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)

        userAdapter = UserAdapter(this,userList)

        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter
        //dialogo para agregar y editar
        addsBtn.setOnClickListener{
            addInfo()
        }
    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_item, null)
        // establecer vista
        val userName = view.findViewById<EditText>(R.id.userName)
        val userNo = view.findViewById<EditText>(R.id.userNo)

        val addialog = AlertDialog.Builder(this)
        addialog.setView(view)
        addialog.setPositiveButton("OK"){
            dialog,_->
            val newUserName = userName.text.toString()
            val newUserNo = userNo.text.toString()
            userList.add(UserData(newUserName, "Tel: $newUserNo"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Usuario guardado!", Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }
        addialog.setNegativeButton("Cancelar"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this, "Cancelado!", Toast.LENGTH_SHORT).show()

        }
        addialog.create()
        addialog.show()
    }


}