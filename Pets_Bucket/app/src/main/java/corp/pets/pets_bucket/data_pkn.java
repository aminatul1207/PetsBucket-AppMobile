package corp.pets.pets_bucket;

import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;


public class data_pkn extends AppCompatActivity {

        private RecyclerView lvpakan;

        private RequestQueue requestQueue;
        private StringRequest stringRequest;

        ArrayList<HashMap<String, String>> list_data;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.data_pkn);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            String url = "http://192.168.43.220/PetS_Bucket/getpakan.php";

            lvpakan = (RecyclerView) findViewById(R.id.lvpakan);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            lvpakan.setLayoutManager(llm);

            requestQueue = Volley.newRequestQueue(data_pkn.this);

            list_data = new ArrayList<HashMap<String, String>>();

            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("pakan");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject json = jsonArray.getJSONObject(a);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("id", json.getString("prd_id"));
                            map.put("nama", json.getString("prd_nama"));
                            map.put("hp", json.getString("pjl_hp"));
                            map.put("harga", json.getString("prd_harga"));
                            map.put("satuan", json.getString("prd_satuan"));
                            map.put("stok", json.getString("prd_stok"));
                            map.put("penjual", json.getString("pjl_nama_usaha"));
                            map.put("gambar", json.getString("prd_gambar"));
                            //map.put("keterangan", json.getString("keterangan"));
                            list_data.add(map);
                            AdapterListP adapter = new AdapterListP(data_pkn.this, list_data);
                            lvpakan.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(data_pkn.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);

        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
