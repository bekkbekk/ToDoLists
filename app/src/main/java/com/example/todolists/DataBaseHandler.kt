import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolists.ToDo

//creating the database logic, extending the SQLiteOpenHelper base class
class DataBaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "ToDoListDatabase"

        private val TABLE_CONTACTS = "ToDoTable"

        private val KEY_ID = "_id"
        private val KEY_TITLE = "title"
        private val KEY_IS_CHECKED = "is_checked"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        // CREATE_CONTACCTS_TABLE = TABLE_CONTACTS(_id: INTEGER PRIMARY KEY, name: TEXT, email: TEXT)
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_IS_CHECKED + " STRING" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }


     //Function to insert data
    fun addToDo(toDo: ToDo): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, toDo.title) // EmpModelClass Name
        if(toDo.isChecked) {
            contentValues.put(KEY_IS_CHECKED, "TRUE") // EmpModelClass Email
        } else {
            contentValues.put(KEY_IS_CHECKED, "FALSE") //ito lagi ang magrurun kasi false naman lagi kada add
        }
         //di na kelangan isali yung _id kasi primary key yun, automatic nang nag a-add

        // Inserting employee details using insert query. Returns a Long
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    //View data in Databas
    @SuppressLint("Range")
    fun viewData(): ArrayList<ToDo>{

        val toDoList: ArrayList<ToDo> = ArrayList()

        val selectQuery = "SELECT * FROM $TABLE_CONTACTS" //all the data that is in there (table_contacts)

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var title: String
        var isChecked: Boolean
        var isCheckedString: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                isCheckedString = cursor.getString(cursor.getColumnIndex(KEY_IS_CHECKED))


                //Boolean true = TRUE pagdating sa database
                isChecked = isCheckedString == "TRUE"

                val toDo = ToDo(id, title, isChecked)
                toDoList.add(toDo)

            } while (cursor.moveToNext())
        }
        return toDoList
    }

    //Delete ToDo
    fun deleteData(toDo: ToDo): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("KEY_ID", toDo.id)

        //delete id
        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + toDo.id, null)
        db.close()

        return success
    }

    //update ToDo
    fun updateData(toDo: ToDo): Int{
        val db = this.writableDatabase
        val contentValues =  ContentValues()
        contentValues.put(KEY_TITLE, toDo.title)
        if(toDo.isChecked) {
            contentValues.put(KEY_IS_CHECKED, "TRUE") // EmpModelClass Email
        } else {
            contentValues.put(KEY_IS_CHECKED, "FALSE")
        }

        val success = db.update(TABLE_CONTACTS, contentValues, "$KEY_ID=" + toDo.id, null )

        db.close()

        return success
    }


}