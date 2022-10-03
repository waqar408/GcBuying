package com.gcbuying.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gcbuying.app.Models.SellHistoryModel;
import com.gcbuying.app.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SellHistoryAdapter extends RecyclerView.Adapter<SellHistoryAdapter.ViewHolder> {


    private Context context;
    private ArrayList<SellHistoryModel> list;

    public SellHistoryAdapter(Context context, ArrayList<SellHistoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.single_sellhistory_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SellHistoryModel model = list.get(position);

        DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null, date1 = null;

        try {
            date = theDateFormat.parse(model.getTime());
            date1 = theDateFormat.parse(model.getTime());
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch (Exception exception) {
            // Generic catch. Do what you want.
        }

        theDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String[] arrayString = model.getTime().split(";");
        String[] arrayString1 = model.getTime().split(";");

        String time = arrayString[0];
        String time1 = arrayString1[0];

        time = time.substring(time.indexOf("2020-10-12T") + 12, time.length());
//        tvDate.setText(theDateFormat.format(date));
        DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
        Date d = null;
        try {
            d = f1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat("h:mm a");
        String timeFormate = f2.format(d).toLowerCase(); // "12:18am"
        holder.tv_Time.setText(theDateFormat.format(date));
        holder.tv_id.setText(model.getId());
        holder.tv_AmountBTC.setText(model.getAmount_BTC());
        holder.tv_AmountNAIRA.setText(model.getAmount_NAIRA());
        holder.tv_Status.setText(model.getStatus());

        bottomShown(model, holder, holder.tv_seemore);
        holder.tv_seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (model.isBottomShown() == false) {
                    model.setBottomShown(true);
                    notifyDataSetChanged();
                    ;
                } else {
                    model.setBottomShown(false);
                    notifyDataSetChanged();
                }


            }
        });
        holder.llSHoeHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (model.isBottomShown() == false) {
                    model.setBottomShown(true);
                    notifyDataSetChanged();
                    ;
                } else {
                    model.setBottomShown(false);
                    notifyDataSetChanged();
                }


            }
        });
        holder.tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.isBottomShown() == false) {
                    model.setBottomShown(true);
                    notifyDataSetChanged();
                    holder.layout.setVisibility(View.GONE);
                    holder.ivUpShow.setVisibility(View.GONE);
                    holder.ivDownShow.setVisibility(View.VISIBLE);
                    holder.ivDownShow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                } else {
                    model.setBottomShown(false);
                    notifyDataSetChanged();
                }

            }
        });

    }

    private void bottomShown(SellHistoryModel listItem, ViewHolder holder, TextView tv_seemore) {

        if (listItem.isBottomShown() == true) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.ivDownShow.setVisibility(View.GONE);
            holder.ivUpShow.setVisibility(View.VISIBLE);
            tv_seemore.setText("Close");
            holder.ivDownShow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
        } else {
            holder.layout.setVisibility(View.GONE);
            holder.ivUpShow.setVisibility(View.GONE);
            holder.ivDownShow.setVisibility(View.VISIBLE);
            holder.ivDownShow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

            tv_seemore.setText("See More");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_id, tvClose, tv_seemore, tv_AmountBTC, tv_AmountNAIRA, tv_Status, tv_Time;

        private LinearLayout layout;
        ImageView ivDownShow, ivUpShow;
        private LinearLayout llSHoeHide;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.text_id);
            tv_seemore = itemView.findViewById(R.id.text_showHide);
            tv_AmountBTC = itemView.findViewById(R.id.text_amountBTC);
            tv_AmountNAIRA = itemView.findViewById(R.id.text_amountNAIRA);
            tv_Status = itemView.findViewById(R.id.text_status);
            tv_Time = itemView.findViewById(R.id.text_time);
            layout = itemView.findViewById(R.id.trades_layout);
            tvClose = itemView.findViewById(R.id.tvClose);
            llSHoeHide = itemView.findViewById(R.id.llSHoeHide);
            ivDownShow = itemView.findViewById(R.id.ivDownShow);
            ivUpShow = itemView.findViewById(R.id.ivUpShow);

        }
    }
}
