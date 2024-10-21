package com.example.eje11_editandorecycler.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.eje11_editandorecycler.R
import com.example.eje11_editandorecycler.model.UserData

class UserAdapter(val c:Context,val  userList: ArrayList<UserData>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val v: View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        var mMenus:ImageView
        init {
            name = v.findViewById<TextView>(R.id.mTitle)
            mbNum = v.findViewById<TextView>(R.id.mSubTitle)
            mMenus = v.findViewById<ImageView>(R.id.mMenus)
            mMenus.setOnClickListener {
                // implementar acciones para el menu
                popupMenus(it)
            }
        }

        private fun popupMenus(v:View) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText -> {
                        // implementar acción para editar
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                        val title = v.findViewById<TextView>(R.id.textView)
                        val name = v.findViewById<TextView>(R.id.userName)
                        val number = v.findViewById<TextView>(R.id.userNo)

                        title.text = "Editar nformación de usuario" //cambiar el titulo
                        //cargar los datos de usuario en los campos a editar

                        name.setText(position.userName)
                        number.setText(position.userMb)

                            AlertDialog.Builder(c)
                                .setView(v)
                                .setPositiveButton("OK"){
                                    dialog,_->
                                    position.userName = name.text.toString()
                                    position.userMb = number.text.toString()
                                    notifyDataSetChanged()
                                    Toast.makeText(c,"Usuario editado!", Toast.LENGTH_SHORT).show()

                                    dialog.dismiss()
                                }
                                .setNegativeButton("Cancelar"){
                                    dialog,_->
                                    dialog.dismiss()
                                    Toast.makeText(c,"Cancelado!", Toast.LENGTH_SHORT).show()
                                }
                                .create()
                                .show()

                        Toast.makeText(c,"Seleccionado editar!", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.delete -> {
                        // implementar acción para borrar
                        AlertDialog.Builder(c)
                            .setTitle("Borrar usuario?")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Seguro de borrar al usuario?")
                            .setPositiveButton("OK"){
                                dialog,_->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Usuario borrado!", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancelar"){
                                dialog,_->
                                dialog.dismiss()
                                Toast.makeText(c,"Cancelado!", Toast.LENGTH_SHORT).show()
                            }
                            .create()
                            .show()
                        Toast.makeText(c,"Seleccionado borrar!", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }
                    else -> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu, true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.name.text = newList.userName
        holder.mbNum.text = newList.userMb
    }
}