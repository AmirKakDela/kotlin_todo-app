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

    companion object {
        const val TAG = "TaskFormFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            TaskFormFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
    }

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
            }
        }
    }


    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        authId = auth.currentUser!!.uid
        database = FirebaseDatabase.getInstance().reference.child("Tasks").child(authId)
        navController = Navigation.findNavController(view)
    }

    // todo = todo: Todo
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
    fun updateTask(todo: String, todoEdit: TextInputEditText) {
        TODO("Not yet implemented")
    }
}