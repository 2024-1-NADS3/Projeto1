# FECAP - Fundação de Comércio Álvares Penteado

<p align="center">
<a href= "https://www.fecap.br/"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhZPrRa89Kma0ZZogxm0pi-tCn_TLKeHGVxywp-LXAFGR3B1DPouAJYHgKZGV0XTEf4AE&usqp=CAU" alt="FECAP - Fundação de Comércio Álvares Penteado" border="0"></a>
</p>

# BemViver Connect

## Grupo 1

## Integrantes: <a href="">Luca Filgiola Silvestri</a>, <a href="">Melissa Lequipe Lecona</a>, <a href="">Nayan Pinho de Oliveira</a>, <a href="">Nicolle Maria Gonçalves Firminio</a>

## Professores Orientadores: <a href="https://www.linkedin.com/in/victorbarq/">Victor Bruno Alexander Rosetti de Quiroz</a>, <a href="https://www.linkedin.com/in/adriano-valente-534576135/">Adriano Felix Valente</a>

## Descrição

<p align="center">
  <img src="imagens/Logo%20BemViver%20Connect.png" alt="Descrição da Imagem" width="300"/>
</p>

<br>O aplicativo Bem Viver Connect, tem como objetivo o promover organização, agilidade na rotina dos seus usuários. Ele terá um checklist onde os usuários terão a oportunidade de inserir suas rotinas diárias, simplificando a gestão do tempo para hábitos saudáveis. Além do mais, o aplicativo visa aprimorar a qualidade de vida dos usuários.

  O publico alvo do aplicativo consiste de jovens interessados em gerenciar suas rotinas, em busca de uma vida mais organizada e saudável. O aplicativo tem o foco de proporcionar uma experiência acessível e personalizável para essa faixa etária, facilitando a incorporação de práticas saudáveis no seu cotidiano.
<br>

## Funcionalidades
* Criação de tarefas para organização da rotina pessoal
* Exibição e controle de humor
* Acompanhamento do processo de realização de tarefas
* Notificações referentes aos avanços das tarefas

## Tecnologias Utilizadas no Projeto

Neste projeto, várias tecnologias e ferramentas são utilizadas para criar uma aplicação completa, desde o backend até o frontend. Abaixo estão as principais tecnologias envolvidas:

## Backend

1. **Node.js**:
   - **Descrição**: Node.js é um ambiente de execução JavaScript que permite executar código JavaScript no lado do servidor.
   - **Uso**: Utilizado para criar o servidor backend que lida com as solicitações HTTP, gerencia a lógica do aplicativo e interage com o banco de dados.

2. **SQLite**:
   - **Descrição**: SQLite é um banco de dados SQL leve, autocontido e embutido.
   - **Uso**: Utilizado como o banco de dados do projeto para armazenar informações sobre usuários e tarefas.

## Frontend

1. **Android Studio**:
   - **Descrição**: Android Studio é o ambiente de desenvolvimento integrado (IDE) oficial para o desenvolvimento de aplicativos Android.
   - **Uso**: Utilizado para desenvolver o aplicativo Android, incluindo a interface do usuário (UI), lógica de aplicação e integração com o backend.

2. **Java**:
   - **Descrição**: Java é uma linguagem de programação orientada a objetos amplamente usada para o desenvolvimento de aplicativos Android.
   - **Uso**: Utilizada para escrever a lógica do aplicativo Android, incluindo a interação com o backend e o gerenciamento da UI.

## Outras Ferramentas e Bibliotecas

1. **AsyncTask**:
   - **Descrição**: AsyncTask é uma classe do Android que permite operações em segundo plano e atualizações da UI.
   - **Uso**: Utilizada para realizar operações de rede em segundo plano, como enviar e receber dados do servidor.

2. **JSON**:
   - **Descrição**: JSON (JavaScript Object Notation) é um formato leve de intercâmbio de dados.
   - **Uso**: Utilizado para enviar e receber dados estruturados entre o servidor e o aplicativo Android.

3. **HTTP**:
   - **Descrição**: Protocolo de Transferência de Hipertexto (HTTP) é um protocolo de aplicação para sistemas de informação distribuídos, colaborativos e hipermídia.
   - **Uso**: Utilizado como protocolo de comunicação entre o cliente Android e o servidor Node.js.

## Integração

1. **HttpURLConnection**:
   - **Descrição**: HttpURLConnection é uma classe do Java que facilita a comunicação HTTP.
   - **Uso**: Utilizada para enviar solicitações HTTP ao servidor a partir do aplicativo Android.

## Design

1. **Figma**:
   - **Descrição**: Figma é um aplicativo destinado à criação de design de aplicativos.
   - **Uso**: Utilizada para criação do layout, paleta de cores, tipografia e elementos visuais planejados para refletir a identidade do aplicativo BemViver Connect.

<p align="center">
  <img src="imagens/Home.png" alt="Descrição da Imagem" width="300"/>
</p>

## Estrutura de pastas

-Raiz<br>
|<br>
|-->documentos<br>
 &emsp;| Testes_e_Qualidade_de_Software-_APP_Bem_Viver_Connect.pdf<br>
 &emsp;| Trabalho_Projeto_Bem_Viver_Connect-_UX.pdf<br>
|-->imagens<br>
|-->src<br>
 &emsp;|-->Backend<br>
 &emsp;|-->Frontend<br>
| .gitignore<br>
| README.md<br>

## 🛠 Instalação

Este projeto consiste em um aplicativo Android desenvolvido em Java utilizando o Android Studio. O aplicativo se conecta a um servidor Node.js hospedado no CodeSandbox, que interage com um banco de dados SQLite. A seguir, você encontrará as instruções para configurar e executar o projeto.

### Android:

<b>Pré-requisitos</b>

- Aparelho celular Android com espaço de, pelo menos, 25 MB de armazenamento livre.

<b>Passos para Instalação</b>

- O aplicativo já está publicado na Google Play Store e pode ser encontrado pelo nome "BemViver Connect". Alternativamente, o aplicativo pode ser instalado pelo arquivo APK, como explicado nos passos seguintes:
   
1. Faça o download do arquivo app-release.apk no seu celular. O arquivo pode ser encontrado em `Projeto1-main\arquivos .aab-.apk`.
   
2. Execute o arquivo e siga as instruções do seu telefone.

### Windows:

<b>Pré-requisitos</b>

- Android Studio instalado e configurado na sua máquina.

<b>Passos para Instalação</b>

1. Clone o repositório do projeto para a sua máquina local usando o seguinte comando:

```bash
git clone https://github.com/2024-1-NADS3/Projeto1.git
cd Projeto1
```

2. Abra o projeto pelo Android Studio, localizado em `Projeto1-main\src\Frontend\MyNavigation`.

3. Conecte um dispositivo Android ao seu computador ou inicie um emulador Android no Android Studio.
   
4. Certifique-se de que o servidor Node.js está em execução no CodeSandbox e o banco de dados SQLite está configurado corretamente, acessando o link <a href=https://codesandbox.io/p/devbox/bemviver-connect-qy5dwc>https://codesandbox.io/p/devbox/bemviver-connect-qy5dwc<a>.
   
5. No Android Studio, clique no botão "Run" (ícone de play) na barra de ferramentas ou pressione Shift + F10.

6. Selecione o dispositivo ou emulador onde deseja executar o aplicativo.

7. Acesse o aplicativo.

## 💻 Configuração para Desenvolvimento

Para abrir este projeto você necessita das seguintes ferramentas:

- [Node.js](https://nodejs.org/) (versão 14 ou superior)
- [Android Studio](https://developer.android.com/studio)
- [Git](https://git-scm.com/)

<b>Passos para Configuração</b>

<b>Clonar o Repositório</b>

Primeiro, clone o repositório para a sua máquina local usando Git:

```bash
git clone https://github.com/2024-1-NADS3/Projeto1.git
cd Projeto1-main
```

<b>Configuração do Servidor Node.js</b>

A configuração do servidor foi feita pela aplicação CodeSandBox, você pode acessá-la pelo link <a href=https://codesandbox.io/p/devbox/bemviver-connect-qy5dwc>https://codesandbox.io/p/devbox/bemviver-connect-qy5dwc<a>.

O servidor estará rodando em ´https://qy5dwc-3000.csb.app´.

<b>Configuração do Projeto Android</b>

1. Abra o Android Studio.

1. Clique em 'Open an existing Android Studio project' e selecione o diretório do projeto Android (Projeto1-main\src\Frontend\MyNavigation).

2. Aguarde o Android Studio sincronizar e baixar todas as dependências.

<b>Executar o Test-Suite Automatizado</b>

<b>Para rodar os testes automatizados do servidor:</b>

Você pode usar um framework como o Mocha e o Chai. Se você ainda não os tiver instalados, adicione-os ao seu projeto:

```bash
npm install mocha chai --save-dev
```

Adicione um script de teste ao seu package.json:

```json
"scripts": {
  "test": "mocha"
}
```

Crie um diretório test e adicione seus arquivos de teste. Por exemplo, test/server.test.js.

Para rodar os testes, use:

```bash
npm test
```

<b>Para executar os testes instrumentados no Android Studio:</b>

No Android Studio, abra o painel Run.
Selecione Run 'All Tests' ou Run 'androidTest' para executar os testes de unidade ou instrumentados, respectivamente.

## 🗃 Histórico de lançamentos

* 0.2.1 - 24/05/2024
    * MUDANÇA: Atualização para o novo servidor node.js
* 0.2.0 - 23/05/2024
    * Lançamento oficial completo, com todas as funcionalidades aplicadas
* 0.1.0 - 11/05/2024
    * O primeiro lançamento adequado com integração com o servidor
* 0.0.1 - 03/04/2024
    * Trabalho em andamento

## 📋 Licença/License


## 🎓 Referências

Aqui estão as referências usadas no projeto.

1. <https://github.com/iuricode/readme-template>
2. <https://github.com/gabrieldejesus/readme-model>
3. <https://creativecommons.org/share-your-work/>
4. <https://freesound.org/>
5. Músicas por: <a href="https://freesound.org/people/DaveJf/sounds/616544/"> DaveJf </a> e <a href="https://freesound.org/people/DRFX/sounds/338986/"> DRFX </a> ambas com Licença CC 0.
