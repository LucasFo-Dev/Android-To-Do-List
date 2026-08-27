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

As evidências de execução ficam em `docs/evidencias/`. Cada imagem abaixo demonstra uma etapa do fluxo funcional da aplicação.

### 1. Lista inicial

![Lista inicial](docs/evidencias/1%20-%20Tela%20Inicial.png)

### 2. Cadastro de tarefa

![Cadastro de tarefa](docs/evidencias/2%20-%20Cadastro.png)

### 3. Tarefa cadastrada aparecendo na lista

![Tarefa cadastrada aparecendo na lista](docs/evidencias/3%20-%20Tarefa%20cadastrada%20aparecendo%20na%20lista.png)

### 4. Edição de uma tarefa

![Edição de uma tarefa](docs/evidencias/4%20-%20Edi%C3%A7%C3%A3o%20de%20uma%20tarefa.png)

### 5. Tarefa concluída

![Tarefa concluída](docs/evidencias/5%20-%20Tarefa%20Conclu%C3%ADda.png)

### 6. Exclusão de tarefa

![Exclusão de tarefa](docs/evidencias/6%20-%20Exclus%C3%A3o%20de%20Tarefa.png)

### 7. Navegação entre lista e formulário

![Navegação entre lista e formulário](docs/evidencias/7%20-%20Navega%C3%A7%C3%A3o%20entre%20lista%20e%20formul%C3%A1rio.png)

### 8. Build e execução bem-sucedidos

![Build bem-sucedido](docs/evidencias/8%20-%20Build%20ou%20execu%C3%A7%C3%A3o%20do%20projeto%20sem%20erros.png)
