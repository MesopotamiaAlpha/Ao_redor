package com.example.senai.ao_redor;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton mInstance;
    private RequestQueue resquestQueue;
    private static Context mCtx;
    private MySingleton(Context context){

        mCtx = context;
        resquestQueue = getResquestQueue();

    }

    public RequestQueue getResquestQueue(){

        if(resquestQueue==null){
            resquestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return resquestQueue;
    }

    public static  synchronized MySingleton getmInstance(Context context){
        if(mInstance==null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }
    public <T> void addToRequestQue(Request<T> request){
        resquestQueue.add(request);
    }

}
