package corp.pets.pets_bucket;

/**
 * Created by eng on 5/29/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AJISETYA.
 */

public class Adapterlist extends RecyclerView.Adapter<Adapterlist.ViewHolder>{

    //private AlertDialog.Builder builder;
    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public Adapterlist(data_acc mainActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = mainActivity;
        this.list_data = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_acc, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context)
                .load("http://192.168.43.220/PetS_Bucket/aksesoris/" + list_data.get(position).get("gambar"))
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imghape);
        holder.txthape.setText(list_data.get(position).get("nama"));
        holder.txtharga.setText(list_data.get(position).get("harga"));
        holder.txtsatuan.setText(list_data.get(position).get("satuan"));
        holder.txtstok.setText(list_data.get(position).get("stok"));
        holder.txtpenjual.setText(list_data.get(position).get("penjual"));
        holder.txtid.setText(list_data.get(position).get("id"));
        holder.txthp.setText(list_data.get(position).get("hp"));
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("Contoh Alert");
                builder.setMessage("Alert dengan 1 Action Button ");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                               dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                //Toast.makeText(v.getContext(), "Ini Sukses "+list_data.get(position).get("nama"), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txthape;TextView txtid;TextView txthp;
        TextView txtharga;TextView txtsatuan;TextView txtstok;TextView txtpenjual;
        ImageView imghape;

        public ViewHolder(View itemView) {
            super(itemView);

            txthape = (TextView) itemView.findViewById(R.id.txthape);
            txtharga= (TextView) itemView.findViewById(R.id.txtharga);
            txtsatuan = (TextView) itemView.findViewById(R.id.txtsatuan);
            txtstok = (TextView) itemView.findViewById(R.id.txtstok);
            txtpenjual = (TextView) itemView.findViewById(R.id.txtpenjual);
            txtid = (TextView) itemView.findViewById(R.id.txt_id);
            txthp = (TextView) itemView.findViewById(R.id.txt_hp);
            imghape = (ImageView) itemView.findViewById(R.id.imghp);
        }
    }
}
