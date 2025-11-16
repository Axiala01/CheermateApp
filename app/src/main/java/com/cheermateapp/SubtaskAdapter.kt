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

/**
 * ListView adapter for displaying and managing subtasks
 */
class SubtaskAdapter(
    private val context: Context,
    private val subtasks: MutableList<SubTask>,
    private val onCheckChanged: (SubTask, Boolean) -> Unit,
    private val onDelete: (SubTask) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = subtasks.size

    override fun getItem(position: Int): SubTask = subtasks[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_subtask, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        val subtask = getItem(position)

        // Set subtask name and completion state
        viewHolder.tvSubTaskName.text = subtask.Name
        viewHolder.cbSubTask.isChecked = subtask.IsCompleted

        // Apply strikethrough if completed
        if (subtask.IsCompleted) {
            viewHolder.tvSubTaskName.paintFlags = 
                viewHolder.tvSubTaskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            viewHolder.tvSubTaskName.alpha = 0.6f
        } else {
            viewHolder.tvSubTaskName.paintFlags = 
                viewHolder.tvSubTaskName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            viewHolder.tvSubTaskName.alpha = 1.0f
        }

        // Set up listeners - remove any existing listeners first
        viewHolder.cbSubTask.setOnCheckedChangeListener(null)
        viewHolder.btnDeleteSubTask.setOnClickListener(null)

        // Set checkbox listener
        viewHolder.cbSubTask.setOnCheckedChangeListener { _, isChecked ->
            onCheckChanged(subtask, isChecked)
        }

        // Set delete button listener
        viewHolder.btnDeleteSubTask.setOnClickListener {
            onDelete(subtask)
        }

        return view
    }

    /**
     * ViewHolder pattern for efficient view recycling
     */
    private class ViewHolder(view: View) {
        val cbSubTask: CheckBox = view.findViewById(R.id.cbSubTask)
        val tvSubTaskName: TextView = view.findViewById(R.id.tvSubTaskName)
        val btnDeleteSubTask: ImageView = view.findViewById(R.id.btnDeleteSubTask)
    }
}
