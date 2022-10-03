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

import com.gcbuying.app.Models.RechargeModel;
import com.gcbuying.app.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.ViewHolder> {


    private Context context;
    private ArrayList<RechargeModel> list;

    public RechargeAdapter(Context context, ArrayList<RechargeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.single_recharge_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final RechargeModel model = list.get(position);
        DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null,date1 = null;

        try {
            date = theDateFormat.parse(model.getCreatedDate());
            date1 = theDateFormat.parse(model.getUpdatedDate());
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch (Exception exception) {
            // Generic catch. Do what you want.
        }

        theDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String[] arrayString = model.getCreatedDate().split(";");
        String[] arrayString1 = model.getUpdatedDate().split(";");

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
        String timeFormate=f2.format(d).toLowerCase(); // "12:18am"
        holder.tv_createdDate.setText(theDateFormat.format(date));
        holder.tv_updatedDate.setText(theDateFormat.format(date1));
        holder.tv_id.setText(String.valueOf(model.getId()));
        holder.tv_bankName.setText(model.getBankName());
        holder.tv_phonenumber.setText(model.getPhonenumber());
        holder.tv_nairaAmount.setText(model.getNairaAmount());
        holder.tv_btcAmount.setText(model.getBtcAmount());
        holder.tv_usdAmount.setText(model.getUsdAmount());
        holder.tv_status.setText(model.getStatus());

//        holder.tv_action.setText(model.getAction());



        bottomShown(model, holder, holder.tv_seemore);

        holder.tv_seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (model.isBottomShown() == false){
                    model.setBottomShown(true);
                    notifyDataSetChanged();;
                }
                else {
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

    private void bottomShown(RechargeModel listItem, ViewHolder holder, TextView tv_seemore) {

        if (listItem.isBottomShown() == true){
            holder.layout.setVisibility(View.VISIBLE);
            holder.ivDownShow.setVisibility(View.GONE);
            holder.ivUpShow.setVisibility(View.VISIBLE);
            tv_seemore.setText("Close");
            holder.ivDownShow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
        }
        else {
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

        private TextView tv_id,tvClose, tv_seemore, tv_bankName, tv_phonenumber, tv_nairaAmount, tv_btcAmount,tv_usdAmount,tv_status,
        tv_createdDate,tv_updatedDate,tv_action;

        private LinearLayout layout;

        ImageView ivDownShow, ivUpShow;
        private LinearLayout  llSHoeHide;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.text_id);
            tv_seemore = itemView.findViewById(R.id.text_showHide);
            tv_bankName = itemView.findViewById(R.id.text_bankname);
            tv_phonenumber = itemView.findViewById(R.id.text_phonenumber);
            tv_nairaAmount = itemView.findViewById(R.id.text_niraAmount);
            tv_btcAmount = itemView.findViewById(R.id.text_btcAmount);
            tv_usdAmount = itemView.findViewById(R.id.text_usdAmount);
            tv_status = itemView.findViewById(R.id.text_status);
            tv_createdDate = itemView.findViewById(R.id.text_created_Date);
            tv_updatedDate = itemView.findViewById(R.id.text_update_date);
//            tv_action = itemView.findViewById(R.id.text_action);
            layout = itemView.findViewById(R.id.layout_details);
            tvClose = itemView.findViewById(R.id.tvClose);
            llSHoeHide = itemView.findViewById(R.id.llSHoeHide);
            ivDownShow = itemView.findViewById(R.id.ivDownShow);
            ivUpShow = itemView.findViewById(R.id.ivUpShow);

        }
    }
}
