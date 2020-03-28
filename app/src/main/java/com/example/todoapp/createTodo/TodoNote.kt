package com.example.todoapp.createTodo

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class TodoNote(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val toDoTitle: String,
    val toDoNote: String
) : Parcelable

@Dao
interface TodoDao {

    @Query("Select * from TodoNote")
    fun getAllTodoItems(): LiveData<List<TodoNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: TodoNote)

    @Query("DELETE from TodoNote where id = :itemId")
    suspend fun deleteTodoItem(itemId: Int)

}


@Database(entities = [TodoNote::class], version = 1, exportSchema = false)
public abstract class TodoDatabase : RoomDatabase() {

    abstract fun toDoDao(): TodoDao

    companion object {

        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }


    }
}

class TodoRepository(private val dao: TodoDao) {

    val allNotes = dao.getAllTodoItems();

    suspend fun insertNote(note: TodoNote) {
        dao.insert(note)
    }

    suspend fun deleteNote(id: Int) {
        dao.deleteTodoItem(id)
    }
}