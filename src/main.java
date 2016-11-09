import java.io.*;

/**
 * Created by yyy on 2016/11/8.
 */
public class main {
    public static void main(String[] args){
        BufferedReader br=null;
        String inputStr = "";
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File("input.txt"))));
            String line = "";
            while((line=br.readLine())!=null){
                inputStr+=line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        char[] input = inputStr.toCharArray();
        Analyzer analyzer = new Analyzer();
        analyzer.syntaxAnalyze(input,"parsing_table.txt","cfg.txt");
    }
}
