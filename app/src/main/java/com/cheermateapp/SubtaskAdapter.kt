package com.cheermateapp

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.cheermateapp.data.model.SubTask

class SubtaskAdapter(
    private val context: Context,
    private val subtasks: MutableList<SubTask>,
    private val onCheckChanged: (SubTask, Boolean) -> Unit,
    private val onDelete: (SubTask) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = subtasks.size

    override fun getItem(position: Int): Any = subtasks[position]

    override fun getItemId(position: Int): Long = subtasks[position].Subtask_ID.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_subtask, parent, false)
        
        val subtask = subtasks[position]
        val checkbox = view.findViewById<CheckBox>(R.id.cbSubTask)
        val textView = view.findViewById<TextView>(R.id.tvSubTaskName)
        val deleteButton = view.findViewById<ImageView>(R.id.btnDeleteSubTask)
        
        // Set subtask data
        textView.text = subtask.Name
        checkbox.isChecked = subtask.IsCompleted
        
        // Apply strikethrough if completed
        if (subtask.IsCompleted) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textView.alpha = 0.6f
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textView.alpha = 1.0f
        }
        
        // Remove any existing listeners before setting new ones
        checkbox.setOnCheckedChangeListener(null)
        
        // Checkbox click listener
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            onCheckChanged(subtask, isChecked)
            
            // Apply/remove strikethrough
            if (isChecked) {
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                textView.alpha = 0.6f
            } else {
                textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                textView.alpha = 1.0f
            }
        }
        
        // Delete button click listener
        deleteButton.setOnClickListener {
            onDelete(subtask)
        }
        
        return view
    }

    fun updateData(newSubtasks: List<SubTask>) {
        subtasks.clear()
        subtasks.addAll(newSubtasks)
        notifyDataSetChanged()
    }
}
