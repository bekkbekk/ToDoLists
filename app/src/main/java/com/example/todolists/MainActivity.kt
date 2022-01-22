package com.example.todolists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.Application
import android.widget.CheckBox


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            if (etToDoTitle.text.toString() != ""){
                val newTodo = etToDoTitle.text.toString()
                newList.add(ToDo(newTodo))
                adapter.notifyItemInserted(newList.size - 1)
                rvToDo.smoothScrollToPosition(newList.size - 1)
                etToDoTitle.text = null
            } else {
                etToDoTitle.error = "Required"
            }
        }

    }

}