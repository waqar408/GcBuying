package com.gcbuying.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gcbuying.app.Models.sellgiftCard.SellGiftCardDataModel;
import com.gcbuying.app.R;
import com.gcbuying.app.Server.Server;
import com.gcbuying.app.networks.RetrofitClientInstance;

import java.util.List;


public class CardCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Callback callback;
    private List<SellGiftCardDataModel> newArrivalModels;

    public CardCategoryAdapter(Context context, List<SellGiftCardDataModel> newArrivalModels, Callback callback) {
        this.context = context;
        this.callback = callback;
        this.newArrivalModels = newArrivalModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category, parent, false);
        return new BookViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookViewHolder holder1 = (BookViewHolder) holder;

        holder1.bind(position);
        holder1.tvCategoryName.setText(newArrivalModels.get(position).getName());
        Glide.with(context).load(Server.IMAGE_BASE_URL+newArrivalModels.get(position).getImage()).into(holder1.ivImage);

//        Picasso.get().load(photo).into(holder1.Image_product);
//        holder1.rlItems.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, ProductDetailsActivity.class));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return newArrivalModels.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName;
        ImageView ivImage;
        CardView rlItems;

        private BookViewHolder(View itemView) {
            super(itemView);

            //init views
            ivImage = itemView.findViewById(R.id.ivImage);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            rlItems = itemView.findViewById(R.id.rlItems);
//            country_flag      = itemView.findViewById(R.id.country_flag);
        }

        private void bind(int pos) {
            SellGiftCardDataModel messagesTabModel = newArrivalModels.get(pos);
            initClickListener();
        }
        private void initClickListener() {
            rlItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onItemClick(getAdapterPosition());
                }
            });
        }
    }
    public interface Callback {
        void onItemClick(int pos);
    }

}
