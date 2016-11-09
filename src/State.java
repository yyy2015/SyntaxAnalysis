import java.io.*;

/**
 * Created by yyy on 2016/11/7.
 */
public class State {
    String pptFileName="";
    String cfgFileName="";
    public State(String ppt,String cfg){
        this.pptFileName = ppt;
        this.cfgFileName = cfg;
    }

    //此方法用来获取当前状态通过读头下的符号要返回的移点状态或者规约情况，返回为Si或者ri
    public String shiftState(int init, char input){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(pptFileName))));
            String line ="";
            while(!((line=br.readLine()).equals("%%%%"))){//%%%%为分析表中Action与GOTO的分界线
                String[] whole = line.split(" ");
                int nowState = Integer.parseInt(whole[0]);
                if(nowState==init){//找到目前状态
//                    System.out.println(whole[1]);//TEST TEST TEST
                    String[] changeArray = whole[1].split("\\|");
                    for(int i=0;i<changeArray.length;i++){
//                        System.out.println(changeArray[i]);//TEST TEST TEST
                        String[] change = changeArray[i].split("%");//change[0]为要与输入带读头指向的字符相比较的字符,change[1]为即将到达的状态
                        char toCmp = (change[0].toCharArray())[0];
                        if(toCmp==input){//字符与读头指向的字符相符合
                            return change[1];//返回相应的Si或者ri
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //此方法用来获取进行规约时，根据规约的序号找到相应的产生式，返回为：左%右 的形式
    public String getProduct(int reduceIndex){
        BufferedReader brCfg = null;//读cfg文件
        try {
            brCfg = new BufferedReader(new InputStreamReader(new FileInputStream(new File(cfgFileName))));
            String lineCfg="";
            int lineNum = -1;
            while((lineCfg=brCfg.readLine())!=null){
                lineNum++;
                if(lineNum == reduceIndex){//找到规约对应的产生式
                    String[] product = lineCfg.split("=");
                    return product[0]+"%"+product[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
//  此方法用来获取进行规约时，将符号栈和状态栈栈顶的若干元素出栈后，应当到达的新的将被压进状态栈的状态
    public int gotoState(int initState,int reduceIndex){
        int result=-1;
        BufferedReader brPpt = null;//读ppt文件
        String[] product=getProduct(reduceIndex).split("%");
        try {
            brPpt = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pptFileName))));
            String left= product[0];
            String linePpt="";
            while(!(brPpt.readLine().equals("%%%%"))){}//%%%%为分析表中Action与GOTO的分界线
            while((linePpt = brPpt.readLine())!=null){
                String[] whole = linePpt.split(" ");
                int nowState = Integer.parseInt(whole[0]);
                if(nowState==initState){//找到了对应目前状态的状态
                    String[] change = whole[1].split("\\|");
                    for(int i=0;i<change.length;i++){
                        String toCmp = change[i].split("%")[0];
                        if(toCmp.equals(left)){//找到了initState通过leftCode要GOTO的状态
                            result=Integer.parseInt(change[i].split("%")[1]);
                            return result;
                        }
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
