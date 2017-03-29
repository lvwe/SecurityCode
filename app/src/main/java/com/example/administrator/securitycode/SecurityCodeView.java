package com.example.administrator.securitycode;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public class SecurityCodeView extends RelativeLayout {


    private TextView[] mTextViews;
    private EditText mEditText;
    private StringBuffer mStringBuffer = new StringBuffer();
    private int count = 0;
    private String inputContent;
    private static final String TAG = "SecurityCodeView";


    public SecurityCodeView(Context context) {
        super(context, null);
    }

    public SecurityCodeView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        LayoutInflater.from(context).inflate(R.layout.view_security_code, this);
        mTextViews = new TextView[4];
//        View.inflate(context, R.layout.view_security_code, this);

        mEditText = (EditText) findViewById(R.id.item_edittext);
        mTextViews[0] = (TextView) findViewById(R.id.item_code_iv1);
        mTextViews[1] = (TextView) findViewById(R.id.item_code_iv2);
        mTextViews[2] = (TextView) findViewById(R.id.item_code_iv3);
        mTextViews[3] = (TextView) findViewById(R.id.item_code_iv4);
        mEditText.setCursorVisible(false); //光标移除
        setListener();


    }

    public SecurityCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    public void setListener() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {

                    if (mStringBuffer.length() > 3) {
                        mEditText.setText("");
                        return;
                    } else {
                        mStringBuffer.append(s);
                        mEditText.setText("");
                        count = mStringBuffer.length();

                        inputContent = mStringBuffer.toString();


//                        Log.d(TAG, "afterTextChanged: "+inputContent);
                        if (mStringBuffer.length() == 4) {
                            if (inputCompleteListener != null)
                                inputCompleteListener.inputComplete();


                        }
                    }
                }


                for (int i = 0; i < mStringBuffer.length(); i++) {

                    Log.d(TAG, "afterTextChanged: " + i + "......." + mStringBuffer.toString());
                    mTextViews[i].setText(String.valueOf(mStringBuffer.toString().charAt(i)));
                    mTextViews[i].setBackgroundResource(R.drawable.bg_verify_press);

                }
            }
        });
        mEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (onKeyDelete()) return true;
                    return true;

                }
                return false;
            }
        });

    }

    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {
        void inputComplete();

        void deleteContent(boolean isDelete);
    }


    public boolean onKeyDelete() {
        if (count == 0) {
            count = 4;
            return true;
        }
        if (mStringBuffer.length() > 0) {
            //删除相应位置的字符
            mStringBuffer.delete((count - 1), count);
            count--;
            //   Log.e(TAG, "afterTextChanged: stringBuffer is " + stringBuffer);
            inputContent = mStringBuffer.toString();
            mTextViews[mStringBuffer.length()].setText("");
            mTextViews[mStringBuffer.length()].setBackgroundResource(R.drawable.bg_verify);
            if (inputCompleteListener != null)
                inputCompleteListener.deleteContent(true);//有删除就通知manger

        }
        return false;
    }

    public void clearEditText() {
        mStringBuffer.delete(0, mStringBuffer.length());
        inputContent = mStringBuffer.toString();
        for (int i = 0; i < mTextViews.length; i++) {
            mTextViews[i].setText("");
            mTextViews[i].setBackgroundResource(R.drawable.bg_verify);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public String getEditContent() {
        return inputContent;
    }

}
