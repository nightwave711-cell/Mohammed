with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "r") as f:
    text = f.read()

# Add searchbox extension
old_scripts = """                      <script src="https://cloud9ide.github.io/emmet-core/emmet.js"></script>"""
new_scripts = """                      <script src="https://cloud9ide.github.io/emmet-core/emmet.js"></script>
                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ext-searchbox.min.js"></script>
                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ext-beautify.min.js"></script>"""
text = text.replace(old_scripts, new_scripts)

# Set theme to monokai (looks more pro)
text = text.replace('editor.setTheme("ace/theme/tomorrow_night_eighties");', 'editor.setTheme("ace/theme/monokai");')

with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "w") as f:
    f.write(text)
