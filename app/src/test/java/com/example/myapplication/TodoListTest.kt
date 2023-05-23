package com.example.myapplication

import com.example.myapplication.utils.models.TodoData
import org.junit.Assert.*
import org.junit.Test

class TodoListTest {
    @Test
    fun testAddTodo() {
        val todoList = ArrayList<TodoData>()
        todoList.add(TodoData("312", "Buy milk"));
        assertEquals(1, todoList.size)
    }

    @Test
    fun testRemoveTodo() {
        val todoList = ArrayList<TodoData>()
        val todoItem = TodoData("312", "Buy milk");
        todoList.add(todoItem)
        todoList.remove(todoItem)
        assertEquals(0, todoList.size)
    }
//
//    @Test
//    fun testCompleteTodo() {
//        val todoList = TodoList()
//        val todoItem = TodoItem("Buy milk")
//        todoList.add(todoItem)
//        todoList.complete(todoItem)
//        assertTrue(todoItem.isCompleted())
//    }
//
//    @Test
//    fun testIncompleteTodo() {
//        val todoList = TodoList()
//        val todoItem = TodoItem("Buy milk")
//        todoList.add(todoItem)
//        todoList.complete(todoItem)
//        todoList.incomplete(todoItem)
//        assertFalse(todoItem.isCompleted())
//    }
}