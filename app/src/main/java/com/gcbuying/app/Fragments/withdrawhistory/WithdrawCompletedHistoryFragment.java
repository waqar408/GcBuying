package com.gcbuying.app.Fragments.withdrawhistory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gcbuying.app.Adapters.WithdrawPendingHistoryAdapter;
import com.gcbuying.app.Models.PendingDataModel;
import com.gcbuying.app.Models.PendingWithdrawsResponseModel;
import com.gcbuying.app.Models.WithdrawPendingHistoryModel;
import com.gcbuying.app.R;
import com.gcbuying.app.networks.GetDataService;
import com.gcbuying.app.networks.UrlController;
import com.gcbuying.app.utilities.Utilities;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WithdrawCompletedHistoryFragment extends Fragment {
    RecyclerView rvPendingWithdrawHistory;
    ArrayList<PendingDataModel> list  = new ArrayList<>();
    String tokens ;
    private KProgressHUD hud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdraw_completed_history, container, false);
        rvPendingWithdrawHistory = view.findViewById(R.id.rvPendingWithdrawHistory);


        tokens = Utilities.getString(getActivity(), "access_token");

        sendAndWithdraw(tokens);
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
        Call<PendingWithdrawsResponseModel> call = service.withdrawResponseModel(UrlController.AddHeaders(getContext(),token));
        call.enqueue(new Callback<PendingWithdrawsResponseModel>() {
            @Override
            public void onResponse(Call<PendingWithdrawsResponseModel> call, Response<PendingWithdrawsResponseModel> response) {

                assert response.body() != null;
                int status = response.body().getStatus();
                if (status == 200) {
                    hud.dismiss();
                    Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    list = response.body().getCompletedDataModels();
                    rvPendingWithdrawHistory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    rvPendingWithdrawHistory.setAdapter(new WithdrawPendingHistoryAdapter(getContext(),list));

                } else {
                    hud.dismiss();
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PendingWithdrawsResponseModel> call, Throwable t) {
                hud.dismiss();
//                t.printStackTrace();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("dd",t.getMessage());
            }
        });
    }

}