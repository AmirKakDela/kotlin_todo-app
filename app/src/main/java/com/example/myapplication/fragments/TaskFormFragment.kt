package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTaskFormBinding
import com.example.myapplication.utils.models.TodoData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class TaskFormFragment : Fragment() {
    private lateinit var binding: FragmentTaskFormBinding
    private lateinit var navController: NavController
    private lateinit var database: DatabaseReference
    private var todoData: TodoData? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        val taskId: String = arguments?.getString("taskId").toString()
        val task: String = arguments?.getString("task").toString()

        if (taskId != "null" && task != "null") {
            todoData = TodoData(taskId, task)
            binding.addTodo.text = getString(R.string.edit_task)
            binding.taskTitle.setText(todoData?.task)
        }

        binding.back.setOnClickListener {
            navController.navigate(R.id.action_taskFormFragment_to_homeFragment)
        }

        binding.addTodoBtn.setOnClickListener {
            val todoTitle = binding.taskTitle.text.toString()
            if (!todoTitle.isNotEmpty()) {
                Toast.makeText(context, "Empty title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (todoData == null) {
                saveTask(todoTitle, binding.taskTitle)
            } else {
                updateTask(todoData!!, binding.taskTitle)
            }
        }
    }


    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        authId = auth.currentUser!!.uid
        database = FirebaseDatabase.getInstance().reference.child("Tasks").child(authId)
        navController = Navigation.findNavController(view)
    }

    private fun saveTask(todoTitle: String, todoEdit: TextInputEditText) {
        database
            .push().setValue(todoTitle)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Task Added Successfully", Toast.LENGTH_SHORT).show()
                    todoEdit.text = null
                } else {
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    // todo = todo: Todo
    fun updateTask(todoData: TodoData, todoEdit: TextInputEditText) {
        val map = HashMap<String, Any>()
        map[todoData.taskId] = todoEdit.text.toString()
        database.updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_taskFormFragment_to_homeFragment)
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}