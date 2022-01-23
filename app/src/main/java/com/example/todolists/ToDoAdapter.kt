package com.example.todolists

import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(
    private val toDos: MutableList<ToDo>
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    inner class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvToDoTitle = itemView.findViewById<TextView>(R.id.tvToDoTitle)
        val cbDone = itemView.findViewById<CheckBox>(R.id.cbDone)
        val view_back = itemView.findViewById<ConstraintLayout>(R.id.view_back)
        val btnDelete = itemView.findViewById<Button>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ToDoViewHolder(view)

    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {

        holder.tvToDoTitle.text = toDos[position].title
        holder.cbDone.isChecked = toDos[position].isChecked

        //initial background ng view
        if (holder.cbDone.isChecked) {
            holder.view_back.setBackgroundResource(R.drawable.is_checked_bg);
        } else {
            holder.view_back.setBackgroundResource(R.drawable.btn_background);
        }

        //when checkbox is clicked
        holder.cbDone.setOnClickListener {
            if (holder.cbDone.isChecked) {
                toDos[position].isChecked = true //kung nakacheck ang checkbox edi dapat true na rin sa list
                holder.view_back.setBackgroundResource(R.drawable.is_checked_bg);
            } else {
                toDos[position].isChecked = false
                holder.view_back.setBackgroundResource(R.drawable.btn_background);
            }
        }

        //when 'x' button is clicked
        holder.btnDelete.setOnClickListener {
            toDos.removeAt(position)
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {

        return toDos.size

    }
}