package br.com.zafcode.atividade1

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PlaceholderTransformation(private val placeholder: String) : VisualTransformation {
    override fun filter(text: AnnotatedString) =
        TransformedText(AnnotatedString(placeholder), object : OffsetMapping {
            override fun originalToTransformed(offset: Int) = 0
            override fun transformedToOriginal(offset: Int) = 0
        })
}