package carreiras.com.github.todolist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import carreiras.com.github.todolist.data.Tarefa
import carreiras.com.github.todolist.ui.theme.FiaptodolistTheme
import carreiras.com.github.todolist.viewmodel.TarefaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTarefasScreen(
    viewModel: TarefaViewModel,
    aoCriarTarefa: () -> Unit,
    aoEditarTarefa: (Int) -> Unit
) {
    val tarefas by viewModel.tarefas.collectAsStateWithLifecycle()
    ListaTarefasContent(
        tarefas = tarefas,
        aoCriarTarefa = aoCriarTarefa,
        aoEditarTarefa = aoEditarTarefa,
        aoAlternarConclusao = { viewModel.atualizar(it.copy(concluida = !it.concluida)) },
        aoExcluirTarefa = viewModel::excluir
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListaTarefasContent(
    tarefas: List<Tarefa>,
    aoCriarTarefa: () -> Unit,
    aoEditarTarefa: (Int) -> Unit,
    aoAlternarConclusao: (Tarefa) -> Unit,
    aoExcluirTarefa: (Tarefa) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Minhas tarefas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = aoCriarTarefa) {
                Icon(Icons.Default.Add, contentDescription = "Nova tarefa")
            }
        }
    ) { padding ->
        if (tarefas.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { Text("Nenhuma tarefa cadastrada") }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tarefas, key = { it.id }) { tarefa ->
                    TarefaItem(tarefa, aoEditarTarefa, aoAlternarConclusao, aoExcluirTarefa)
                }
            }
        }
    }
}

@Composable
private fun TarefaItem(
    tarefa: Tarefa,
    aoEditarTarefa: (Int) -> Unit,
    aoAlternarConclusao: (Tarefa) -> Unit,
    aoExcluirTarefa: (Tarefa) -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(tarefa.concluida, onCheckedChange = { aoAlternarConclusao(tarefa) })
            Column(Modifier.weight(1f)) {
                Text(
                    tarefa.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (tarefa.concluida) TextDecoration.LineThrough else null
                )
                if (tarefa.descricao.isNotBlank()) Text(tarefa.descricao)
            }
            IconButton(onClick = { aoEditarTarefa(tarefa.id) }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar tarefa")
            }
            IconButton(onClick = { aoExcluirTarefa(tarefa) }) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir tarefa")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListaTarefasPreview() = FiaptodolistTheme {
    ListaTarefasContent(
        tarefas = listOf(Tarefa(1, "Estudar Compose", "Implementar telas", false)),
        aoCriarTarefa = {}, aoEditarTarefa = {}, aoAlternarConclusao = {}, aoExcluirTarefa = {}
    )
}
