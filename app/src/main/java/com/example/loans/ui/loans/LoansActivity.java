package com.example.loans.ui.loans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.loans.R;
import com.example.loans.data.local.SharedPrefs;
import com.example.loans.data.remote.ApiService;
import com.example.loans.data.remote.RemoteSource;
import com.example.loans.data.remote.model.BadResponse;
import com.example.loans.data.remote.model.BaseResponse;
import com.example.loans.data.remote.model.Data;
import com.example.loans.data.remote.model.Loan;
import com.example.loans.databinding.ActivityLoansBinding;
import com.example.loans.ui.main.LoansAdapter;
import com.example.loans.ui.main.MainActivity;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoansActivity extends AppCompatActivity {

    public static String MEMBER_ID = "MEMBER_ID";
    private String memberId;
    private ApiService service;
    private ActivityLoansBinding binding;
    private LoansAdapter adapter;
    private SharedPrefs sharedPrefs;
    private List<Loan> loans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loans);
        sharedPrefs = new SharedPrefs(this);

        if (!getIntent().hasExtra(MEMBER_ID)) {
            loans = sharedPrefs.getLoansList();
            if(loans == null){
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return;
            }
        }

        memberId = getIntent().getStringExtra(MEMBER_ID);
        service = RemoteSource.getApiService();
        setUp();
    }

    private void setUp() {
        adapter = new LoansAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        binding.shimmer.startShimmer();
        if (loans == null) {
            service.getLoans(memberId).enqueue(new Callback<BaseResponse<Data>>() {
                @Override
                public void onResponse(@NotNull Call<BaseResponse<Data>> call, @NotNull Response<BaseResponse<Data>> response) {
                    if (response.code() != 200) {
                        try {
                            String str = response.errorBody().string();

                            BadResponse badResponse = new Gson().fromJson(str, BadResponse.class);

                            String message = "Unknown error";
                            if (badResponse != null) {
                                message = badResponse.getMessage();
                            }

                            Toast.makeText(LoansActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }

                    loans = response.body().getData().getLoans();
                    adapter.submitList(loans);
                    sharedPrefs.setLoansList(loans);
                    binding.shimmer.stopShimmer();
                    binding.shimmer.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFailure(@NotNull Call<BaseResponse<Data>> call, @NotNull Throwable t) {
                    Toast.makeText(LoansActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    binding.shimmer.stopShimmer();
                    binding.shimmer.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                }
            });
        } else {
            adapter.submitList(loans);
            binding.shimmer.stopShimmer();
            binding.shimmer.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra(MEMBER_ID)) {
            super.onBackPressed();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}