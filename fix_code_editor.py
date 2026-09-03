import re

with open("app/src/main/java/com/example/ui/components/CodeEditor.kt", "r") as f:
    text = f.read()

effects = """
    LaunchedEffect(insertTextTrigger) {
        insertTextTrigger?.let { trigger ->
            if (trigger.first.isNotEmpty()) {
                val currentText = internalText.text
                val selection = internalText.selection
                val before = currentText.substring(0, selection.start)
                val after = currentText.substring(selection.end)
                val newText = before + trigger.first + after
                val newCursor = selection.start + trigger.first.length
                updateTextWithHistory(TextFieldValue(newText, TextRange(newCursor)))
            }
        }
    }

    LaunchedEffect(formatTrigger) {
        formatTrigger?.let {
            // Basic format: trim lines
            val lines = internalText.text.split("\\n")
            val formatted = lines.joinToString("\\n") { it.trimEnd() }
            updateTextWithHistory(TextFieldValue(formatted, internalText.selection))
        }
    }
"""

# Remove from current location
text = text.replace(effects + "\n    LaunchedEffect(code) {", "    LaunchedEffect(code) {")

# Insert after updateTextWithHistory
update_fn = """    fun updateTextWithHistory(newValue: TextFieldValue) {
        if (internalText.text != newValue.text) {
            val last = undoStack.lastOrNull()
            if (last?.text != internalText.text) { 
                undoStack = (undoStack + internalText).takeLast(20) // keep last 20 states
            }
            redoStack = emptyList()
            internalText = newValue
            onCodeChange(newValue.text)
        } else {
            internalText = newValue // just cursor change
        }
    }"""

text = text.replace(update_fn, update_fn + "\n" + effects)

with open("app/src/main/java/com/example/ui/components/CodeEditor.kt", "w") as f:
    f.write(text)

