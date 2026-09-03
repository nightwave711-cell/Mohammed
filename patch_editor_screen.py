with open("app/src/main/java/com/example/ui/screens/EditorScreen.kt", "r") as f:
    text = f.read()

old_loop = """                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    uiState.files.sortedWith(compareBy({ !it.isDirectory }, { it.name })).forEach { file ->"""
new_loop = """                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    androidx.compose.runtime.key(uiState.fileTreeTrigger) {
                    uiState.files.sortedWith(compareBy({ !it.isDirectory }, { it.name })).forEach { file ->"""

text = text.replace(old_loop, new_loop)
text = text.replace("                            onNewFolderClick = {\n                            }\n                        )\n                    }\n                }", "                            onNewFolderClick = {\n                            }\n                        )\n                    }\n                    }\n                }")
# Actually a safer replace for the closing brace:
with open("app/src/main/java/com/example/ui/screens/EditorScreen.kt", "w") as f:
    f.write(text)
