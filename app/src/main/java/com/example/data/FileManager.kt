package com.example.data

import com.example.data.TemplateProvider

import android.content.Context
import android.net.Uri
import java.io.File

class FileManager(private val context: Context) {

    fun getProjectDir(projectId: Int): File {
        val dir = File(context.filesDir, "projects/project_$projectId")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun listFiles(projectId: Int): List<File> {
        val dir = getProjectDir(projectId)
        return dir.listFiles()?.toList() ?: emptyList()
    }

    fun readFile(file: File): String {
        return if (file.exists()) file.readText() else ""
    }

    fun writeFile(projectId: Int, fileName: String, content: String) {
        val file = File(getProjectDir(projectId), fileName)
        file.writeText(content)
    }

    fun renameFile(projectId: Int, oldName: String, newName: String) {
        val oldFile = File(getProjectDir(projectId), oldName)
        val newFile = File(getProjectDir(projectId), newName)
        if (oldFile.exists()) {
            oldFile.renameTo(newFile)
        }
    }

    fun deleteFile(projectId: Int, fileName: String) {
        val file = File(getProjectDir(projectId), fileName)
        if (file.exists()) {
            file.delete()
        }
    }
    
    
    fun createFolder(parentDir: File, folderName: String) {
        val folder = File(parentDir, folderName)
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }

    fun createFileAt(parentDir: File, fileName: String, content: String = "") {
        val file = File(parentDir, fileName)
        file.writeText(content)
    }

    fun renameFileObj(oldFile: File, newName: String) {
        val newFile = File(oldFile.parentFile, newName)
        if (oldFile.exists()) {
            oldFile.renameTo(newFile)
        }
    }

    fun deleteFileObj(file: File) {
        if (file.exists()) {
            if (file.isDirectory) {
                file.deleteRecursively()
            } else {
                file.delete()
            }
        }
    }

    fun importFileFromUri(projectId: Int, uri: Uri, fileName: String) {
        try {
            val destFile = File(getProjectDir(projectId), fileName)
            context.contentResolver.openInputStream(uri)?.use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun applyTemplate(projectId: Int, template: String) {
        when (template) {
            "Bootstrap" -> {
                writeFile(projectId, "index.html", "<!DOCTYPE html>\n<html>\n<head>\n  <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n</head>\n<body>\n  <div class=\"container text-center\">\n    <h1 class=\"mt-5 text-primary\">Hello Bootstrap</h1>\n    <button class=\"btn btn-success\">Click Me</button>\n  </div>\n  <script src=\"main.js\"></script>\n</body>\n</html>")
                writeFile(projectId, "style.css", "/* Custom CSS */")
                writeFile(projectId, "main.js", "// JS here")
            }
            "Tailwind" -> {
                writeFile(projectId, "index.html", "<!DOCTYPE html>\n<html>\n<head>\n  <script src=\"https://cdn.tailwindcss.com\"></script>\n</head>\n<body class=\"bg-slate-900 text-white flex items-center justify-center h-screen\">\n  <div class=\"text-center\">\n    <h1 class=\"text-4xl font-bold text-blue-400\">Hello Tailwind</h1>\n    <button class=\"mt-4 px-4 py-2 bg-blue-600 rounded-lg hover:bg-blue-500\">Click Me</button>\n  </div>\n</body>\n</html>")
                writeFile(projectId, "style.css", "/* Custom CSS */")
                writeFile(projectId, "main.js", "// JS here")
            }
            "Three.js" -> {
                writeFile(projectId, "index.html", "<!DOCTYPE html>\n<html>\n<head>\n  <style>body { margin: 0; }</style>\n</head>\n<body>\n  <script type=\"module\" src=\"main.js\"></script>\n</body>\n</html>")
                writeFile(projectId, "main.js", "import * as THREE from 'https://unpkg.com/three/build/three.module.js';\n\nconst scene = new THREE.Scene();\nconst camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 0.1, 1000 );\nconst renderer = new THREE.WebGLRenderer();\nrenderer.setSize( window.innerWidth, window.innerHeight );\ndocument.body.appendChild( renderer.domElement );\n\nconst geometry = new THREE.BoxGeometry();\nconst material = new THREE.MeshBasicMaterial( { color: 0x00ff00 } );\nconst cube = new THREE.Mesh( geometry, material );\nscene.add( cube );\n\ncamera.position.z = 5;\n\nfunction animate() {\n\trequestAnimationFrame( animate );\n\tcube.rotation.x += 0.01;\n\tcube.rotation.y += 0.01;\n\trenderer.render( scene, camera );\n}\nanimate();")
            }
            "TicTacToe" -> {
                writeFile(projectId, "index.html", TemplateProvider.getTicTacToeHtml())
                writeFile(projectId, "style.css", TemplateProvider.getTicTacToeCss())
                writeFile(projectId, "main.js", TemplateProvider.getTicTacToeJs())
            }
            "Calculator" -> {
                writeFile(projectId, "index.html", "<!DOCTYPE html>\n<html>\n<head>\n  <link rel=\"stylesheet\" href=\"style.css\">\n</head>\n<body>\n  <div class=\"calculator\">\n    <div class=\"display\" id=\"display\">0</div>\n    <div class=\"buttons\">\n      <button class=\"btn\" onclick=\"clearDisplay()\">C</button>\n      <button class=\"btn\" onclick=\"append('/')\">/</button>\n      <button class=\"btn\" onclick=\"append('*')\">*</button>\n      <button class=\"btn\" onclick=\"append('-')\">-</button>\n      <button class=\"btn\" onclick=\"append('7')\">7</button>\n      <button class=\"btn\" onclick=\"append('8')\">8</button>\n      <button class=\"btn\" onclick=\"append('9')\">9</button>\n      <button class=\"btn\" onclick=\"append('+')\">+</button>\n      <button class=\"btn\" onclick=\"append('4')\">4</button>\n      <button class=\"btn\" onclick=\"append('5')\">5</button>\n      <button class=\"btn\" onclick=\"append('6')\">6</button>\n      <button class=\"btn\" onclick=\"calculate()\" style=\"grid-row: span 2; background-color: #6200ea;\">=</button>\n      <button class=\"btn\" onclick=\"append('1')\">1</button>\n      <button class=\"btn\" onclick=\"append('2')\">2</button>\n      <button class=\"btn\" onclick=\"append('3')\">3</button>\n      <button class=\"btn\" onclick=\"append('0')\" style=\"grid-column: span 2;\">0</button>\n      <button class=\"btn\" onclick=\"append('.')\">.</button>\n    </div>\n  </div>\n  <script src=\"main.js\"></script>\n</body>\n</html>")
                writeFile(projectId, "style.css", "body {\n  display: flex;\n  justify-content: center;\n  align-items: center;\n  height: 100vh;\n  background-color: #050505;\n  font-family: sans-serif;\n  margin: 0;\n}\n.calculator {\n  background-color: #1e1e1e;\n  border-radius: 10px;\n  padding: 20px;\n  box-shadow: 0 4px 10px rgba(0,0,0,0.5);\n  width: 250px;\n}\n.display {\n  background-color: #000;\n  color: #fff;\n  font-size: 2em;\n  text-align: right;\n  padding: 10px;\n  border-radius: 5px;\n  margin-bottom: 20px;\n  min-height: 40px;\n  overflow-x: auto;\n}\n.buttons {\n  display: grid;\n  grid-template-columns: repeat(4, 1fr);\n  gap: 10px;\n}\n.btn {\n  background-color: #333;\n  color: #fff;\n  border: none;\n  padding: 15px;\n  font-size: 1.2em;\n  border-radius: 5px;\n  cursor: pointer;\n}\n.btn:hover {\n  background-color: #444;\n}")
                writeFile(projectId, "main.js", "const display = document.getElementById('display');\n\nfunction append(value) {\n  if (display.innerText === '0' && value !== '.') {\n    display.innerText = value;\n  } else {\n    display.innerText += value;\n  }\n}\n\nfunction clearDisplay() {\n  display.innerText = '0';\n}\n\nfunction calculate() {\n  try {\n    display.innerText = eval(display.innerText);\n  } catch (e) {\n    display.innerText = 'Error';\n  }\n}")
            }
            "LandingPage" -> {
                writeFile(projectId, "index.html", TemplateProvider.getLandingPageHtml())
                writeFile(projectId, "style.css", TemplateProvider.getLandingPageCss())
                writeFile(projectId, "main.js", TemplateProvider.getLandingPageJs())
            }
            "TodoList" -> {
                writeFile(projectId, "index.html", TemplateProvider.getKanbanHtml())
                writeFile(projectId, "style.css", TemplateProvider.getKanbanCss())
                writeFile(projectId, "main.js", TemplateProvider.getKanbanJs())
            }
            else -> {
                writeFile(projectId, "index.html", "<!DOCTYPE html>\n<html>\n<head>\n  <link rel=\"stylesheet\" href=\"style.css\">\n</head>\n<body>\n  <h1>Hello WebCode</h1>\n  <script src=\"main.js\"></script>\n</body>\n</html>")
                writeFile(projectId, "style.css", "body {\n  background: #000000;\n  color: #ffffff;\n  text-align: center;\n  font-family: sans-serif;\n}")
                writeFile(projectId, "main.js", "console.log('Hello from JS');")
            }
        }
    }
    
    // Kept for backward compatibility when loading old projects
    fun createDefaultFilesIfNeeded(projectId: Int, html: String, css: String, js: String) {
        val dir = getProjectDir(projectId)
        if (dir.listFiles()?.isEmpty() != false) {
            writeFile(projectId, "index.html", html.ifEmpty { "<!DOCTYPE html>\n<html>\n<head>\n  <link rel=\"stylesheet\" href=\"style.css\">\n</head>\n<body>\n  <h1>Hello WebCode</h1>\n  <script src=\"main.js\"></script>\n</body>\n</html>" })
            writeFile(projectId, "style.css", css)
            writeFile(projectId, "main.js", js)
        }
    }
    
    fun getProjectUrl(projectId: Int): String {
        return "https://appassets.androidplatform.net/projects/project_$projectId/index.html"
    }
}
