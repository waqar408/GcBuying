package com.gcbuying.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gcbuying.app.Activities.TradeHistoryDetailActivity;
import com.gcbuying.app.Models.HistoryDummyModel;
import com.gcbuying.app.Models.TradesModel;
import com.gcbuying.app.Models.tradehistory.TradeHistoryApi;
import com.gcbuying.app.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HistoryFragmentDummyAdapter extends RecyclerView.Adapter<HistoryFragmentDummyAdapter.ViewHolder> {


    private Context context;
    private ArrayList<TradeHistoryApi> list;

    public HistoryFragmentDummyAdapter(Context context, ArrayList<TradeHistoryApi> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_history_dummy, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final TradeHistoryApi model = list.get(position);
        holder.tv_itemName.setText(model.getMethod());
        holder.tv_price.setText( model.getRate());
        holder.tv_amountinngn.setText("NGN "+model.getGet_amount());
        holder.tvDate.setText(model.getGet_amount());
        holder.btnStatus.setText(model.getStatus());
        if (model.getStatus().equals("Cancelled"))
        {
            holder.btnStatus.setBackgroundResource(R.drawable.cancelled_background);
        }
        if (model.getStatus().equals("Completed"))
        {
            holder.btnStatus.setBackgroundResource(R.drawable.complete_background);
        }
        if (model.getStatus().equals("Pending"))
        {
            holder.btnStatus.setBackgroundResource(R.drawable.pending_background);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TradeHistoryDetailActivity.class);
                intent.putExtra("ngnamount",model.getGet_amount());
                intent.putExtra("status",model.getStatus());
                intent.putExtra("txnid",model.getTxn_id());
                intent.putExtra("method",model.getMethod());
                intent.putExtra("rate",model.getRate());
                intent.putExtra("sellamount",model.getTotal_amount());
                context.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_itemName,tv_price, tv_amountinngn, tvDate;
        Button btnStatus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_itemName = itemView.findViewById(R.id.tv_itemName);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_amountinngn = itemView.findViewById(R.id.tv_amountinngn);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnStatus = itemView.findViewById(R.id.btnStatus);


        }
    }
}
