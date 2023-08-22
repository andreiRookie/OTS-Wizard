package ru.otus.basicarchitecture.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class EditTextDateMask(
    private val editText: EditText
) {

    private val textWatcher = object : TextWatcher {

        private val firstDividerPosition = 2
        private val secondDividerPosition = 5
        private val maxTextLength = 10

        var isEdited = false
        val divider = "."

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (isEdited) {
                isEdited = false
                return
            }

            var currText = getText()
            currText = handleDateDivider(currText, firstDividerPosition, start, before)
            currText = handleDateDivider(currText, secondDividerPosition, start, before)

            isEdited = true
            editText.setText(currText)
            editText.setSelection(editText.text.length)
        }

        private fun getText(): String {
            return if (editText.text.length >= maxTextLength)
                editText.text.toString().substring(0, maxTextLength)
            else
                editText.text.toString()
        }

        private fun handleDateDivider(
            currText: String,
            dividerPosition: Int,
            start: Int,
            before: Int
        ): String {
            if (currText.length == dividerPosition) {
                return if (before <= dividerPosition && start < dividerPosition)
                    currText + divider
                else
                    currText.dropLast(1)
            }
            return currText
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
    }

    fun startListen() {
        editText.addTextChangedListener(textWatcher)
    }
}