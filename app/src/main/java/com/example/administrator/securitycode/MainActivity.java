package com.example.administrator.securitycode;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SecurityCodeView.InputCompleteListener {

    private SecurityCodeView edtText;
    private TextView textView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListener();
    }

    private void setListener() {
        edtText.setInputCompleteListener(this);
    }

    private void findViews() {
        edtText = (SecurityCodeView) findViewById(R.id.scv_edt);
        textView = (TextView) findViewById(R.id.tv_text);
    }


    @Override
    public void inputComplete() {
        Toast.makeText(this, "验证码是:" +edtText.getEditContent() , Toast.LENGTH_LONG).show();
        if(edtText.getEditContent().equals("1234")){
            textView.setText("验证码正确");
            textView.setTextColor(Color.RED);
        }else{
            textView.setText("验证码错误");
            textView.setTextColor(Color.RED);
        }
    }

    @Override
    public void deleteContent(boolean isDelete) {
        if (isDelete) {
            textView.setText("输入验证码表示同意《用户协议》");
            textView.setTextColor(Color.BLACK);
        }
    }
}
