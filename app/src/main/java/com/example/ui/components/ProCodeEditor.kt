package com.example.ui.components

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ProCodeEditor(
    code: String,
    onCodeChange: (String) -> Unit,
    language: String,
    fontSize: Float,
    modifier: Modifier = Modifier,
    insertTextTrigger: Pair<String, Long>? = null
) {
    var webView by remember { mutableStateOf<WebView?>(null) }
    var isReady by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    // Convert language to ace mode
    val mode = if (language == "c_cpp") "ace/mode/c_cpp" else if (language == "text") "ace/mode/text" else "ace/mode/$language" 


    // Function to insert text at cursor
    fun insertTextAtCursor(textToInsert: String) {
        if (isReady && webView != null) {
            val escapedText = JSONObject.quote(textToInsert)
            webView?.evaluateJavascript("if (window.editor) window.editor.insert($escapedText);", null)
        }
    }

    // When the external code changes, update the editor if it differs from what's inside
    LaunchedEffect(code, isReady) {
        if (isReady && webView != null) {
            val escapedCode = JSONObject.quote(code)
            webView?.evaluateJavascript(
                """
                (function() {
                    if (window.editor && window.editor.setValueSafe) {
                        window.editor.setValueSafe($escapedCode);
                    }
                })();
                """.trimIndent(), null
            )
        }
    }
    

    LaunchedEffect(insertTextTrigger) {
        insertTextTrigger?.let { trigger ->
            if (isReady && webView != null && trigger.first.isNotEmpty()) {
                val escapedText = JSONObject.quote(trigger.first)
                webView?.evaluateJavascript("if (window.editor) { window.editor.insert($escapedText); window.editor.focus(); }", null)
            }
        }
    }

    // When language changes
    LaunchedEffect(language, isReady) {
        if (isReady && webView != null) {
            webView?.evaluateJavascript("if (editor) editor.session.setMode('$mode');", null)
        }
    }

    // When font size changes
    LaunchedEffect(fontSize, isReady) {
        if (isReady && webView != null) {
            webView?.evaluateJavascript("if (editor) editor.setFontSize(${fontSize});", null)
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                
                webChromeClient = WebChromeClient()
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isReady = true
                    }
                }
                
                addJavascriptInterface(object {
                    @JavascriptInterface
                    fun onCodeChanged(newCode: String) {
                        // Callback to compose
                        onCodeChange(newCode)
                    }
                }, "Android")
                
                val html = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                      <meta charset="UTF-8">
                      <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
                      <style>
                        body, html { margin: 0; padding: 0; height: 100%; overflow: hidden; background-color: #1e1e1e; }
                        #editor { position: absolute; top: 0; bottom: 0; left: 0; right: 0; }
                      </style>
                    </head>
                    <body>
                      <div id="editor"></div>
                      <!-- Using Ace Editor -->
                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ace.js" type="text/javascript" charset="utf-8"></script>
                      <script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.32.7/ext-language_tools.min.js"></script>
                      <script>
                        var editor = ace.edit("editor");
                        editor.setTheme("ace/theme/tomorrow_night_eighties");
                        editor.session.setMode("$mode");
                        editor.setOptions({
                            enableBasicAutocompletion: true,
                            enableLiveAutocompletion: true,
                            enableSnippets: true,
                            showLineNumbers: true,
                            tabSize: 4,
                            useSoftTabs: true,
                            fontSize: $fontSize,
                            wrap: true
                        });
                        
                        var isUpdating = false;
                        editor.session.on('change', function(delta) {
                            if (!isUpdating) {
                                Android.onCodeChanged(editor.getValue());
                            }
                        });
                        
                        // Function used by Android to set value safely without triggering onchange loop
                        editor.setValueSafe = function(val) {
                            if (editor.getValue() !== val) {
                                isUpdating = true;
                                var cursor = editor.getCursorPosition();
                                editor.setValue(val, -1);
                                editor.moveCursorToPosition(cursor);
                                isUpdating = false;
                            }
                        };
                      </script>
                    </body>
                    </html>
                """.trimIndent()
                
                loadDataWithBaseURL("https://acode.editor", html, "text/html", "UTF-8", null)
                webView = this
            }
        },
        update = {
            // Keep reference updated if needed
            webView = it
        }
    )
}
