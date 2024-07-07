package uk.ac.tees.mad.D3933743.ui.theme.Util

import android.util.Patterns
import android.widget.EditText
import android.widget.Toast


object Utils {
    fun emailValidator(email: String): Boolean {
        return (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }
}