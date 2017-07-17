package mobile.beweb.fondespierre.apprenantstest;
//This is all imports that we need to this work!
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mobile.beweb.fondespierre.apprenantstest.Adapter.ListApprenantAdapter;


public class MainActivity extends AppCompatActivity implements ListApprenantAdapter.ListApprenantAdapterOnClickHandler,AdapterView.OnItemSelectedListener {
//This is the differents variables that we have to declare for all methods
    private RecyclerView mRecyclerView;
    private ListApprenantAdapter listeApprenantAdapter;

    /**
     * This method is like a constructor, launch when the project start
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// We choose the layout that we use
        setContentView(R.layout.layout);

//On this part, we fill spinners and we set listeners on them
        Spinner spinnerpromo = (Spinner) findViewById(R.id.la_spinner_promo);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.promotion, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpromo.setAdapter(adapter1);
        spinnerpromo.setOnItemSelectedListener(this);
        Spinner spinnersession = (Spinner) findViewById(R.id.la_spinner_session);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.skills, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersession.setAdapter(adapter2);
        spinnersession.setOnItemSelectedListener(this);
        Spinner spinnerskills = (Spinner) findViewById(R.id.la_spinner_skills);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.session, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerskills.setAdapter(adapter3);
        spinnerskills.setOnItemSelectedListener(this);
    }

    /**
     * With this method, we have a link between MainActivity and DetailapprenantActivity
     * @param apprenantDetails
     * @return void
     */
    @Override
    public void onClick(JSONObject apprenantDetails) {
        Intent intent = new Intent(MainActivity.this, DetailapprenantActivity.class);
        intent.putExtra("apprenant",apprenantDetails.toString());
        MainActivity.this.getApplicationContext().startActivity(intent);
    }

    /**
     * @return void
     * @param parent
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
//We fetch the value of the 3 spinners
        Spinner promo_sp = (Spinner) findViewById(R.id.la_spinner_promo);
        final String promo = promo_sp.getSelectedItem().toString();
        Spinner session_sp = (Spinner) findViewById(R.id.la_spinner_session);
        final String session = session_sp.getSelectedItem().toString();
        Spinner skill_sp = (Spinner) findViewById(R.id.la_spinner_skills);
        final String skill = skill_sp.getSelectedItem().toString();
//we create a variable for the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_liste_apprenant);
//To manage the display of the list
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
//We create a variable ListApprenantAdapter and we use it on the RecyclerView
        listeApprenantAdapter = new ListApprenantAdapter(this);
        mRecyclerView.setAdapter(listeApprenantAdapter);
//We create a varaible RequestQueue and a JsonArrayRequest to fetch datas from Blackbooks Api
        RequestQueue queue = VolleySingleton.getInstance(MainActivity.this).getRequestQueue();
        JsonArrayRequest jsonreq = new JsonArrayRequest("http://10.216.0.138/apiBlackBooks/",
//We declare  a new Response
                new Response.Listener<JSONArray>() {
//When the response is ok
                    @Override
                    public void onResponse(JSONArray response) {
//We sort the data if the spinner promo isn't empty
                        if(!promo.equals("")){
                            JSONArray tab = new JSONArray();
                            for (int i=1;i<=response.length();i++){
                                try {
                                    JSONObject ligne = response.getJSONObject(i);
                                    if(ligne.get("promotion").equals(promo)){
                                        tab.put(ligne);
                                    };
                                } catch (JSONException e){
                                }
                            }
                            response = tab;
                        }
//We sort the data if the spinner session isn't empty
                        if(!session.equals("")){
                            JSONArray tab = new JSONArray();
                            for (int i=1;i<=response.length();i++){
                                try {
                                    JSONObject ligne = response.getJSONObject(i);
                                    if(ligne.get("session").equals(session)){
                                        tab.put(ligne);
                                    };
                                } catch (JSONException e){

                                }
                            }
                            response = tab;
                        }
//We sort the data if the spinner skill isn't empty
                        if(!skill.equals("")){
                            JSONArray tab = new JSONArray();
                            for (int i=1;i<=response.length();i++){
                                try {
                                    JSONObject ligne = response.getJSONObject(i);
                                    if(ligne.get("skill").equals(skill)){
                                        tab.put(ligne);
                                    };
                                } catch (JSONException e){
                                }
                            }
                            response = tab;
                        }
//We fill the Recyclerview with the response
                          listeApprenantAdapter.setWeatherData(response);
                    }
                },
//If the  Listener isn't ok he go on Error
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
//We add the JsonRequest to the queue
        queue.add(jsonreq);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}