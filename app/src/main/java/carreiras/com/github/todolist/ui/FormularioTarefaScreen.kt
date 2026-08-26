package carreiras.com.github.todolist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import carreiras.com.github.todolist.data.Tarefa
import carreiras.com.github.todolist.ui.theme.FiaptodolistTheme
import carreiras.com.github.todolist.viewmodel.TarefaViewModel

@Composable
fun FormularioTarefaScreen(
    tarefaId: Int?,
    viewModel: TarefaViewModel,
    aoVoltar: () -> Unit
) {
    val tarefas by viewModel.tarefas.collectAsStateWithLifecycle()
    val tarefaEmEdicao = tarefas.firstOrNull { it.id == tarefaId }
    FormularioTarefa(
        tarefa = tarefaEmEdicao,
        aoSalvar = { titulo, descricao ->
            if (tarefaEmEdicao == null) viewModel.inserir(titulo, descricao)
            else viewModel.atualizar(tarefaEmEdicao.copy(titulo = titulo, descricao = descricao))
            aoVoltar()
        },
        aoVoltar = aoVoltar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormularioTarefa(
    tarefa: Tarefa?,
    aoSalvar: (String, String) -> Unit,
    aoVoltar: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    LaunchedEffect(tarefa?.id) {
        tarefa?.let {
            titulo = it.titulo
            descricao = it.descricao
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (tarefa == null) "Nova tarefa" else "Editar tarefa") },
                navigationIcon = {
                    IconButton(onClick = aoVoltar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Título") },
                isError = titulo.isBlank(),
                singleLine = true
            )
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                label = { Text("Descrição") },
                minLines = 3
            )
            Button(
                onClick = { aoSalvar(titulo.trim(), descricao.trim()) },
                enabled = titulo.isNotBlank(),
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
            ) { Text("Salvar") }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NovoFormularioPreview() = FiaptodolistTheme {
    FormularioTarefa(null, { _, _ -> }, {})
}

@Preview(showBackground = true)
@Composable
private fun EdicaoFormularioPreview() = FiaptodolistTheme {
    FormularioTarefa(Tarefa(1, "Comprar pão", "Padaria antes das 18h"), { _, _ -> }, {})
}
