with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "r") as f:
    text = f.read()

old_scripts = """                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ext-emmet.min.js"></script>
                      <script src="https://cloud9ide.github.io/emmet-core/emmet.js"></script>"""
new_scripts = """                      <script src="https://cloud9ide.github.io/emmet-core/emmet.js"></script>
                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ext-emmet.min.js"></script>"""

text = text.replace(old_scripts, new_scripts)

with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "w") as f:
    f.write(text)
