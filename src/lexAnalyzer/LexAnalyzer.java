package lexAnalyzer;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * logic of lexical analyzer
 * @author cuihao
 */
public class LexAnalyzer {
    private IOHelper ioHelper;
    private List<Token> tokens = new ArrayList<>();

    public LexAnalyzer(IOHelper ioHelper) {
        this.ioHelper = ioHelper;
    }

    public void lexicalAnalyze(String outFileName) {
        String line = "";
        while ((line = ioHelper.nextLine()) != null) {
            int index = 0;
            String tempWord = "";
            State state = State.STATE_0;
            while (index < line.length()) {
                char current = line.charAt(index);
                switch (state){
                    case STATE_0:
                        if (Constant.isDigit(current)) {
                            state = State.STATE_2;
                            tempWord += current;
                        } else if (Constant.isLetter(current)) {
                            state = State.STATE_1;
                            tempWord += current;
                        } else if (Constant.isOperator(current+"")>=0) {
                            state = State.STATE_3;
                            tempWord += current;
                        } else if (Constant.isSeparator(current)>=0) {
                            state = State.STATE_3;
                            tempWord += current;
                        }
                        break;
                    case STATE_1:
                        if (Constant.isDigit(current)) {
                            tempWord += current;
                        } else if (Constant.isLetter(current)) {
                            tempWord += current;
                        } else {
                            index--;
                            state = State.STATE_0;
                            if (Constant.isKeyword(tempWord)>=0) {
                                addToken(tempWord, Catalog.KEYWORD, Constant.isKeyword(tempWord));
                            } else {
                                addToken(tempWord, Catalog.ID, -1);
                            }
                            tempWord = "";
                        }
                        break;
                    case STATE_2:
                        if (Constant.isDigit(current)) {
                            tempWord += current;
                        } else if (current=='.') {
                            tempWord += current;
                            state = State.STATE_4;
                        } else {
                            index--;
                            state = State.STATE_0;
                            addToken(tempWord, Catalog.INT, -1);
                            tempWord = "";
                        }
                        break;
                    case STATE_3:
                        char su = tempWord.charAt(0);
                        if (((su=='+'||su=='-'||su=='*'||su=='/'||su=='<'||su=='>'||su=='!'||su=='=') && current=='=')
                                || ((su=='|' && current=='|')||(su=='&' && current=='&')||(su=='<' && current=='<')||(su=='>' && current=='>'))) {
                            addToken(tempWord+current, Catalog.OPERATOR, Constant.isOperator(tempWord+current));
                        }else {
                            index--;
                            if (Constant.isOperator(tempWord)>=0) {
                                addToken(tempWord, Catalog.OPERATOR, Constant.isOperator(tempWord));
                            } else {
                                addToken(tempWord, Catalog.SEPARATOR, Constant.isSeparator(tempWord.charAt(0)));
                            }
                        }
                        state = State.STATE_0;
                        tempWord = "";
                        break;
                    case STATE_4:
                        if (Constant.isDigit(current)) {
                            state = State.STATE_5;
                            tempWord += current;
                        } else {
                            System.out.println("error: state 4");
                        }
                        break;
                    case STATE_5:
                        if (Constant.isDigit(current)) {
                            tempWord += current;
                        } else {
                            index --;
                            state = State.STATE_0;
                            addToken(tempWord, Catalog.DOUBLE,-1);
                            tempWord="";
                        }
                        break;
                }
                index++;
            }
            switch (state) {
                case STATE_1:
                    if (Constant.isKeyword(tempWord)>=0) {
                        addToken(tempWord, Catalog.KEYWORD, Constant.isKeyword(tempWord));
                    } else {
                        addToken(tempWord, Catalog.ID, -1);
                    }
                    break;
                case STATE_2:
                    addToken(tempWord, Catalog.INT, -1);
                    break;
                case STATE_3:
                    if (tempWord.length()>0) {
                        if (Constant.isOperator(tempWord)>=0) {
                            addToken(tempWord, Catalog.OPERATOR, Constant.isOperator(tempWord));
                        } else {
                            addToken(tempWord, Catalog.SEPARATOR, Constant.isSeparator(tempWord.charAt(0)));
                        }
                    }
                    break;
                case STATE_5:
                    addToken(tempWord, Catalog.DOUBLE,-1);
                    break;
            }
        }
        output(outFileName);
//        for(Token token:tokens){
//            System.out.println(token);
//        }
    }
    private void output(String fileName){
        File outputFile = new File(fileName);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile,false));
            for(Token token:tokens){
                bw.write(token.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addToken(String lex, Catalog catalog, int index) {
        Token token = new Token(catalog, lex, index);
        tokens.add(token);
    }
}
