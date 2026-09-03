import re

# 1. Update ProCodeEditor.kt
with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "r") as f:
    text = f.read()

# Add formatTrigger parameter
param_old = "    insertTextTrigger: Pair<String, Long>? = null"
param_new = "    insertTextTrigger: Pair<String, Long>? = null,\n    formatTrigger: Long? = null"
text = text.replace(param_old, param_new)

effect_format = """
    // When format triggered
    LaunchedEffect(formatTrigger) {
        if (isReady && webView != null && formatTrigger != null) {
            webView?.evaluateJavascript("if (window.editor) { var beautify = ace.require('ace/ext/beautify'); beautify.beautify(window.editor.session); }", null)
        }
    }
"""
text = text.replace("    // When font size changes", effect_format + "\n    // When font size changes")

with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "w") as f:
    f.write(text)

# 2. Update EditorScreen.kt
with open("app/src/main/java/com/example/ui/screens/EditorScreen.kt", "r") as f:
    text2 = f.read()

state_old = "    var insertTextTrigger by remember { mutableStateOf<Pair<String, Long>?>(null) }"
state_new = "    var insertTextTrigger by remember { mutableStateOf<Pair<String, Long>?>(null) }\n    var formatTrigger by remember { mutableStateOf<Long?>(null) }"
text2 = text2.replace(state_old, state_new)

editor_call_old = "insertTextTrigger = insertTextTrigger,"
editor_call_new = "insertTextTrigger = insertTextTrigger,\n                            formatTrigger = formatTrigger,"
text2 = text2.replace(editor_call_old, editor_call_new)

# Add format button before the symbols in the toolbar
toolbar_old = """                            val helpers = listOf("{", "}", "(", ")", "[", "]", "<", ">", "=", "+", "-", "*", "/", "\\\\", "\\"", "'", ";", ":", ",", ".", "!", "?", "&", "|")
                            helpers.forEach { symbol ->"""
toolbar_new = """                            TextButton(
                                onClick = { formatTrigger = System.currentTimeMillis() },
                                modifier = Modifier.padding(horizontal = 2.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                            ) {
                                Icon(androidx.compose.material.icons.Icons.Default.AutoFixHigh, contentDescription = "Format", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Format", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                            }
                            
                            val helpers = listOf("{", "}", "(", ")", "[", "]", "<", ">", "=", "+", "-", "*", "/", "\\\\", "\\"", "'", ";", ":", ",", ".", "!", "?", "&", "|")
                            helpers.forEach { symbol ->"""
text2 = text2.replace(toolbar_old, toolbar_new)

if "import androidx.compose.material.icons.filled.AutoFixHigh" not in text2:
    text2 = text2.replace("import androidx.compose.material.icons.filled.*", "import androidx.compose.material.icons.filled.*\nimport androidx.compose.material.icons.filled.AutoFixHigh\nimport androidx.compose.foundation.layout.size\nimport androidx.compose.foundation.layout.width")

with open("app/src/main/java/com/example/ui/screens/EditorScreen.kt", "w") as f:
    f.write(text2)
