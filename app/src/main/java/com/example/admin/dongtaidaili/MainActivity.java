package com.example.admin.dongtaidaili;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        router = new Router.RouterBuilder(this).builder();
    }
    public void click(View v){
        HashMap<String, String> agrsMap = new HashMap<>();
        agrsMap.put("id","101");
        agrsMap.put("name","张三");
        agrsMap.put("age","18");
        router.create(SecondActivityService.class).startSecondActivity(agrsMap);
    }
}
