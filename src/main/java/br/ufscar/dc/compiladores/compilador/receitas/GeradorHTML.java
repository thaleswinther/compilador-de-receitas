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
        html.append("<html><head>\n");
        html.append("<meta charset='UTF-8'>\n");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1'>\n");
        html.append("<style>\n");
        html.append("body { font-family: 'Arial', sans-serif; background-color: #f8f9fa; color: #333; padding: 20px; }\n");
        html.append(".container { max-width: 800px; margin: 0 auto; background: #fff; padding: 20px; box-shadow: 0 0 15px rgba(0,0,0,0.1); border-radius: 8px; }\n");
        html.append("h1 { font-size: 2.5em; color: #343a40; text-align: center; margin-bottom: 0.5em; }\n");
        html.append("h2 { font-size: 1.8em; color: #495057; border-bottom: 2px solid #dee2e6; padding-bottom: 0.3em; }\n");
        html.append("p { font-size: 1.1em; color: #6c757d; margin: 0.8em 0; }\n");
        html.append("ul { list-style-type: none; padding-left: 0; }\n");
        html.append("ul.ingredientes li { font-size: 1.1em; padding: 0.5em 0; border-bottom: 1px solid #dee2e6; }\n");
        html.append("ul.instrucoes li { margin-bottom: 0.5em; }\n");
        html.append("input[type='checkbox'] { margin-right: 10px; }\n");
        html.append("button { background-color: #28a745; color: white; border: none; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; border-radius: 5px; margin: 10px 0; cursor: pointer; }\n");
        html.append("button:hover { background-color: #218838; }\n");
        html.append("</style>\n");
        html.append("</head><body>\n");

        html.append("<div class='container'>\n");
        html.append("<h1>").append(ctx.titulo().CADEIA().getText().replace("\"", "")).append("</h1>\n");
        html.append("<p><strong>Tempo de preparo:</strong> ").append(ctx.tempo_preparo().NUMERO().getText()).append(" ")
                .append(ctx.tempo_preparo().UNIDADE_TEMPO().getText()).append("</p>\n");
        html.append("<p><strong>Tempo de cozimento:</strong> ").append(ctx.tempo_cozimento().NUMERO().getText()).append(" ")
                .append(ctx.tempo_cozimento().UNIDADE_TEMPO().getText()).append("</p>\n");

        visitIngredientes(ctx.ingredientes());
        visitInstrucoes(ctx.instrucoes());

        html.append("</div>\n");
        html.append("</body></html>");
        return null;
    }

    @Override
    public Void visitIngredientes(ReceitaParser.IngredientesContext ctx) {
        html.append("<h2>Ingredientes</h2>\n<ul class='ingredientes'>\n");
        for (ReceitaParser.IngredienteContext ingredienteCtx : ctx.ingrediente()) {
            html.append("<li>").append(formatarIngrediente(ingredienteCtx)).append("</li>\n");
        }
        html.append("</ul>\n");
        return null;
    }

    @Override
    public Void visitInstrucoes(ReceitaParser.InstrucoesContext ctx) {
        html.append("<h2>Instruções</h2>\n<ul class='instrucoes'>\n");
        for (ReceitaParser.InstrucaoContext instrucaoCtx : ctx.instrucao()) {
            html.append("<li><button><input type='checkbox'> ").append(formatarInstrucao(instrucaoCtx)).append("</button></li>\n");
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
