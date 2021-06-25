package com.example.loans.ui.main;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loans.R;
import com.example.loans.data.local.SharedPrefs;
import com.example.loans.data.remote.ApiService;
import com.example.loans.data.remote.RemoteSource;
import com.example.loans.data.remote.model.BadResponse;
import com.example.loans.data.remote.model.BaseResponse;
import com.example.loans.data.remote.model.Data;
import com.example.loans.data.remote.model.Loan;
import com.example.loans.databinding.ActivityMainBinding;
import com.example.loans.ui.loans.LoansActivity;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ApiService service;

    private LoansAdapter adapter;

    private SharedPrefs sharedPrefs;

    private List<Loan> loans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //Buat instance sharedpreferences
        sharedPrefs = new SharedPrefs(this);

        // Get Loans dari sharedpreferences
//        List<Loan> tempLoans = sharedPrefs.getLoansList();

        //Jika variable loans dari sharedpreferences tidak null, tampilkan constraintList
//        binding.constraintList.setVisibility(loans != null ? View.VISIBLE : View.GONE);

        //Buat instance service
        service = RemoteSource.getApiService();

        // Setup UI Logic
        setUp();

        // Jika variable tempLoans dari sharedpreferences tidak null maka addAll loans dengan value dari tempLoans dan notifydatasetchanged
//        if (tempLoans != null) {
//            loans.addAll(tempLoans);
//            adapter.notifyDataSetChanged();
//            binding.shimmer.stopShimmer();
//            binding.shimmer.setVisibility(View.GONE);
//            binding.recyclerView.setVisibility(View.VISIBLE);
//        }
    }

    private void setUp() {

        adapter = new LoansAdapter(loans);
        //set adapter recyclerview
        binding.recyclerView.setAdapter(adapter);

        //tambahkan garis divider diantara masing-masing item
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        binding.tvMemberId.setText("3");
        getData(binding.tvMemberId.getText().toString());
    }

    private void getData(String memberId) {
        //Show constraintList dan jalankan shimmer jika belum tampil
        binding.constraintList.setVisibility(View.VISIBLE);
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.shimmer.startShimmer();
        //clear data recyclerview
        loans.clear();

        //notifydatasetchanged recyclerview
        adapter.notifyDataSetChanged();

        //panggil api getLoans berdasarkan memberId yang diketik
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

                        //Tampilkan pesan error
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                loans.addAll(response.body().getData().getLoans());
                adapter.notifyDataSetChanged();
                sharedPrefs.setLoansList(loans);
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(@NotNull Call<BaseResponse<Data>> call, @NotNull Throwable t) {

                //Tampilkan pesan error
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }
}