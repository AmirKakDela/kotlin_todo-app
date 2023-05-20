package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.utils.adapter.TaskAdapter
import com.example.myapplication.utils.models.TodoData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(), TaskAdapter.TaskAdapterInterface {
    private val TAG = "HomeFragment"
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var todoItemList: MutableList<TodoData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBtn.setOnClickListener {
            logout()
        }

        binding.addTodo.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_taskFormFragment)
        }

        init(view)
        getTaskFromFirebase()
    }

    private fun logout() {
        auth.signOut()
        navController.navigate(R.id.action_homeFragment_to_signInFragment)
    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        authId = auth.currentUser!!.uid
        navController = Navigation.findNavController(view)
        database = FirebaseDatabase.getInstance().reference.child("Tasks").child(authId)


        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)

        todoItemList = mutableListOf()
        taskAdapter = TaskAdapter(todoItemList)
        taskAdapter.setListener(this)
        binding.mainRecyclerView.adapter = taskAdapter
    }

    private fun getTaskFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                todoItemList.clear()
                for (taskSnapshot in snapshot.children) {
                    val todoTask =
                        taskSnapshot.key?.let { TodoData(it, taskSnapshot.value.toString()) }

                    if (todoTask != null) {
                        todoItemList.add(todoTask)
                    }

                }
                Log.d(TAG, "onDataChange: " + todoItemList)
                taskAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDeleteItemClicked(todoData: TodoData, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onEditItemClicked(todoData: TodoData, position: Int) {
        TODO("Not yet implemented")
    }
}