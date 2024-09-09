package br.ufscar.dc.compiladores.compilador.receitas;

import org.antlr.v4.runtime.Token;
import java.util.List;
import java.util.ArrayList;
import org.antlr.v4.runtime.tree.TerminalNode;


public class AnalisadorSemantico extends ReceitaBaseVisitor<Void> {

    // Tabela para armazenar os ingredientes e instruções
    TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
    
    // Lista para armazenar erros semânticos encontrados
    public static List<String> errosSemanticos = new ArrayList<>();
    
    // Flag para marcar se já houve um erro
    private boolean erroEncontrado = false;  

    // Método estático para registrar erros semânticos
    public static void registrarErroSemantico(Token t, String mensagem) {
        if (!errosSemanticos.isEmpty()) {
            return; 
        }

        int linha = t != null ? t.getLine() : 0;
        if (linha > 0) {
            errosSemanticos.add(String.format("Erro na linha %d: %s", linha, mensagem));
        } else {
            errosSemanticos.add(String.format("Erro: %s", mensagem));
        }
    }
    
    // Visita um nó de ingrediente e verifica se já foi declarado
    @Override
    public Void visitIngrediente(ReceitaParser.IngredienteContext ctx) {
        if (erroEncontrado) return null;

        // Obter o nome do ingrediente
        StringBuilder nomeIngrediente = new StringBuilder();
        for (TerminalNode texto : ctx.nome_ingrediente().TEXTO()) {
            nomeIngrediente.append(texto.getText()).append(" ");
        }

        String ingredienteStr = nomeIngrediente.toString().trim();

        if (ingredienteStr.startsWith("de ")) {
            ingredienteStr = ingredienteStr.substring(3);  
        }

        if (tabelaDeSimbolos.ingredienteExiste(ingredienteStr)) {
            registrarErroSemantico(ctx.start, "Ingrediente duplicado - " + ingredienteStr);
        } else {
            tabelaDeSimbolos.adicionarIngrediente(ingredienteStr);
        }
        return null;
    }
    

    // Visita um nó de frase e verifica se contém algum ingrediente
    @Override
    public Void visitFrase(ReceitaParser.FraseContext ctx) {
        if (erroEncontrado) return null; 
        StringBuilder fraseTexto = new StringBuilder();

        for (TerminalNode texto : ctx.TEXTO()) {
            fraseTexto.append(texto.getText()).append(" ");
        }

        String fraseFinal = fraseTexto.toString().trim();

        for (String ingrediente : tabelaDeSimbolos.ingredientes) {
            if (fraseFinal.contains(ingrediente)) {
                tabelaDeSimbolos.adicionarInstrucao(fraseFinal);
            }
        }

        return null;
    }
    

    // Visita um nó de instrução e verifica se já foi declarado
    @Override
    public Void visitInstrucao(ReceitaParser.InstrucaoContext ctx) {
        if (erroEncontrado) return null;

        StringBuilder instrucaoTexto = new StringBuilder();
        for (TerminalNode texto : ctx.frase().TEXTO()) {
            instrucaoTexto.append(texto.getText()).append(" ");
        }

        String instrucaoFinal = instrucaoTexto.toString().trim();

        if (tabelaDeSimbolos.instrucaoExiste(instrucaoFinal)) {
            registrarErroSemantico(ctx.start, "Instrução duplicada - " + instrucaoFinal);
        } else {
            tabelaDeSimbolos.adicionarInstrucao(instrucaoFinal);
        }
        return null;
    }


    // Verifica se há ingredientes declarados, mas não utilizados nas instruções
    public void verificarIngredientesNaoUtilizados() {
        if (erroEncontrado) return;  
        for (String ingrediente : tabelaDeSimbolos.verificarIngredientesNaoUsados()) {
            registrarErroSemantico(null, "Ingrediente não utilizado - " + ingrediente);
        }
    }

    // Visita a receita e ao final verifica se há ingredientes não utilizados
    @Override
    public Void visitReceita(ReceitaParser.ReceitaContext ctx) {
        super.visitReceita(ctx);
        verificarIngredientesNaoUtilizados();
        return null;
    }
}

