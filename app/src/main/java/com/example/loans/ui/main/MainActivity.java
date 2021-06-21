package com.example.loans.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.loans.ui.loans.LoansActivity;
import com.example.loans.R;
import com.example.loans.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUp();
    }

    private void setUp() {
        binding.btnShowData.setOnClickListener(view -> {
           if(binding.etMemberId.getText().length() > 0){
               Intent intent = new Intent(this, LoansActivity.class);
               intent.putExtra(LoansActivity.MEMBER_ID, binding.etMemberId.getText().toString());
               startActivity(intent);
           } else {
               Toast.makeText(this, getString(R.string.empty_member_id), Toast.LENGTH_SHORT).show();
           }
        });
    }
}