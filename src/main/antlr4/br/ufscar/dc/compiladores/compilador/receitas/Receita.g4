grammar Receita;

receita: RECEITA titulo tempo_preparo tempo_cozimento ingredientes instrucoes FIM_RECEITA;

titulo: TITULO ':' CADEIA;
tempo_preparo: TEMPO_PREPARO ':' NUMERO UNIDADE_TEMPO;
tempo_cozimento: TEMPO_COZIMENTO ':' NUMERO UNIDADE_TEMPO;
ingredientes: INGREDIENTES ':' ingrediente+;

ingrediente: '-' NUMERO UNIDADE_MEDIDA nome_ingrediente;
nome_ingrediente: TEXTO? (TEXTO)*;


instrucoes: INSTRUCOES ':' instrucao+;
instrucao: NUMERO '.' frase '.';
frase: (TEXTO | NUMERO | UNIDADE_TEMPO | UNIDADE_MEDIDA | SIMBOLO | WS)*;


// Tokens separados
RECEITA: 'RECEITA';
UNIDADE_TEMPO: 'horas' | 'minutos' | 'hora' |'segundos';
FIM_RECEITA: 'FIM_RECEITA';
TITULO: 'titulo';
TEMPO_PREPARO: 'tempo_preparo';
TEMPO_COZIMENTO: 'tempo_cozimento';
INGREDIENTES: 'INGREDIENTES';
INSTRUCOES: 'INSTRUCOES';
CADEIA: '"' ~[\n"']* '"';
NUMERO: [0-9]+;
UNIDADE_MEDIDA: 'g' | 'ml' | 'colher de chá' | 'pitada' | 'ovo' | 'ovos' | 'colher de sopa' | 'dentes';  // Unidades de medida
TEXTO: [a-zA-Zá-úÁ-Ú\-]+( [a-zA-Zá-úÁ-Ú\-]+)*;
SIMBOLO: [.,°]+;
WS: [ \t\r\n]+ -> skip;
