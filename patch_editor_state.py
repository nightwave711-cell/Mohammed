import re

with open("app/src/main/java/com/example/ui/screens/EditorViewModel.kt", "r") as f:
    text = f.read()

text = text.replace(
    "val previewZoom: Float = 1f\n)",
    "val previewZoom: Float = 1f,\n    val fileTreeTrigger: Int = 0\n)"
)

text = text.replace(
    "_uiState.update { it.copy(files = files) }",
    "_uiState.update { it.copy(files = files, fileTreeTrigger = it.fileTreeTrigger + 1) }"
)
text = text.replace(
    "_uiState.update { \n            it.copy(\n                files = files",
    "_uiState.update { \n            it.copy(\n                fileTreeTrigger = it.fileTreeTrigger + 1,\n                files = files"
)
text = text.replace(
    "_uiState.update { \n                 it.copy(\n                    files = files,",
    "_uiState.update { \n                 it.copy(\n                    fileTreeTrigger = it.fileTreeTrigger + 1,\n                    files = files,"
)

with open("app/src/main/java/com/example/ui/screens/EditorViewModel.kt", "w") as f:
    f.write(text)

