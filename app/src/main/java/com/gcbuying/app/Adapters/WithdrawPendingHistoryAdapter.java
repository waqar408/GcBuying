package com.gcbuying.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gcbuying.app.Activities.BTCHistoryDetailActivity;
import com.gcbuying.app.Activities.WithdrawHistoryDetailActivity;
import com.gcbuying.app.Models.PendingDataModel;
import com.gcbuying.app.Models.SentHistoryModel;
import com.gcbuying.app.Models.WithdrawPendingHistoryModel;
import com.gcbuying.app.R;

import java.util.ArrayList;
import java.util.List;


public class WithdrawPendingHistoryAdapter extends RecyclerView.Adapter<WithdrawPendingHistoryAdapter.MyViewHolder> {

    Context context;
    ArrayList<PendingDataModel> supportersList;

    public WithdrawPendingHistoryAdapter(Context context, ArrayList<PendingDataModel> supportersList) {
        this.context = context;
        this.supportersList = supportersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pending_withdraw_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        PendingDataModel model = supportersList.get(position);
        holder.tvNotes.setText(model.getNotes());
        holder.tvNGNAmount.setText("NGN "+model.getAmount());
        if (model.getStatus().equals("Completed"))
        {
            holder.status.setBackgroundResource(R.drawable.complete_background);
            holder.status.setText(model.getStatus());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WithdrawHistoryDetailActivity.class);
                intent.putExtra("trxId",model.getTrx_id());
                intent.putExtra("amount","NGN "+model.getAmount());
                intent.putExtra("notes",model.getNotes());
                intent.putExtra("status",model.getStatus());
                intent.putExtra("bankName",model.getBank_name());
                intent.putExtra("accountName",model.getAccount_name());
                intent.putExtra("accountNo",model.getAccount_no());
                intent.putExtra("comment",model.getComment());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return supportersList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNotes,tvNGNAmount,status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNotes =  itemView.findViewById(R.id.tvNotes);
            tvNGNAmount =  itemView.findViewById(R.id.tvNGNAmount);
            status = itemView.findViewById(R.id.status);


        }
    }
}

