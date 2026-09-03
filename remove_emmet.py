with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "r") as f:
    text = f.read()

text = text.replace('                      <script src="https://cloud9ide.github.io/emmet-core/emmet.js"></script>\n', '')
text = text.replace('                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ext-emmet.min.js"></script>\n', '')
text = text.replace('                            enableEmmet: true,\n', '')

with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "w") as f:
    f.write(text)
