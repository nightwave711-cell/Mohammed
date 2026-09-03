with open("app/src/main/java/com/example/ui/screens/EditorViewModel.kt", "r") as f:
    text = f.read()

# Replace fileManager.writeFile(projectId, file.name, state.fileContent) with file.writeText(state.fileContent)
old_save = "fileManager.writeFile(projectId, file.name, state.fileContent)"
new_save = "fileManager.writeFileByObj(file, state.fileContent)"
text = text.replace(old_save, new_save)

with open("app/src/main/java/com/example/ui/screens/EditorViewModel.kt", "w") as f:
    f.write(text)
