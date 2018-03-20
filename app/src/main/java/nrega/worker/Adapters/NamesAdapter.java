package nrega.worker.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import nrega.worker.R;

/**
 * Created by dell-pc on 3/27/2017.
 */

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.ProductViewHolder>{

    Context appContext;
    public ArrayList<JSONObject> data;
    public Map<Integer,Integer> params = new HashMap   <Integer, Integer>();

    public NamesAdapter(Context context,ArrayList<JSONObject> array){
        appContext=context;
        data=array;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_layout_list, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return  productViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {

        try {
            JSONObject jsonObject = data.get(position);
            new AQuery(appContext).id(holder.name).text(jsonObject.getString("workerName"));
            new AQuery(appContext).id(holder.gender).text(jsonObject.getString("gender"));
            new AQuery(appContext).id(holder.age).text(jsonObject.getString("age"));


            //new AQuery(appContext).id(holder.gender).text(jsonObject.getString("name3"));

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(appContext, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView gender;
        TextView age;
        CardView cv;
        int count;


        public ProductViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            gender = (TextView) itemView.findViewById(R.id.gender);
            age = (TextView) itemView.findViewById(R.id.age);
            cv = (CardView) itemView.findViewById(R.id.View_card2);
            count =0;
            for(int i=0;i<data.size();i++){
                params.put(i,0);
            }

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count ==0)
                    {
                        cv.setBackgroundColor(Color.parseColor("#58EF61"));
                        count =1;
                        params.put(getAdapterPosition(),1);
                    }
                    else {
                        cv.setBackgroundColor(Color.parseColor("#F4F4F4"));
                        count=0;
                        params.put(getAdapterPosition(),0);
                    }
                }
            });
        }
    }
}