import lexAnalyzer.IOHelper;
import lexAnalyzer.LexAnalyzer;

import java.io.*;

/**
 * Created by yyy on 2016/11/9.
 * 用来将token序列转换为符合语法分析程序要求输入字符串，为简化id类型均用i来代替，操作符使用原先的符号
 */
public class Input {
    public String getSyntaxInput(String inputFileName,String outFileName){
        IOHelper helper = new IOHelper(inputFileName);
        LexAnalyzer analyzer = new LexAnalyzer(helper);
        analyzer.lexicalAnalyze(outFileName);
        helper.closeFile();

        BufferedReader br = null;
        String result="";
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(outFileName))));
            String line ="";
            while((line=br.readLine())!=null){
                line = line.substring(1,line.length()-1);
                String[] token = line.split(",");
                if(token[0].equals("ID")){
                    result+="i";
                }else{
                    result+=token[1];
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result+="$";
        return result;
    }
    private void getTokenSequence(String inputFileName,String outFileName){

    }
}
