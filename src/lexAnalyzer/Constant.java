package lexAnalyzer;

import java.util.Arrays;

/**
 * @author cuihao
 */
public class Constant {
    private static final String[] KEYWORDS={"abstract","assert","boolean","break","byte","case","catch","char","class",
            "const","continue","default","do","double","else","enum","extends","final","finally","float","for","goto",
            "if", "implements", "import","instanceof","int","interface","long","native","new","package","private",
            "protected","public","return","strictfp","short","static","super","switch","synchronized","this","throw",
            "throws","transient","try","void","volatile","while"};
    private static final String[] OPERATOR={"=","+","-","*","/","％",">","<","&","|","!","?", ":","+=",
            "-=","*=","/=","!=",">=","<=","<<",">>","==","||","&&"};
    private static final String[] SEPARATOR={";","{","}","[","]","(",")",","};

    public static boolean isDigit(char test) {
        return  test>='0' && test<='9';
    }

    public static boolean isLetter(char test) {
        return ( test>='a' && test <= 'z' ) || (test>='A' && test<='Z');
    }

    public static int isKeyword(String word) {
        return Arrays.asList(KEYWORDS).indexOf(word);
    }

    public static int isOperator(String test) {
        return Arrays.asList(OPERATOR).indexOf(test+"");
    }

    public static int isSeparator(char test) {
        return Arrays.asList(SEPARATOR).indexOf(test+"");
    }

}
