package corp.pets.pets_bucket;

/**
 * Created by PC on 29/05/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class listview extends AppCompatActivity {

    private ImageView imghp;
    private TextView txtproduk, txtharga, txtketerangan;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        String url = "http://192.168.43.220/listview/getdata.php";

        imghp = (ImageView)findViewById(R.id.imghp);
        txtproduk = (TextView)findViewById(R.id.txtproduk);
        txtharga = (TextView)findViewById(R.id.txtharga);
        txtketerangan = (TextView)findViewById(R.id.txtketerangan);

        requestQueue = Volley.newRequestQueue(listview.this);

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("produk");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map  = new HashMap<String, String>();
                        map.put("id", json.getString("prd_id"));
                        map.put("nama", json.getString("prd_nama"));
                        map.put("harga", json.getString("prd_harga"));
                        map.put("gambar", json.getString("prd_gambar"));
                        map.put("keterangan", json.getString("prd_deskripsi"));
                        list_data.add(map);
                    }
                    Glide.with(getApplicationContext())
                            .load("http://192.168.43.220/listview/aksesoris/" + list_data.get(0).get("gambar"))
                            .crossFade()
                            .placeholder(R.mipmap.ic_launcher)
                            .into(imghp);
                    txtproduk.setText(list_data.get(0).get("nama"));
                    txtharga.setText(list_data.get(0).get("harga"));
                    txtketerangan.setText(list_data.get(0).get("keterangan"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(listview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}
