package com.cheermateapp

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.cheermateapp.data.model.SubTask

/**
 * ListView adapter for displaying and managing subtasks with fixed height
 */
class SubTaskListAdapter(
    private val context: Context,
    private val subtasks: MutableList<SubTask>,
    private val onSubTaskToggle: (SubTask) -> Unit,
    private val onSubTaskDelete: (SubTask) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = subtasks.size

    override fun getItem(position: Int): SubTask = subtasks[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_subtask, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val subtask = getItem(position)
        
        // Set subtask data
        holder.tvSubTaskName.text = subtask.Name
        holder.checkBox.isChecked = subtask.IsCompleted
        
        // Apply strikethrough if completed
        if (subtask.IsCompleted) {
            holder.tvSubTaskName.paintFlags = holder.tvSubTaskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvSubTaskName.alpha = 0.6f
        } else {
            holder.tvSubTaskName.paintFlags = holder.tvSubTaskName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.tvSubTaskName.alpha = 1.0f
        }
        
        // Set up click listeners - remove previous listeners to prevent duplicates
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onSubTaskToggle(subtask.copy(IsCompleted = isChecked))
        }
        
        holder.btnDeleteSubTask.setOnClickListener {
            onSubTaskDelete(subtask)
        }
        
        return view
    }

    /**
     * Update the subtask list
     */
    fun updateSubtasks(newSubtasks: List<SubTask>) {
        subtasks.clear()
        subtasks.addAll(newSubtasks)
        notifyDataSetChanged()
    }

    /**
     * ViewHolder pattern for efficient view recycling
     */
    private class ViewHolder(view: View) {
        val checkBox: CheckBox = view.findViewById(R.id.cbSubTask)
        val tvSubTaskName: TextView = view.findViewById(R.id.tvSubTaskName)
        val btnDeleteSubTask: ImageView = view.findViewById(R.id.btnDeleteSubTask)
    }
}
