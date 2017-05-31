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

public class login extends AppCompatActivity{
    public static final String LOGIN_URL = "http://192.168.43.220/pets_bucket/login.php";
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        editTextUsername = (EditText) findViewById(R.id.userInput);
        editTextPassword = (EditText) findViewById(R.id.passInput);
        Button MASUK=(Button) findViewById(R.id.button1);
        Button Daftar=(Button) findViewById(R.id.button2);
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please wait...");


        Daftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View df){
                Intent reg=new Intent(login.this, register.class);
                startActivity(reg);
            }
        });

       MASUK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
});
        }

    private void userLogin() {
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("status")){
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        jsonObject.getInt("id"),jsonObject.getString("nama"),jsonObject.getString("email"),jsonObject.getString("hp")

                                );
                                startActivity(new Intent(getApplicationContext(), ActivityNav.class));
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,username);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
