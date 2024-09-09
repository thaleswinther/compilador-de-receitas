package br.ufscar.dc.compiladores.compilador.receitas;

import java.util.HashSet;
import java.util.Set;

public class TabelaDeSimbolos {

    public enum TipoSimbolo {
        INGREDIENTE,
        INSTRUCAO
    }

    public final Set<String> ingredientes = new HashSet<>();
    public final Set<String> instrucoes = new HashSet<>();

    public void adicionarIngrediente(String nome) {
        ingredientes.add(nome);
    }

    public void adicionarInstrucao(String instrucao) {
        instrucoes.add(instrucao);
    }

    public boolean ingredienteExiste(String nome) {
        return ingredientes.contains(nome);
    }

    public boolean instrucaoExiste(String instrucao) {
        return instrucoes.contains(instrucao);
    }

    public Set<String> verificarIngredientesNaoUsados() {
        Set<String> naoUsados = new HashSet<>(ingredientes);

        for (String instrucao : instrucoes) {
            // Verifica se o nome de algum ingrediente está contido na instrução
            naoUsados.removeIf(ingrediente -> instrucao.contains(ingrediente));
        }

        return naoUsados;
    }

}

