package corp.pets.pets_bucket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by PC on 15/05/2017.
 */

public class akun extends AppCompatActivity {
    private TextView textNama,textEmail,textHp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, login.class));
        }
        textNama=(TextView) findViewById(R.id.textNama);
        textNama.setText(SharedPrefManager.getInstance(this).getNama());
        textHp=(TextView) findViewById(R.id.textHp);
        textHp.setText(SharedPrefManager.getInstance(this).getHp());
        textEmail=(TextView) findViewById(R.id.textEmail);
        textEmail.setText(SharedPrefManager.getInstance(this).getEmail());


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