package com.example.todolists

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "To-Do's"

        val newList = mutableListOf<ToDo>(
            ToDo("Video sa badminton"),
            ToDo("Aral ng prog"),
            ToDo("Gawa essay sa UTS", true),
            ToDo("Gawa essay sa WorldLit", true)

        )

        val rvToDo: RecyclerView = findViewById(R.id.rvToDoItems)

        val adapter = ToDoAdapter(newList)
        rvToDo.adapter = adapter

        val linearLayoutManager = LinearLayoutManager(this)
        rvToDo.layoutManager = linearLayoutManager

        //pag open ay yung latest ang nasa pinakababa
        rvToDo.smoothScrollToPosition(newList.size - 1)

        val btnAdd: Button = findViewById(R.id.btnAddToDo)
        val etToDoTitle: EditText = findViewById(R.id.etToDoTitle)


        btnAdd.setOnClickListener {
            if (etToDoTitle.text.toString().trim() != ""){
                val newTodo = etToDoTitle.text.toString().trim()
                newList.add(ToDo(newTodo, false))
                adapter.notifyItemInserted(newList.size - 1)
                rvToDo.smoothScrollToPosition(newList.size - 1)
                etToDoTitle.text = null
            } else {
                etToDoTitle.error = "Required"
            }
        }



    }



}