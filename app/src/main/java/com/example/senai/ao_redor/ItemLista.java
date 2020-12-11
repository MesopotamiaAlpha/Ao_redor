package com.example.senai.ao_redor;


import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class ItemLista extends AppCompatActivity {


    Button button;
    ImageView imageView;
    TextView lblCampo_monta,lblCampo_descricao,lblCampo_lancefinal,lblCampo_lanceinicial,lblCampo_ano;

    //ip do raspberry

    //ip local
    //String server_url = "http://192.168.1.107:8082/android/cevis/foto1.jpg";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_lista);

        //teste de receber dados
        lblCampo_descricao = (TextView)findViewById(R.id.lblCampo_descricao);
        //ip do servidor
        String detalhes = "http://192.168.4.1/android/cevis/detalhes.php";
        EnviarReceberDados(detalhes);


        imageView = (ImageView) findViewById(R.id.img);
        lblCampo_monta = (TextView) findViewById(R.id.lblCampo_monta);
        lblCampo_lanceinicial = (TextView) findViewById(R.id.lblCampo_lanceinicial);
        lblCampo_lancefinal = (TextView) findViewById(R.id.lblCampo_lancefinal);
        lblCampo_ano = (TextView) findViewById(R.id.lblCampo_ano);

        //recuperando qual foi o id selecionado
        Intent intent = getIntent();
        Bundle params = new Bundle();

        params = intent.getExtras();


        //aqui estou pegando o numero do registro clicado e procurando a foto
        String numFoto;
        numFoto = params.getString("id");
        String server_url = "http://192.168.4.1/pagina/img/produto/foto" + numFoto + ".jpg";

        if (params != null) {
            lblCampo_monta.setText(params.getString("id"));

        }

        //recuperar imagem
        ImageRequest imageRequest = new ImageRequest(server_url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ItemLista.this, "Não foi possivel recuperar a imagem.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        MySingleton.getmInstance(ItemLista.this).addToRequestQue(imageRequest);


    }


    ///teste de receber dados
    public void EnviarReceberDados(String URL) {

        Toast.makeText(getApplicationContext(), "Detalhes recuperados", Toast.LENGTH_SHORT).show();


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("detalhesjson",""+ja.length());
                        CarregarListView(ja);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CarregarListView(JSONArray ja) {


        //aqui estou criando o array de itens na lista
        ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                //para eu omitir a coluna do id eu tenho que tirar esta parte ja.getString(i)+" "+
                lista.add(ja.getString(i) + " " + ja.getString(i + 1) + " " + ja.getString(i + 2)+ " " + ja.getString(i + 3)+ " " + ja.getString(i + 4));
                //aqui ele esta pegando os detalhes do array e mostrando, mas por algum motivo nao consigo escolher o array
                lblCampo_monta.setText(ja.getString(i+2));
                lblCampo_ano.setText(ja.getString(i+3));
                lblCampo_lanceinicial.setText(ja.getString(i+0));
                lblCampo_lancefinal.setText(ja.getString(i+1));
                lblCampo_descricao.setText(ja.getString(i+4));
                //lblCampo_descricao.setText(lista.get(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }



    }

}

