package corp.pets.pets_bucket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PC on 12/05/2017.
 */

public class pakan extends ActionBarActivity {
    public static final String PAKAN_URL = "http://10.10.2.76/pets_bucket/produk_pakan.php";
    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_HARGA ="harga";
    private static final String TAG_STOK="stok";
    private static final String TAG_USAHA ="usaha";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pakan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();
        getData();
    }
    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String nama = c.getString(TAG_NAMA);
                String harga = c.getString(TAG_HARGA);
                String stok = c.getString(TAG_STOK);
                String usaha = c.getString(TAG_USAHA);

                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(TAG_ID,id);
                persons.put(TAG_NAMA,nama);
                persons.put(TAG_HARGA,harga);
                persons.put(TAG_STOK,stok);
                persons.put(TAG_USAHA,usaha);

                personList.add(persons);
            }

           /* ListAdapter adapter = new SimpleAdapter(
                    pakan.this, personList, R.layout.list_item,
                    new String[]{TAG_ID,TAG_NAMA,TAG_HARGA,TAG_STOK,TAG_USAHA},
                    new int[]{R.id.id, R.id.nama, R.id.harga,R.id.stok,R.id.usaha}
            );*/

           // list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
               // DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
               // HttpPost httppost = new HttpPost("http://10.10.2.76/pets_bucket/produk_pakan.php");

                // Depends on your web service
              // httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                  //  HttpResponse response = httpclient.execute(httppost);
                  //  HttpEntity entity = response.getEntity();

                  //inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
