import lexAnalyzer.IOHelper;
import lexAnalyzer.LexAnalyzer;

import java.io.*;

/**
 * Created by yyy on 2016/11/8.
 */
public class main {
    public static void main(String[] args){
        Input input = new Input();
        Analyzer analyzer = new Analyzer();
        char[] input_array = input.getSyntaxInput("input.txt","tokenSequence.txt").toCharArray();
        analyzer.syntaxAnalyze(input_array,"parsing_table.txt","cfg.txt");
    }

}
