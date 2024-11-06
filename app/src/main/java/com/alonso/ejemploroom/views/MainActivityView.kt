package com.alonso.ejemploroom.views

import android.graphics.drawable.Icon
import android.widget.CheckBox
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alonso.ejemploroom.MainActivity.Companion.coroutine
import com.alonso.ejemploroom.ent.TareaEntity
import com.alonso.ejemploroom.MainActivity.Companion.database
import kotlinx.coroutines.launch

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier) {
    var txtField by remember{ mutableStateOf("") }
    val listaTareas = remember {mutableStateListOf<TareaEntity>()}

    LaunchedEffect(Unit) {
        listaTareas.clear()
        listaTareas.addAll(database.tareaDao().getAll())
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = txtField,
                onValueChange = {txtField = it},
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    if (txtField.isNotBlank()) {
                        var newTask = TareaEntity(name = txtField, isDone = false)
                        coroutine.launch {
                            database.tareaDao().insert(newTask)
                            txtField = ""
                            listaTareas.add(newTask)
                        }
                    }
                }
            ) {
                Text("Añadir")
            }
        }
        LazyColumn {
            items(items = listaTareas.asReversed()) { t ->
                TaskView(t, onDelete = {task ->
                    coroutine.launch {
                        database.tareaDao().delete(task)
                        listaTareas.remove(task)
                    }
                })
            }
        }
    }
}

@Composable
fun TaskView(task: TareaEntity, onDelete: (TareaEntity) -> Unit) {
    var checked by remember {mutableStateOf(task.isDone)}

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment =  Alignment.CenterVertically
    ) {
        Checkbox (
            checked = checked,
            onCheckedChange = {
                checked = !checked
                task.isDone = !task.isDone
                coroutine.launch {
                    database.tareaDao().update(task)
                }
            }
        )
        Text(task.name, fontSize = 16.sp, modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                onDelete(task)
            },
            content = {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            },
        )
    }
}