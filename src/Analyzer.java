import java.util.Stack;

/**
 * Created by yyy on 2016/11/8.
 */
public class Analyzer {
    Stack<Integer> stateSta = new Stack<Integer>();
    Stack<String> symbolSta = new Stack<String>();

    private void initialize(){
        stateSta.push(0);
        symbolSta.push("#");
    }

    public void syntaxAnalyze(char[] inputBelt,String pptFileName,String cfgFileName){
        initialize();
        State stateOp = new State(pptFileName,cfgFileName);
        String action="";
        for(int i=0;i<inputBelt.length;i++){
            int nowState = stateSta.peek();
            String tmp = stateOp.shiftState(nowState,inputBelt[i]);
            if(tmp==null){
                System.out.println("analyze failed!");
                return;
            }
            char[] op = tmp.toCharArray();
            if(op[0]=='S'){//遇到S为移点操作，op[1]为移点后到达的状态
                action = "shift";
                myPrint(inputBelt,i,action);
                stateSta.push(Integer.parseInt(op[1]+""));//新状态进状态栈
                symbolSta.push(inputBelt[i]+"");//读头下的字符进符号栈
                nowState = stateSta.peek();
            }else{//遇到r为规约操作，op[1]为规约要使用的产生式序号
                //accept
                if(Integer.parseInt(op[1]+"")==0){
                    action = "accept";
                    myPrint(inputBelt,i,action);
                    System.out.println("analyze successfully!");
                    return;
                }
                String productStr = stateOp.getProduct(Integer.parseInt(op[1]+""));//规约对应的产生式
                if(productStr==null){
                    System.out.println("analyze failed!");
                }
                String[] product = productStr.split("%");
                action = "reduced by "+op[1]+":"+product[0]+"->"+product[1];
                myPrint(inputBelt,i,action);
                String topElement="";
                int len = product[1].length();
                for(int j=0;j<len;j++){
                    topElement=symbolSta.pop()+topElement;//获取栈顶的若干元素
                }
                if(topElement.equals(product[1])){//比较栈顶的若干元素与产生式的右边是否相同，相同则进行规约
                    for(int k=0;k<len;k++){
                        stateSta.pop();
                    }
                    symbolSta.push(product[0]);//将产生式的左边压栈
                    int newState = stateOp.gotoState(stateSta.peek(),Integer.parseInt(op[1]+""));//获取新状态
                    stateSta.push(newState);//将状态压栈
                    if(newState==-1){
                        System.out.println("analyze failed!");
                    }
                    nowState = newState;
                    i--;
                }else{
                    System.out.println("analyze failed!");
                }

            }
        }
    }


    private void myPrint(char[] inputBelt, int index, String action){
        String input = "";
        for(int i=index;i<inputBelt.length;i++){
            input+=(inputBelt[i]+"");
        }
        System.out.println("StateStack:"+intSta2string(stateSta)+"  SymbolStack:"+
                charSta2string(symbolSta)+"  input:"+input+"  action:"+action);
    }
    private String intSta2string(Stack<Integer> s){
        Stack<Integer> tmp = new Stack<Integer>();
        String result="";
        while(!s.empty()){
            tmp.push(s.pop());
            result = tmp.peek()+""+result;
        }
        while(!tmp.empty()){
            s.push(tmp.pop());
        }
        return result;
    }
    private String charSta2string(Stack<String> s){
        Stack<String> tmp = new Stack<String>();
        String result="";
        while(!s.empty()){
            tmp.push(s.pop());
            result=tmp.peek()+result;
        }
        while(!tmp.empty()){
            s.push(tmp.pop());
        }
        return result;
    }
}
