package com.huaxin.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


//import com.sx.aop.annotation.Aop;
//import com.sx.aop.internal.AopManager;

import com.sx.aop.annotation.Aop;
import com.sx.aop.internal.AopManager;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (TextView) findViewById(R.id.show);

        //add Inject
        AopManager.addInject(MainActivity.class, new AopManager.Inject() {
            @Override
            public boolean before(String methodName) {
                log("before:" + methodName);
                return "add".equals(methodName);
            }

            @Override
            public Object process(String methodName, Object... params) {
                log("process:" + params[0]);
                return (Integer) params[0] * 2;
            }

            @Override
            public Object after(String methodName, Object result) {
                log("after:" + result);
                if (result == null) return null;
                return (Integer) result * 2;
            }
        });
        //test it!
        test();
        log("add(10)'s result is : " + add(10));
    }

    @Aop
    public void test() {
        log("test");
    }

    @Aop
    public int add(int i) {
        return i + 10;
    }

    private void log(String info) {
        show.append(info + "\n");
    }

}
