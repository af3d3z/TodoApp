package com.alonso.ejemploroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.alonso.ejemploroom.dal.TareasDatabase
import com.alonso.ejemploroom.ent.TareaEntity
import com.alonso.ejemploroom.ui.theme.EjemploRoomTheme
import com.alonso.ejemploroom.views.PantallaPrincipal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: TareasDatabase
        lateinit var todas: List<TareaEntity>
        lateinit var coroutine: CoroutineScope
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            this, TareasDatabase::class.java, "tareas-db"
        ).build()
        enableEdgeToEdge()
        setContent {
            coroutine = rememberCoroutineScope()
            EjemploRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaPrincipal(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EjemploRoomTheme {
        Greeting("Android")
    }
}
