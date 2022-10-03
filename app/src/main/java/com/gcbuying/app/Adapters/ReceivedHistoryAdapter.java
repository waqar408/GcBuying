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

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.gcbuying.app.Models.ReceivedHistoryModel;
import com.gcbuying.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReceivedHistoryAdapter extends RecyclerView.Adapter<ReceivedHistoryAdapter.ViewHolder> {


    private Context context;
    private ArrayList<ReceivedHistoryModel> list;

    public ReceivedHistoryAdapter(Context context, ArrayList<ReceivedHistoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.single_receivedhistory_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ReceivedHistoryModel model = list.get(position);
        String time=model.getTime();
        long itemLong = (long) Double.parseDouble(time);
        java.util.Date d = new java.util.Date(itemLong*1000L);
        String itemDateStr = new SimpleDateFormat("dd-MMM-YYYY HH:mm").format(d);
        holder.tv_Time.setText(itemDateStr);

        holder.text_id.setText(model.getId());
        holder.tv_Amount.setText(model.getAmount());
        holder.tv_Received_From.setText(model.getReceived_from());
        holder.tv_Confirmation.setText(model.getConfirmation());
//        holder.tv_Time.setText(model.getTime());

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

    private void bottomShown(ReceivedHistoryModel listItem, ViewHolder holder, TextView tv_seemore) {

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

        private TextView  tv_seemore, tv_Amount, tv_Received_From, tvClose,tv_Confirmation, tv_Time;
        ReadMoreTextView text_id;
        private LinearLayout layout;
        ImageView ivDownShow, ivUpShow;
        private LinearLayout  llSHoeHide;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_id = itemView.findViewById(R.id.text_id);
            tv_seemore = itemView.findViewById(R.id.text_showHide);
            tv_Amount = itemView.findViewById(R.id.text_amount);
            tv_Received_From = itemView.findViewById(R.id.text_reveivedfrom);
            tv_Confirmation = itemView.findViewById(R.id.text_confirmation);
            tv_Time = itemView.findViewById(R.id.text_time);
            tvClose = itemView.findViewById(R.id.tvClose);
            llSHoeHide = itemView.findViewById(R.id.llSHoeHide);
            ivDownShow = itemView.findViewById(R.id.ivDownShow);
            ivUpShow = itemView.findViewById(R.id.ivUpShow);
            layout = itemView.findViewById(R.id.trades_layout);

        }
    }
}
