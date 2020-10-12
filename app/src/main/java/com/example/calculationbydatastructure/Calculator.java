package com.example.calculationbydatastructure;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Stack;


public  class Calculator {

    /*
    判断表达式错误信息的域

     */
    public static String illegalMessage = null;
    private static String expression = null;

     private static Stack<Double> stackNum= new Stack<>();
     private static  Stack<Character> stackOpr= new Stack<>();
     private static String stringWithoutOpr = expression.replaceAll("[+\\-*/()]","@");
     private static String stringWithoutNum = expression.replaceAll("[^+\\-*/()]","#");

    static Double compute(String expression) throws ArithmeticException{

        Calculator.expression = expression;


        char[] charsNum = stringWithoutOpr.toCharArray();
        char[] charsOpr = stringWithoutNum.toCharArray();

        Scanner scanner = new Scanner(expression.replaceAll("[+\\-*/()]"," "));
        //设置一个flag，如果是读到数字，需要读到下一串数字才能再读取double
        int flag = 0;
        for(int i = 0; i < charsNum.length; i++){
            /*
            数字直接入栈
             */
            if(charsNum[i] != '@' && flag == 0){
                flag = 1;
                if(scanner.hasNext("\\d+(\\.\\d+)?")) {
                    Double data = Double.valueOf(scanner.next("\\d+(\\.\\d+)?"));
                    stackNum.push(data);
                }

            }
            else{
                flag = 0;
                /*
                如果遇到加减号，把符号栈的所有符号都处理完，直到栈空，或者左括号
                 */
                if(charsOpr[i] == '+' || charsOpr[i] == '-'){

                    while(!stackOpr.isEmpty() && stackOpr.peek()!='('){
                        //处理符号栈的符号
                        Double data2 = stackNum.pop();
                        Double data1 = stackNum.pop();
                        char opr = stackOpr.pop();
                        Double answer = getNumber(data1,opr,data2);
                        stackNum.push(answer);
                    }
//                    if(!stackOpr.isEmpty() && stackOpr.peek() == '('){
//                        stackOpr.pop();
//                    }
                    stackOpr.push(charsOpr[i]);
                }
                /*
                如果是乘除符号，就把栈顶的乘除符号先给解决
                 */
                else if(charsOpr[i] == '*' || charsOpr[i] == '/'){
                    while(!stackOpr.isEmpty() && stackOpr.peek() != '+' &&
                            stackOpr.peek() != '-' && stackOpr.peek()!='('){
                        //处理符号栈的符号
                        Double data2 = stackNum.pop();
                        Double data1 = stackNum.pop();
                        char opr = stackOpr.pop();
                        Double answer = getNumber(data1,opr,data2);
                        stackNum.push(answer);
                    }
//                    if(!stackOpr.isEmpty() && stackOpr.peek() == '('){
//                        stackOpr.pop();
//                    }
                    stackOpr.push(charsOpr[i]);

                }
                else if(charsOpr[i] == '('){
                    //左括号直接入栈
                    stackOpr.push(charsOpr[i]);
                }
                else if(charsOpr[i] == ')'){
                    //处理符号，直到遇到左括号
                    while(!stackOpr.isEmpty() && stackOpr.peek() != '('){
                        Double data2 = stackNum.pop();
                        Double data1 = stackNum.pop();
                        char opr = stackOpr.pop();
                        Double answer = getNumber(data1,opr,data2);
                        stackNum.push(answer);
                    }
                    if(!stackOpr.isEmpty() && stackOpr.peek() == '('){
                        stackOpr.pop();
                    }

                }
            }
        }
        while(!stackOpr.isEmpty()){
            Double data2 = stackNum.pop();
            Double data1 = stackNum.pop();
            char opr = stackOpr.pop();
            Double answer = getNumber(data1,opr,data2);
            stackNum.push(answer);
        }
        return stackNum.peek();

    }
    private static  Double getNumber(Double data1,char opr,Double data2) throws ArithmeticException{
        Double answer = 0d;
        switch (opr){
            case '+':
                answer = Arith.add(data1,data2);
                break;
            case '-':
                answer = Arith.sub(data1,data2);
                break;
            case '*':
                answer = Arith.mul(data1,data2);
                break;
            case '/':
                answer = Arith.div(data1,data2);
                break;
        }
        return answer;
    }



    /*
    中缀表达式转为后缀
     */
//    static String turnExpression(String expression){
//        String stringWithoutOpr = expression.replaceAll("[+\\-*/()]"," ");
//        Scanner scanner = new Scanner(stringWithoutOpr);
//        while(scanner.hasNext("//d+(//.//d+)?")){
//            Double data = Double.valueOf(scanner.next("//d+(//.//d+)?"));
//            stackNum.push(data);
//        }
//
//
//    }

    /*
    这里的一些字段域需要优化才行，应该定义为static，不然每次调用一次方法，都要再次申请内存空间
     */

    static boolean isLegalExpression(String mainString){
        //把运算符用@代替
        String stringWithoutOpr = mainString.replaceAll("[+\\-*/]","@");
        //把数字用#代替
        String stringWithoutNum = stringWithoutOpr.replaceAll("[^+\\-*/()]","#");

        //出现了一个小bug，stringWithoutNum里面是没有运算符
        String stringWithoutNumButOpr = mainString.replaceAll("[^+\\-*/()]","#");


        char[] chars = stringWithoutOpr.toCharArray();
        char[] chars1 = stringWithoutNum.toCharArray();
        char[] chars2 = stringWithoutNumButOpr.toCharArray();

        /*
        先判断表达式的头和尾两边是不是有运算符
         */
        if(chars[0] == '@' || chars[chars.length-1] == '@'){
            illegalMessage = "表达式头或者尾有运算符";
            return false;
        }

        /*
        判断表达式的数字两边是否有多余的运算符
         */
        for(int i = 1; i < chars1.length; i++){
            if(chars[i] == '@'){
                if(chars[i+1] == '@'){
                    illegalMessage = "表达式数字两边有多余的运算符";
                    return false;
                }
            }
        }

        /*
        判断开头和结尾括号的正确性（左括号结尾，右括号开头）
         */
        if(chars[0] == ')' || chars[chars.length-1] == '('){
            illegalMessage = "表达式含有左括号结尾或者右括号开头错误";
            return false;
        }

        /*
        判断括号的匹配是否正确
         */
        Stack<Character> stack = new Stack<>();
        for(char x : chars){
            if(x == '('){
                stack.push(x);
            }
            else if(x == ')'){
                if(stack.isEmpty() || stack.peek()!='('){
                    illegalMessage = "括号匹配不正确";
                    return false;
                }
                else{
                    stack.pop();
                }
            }
        }
        if(!stack.isEmpty()){
            illegalMessage = "括号匹配不正确";
            return false;
        }

        /*
        数字直接加一个左括号情况也是不合法的
         */
        for(int i = 0; i < chars2.length; i++){
            if(chars2[i] == '#' && i+1 < chars2.length){
                if(chars2[i+1] == '('){
                    illegalMessage = "数字不能直接加左括号";
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isLegalExpression("(1+2)"));
        Double dou = compute("31.6*(122.2+2)");
        System.out.println(dou);
    }
}
class Arith
{


    private static final int DEF_DIV_SCALE = 10;


    private Arith()
    {
    }


    public static double add(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }


    public static double sub(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }


    public static double mul(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }


    public static double div(double v1, double v2) throws ArithmeticException
    {
        return div(v1, v2, DEF_DIV_SCALE);
    }


    public static double div(double v1, double v2, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if (b1.compareTo(BigDecimal.ZERO) == 0)
        {
            return BigDecimal.ZERO.doubleValue();
        }
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static double round(double v, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
//        print(div(1.212123,2.332,17));

    }
}


