package com.xilin.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xilin.myviewinject.OnClick;
import com.xilin.myviewinject.ViewInject;
import com.xilin.myviewinject.ViewUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @ViewInject(R.id.textView)
    private TextView tv1;

    @ViewInject(R.id.textView2)
    private TextView tv2;

    @ViewInject(R.id.textView3)
    private TextView tv3;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        Log.d(TAG, "tv1="+tv1.getText()+",tv2="+tv2.getText()+",tv3="+tv3.getText());
    }

    @OnClick(R.id.button)
    private void clickToast(View view){
        Toast.makeText(MainActivity.this,"点击我了",Toast.LENGTH_SHORT).show();
    }
}
