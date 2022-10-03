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

import com.bumptech.glide.Glide;
import com.gcbuying.app.Models.CardRatesModel;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.Server;

import java.util.ArrayList;

public class CardRatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<CardRatesModel> cardRatesModels;

    public CardRatesAdapter(Context context, ArrayList<CardRatesModel> cardRatesModels) {
        this.context = context;
        this.cardRatesModels = cardRatesModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_cardrates_item, parent, false);
        return new BookViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookViewHolder holder1 = (BookViewHolder) holder;
        holder1.tvproductName.setText(cardRatesModels.get(position).getName());
        holder1.tv_dolar.setText(cardRatesModels.get(position).getPer_rate());

        String photo=cardRatesModels.get(position).getImage();
        Glide.with(context)
                .load(Server.IMAGE_BASE_URL+photo)
                .into(holder1.Image_product);
        holder1.bind(position);
    }

    @Override
    public int getItemCount() {
        return cardRatesModels.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView tvproductName,tv_perudrate,tv_dolar;
        ImageView Image_product;
        LinearLayout rlItems;

        private BookViewHolder(View itemView) {
            super(itemView);

            //init views
            tvproductName = itemView.findViewById(R.id.tv_name);
            tv_perudrate = itemView.findViewById(R.id.tv_perudrate);
            tv_dolar = itemView.findViewById(R.id.tv_dolar);
            Image_product = itemView.findViewById(R.id.iv_image);
            rlItems = itemView.findViewById(R.id.rlItems);
        }

        private void bind(int pos) {
            CardRatesModel messagesTabModel = cardRatesModels.get(pos);
            tvproductName.setText(messagesTabModel.getName());
//            initClickListener();
        }
//        private void initClickListener() {
//            rlItems.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    callback.onItemClick(getAdapterPosition());
//                }
//            });
//        }
    }
//    public interface Callback {
//        void onItemClick(int pos);
//    }
}
