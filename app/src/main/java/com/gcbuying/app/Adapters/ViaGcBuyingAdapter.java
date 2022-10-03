package com.gcbuying.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gcbuying.app.Activities.BTCHistoryDetailActivity;
import com.gcbuying.app.Models.RecieveDataModel;
import com.gcbuying.app.Models.ViaGcBuyingDataModel;
import com.gcbuying.app.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ViaGcBuyingAdapter extends RecyclerView.Adapter<ViaGcBuyingAdapter.MyViewHolder> {

    Context context;
    List<ViaGcBuyingDataModel> supportersList;

    public ViaGcBuyingAdapter(Context context, List<ViaGcBuyingDataModel> supportersList) {
        this.context = context;
        this.supportersList = supportersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_viagc,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        ViaGcBuyingDataModel model = supportersList.get(position);
        holder.tvtxid.setText("Transaction Id: "+model.getTxid());
        holder.amountBtc.setText("Btc Amount: " + model.getAmount_btc());
//        holder.tvDate.setText(model.getTime());
        holder.amountNaira.setText("Naira "+model.getAmount_naira());
        holder.tvStatus.setText(model.getStatus());



    }

    @Override
    public int getItemCount() {
        return supportersList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvtxid,amountNaira,amountBtc,tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvtxid =  itemView.findViewById(R.id.tvtxid);
            amountNaira =  itemView.findViewById(R.id.amountNaira);
            amountBtc =  itemView.findViewById(R.id.amountBtc);
            tvStatus = itemView.findViewById(R.id.tvStatus);


        }
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}



