package com.example.ui.components

import java.io.File
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FileTreeView(
    file: File,
    level: Int = 0,
    selectedFile: File?,
    onFileClick: (File) -> Unit,
    onDeleteClick: (File) -> Unit,
    onRenameClick: (File) -> Unit,
    onNewFileClick: (File) -> Unit,
    onNewFolderClick: (File) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val isSelected = selectedFile?.absolutePath == file.absolutePath

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isSelected && !file.isDirectory) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                .clickable {
                    if (file.isDirectory) {
                        isExpanded = !isExpanded
                    } else {
                        onFileClick(file)
                    }
                }
                .padding(start = (level * 16 + 16).dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (file.isDirectory) {
                        if (isExpanded) Icons.Default.FolderOpen else Icons.Default.Folder
                    } else {
                        Icons.Default.InsertDriveFile // Or match extension
                    },
                    contentDescription = null,
                    tint = if (file.isDirectory) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = file.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected && !file.isDirectory) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                )
            }
            
            Row {
                if (file.isDirectory) {
                    IconButton(onClick = { onNewFileClick(file) }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Add, contentDescription = "New File", modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = { onNewFolderClick(file) }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.CreateNewFolder, contentDescription = "New Folder", modifier = Modifier.size(16.dp))
                    }
                }
                IconButton(onClick = { onRenameClick(file) }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Edit, contentDescription = "Rename", modifier = Modifier.size(16.dp))
                }
                IconButton(onClick = { onDeleteClick(file) }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                }
            }
        }

        if (file.isDirectory && isExpanded) {
            val children = file.listFiles()?.sortedWith(compareBy({ !it.isDirectory }, { it.name })) ?: emptyList()
            children.forEach { child ->
                FileTreeView(
                    file = child,
                    level = level + 1,
                    selectedFile = selectedFile,
                    onFileClick = onFileClick,
                    onDeleteClick = onDeleteClick,
                    onRenameClick = onRenameClick,
                    onNewFileClick = onNewFileClick,
                    onNewFolderClick = onNewFolderClick
                )
            }
        }
    }
}
