package com.example.senai.ao_redor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;


public class Lista extends AppCompatActivity {

    //Aqui estou resgatando os botoes e label
    ListView listaResultado;

    /*/teste de animacao
    private int ultimaPos = -1;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        //essa linha de baixo é a barra de cima, que nao esta voltando
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //aqui estou ativando os botoes e label
        listaResultado = (ListView)findViewById(R.id.lvLista);

        //ip servidor aqui estou resgatando os dados em json e guardando dentro da variavel consulta.
        String consulta = "http://192.168.4.1/android/cevis/consulta.php";

        //ip local
        //String consulta = "http://192.168.4.18:8082/android/cevis/consulta.php";

        EnviarReceberDados(consulta);
    }

    //aqui estou executando a função Enviar e receber dados
    public void EnviarReceberDados(String URL){

        Toast.makeText(getApplicationContext(), "Consulta realizada com sucesso", Toast.LENGTH_SHORT).show();


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CarregarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CarregarListView(JSONArray ja){


        //aqui estou criando o array de itens na lista
        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=3){

            try {

                //para eu omitir a coluna do id eu tenho que tirar esta parte ja.getString(i)+" "+
                lista.add(ja.getString(i)+" "+ja.getString(i+1)+" "+ja.getString(i+2));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        /*/teste de animacao
        final View resultado;
        resultado = lista;

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (i > ultimaPos) ? R.anim.carregando_baixo_anim : R.anim.carregando_cima_anim);
        resultado.startAnimation(animation);
        ultimaPos = i;
*/

        //nesta parte estou identificando qual item foi clicado da lista
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listaResultado.setAdapter(adaptador);

        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int lista, long id) {
                Toast.makeText(getApplicationContext(), "Item selecionado da lista: " + lista , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Lista.this, ItemLista.class);

                //aqui estou pegando dados desta activity para outra
                Bundle params = new Bundle();
                params.putString("id", String.valueOf(lista));
                i.putExtras(params);
                startActivities(new Intent[]{i});

            }



        });

    }




}
