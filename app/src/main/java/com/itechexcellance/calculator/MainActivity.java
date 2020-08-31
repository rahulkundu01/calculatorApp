package com.itechexcellance.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private String s1;
    private Character preOperator;
    private boolean newElement = true;
    private Double firstValue;

    //constant variables
    public static final String ZERO = "0";
    public static final Character EQUALS = '=';
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static final Character MINUS = '-';

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.EditText01);
    }
    /**
     * when press on 0-9 numbers
     */
    public void pressNumber(View view){
        s1 = ((Button) view).getText().toString();
        if(newElement || ZERO.equals(editText.getText().toString())){
            if(POINT.equals(s1))
                s1 = "0" + POINT;
            editText.setText(s1);
            newElement = false;
            return;
        }
        if(POINT.equals(s1) && editText.getText().toString().contains(POINT))
            return;
        editText.setText(editText.getText() + s1);
    }

    /**
     * when press on operator's like +, -, *, /, =, +/- (or) sqrt buttons
     * @param view
     */
    public void pressOperator(View view){
        try{
            s1 = ((Button) view).getText().toString();
//	    	 +/- button
            if("+/-".equals(s1)){
                if(editText.getText().toString().charAt(0) == MINUS)
                    editText.setText(editText.getText().toString().substring(1));
                else
                    editText.setText(MINUS + editText.getText().toString());
                return;
            }
//	    	sqrt button
            if("sqrt".equalsIgnoreCase(s1)){
                editText.setText(Double.toString(Math.sqrt(Double.valueOf(editText.getText().toString()))));
                return;
            }
            if(preOperator != null && firstValue != null){
                switch (preOperator) {
                    case '+':
                        firstValue = firstValue + Double.valueOf(editText.getText().toString());
                        break;
                    case '-':
                        firstValue = firstValue - Double.valueOf(editText.getText().toString());
                        break;
                    case '*':
                        firstValue = firstValue * Double.valueOf(editText.getText().toString());
                        break;
                    case '/':
                        firstValue = firstValue / Double.valueOf(editText.getText().toString());
                }
                //editText.setText(firstValue.equals(firstValue.intValue()) ? Integer.toString(firstValue.intValue()) : firstValue.toString());
                editText.setText(firstValue.toString());
            }
            else
                firstValue = Double.valueOf(editText.getText().toString());
            preOperator = ((Button) view).getText().charAt(0);
            newElement = true;
            if(EQUALS.equals(preOperator))
                firstValue = null;
        }catch (Exception e) {
            // TODO: handle exception
            firstValue = null;
            newElement = true;
            preOperator = null;
            editText.setText("Error");
        }
    }

    /**
     * when press on Back Space, CE & cancel(C) button
     * @param view
     */
    public void cancelButton(View view){
        s1 = ((Button) view).getText().toString();
        Log.i("caluculator", "cancel button");
        //cancel button
        if("C".equalsIgnoreCase(s1)){
            newElement = true;
            firstValue = null;
            editText.setText(ZERO);
            return;
        }
        //CE button
        else if("ce".equalsIgnoreCase(s1)){
            newElement = true;
            editText.setText(ZERO);
            return;
        }
        //Back Space button
        else if(s1.startsWith("B")){
            s1 = editText.getText().toString();
            if(!ZERO.equals(s1))
                s1 = s1.substring(0, s1.length() -1);
            if(EMPTY.equals(s1) || "-".equals(s1)){
                s1 = ZERO;
                newElement = true;
            }
            editText.setText(s1);
        }
    }
}