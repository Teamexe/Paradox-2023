package com.exe.paradox.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exe.paradox.Models.PrizeModel;
import com.exe.paradox.R;
import com.exe.paradox.rest.response.PrizesRP;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class PrizesAdapter extends RecyclerView.Adapter<PrizesAdapter.PrizesViewHolder> {

    Context context;
    PrizesRP prizesRP;

    public PrizesAdapter(Context context, PrizesRP prizesRP) {
        this.context = context;
        this.prizesRP = prizesRP;
    }

    @NonNull
    @Override
    public PrizesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PrizesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_prize, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PrizesViewHolder holder, int position) {
        //Todo: Add Null Checks
        PrizeModel prize = prizesRP.getPrizes().get(position);
        holder.titleTxt.setText(prize.getTitle());
        holder.bodyTxt.setText(prize.getBody());
        Picasso.get()
                .load(prize.getImage())
                .into(holder.imageView);
        if (prize.getUrl() != null
        && !prize.getUrl().isEmpty()){
            holder.itemView.setOnClickListener(view->launchWebsite(prize.getUrl()));
        }
    }

    private void launchWebsite(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) 	url = "http://" + url;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        if (prizesRP == null || prizesRP.getPrizes() == null)
            return 0;
        return prizesRP.getPrizes().size();
    }

    class PrizesViewHolder extends RecyclerView.ViewHolder{

        TextView titleTxt;
        TextView bodyTxt;
        ImageView imageView;

        public PrizesViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt = itemView.findViewById(R.id.titleTxt);
            bodyTxt = itemView.findViewById(R.id.bodyTxt);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
