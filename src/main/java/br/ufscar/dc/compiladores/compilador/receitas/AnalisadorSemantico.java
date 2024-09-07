package br.ufscar.dc.compiladores.compilador.receitas;

import org.antlr.v4.runtime.Token;
import java.util.List;
import java.util.ArrayList;
import org.antlr.v4.runtime.tree.TerminalNode;


public class AnalisadorSemantico extends ReceitaBaseVisitor<Void> {

    TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
    public static List<String> errosSemanticos = new ArrayList<>();
    private boolean erroEncontrado = false;  // Flag para marcar se já houve um erro

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

    @Override
    public Void visitIngrediente(ReceitaParser.IngredienteContext ctx) {
        if (erroEncontrado) return null;

        StringBuilder nomeIngrediente = new StringBuilder();
        nomeIngrediente.append(ctx.NUMERO().getText()).append(" ");
        nomeIngrediente.append(ctx.UNIDADE_MEDIDA().getText()).append(" ");

        // Incluindo TEXTO adicional se houver
        for (TerminalNode texto : ctx.TEXTO()) {
            nomeIngrediente.append(texto.getText()).append(" ");
        }

        String ingredienteStr = nomeIngrediente.toString().trim();

        if (tabelaDeSimbolos.ingredienteExiste(ingredienteStr)) {
            registrarErroSemantico(ctx.start, "Ingrediente duplicado - " + ingredienteStr);
        } else {
            tabelaDeSimbolos.adicionarIngrediente(ingredienteStr);
        }
        return null;
    }


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


    public void verificarIngredientesNaoUtilizados() {
        if (erroEncontrado) return;  
        for (String ingrediente : tabelaDeSimbolos.verificarIngredientesNaoUsados()) {
            registrarErroSemantico(null, "Ingrediente não utilizado - " + ingrediente);
        }
    }

    @Override
    public Void visitReceita(ReceitaParser.ReceitaContext ctx) {
        super.visitReceita(ctx);
        verificarIngredientesNaoUtilizados();
        return null;
    }
}

