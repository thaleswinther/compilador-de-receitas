package br.ufscar.dc.compiladores.compilador.receitas;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class GeradorHTML extends ReceitaBaseVisitor<Void> {
    private StringBuilder html;

    public GeradorHTML() {
        html = new StringBuilder();
    }

    public String getHTML() {
        return html.toString();
    }

    @Override
    public Void visitReceita(ReceitaParser.ReceitaContext ctx) {
        html.append("<html><body>\n");

        html.append("<style>\n");
        html.append("ul.ingredientes { list-style-type: disc; margin-left: 40px; padding: 0; }\n");
        html.append("ul.instrucoes { list-style-type: none; margin-left: 20px; padding: 0; }\n"); 
        html.append("input[type=\"checkbox\"] { margin-left: 0px; }\n"); 
        html.append("h1, h2, p { margin-left: 0; }\n"); 
        html.append("</style>\n");

        html.append("<h1>Receita: ").append(ctx.titulo().CADEIA().getText().replace("\"", "")).append("</h1>\n");
        html.append("<p>Tempo de preparo: ").append(ctx.tempo_preparo().NUMERO().getText()).append(" ")
                .append(ctx.tempo_preparo().UNIDADE_TEMPO().getText()).append("</p>\n");
        html.append("<p>Tempo de cozimento: ").append(ctx.tempo_cozimento().NUMERO().getText()).append(" ")
                .append(ctx.tempo_cozimento().UNIDADE_TEMPO().getText()).append("</p>\n");

        visitIngredientes(ctx.ingredientes());
        visitInstrucoes(ctx.instrucoes());

        html.append("</body></html>");
        return null;
    }

    @Override
    public Void visitIngredientes(ReceitaParser.IngredientesContext ctx) {
        html.append("<h2>Ingredientes:</h2>\n<ul class='ingredientes'>\n");
        for (ReceitaParser.IngredienteContext ingredienteCtx : ctx.ingrediente()) {
            html.append("<li>").append(formatarIngrediente(ingredienteCtx)).append("</li>\n");
        }
        html.append("</ul>\n");
        return null;
    }

    @Override
    public Void visitInstrucoes(ReceitaParser.InstrucoesContext ctx) {
        html.append("<h2>Instruções:</h2>\n<ul class='instrucoes'>\n");
        for (ReceitaParser.InstrucaoContext instrucaoCtx : ctx.instrucao()) {
            html.append("<li><input type=\"checkbox\"> ").append(formatarInstrucao(instrucaoCtx)).append("</li>\n");
        }
        html.append("</ul>\n");
        return null;
    }


    private String formatarIngrediente(ReceitaParser.IngredienteContext ctx) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(ctx.NUMERO().getText()).append(" ");
        sb.append(ctx.UNIDADE_MEDIDA().getText()).append(" ");

        for (TerminalNode texto : ctx.nome_ingrediente().TEXTO()) {
            sb.append(texto.getText()).append(" ");
        }
        return sb.toString().trim();
    }

    private String formatarInstrucao(ReceitaParser.InstrucaoContext ctx) {
        StringBuilder sb = new StringBuilder();

        for (ParseTree child : ctx.frase().children) {
            sb.append(child.getText()).append(" ");
        }

        String textoInstrucao = sb.toString().trim().replaceFirst("^\\d+\\.\\s*", "");

        return textoInstrucao.trim();
    }




    @Override
    public Void visitTitulo(ReceitaParser.TituloContext ctx) {
        return super.visitTitulo(ctx);
    }

    @Override
    public Void visitTempo_preparo(ReceitaParser.Tempo_preparoContext ctx) {
        return super.visitTempo_preparo(ctx);
    }

    @Override
    public Void visitTempo_cozimento(ReceitaParser.Tempo_cozimentoContext ctx) {
        return super.visitTempo_cozimento(ctx);
    }

    @Override
    public Void visitIngrediente(ReceitaParser.IngredienteContext ctx) {
        return super.visitIngrediente(ctx);
    }

    @Override
    public Void visitInstrucao(ReceitaParser.InstrucaoContext ctx) {
        return super.visitInstrucao(ctx);
    }

    @Override
    public Void visitFrase(ReceitaParser.FraseContext ctx) {
        return super.visitFrase(ctx);
    }
}