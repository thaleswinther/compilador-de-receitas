package br.ufscar.dc.compiladores.compilador.receitas;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

public class Principal {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Uso: java Principal <arquivo de entrada> <arquivo de saída>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        processarArquivo(inputFile, outputFile);
    }

    public static void processarArquivo(String inputFile, String outputFile) {
        try (InputStream is = new FileInputStream(inputFile);
             PrintWriter writer = new PrintWriter(outputFile, "UTF-8")) {

            CharStream input = CharStreams.fromStream(is);
            ReceitaLexer lexer = new ReceitaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ReceitaParser parser = new ReceitaParser(tokens);

            CustomSyntaxErrorListener errorListener = new CustomSyntaxErrorListener(writer);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

            ParseTree tree = parser.receita();
            
            if (!errorListener.hasErrors()) {
                // Análise semântica
                AnalisadorSemantico semantico = new AnalisadorSemantico();
                semantico.visit(tree);

                if (!AnalisadorSemantico.errosSemanticos.isEmpty()) {
                    for (String erro : AnalisadorSemantico.errosSemanticos) {
                        writer.println(erro);
                    }
                } else {
                    // Geração de código HTML
                    GeradorHTML geradorHTML = new GeradorHTML();
                    geradorHTML.visit(tree);

                    writer.print(geradorHTML.getHTML());
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar arquivos: " + e.getMessage());
        }
    }

    private static class CustomSyntaxErrorListener extends BaseErrorListener {
        private PrintWriter writer;
        private boolean errorOccurred = false;

        public CustomSyntaxErrorListener(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine, String msg, RecognitionException e) {
            if (!errorOccurred) {
                writer.println("Erro de sintaxe na linha " + line + ":" + charPositionInLine + " - " + msg);
                errorOccurred = true;
            }
        }

        public boolean hasErrors() {
            return errorOccurred;
        }
    }
}
