grammar Receita;

// Definição principal da receita
receita: RECEITA titulo tempo_preparo tempo_cozimento ingredientes instrucoes FIM_RECEITA;

// Definições de título, tempos e ingredientes
titulo: TITULO ':' CADEIA;
tempo_preparo: TEMPO_PREPARO ':' NUMERO UNIDADE_TEMPO;
tempo_cozimento: TEMPO_COZIMENTO ':' NUMERO UNIDADE_TEMPO;
ingredientes: INGREDIENTES ':' ingrediente+;

// Cada ingrediente tem uma quantidade, unidade e nome
ingrediente: '-' NUMERO UNIDADE_MEDIDA nome_ingrediente;
nome_ingrediente: TEXTO? (TEXTO)*;

// Definições das instruções da receita
instrucoes: INSTRUCOES ':' instrucao+;
instrucao: NUMERO '.' frase '.';

// Frase composta por diferentes tokens
frase: (TEXTO | NUMERO | UNIDADE_TEMPO | UNIDADE_MEDIDA | SIMBOLO | WS)*;

// Tokens para palavras-chave e símbolos da receita
RECEITA: 'RECEITA';
UNIDADE_TEMPO: 'horas' | 'minutos' | 'hora' | 'segundos';
FIM_RECEITA: 'FIM_RECEITA';
TITULO: 'titulo';
TEMPO_PREPARO: 'tempo_preparo';
TEMPO_COZIMENTO: 'tempo_cozimento';
INGREDIENTES: 'INGREDIENTES';
INSTRUCOES: 'INSTRUCOES';
CADEIA: '"' ~[\n"']* '"'; // Cadeia de caracteres entre aspas
NUMERO: [0-9]+;
UNIDADE_MEDIDA: 'g' | 'ml' | 'colher de chá' | 'pitada' | 'ovo' | 'ovos' | 'colher de sopa' | 'dentes';
TEXTO: [a-zA-Zá-úÁ-Ú\-]+( [a-zA-Zá-úÁ-Ú\-]+)*; // Texto com suporte a acentos
SIMBOLO: [.,°]+;
WS: [ \t\r\n]+ -> skip; // Ignora espaços em branco
