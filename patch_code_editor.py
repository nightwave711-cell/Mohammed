import re

with open("app/src/main/java/com/example/ui/components/CodeEditor.kt", "r") as f:
    text = f.read()

# Add parameters
old_sig = """    language: String,
    fontSize: Float,
    modifier: Modifier = Modifier
) {"""
new_sig = """    language: String,
    fontSize: Float,
    modifier: Modifier = Modifier,
    insertTextTrigger: Pair<String, Long>? = null,
    formatTrigger: Long? = null
) {"""
text = text.replace(old_sig, new_sig)

# Add LaunchedEffects
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

text = text.replace("    LaunchedEffect(code) {", effects + "\n    LaunchedEffect(code) {")

with open("app/src/main/java/com/example/ui/components/CodeEditor.kt", "w") as f:
    f.write(text)
