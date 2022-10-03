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

import com.gcbuying.app.Models.TradesModel;
import com.gcbuying.app.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TradesAdapter extends RecyclerView.Adapter<TradesAdapter.ViewHolder> {


    private Context context;
    private ArrayList<TradesModel> list;

    public TradesAdapter(Context context, ArrayList<TradesModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.single_trade_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final TradesModel model = list.get(position);
        DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null,date1 = null;

        try {
            date = theDateFormat.parse(model.getCreated_at());
            date1 = theDateFormat.parse(model.getUpdated_at());
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch (Exception exception) {
            // Generic catch. Do what you want.
        }

        theDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String[] arrayString = model.getCreated_at().split(";");
        String[] arrayString1 = model.getUpdated_at().split(";");

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

        holder.tv_id.setText(String.valueOf(model.getId()));
        holder.tv_method.setText(model.getMethod());
        holder.tv_sellAmount.setText(model.getTotal_amount());
        holder.tv_rate.setText(model.getRate());
        holder.tv_ReceivedAmount.setText(model.getGet_amount());
        holder.tv_Status.setText(model.getStatus1());
        holder.tv_CreatedDate.setText(theDateFormat.format(date));
        holder.tv_UpdateDate.setText(theDateFormat.format(date1));

        bottomShown(model, holder, holder.tv_seemore);

        holder.tv_seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (model.getBottomShown() == false) {
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

                if (model.getBottomShown() == false) {
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
                if (model.getBottomShown() == false) {
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

    public void bottomShown(TradesModel listItem, ViewHolder holder, TextView textView) {

        if (listItem.getBottomShown() == true) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.ivDownShow.setVisibility(View.GONE);
            holder.ivUpShow.setVisibility(View.VISIBLE);
            holder.ivDownShow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            textView.setText("Close");
        } else {
            holder.layout.setVisibility(View.GONE);
            holder.ivUpShow.setVisibility(View.GONE);
            holder.ivDownShow.setVisibility(View.VISIBLE);
            holder.ivDownShow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            textView.setText("See More");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClose,tv_id, tv_seemore, tv_method, tv_sellAmount, tv_rate, tv_ReceivedAmount, tv_Status, tv_CreatedDate, tv_UpdateDate;
        ImageView ivDownShow, ivUpShow;
        private LinearLayout layout,llSHoeHide;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.text_id);
            tv_seemore = itemView.findViewById(R.id.text_showHide);
            tv_method = itemView.findViewById(R.id.text_method);
            tv_sellAmount = itemView.findViewById(R.id.text_sell_amount);
            tv_rate = itemView.findViewById(R.id.text_rate);
            tv_ReceivedAmount = itemView.findViewById(R.id.text_reveived_amount);
            tv_Status = itemView.findViewById(R.id.text_status);
            tv_CreatedDate = itemView.findViewById(R.id.text_created_date);
            tv_UpdateDate = itemView.findViewById(R.id.text_update_date);
            tvClose = itemView.findViewById(R.id.tvClose);
            llSHoeHide = itemView.findViewById(R.id.llSHoeHide);
            ivDownShow = itemView.findViewById(R.id.ivDownShow);
            ivUpShow = itemView.findViewById(R.id.ivUpShow);
            layout = itemView.findViewById(R.id.trades_layout);

        }
    }
}
