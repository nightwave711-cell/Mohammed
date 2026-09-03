with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "r") as f:
    text = f.read()

import_str = "import android.webkit.WebChromeClient"
if "import android.webkit.ConsoleMessage" not in text:
    text = text.replace(import_str, import_str + "\nimport android.webkit.ConsoleMessage\nimport android.util.Log")

chrome_old = "webChromeClient = WebChromeClient()"
chrome_new = """webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        Log.e("WebViewConsole", "${consoleMessage?.message()} -- From line ${consoleMessage?.lineNumber()} of ${consoleMessage?.sourceId()}")
                        return super.onConsoleMessage(consoleMessage)
                    }
                }"""
text = text.replace(chrome_old, chrome_new)

with open("app/src/main/java/com/example/ui/components/ProCodeEditor.kt", "w") as f:
    f.write(text)
