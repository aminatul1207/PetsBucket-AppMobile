package corp.pets.pets_bucket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PC on 09/05/2017.
 */

public class register extends AppCompatActivity implements View.OnClickListener{
   // public static final String DAFTAR_URL = "http://192.168.43.220/pets_bucket/daftar.php";
    private EditText edtNama, edtHp, edtEmail,edtPass ;
    private Button btnDaftar;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        edtNama = (EditText)findViewById(R.id.namauser);
        edtHp = (EditText)findViewById(R.id.nomortelp);
        edtPass = (EditText)findViewById(R.id.passworduser);
        edtEmail = (EditText)findViewById(R.id.emailuser);
        btnDaftar = (Button)findViewById(R.id.signup);
        progressDialog = new ProgressDialog(this);


        btnDaftar.setOnClickListener(this);

    }

    private void registerUser(){
       final String  nama= edtNama.getText().toString().trim();
        final String hp= edtHp.getText().toString().trim();
        final String email= edtEmail.getText().toString().trim();
        final String password = edtPass.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), login.class));
                            /*if (jsonObject.getBoolean("status")){
                                emptyFields();
                            }else {
                                startActivity(new Intent(getApplicationContext(), login.class));
                                finish();
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("hp", hp);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        //Digantikan dengan class terpisah (Google recommended)
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void emptyFields(){
        edtNama.setText("");
        edtPass.setText("");
        edtEmail.setText("");
        edtHp.requestFocus();
    }

    public void onClick(View view){
        if(view == btnDaftar){
            registerUser();
        }
    }
}
