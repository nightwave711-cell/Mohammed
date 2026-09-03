with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "r") as f:
    text = f.read()

# Add emmet core script and ace emmet ext
old_scripts = """                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ace.js" type="text/javascript" charset="utf-8"></script>
                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ext-language_tools.min.js"></script>"""
new_scripts = """                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ace.js" type="text/javascript" charset="utf-8"></script>
                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ext-language_tools.min.js"></script>
                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ext-emmet.min.js"></script>
                      <script src="https://cloud9ide.github.io/emmet-core/emmet.js"></script>"""

text = text.replace(old_scripts, new_scripts)

# Enable emmet in options
old_opts = """                            enableBasicAutocompletion: true,
                            enableLiveAutocompletion: true,
                            enableSnippets: true,"""
new_opts = """                            enableBasicAutocompletion: true,
                            enableLiveAutocompletion: true,
                            enableSnippets: true,
                            enableEmmet: true,"""

text = text.replace(old_opts, new_opts)

with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "w") as f:
    f.write(text)

