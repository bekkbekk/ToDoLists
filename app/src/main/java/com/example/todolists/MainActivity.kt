package com.example.todolists

import DataBaseHandler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "To-Do's"

        setUpListOfDataIntoRecyclerView()

        val btnAdd: Button = findViewById(R.id.btnAddToDo)
        val etToDoTitle: EditText = findViewById(R.id.etToDoTitle)


        btnAdd.setOnClickListener {

            val title = etToDoTitle.text.toString()
            val dbHandler = DataBaseHandler(this)
            if (title.isNotEmpty()) {
                val status = dbHandler.addToDo(ToDo(0, title))
                if (status > -1) {
                    etToDoTitle.text.clear()
                }

                setUpListOfDataIntoRecyclerView()
            } else {
                etToDoTitle.error = "Required"
            }


            /*
            if (etToDoTitle.text.toString().trim() != ""){
                val newTodo = etToDoTitle.text.toString().trim()
                newList.add(ToDo(0, newTodo, false))
                adapter.notifyItemInserted(newList.size - 1)
                rvToDo.smoothScrollToPosition(newList.size - 1)
                etToDoTitle.text = null
            } else {
                etToDoTitle.error = "Required"
            }
             */

        }

    }

    private fun setUpListOfDataIntoRecyclerView() {

        val rvToDo: RecyclerView = findViewById(R.id.rvToDoItems)
        val tvNoRecords: TextView = findViewById(R.id.tvNoRecords)

        if (getItemList().size > 0) {

            rvToDo.visibility = View.VISIBLE
            tvNoRecords.visibility = View.GONE

            val adapter = ToDoAdapter(this, getItemList())
            rvToDo.adapter = adapter

            val linearLayoutManager = LinearLayoutManager(this)
            rvToDo.layoutManager = linearLayoutManager

            //pag open ay yung latest ang nasa pinakababa
            rvToDo.smoothScrollToPosition(getItemList().size - 1)

        } else {
//            rvToDo.visibility = View.GONE
            tvNoRecords.visibility = View.VISIBLE
        }
    }

    private fun getItemList(): ArrayList<ToDo> {

        val dbHandler = DataBaseHandler(this)

        return dbHandler.viewData()


    }

    fun deleteRecord(toDos: ToDo) {
        val dbHandler = DataBaseHandler(this)
        val status = dbHandler.deleteData(toDos)
        if (status > -1) {
            setUpListOfDataIntoRecyclerView()
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT)
        }
    }

    fun updateRecord(toDos: ToDo) {
        val dbHanlder = DataBaseHandler(this)
        val status = dbHanlder.updateData(toDos)
        if (status > -1) {
            setUpListOfDataIntoRecyclerView()
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT)
        }
    }


    // TO FIX. goal -> ilagay sa dulo yung mga nakacheck na ang checkbox
    fun moveDoneToEnd(toDoLists: ArrayList<ToDo>): ArrayList<ToDo> {
        val isNotDone: ArrayList<ToDo> = ArrayList()
        val isDone: ArrayList<ToDo> = ArrayList()
        val sortedList: ArrayList<ToDo> = ArrayList()

        for(data in toDoLists){
            if(data.isChecked){
                isDone.add(data)
            } else {
                isNotDone.add(data)
            }
        }

        sortedList.addAll(isNotDone)
        sortedList.addAll(isDone)

        setUpListOfDataIntoRecyclerView()

        return sortedList
    }


}