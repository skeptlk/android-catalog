package com.example.thecatalog.helpers

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

class Prompt(title: String, context: Context?): AlertDialog.Builder(context) {
    // Set up the input
    private val input = EditText(context);

    var text: String = ""
    var listener: ((result: String)->Unit)? = null

    init {
        setTitle(title)

        input.inputType = InputType.TYPE_CLASS_TEXT;

        setView(input);

        setPositiveButton("OK", DialogInterface.OnClickListener (
            fun (dialog: DialogInterface, _: Int) {
                text = input.text.toString()
                listener?.invoke(text)

                clearValue()
                (input.parent as ViewGroup).removeView(input)
            }
        ))

        setNegativeButton("Cancel", DialogInterface.OnClickListener (
            fun (dialog: DialogInterface, _: Int) {
                dialog.cancel()
                clearValue()
                (input.parent as ViewGroup).removeView(input)
            }
        ))
    }

    fun show(cb: ((result: String)->Unit)): AlertDialog {
        listener = cb
        return super.show()
    }

    fun setValue(value: String) {
        input.setText(value, TextView.BufferType.EDITABLE)
    }

    fun clearValue() {
        input.setText("", TextView.BufferType.EDITABLE)
    }
}