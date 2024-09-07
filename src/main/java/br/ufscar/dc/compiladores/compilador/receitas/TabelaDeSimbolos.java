package br.ufscar.dc.compiladores.compilador.receitas;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TabelaDeSimbolos {

    public enum TipoSimbolo {
        INGREDIENTE,
        INSTRUCAO
    }

    private final Map<String, TipoSimbolo> mapaSimbolos = new HashMap<>();
    public final Set<String> ingredientes = new HashSet<>();
    public final Set<String> instrucoes = new HashSet<>();

    public void adicionarIngrediente(String nome) {
        ingredientes.add(nome);
        mapaSimbolos.put(nome, TipoSimbolo.INGREDIENTE);
    }

    public void adicionarInstrucao(String instrucao) {
        instrucoes.add(instrucao);
        mapaSimbolos.put(instrucao, TipoSimbolo.INSTRUCAO);
    }

    public boolean ingredienteExiste(String nome) {
        return ingredientes.contains(nome);
    }

    public boolean instrucaoExiste(String instrucao) {
        return instrucoes.contains(instrucao);
    }

    public Set<String> verificarIngredientesNaoUsados() {
        Set<String> naoUsados = new HashSet<>(ingredientes);
        naoUsados.removeAll(instrucoes);  // Remove os ingredientes que foram utilizados
        return naoUsados;
    }
}

