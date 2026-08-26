package carreiras.com.github.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import carreiras.com.github.todolist.data.TarefaDatabase
import carreiras.com.github.todolist.navigation.AppNavigation
import carreiras.com.github.todolist.repository.TarefaRepository
import carreiras.com.github.todolist.ui.theme.FiaptodolistTheme
import carreiras.com.github.todolist.viewmodel.TarefaViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: TarefaViewModel by viewModels {
        val database = TarefaDatabase.getDatabase(applicationContext)
        TarefaViewModel.Factory(TarefaRepository(database.tarefaDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FiaptodolistTheme {
                AppNavigation(viewModel)
            }
        }
    }
}

