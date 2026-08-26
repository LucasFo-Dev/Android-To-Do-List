package carreiras.com.github.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import carreiras.com.github.todolist.data.Tarefa
import carreiras.com.github.todolist.repository.TarefaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TarefaViewModel(private val repository: TarefaRepository) : ViewModel() {
    val tarefas: StateFlow<List<Tarefa>> = repository.tarefas.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun inserir(titulo: String, descricao: String) = viewModelScope.launch {
        repository.inserir(Tarefa(titulo = titulo.trim(), descricao = descricao.trim()))
    }

    fun atualizar(tarefa: Tarefa) = viewModelScope.launch { repository.atualizar(tarefa) }

    fun excluir(tarefa: Tarefa) = viewModelScope.launch { repository.excluir(tarefa) }

    class Factory(private val repository: TarefaRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(TarefaViewModel::class.java))
            return TarefaViewModel(repository) as T
        }
    }
}
