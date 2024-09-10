# Compilador de Receitas Culinárias

![image](https://github.com/user-attachments/assets/528e24ee-ff03-4623-b0d9-75009a999285)


## Integrantes do grupo
- Arisa Abiko Sakaguti - 800357
- Matheus Goulart Ranzani - 800278
- Thales Winther - 802499

## Contexto
O trabalho 6 (T6) da disciplina consistiu em implementar um compilador completo para uma determinada linguagem escolhida, de forma que o compilador tenha:
- Análise léxica + sintática: em outras palavras, uma gramática;
- Análise semântica: três ou quatro (ou mais) verificações de conformidade que não são feitas pela gramática;
- Geração de código ou interpretação: algo de útil ou interessante feito com a linguagem.

## Descrição

O compilador desenvolvido tem como objetivo processar receitas culinárias e gerar um documento HTML formatado a partir dessas receitas. 
A linguagem de entrada é projetada para permitir a descrição clara e estruturada de uma receita, incluindo título, tempos de preparo e cozimento, ingredientes e instruções.

O compilador é responsável por fazer as seguintes análises:

- Análise Léxica e Sintática: Utiliza uma gramática definida para verificar a estrutura e a sintaxe das receitas. A análise léxica identifica e classifica os componentes do texto, enquanto a análise sintática verifica a conformidade com a gramática definida.
- Análise Semântica: Realiza verificações adicionais para garantir que:
    - Ingredientes não sejam duplicados.
    - Todos os ingredientes listados sejam utilizados nas instruções.
    - Não haja passos duplicados nas instruções.
- Geração de Código HTML: Converte a receita processada em um documento HTML bem formatado, com estilo e estrutura adequados para visualização em navegadores.

## Como utilizar o compilador

O compilador recebe como entrada um arquivo texto contendo uma receita culinária:

### Arquivo de entrada:
```
RECEITA 

titulo: "Bolo de Chocolate"
tempo_preparo: 20 minutos
tempo_cozimento: 45 minutos

INGREDIENTES:
- 200 g de farinha de trigo
- 100 g de açúcar
- 50 g de cacau em pó
- 200 ml de leite
- 1 colher de chá de fermento em pó
- 1 pitada de sal

INSTRUCOES:
1. Pré-aqueça o forno a 180°C.
2. Misture a farinha de trigo, o açúcar, o cacau em pó, o fermento em pó e o sal em uma tigela.
3. Adicione o leite e mexa até obter uma massa homogênea.
4. Despeje a massa em uma forma untada.
5. Asse por 45 minutos ou até que um palito inserido no centro saia limpo.

FIM_RECEITA
```

### HTML Gerado:
![image](https://github.com/user-attachments/assets/4936ecf8-eba4-40e6-9e90-46a35e5c7faf)

### Regras do arquivo de entrada:
- O arquivo deve começar com a palavra-chave RECEITA e terminar com a palavra-chave FIM_RECEITA.
- O título da receita deve ser declarado logo após a palavra-chave RECEITA. Ele deve seguir o formato:
```
titulo: "Nome da Receita"
```
- O tempo de preparo deve ser declarado no formato:
```
tempo_preparo: Número Unidade de Tempo
```
- O tempo de cozimento deve seguir o mesmo formato do tempo de preparo:
```
tempo_cozimento: Número Unidade de Tempo
```
- A lista de ingredientes deve ser precedida pela palavra-chave INGREDIENTES:. Cada ingrediente deve ser declarado em uma linha no formato:
```
Quantidade Unidade de Medida Nome do Ingrediente
exemplo:
- 200 g de farinha de trigo
- 100 g de açúcar
```
- A lista de instruções deve ser precedida pela palavra-chave INSTRUCOES:. Cada instrução deve ser numerada e seguir o formato:
```
1. Texto da instrução.
2. Texto da próxima instrução.
```
- A receita deve ser finalizada com a palavra-chave FIM_RECEITA.

## Identificação de erros pelo compilador
O projeto contém exemplos de execução e compilação de receitas que contém erros tanto léxicos/sintáticos, quanto semânticos.
Os exemplos de receitas e erros estão dentro da pasta `compilador-de-receitas/casos-de-teste`.
Os casos de teste que identificam erros estão separados em análise léxicas, análise sintáticas e análise semânticas em suas respectivas pastas.

## Pré-requisitos
- Java instalado na versão 11 ou superior
- Alguma IDE capaz de buildar e compilar o projeto
- Algum terminal que consiga executar comandos Java

## Compilação
Para o desenvolvimento do trabalho foi utilizada o Apache NetBeans na versão IDE 21.\
Abra o projeto através da IDE e defina o SDK do projeto como um SDK do Java 11 ou superior, caso ainda não esteja definido.\
No menu superior acesse a seção de `Build` e realize o build do projeto.
Se o build for concluído com sucesso já é possível criar o arquivo .jar do analisador ou executar localmente o programa através da classe `Principal.java`.

Para compilar e executar o analisador pela própria IDE é preciso seguir as etapas:
- No menu superior acessar e cliclar com o botão direito no diretório raiz do projeto
- Clicar em "Clean and Build"
- Definir a seção de `Program arguments` com os dois argumentos do programa separados por vírgula, que são:
  - Argumento 1: arquivo de entrada (caminho completo)
  - Argumento 2: arquivo de saída (caminho completo)
- Por fim, basta clicar no botão `Run` e se tudo correr como o esperado o programa analisará o Argumento 1 e criará a sáida como sendo o Argumento 2

## Execução
Para a execução do compilador de receitas culinárias em uma linha de comando, siga as seguintes instruções:

Com o ambiente configurado, basta rodar o seguinte comando e a saída será gravada no Argumento 2:

```
java -jar /caminho_absoluto/compilador-de-receitas/target/compilador-de-receitas-1.0-SNAPSHOT-jar-with-dependencies.jar entrada.txt saida.txt

```

