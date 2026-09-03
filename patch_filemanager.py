with open("app/src/main/java/com/example/data/FileManager.kt", "r") as f:
    text = f.read()

text = text.replace("fun readFile(file: File): String {", "fun writeFileByObj(file: File, content: String) {\n        file.writeText(content)\n    }\n\n    fun readFile(file: File): String {")

with open("app/src/main/java/com/example/data/FileManager.kt", "w") as f:
    f.write(text)
