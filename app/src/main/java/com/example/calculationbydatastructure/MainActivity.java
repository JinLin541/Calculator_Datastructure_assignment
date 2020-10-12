package com.example.calculationbydatastructure;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/*
可视化界面
 */
public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button_0,button_1,button_2,button_3,button_4,button_5,
            button_6,button_7,button_8,button_9,button_clear,button_right,
            button_left,button_plus,button_sub,button_multi,button_chu,button_dian,button_equal;
    StringBuilder stringBuilder  = new StringBuilder("");
    private Calculator calculator = new Calculator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        绑定控件
         */
        init();
        bind();
        textView.setText("");



    }

    private void init(){
        /*
        绑定控件
         */
        textView = findViewById(R.id.textView);
        button_0 = findViewById(R.id.button_0);
        button_1 = findViewById(R.id.button1);
        button_2 = findViewById(R.id.button2);
        button_3 = findViewById(R.id.button3);
        button_4 = findViewById(R.id.button4);
        button_5 = findViewById(R.id.button5);
        button_6 = findViewById(R.id.button6);
        button_7 = findViewById(R.id.button_7);
        button_8 = findViewById(R.id.button8);
        button_9 = findViewById(R.id.button9);
        button_plus = findViewById(R.id.button_plus);
        button_sub = findViewById(R.id.button_jian);
        button_chu = findViewById(R.id.button_chu);
        button_multi = findViewById(R.id.button_chen);
        button_right = findViewById(R.id.button_right);
        button_left = findViewById(R.id.button_left);
        button_clear = findViewById(R.id.button_clear);
        button_dian = findViewById(R.id.button_dian);
        button_equal = findViewById(R.id.button_equal);
    }
    private void bind(){
        /*
        注册监听方法
         */
        button_0.setOnClickListener(new MyListener());
        button_1.setOnClickListener(new MyListener());
        button_2.setOnClickListener(new MyListener());
        button_3.setOnClickListener(new MyListener());
        button_4.setOnClickListener(new MyListener());
        button_5.setOnClickListener(new MyListener());
        button_6.setOnClickListener(new MyListener());
        button_7.setOnClickListener(new MyListener());
        button_8.setOnClickListener(new MyListener());
        button_9.setOnClickListener(new MyListener());
        button_plus.setOnClickListener(new MyListener());
        button_sub.setOnClickListener(new MyListener());
        button_multi.setOnClickListener(new MyListener());
        button_chu.setOnClickListener(new MyListener());
        button_equal.setOnClickListener(new MyListener());
        button_dian.setOnClickListener(new MyListener());
        button_clear.setOnClickListener(new MyListener());
        button_right.setOnClickListener(new MyListener());
        button_left.setOnClickListener(new MyListener());
    }

    private  class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch(id){
                case R.id.button1:
                    stringBuilder.append("1");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button_0:
                    stringBuilder.append("0");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button2:
                    stringBuilder.append("2");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button3:
                    stringBuilder.append("3");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button4:
                    stringBuilder.append("4");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button5:
                    stringBuilder.append("5");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button6:
                    stringBuilder.append("6");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button_7:
                    stringBuilder.append("7");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button8:
                    stringBuilder.append("8");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button9:
                    stringBuilder.append("9");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button_plus:
                    stringBuilder.append("+");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button_jian:
                    stringBuilder.append("-");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button_chen:
                    stringBuilder.append("×");
                    textView.setText(stringBuilder.toString());
                    break;case R.id.button_chu:
                    stringBuilder.append("÷");
                    textView.setText(stringBuilder.toString());
                    break;
                    /*
                    等号按的时候，要会判断这个表达式是不是对的
                     */
                case R.id.button_equal:
                    String s1 = (String)textView.getText();
                    String s = s1.replaceAll("[×]","*").replaceAll("[÷]","/");
                    if(!calculator.isLegalExpression(s)){
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(Calculator.illegalMessage)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        stringBuilder = new StringBuilder("");
                                        textView.setText(stringBuilder.toString());
                                    }
                                }).create().show();
                        Calculator.illegalMessage = "";
                    }
                    else {
                        //捕捉除数为0的情况
                        try {
                            Double answer = calculator.compute(s);
                            stringBuilder = new StringBuilder(String.valueOf(answer));
                            textView.setText(stringBuilder.toString());
                        }catch(ArithmeticException e){
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("除数为0")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            stringBuilder = new StringBuilder("");
                                            textView.setText(stringBuilder.toString());
                                        }
                                    }).create().show();
                        }
                    }
                    break;
                case R.id.button_left:
                    stringBuilder.append("(");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button_right:
                    stringBuilder.append(")");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button_dian:
                    stringBuilder.append(".");
                    textView.setText(stringBuilder.toString());
                    break;
                case R.id.button_clear:
                    stringBuilder = new StringBuilder("");
                    textView.setText(stringBuilder.toString());
                    break;


            }
        }
    }

}