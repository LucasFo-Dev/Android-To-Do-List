package carreiras.com.github.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import carreiras.com.github.todolist.ui.FormularioTarefaScreen
import carreiras.com.github.todolist.ui.ListaTarefasScreen
import carreiras.com.github.todolist.viewmodel.TarefaViewModel

object Destinos {
    const val LISTA = "lista"
    const val FORMULARIO = "formulario/{tarefaId}"

    fun formulario(tarefaId: Int? = null) = "formulario/${tarefaId ?: -1}"
}

@Composable
fun AppNavigation(viewModel: TarefaViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinos.LISTA) {
        composable(Destinos.LISTA) {
            ListaTarefasScreen(
                viewModel = viewModel,
                aoCriarTarefa = { navController.navigate(Destinos.formulario()) },
                aoEditarTarefa = { navController.navigate(Destinos.formulario(it)) }
            )
        }
        composable(
            route = Destinos.FORMULARIO,
            arguments = listOf(navArgument("tarefaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("tarefaId") ?: -1
            FormularioTarefaScreen(
                tarefaId = id.takeIf { it >= 0 },
                viewModel = viewModel,
                aoVoltar = { navController.popBackStack() }
            )
        }
    }
}
