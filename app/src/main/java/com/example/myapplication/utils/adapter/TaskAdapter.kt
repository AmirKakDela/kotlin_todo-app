package com.example.myapplication.utils.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.TodoItemBinding
import com.example.myapplication.utils.models.TodoData

class TaskAdapter(private val list: MutableList<TodoData>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val TAG = "TaskAdapter"
    private var listener: TaskAdapterInterface? = null
    fun setListener(listener: TaskAdapterInterface) {
        this.listener = listener
    }

    class TaskViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todoTitle.text = this.task

                Log.d(TAG, "onBindViewHolder: " + this)
                binding.editTodo.setOnClickListener {
                    listener?.onEditItemClicked(this, position)
                }

                binding.deleteTodo.setOnClickListener {
                    listener?.onDeleteItemClicked(this, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    interface TaskAdapterInterface {
        fun onDeleteItemClicked(toDoData: TodoData, position: Int)
        fun onEditItemClicked(toDoData: TodoData, position: Int)
    }

}