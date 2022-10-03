package com.gcbuying.app.Fragments.history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gcbuying.app.Adapters.RecieveHistoryAdapter;
import com.gcbuying.app.Adapters.SentHistoryAdapter;
import com.gcbuying.app.Adapters.ViaGcBuyingAdapter;
import com.gcbuying.app.Models.RecieveAndWithdrawalResponseModel;
import com.gcbuying.app.Models.RecieveDataModel;
import com.gcbuying.app.Models.SentHistoryModel;
import com.gcbuying.app.Models.ViaGcBuyingDataModel;
import com.gcbuying.app.Models.ViaGcBuyingResponseModel;
import com.gcbuying.app.R;
import com.gcbuying.app.networks.GetDataService;
import com.gcbuying.app.networks.UrlController;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SoldViaGCBuyingFragment extends Fragment {
    RecyclerView rvSentHistory;
    private ArrayList<ViaGcBuyingDataModel> list = new ArrayList<>();
    KProgressHUD hud;
    String tokens ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sold_via_g_c_buying, container, false);
        tokens = Utilities.getString(getActivity(), "access_token");
        sendAndWithdraw(tokens);
        rvSentHistory = view.findViewById(R.id.rvSentHistory);
       /* list.add(new SentHistoryModel("0.05 Btc","Sent 0.05 Btc to jf4n29389bn2fh48fbuf","12-03-2021"));
        list.add(new SentHistoryModel("0.05 Btc","Sent 0.05 Btc to jf4n29389bn2fh48fbuf","12-03-2021"));
        list.add(new SentHistoryModel("0.05 Btc","Sent 0.05 Btc to jf4n29389bn2fh48fbuf","12-03-2021"));
        list.add(new SentHistoryModel("0.05 Btc","Sent 0.05 Btc to jf4n29389bn2fh48fbuf","12-03-2021"));
        list.add(new SentHistoryModel("0.05 Btc","Sent 0.05 Btc to jf4n29389bn2fh48fbuf","12-03-2021"));

        rvSentHistory = view.findViewById(R.id.rvSentHistory);
        rvSentHistory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvSentHistory.setAdapter(new SentHistoryAdapter(getContext(),list));*/
        return view;
    }
    private void sendAndWithdraw(String token) {
        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        GetDataService service = UrlController.createService(GetDataService.class);
        Call<ViaGcBuyingResponseModel> call = service.sendAndWithdraw3(UrlController.AddHeaders(getContext(),token));
        call.enqueue(new Callback<ViaGcBuyingResponseModel>() {
            @Override
            public void onResponse(Call<ViaGcBuyingResponseModel> call, Response<ViaGcBuyingResponseModel> response) {

                assert response.body() != null;
                String status = response.body().getStatus();
                if (status.equals("200")) {
                    hud.dismiss();
                    Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    list = response.body().getSendDataModels();
                    rvSentHistory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    rvSentHistory.setAdapter(new ViaGcBuyingAdapter(getContext(),list));

                } else {
                    hud.dismiss();
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ViaGcBuyingResponseModel> call, Throwable t) {
                hud.dismiss();
//                t.printStackTrace();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("dd",t.getMessage());
            }
        });
    }
}