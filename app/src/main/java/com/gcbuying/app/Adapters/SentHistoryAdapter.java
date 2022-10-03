package com.gcbuying.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gcbuying.app.Activities.BTCHistoryDetailActivity;
import com.gcbuying.app.Models.SendAndWithdrawalResponseModel;
import com.gcbuying.app.Models.SendDataModel;
import com.gcbuying.app.Models.SentHistoryModel;
import com.gcbuying.app.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SentHistoryAdapter extends RecyclerView.Adapter<SentHistoryAdapter.MyViewHolder> {

    Context context;
    List<SendDataModel> supportersList;

    public SentHistoryAdapter(Context context, List<SendDataModel> supportersList) {
        this.context = context;
        this.supportersList = supportersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_senthistory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        SendDataModel model = supportersList.get(position);
        holder.tvBtc.setText(model.getAmounts_sent().get(0).getAmount() + " Btc");
        holder.tvBtcTo.setText("sent Btc to" + model.getAmounts_sent().get(0).getRecipient());
//        holder.tvDate.setText(model.getTime());
       holder.tvDate.setText(getDate(model.getTime()));
       holder.tvStatus.setText("Completed");
       holder.rl.setBackgroundResource(R.drawable.sent_btngreen);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BTCHistoryDetailActivity.class);
                intent.putExtra("amount",model.getAmounts_sent().get(0).getAmount());
                intent.putExtra("txid",model.getTxid());
                intent.putExtra("totalAmount",model.getTotal_amount_sent());
                intent.putExtra("destAddress",model.getAmounts_sent().get(0).getRecipient());
                intent.putExtra("status","Completed");
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return supportersList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvBtc,tvBtcTo,tvDate,tvStatus;
        RelativeLayout rl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBtc =  itemView.findViewById(R.id.tvBtc);
            tvBtcTo =  itemView.findViewById(R.id.tvToBtc);
            tvDate =  itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            rl = itemView.findViewById(R.id.rl);


        }
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}

