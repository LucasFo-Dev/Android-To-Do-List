package carreiras.com.github.todolist.repository

import carreiras.com.github.todolist.data.Tarefa
import carreiras.com.github.todolist.data.TarefaDao
import kotlinx.coroutines.flow.Flow

/** Camada que centraliza o acesso aos dados de tarefas. */
class TarefaRepository(private val tarefaDao: TarefaDao) {
    val tarefas: Flow<List<Tarefa>> = tarefaDao.listarTodas()

    suspend fun inserir(tarefa: Tarefa) = tarefaDao.inserir(tarefa)
    suspend fun atualizar(tarefa: Tarefa) = tarefaDao.atualizar(tarefa)
    suspend fun excluir(tarefa: Tarefa) = tarefaDao.deletar(tarefa)
}
