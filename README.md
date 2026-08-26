# Android To-Do List

Aplicativo Android de lista de tarefas criado para praticar a camada de apresentação com Jetpack Compose e a arquitetura MVVM. O usuário pode listar, criar, editar, concluir/desmarcar e excluir tarefas, mantendo os dados localmente.

## Tecnologias

- Kotlin e Jetpack Compose (Material 3)
- Room para persistência SQLite
- Coroutines e Flow para dados assíncronos e estado observável
- ViewModel do AndroidX
- Navigation Compose

## Arquitetura

O projeto usa MVVM, separando responsabilidade de dados, regras de apresentação e interface.

- `data`: contém `Tarefa`, `TarefaDao` e `TarefaDatabase`. O DAO consulta e altera as tarefas no Room.
- `repository/TarefaRepository`: é a única camada usada pela ViewModel para acessar o DAO. Ela expõe o `Flow` da lista e encapsula inserir, atualizar e excluir.
- `viewmodel/TarefaViewModel`: transforma o `Flow` do repositório em um `StateFlow` de tarefas, mantido no escopo da tela. Também executa as operações de CRUD usando `viewModelScope`.
- `ui/ListaTarefasScreen`: observa o estado com `collectAsStateWithLifecycle`, mostra as tarefas em `LazyColumn` e dispara eventos de criar, editar, concluir e excluir.
- `ui/FormularioTarefaScreen`: sem ID cria uma nova tarefa; com ID procura a tarefa no estado da ViewModel, preenche os campos e salva a atualização, preservando os demais dados.

## Navegação

`AppNavigation` inicia em `lista` e possui a rota `formulario/{tarefaId}`. Para cadastro, a rota recebe `-1`; para edição, recebe o ID persistido da tarefa. Ao salvar ou voltar, `popBackStack()` retorna à lista sem encerrar o aplicativo.

A `MainActivity` obtém o banco Room, cria `TarefaRepository` e fornece uma `TarefaViewModel.Factory` ao delegate `viewModels`. A mesma instância de ViewModel é entregue à navegação, mantendo o estado compartilhado pelas duas telas.

## Como executar

1. Abra o projeto no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Selecione um emulador Android ou conecte um dispositivo.
4. Execute o app pelo botão Run ou use `./gradlew assembleDebug`.

## Evidências

As evidências de execução devem ficar em `docs/evidencias/`. O arquivo `build.md` registra a validação de compilação. Para completar a entrega visual no emulador, salve capturas legíveis dos fluxos abaixo nessa pasta:

- lista inicial e acesso ao formulário;
- cadastro e tarefa exibida na lista;
- edição de uma tarefa;
- tarefa concluída;
- exclusão de uma tarefa.
