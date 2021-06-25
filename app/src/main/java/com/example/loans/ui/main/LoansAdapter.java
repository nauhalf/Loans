package com.example.loans.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loans.data.remote.model.Loan;
import com.example.loans.databinding.ItemLoanBinding;

import java.util.List;

public class LoansAdapter extends RecyclerView.Adapter<LoansAdapter.LoansViewHolder> {

    private List<Loan> list;

    public LoansAdapter(List<Loan> list) {
        this.list = list;
    }

    public void submitList(List<Loan> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LoansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new LoansViewHolder(ItemLoanBinding.inflate(inflater));
    }

    @Override
    public void onBindViewHolder(@NonNull LoansViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class LoansViewHolder extends RecyclerView.ViewHolder {

        private ItemLoanBinding binding;
        public LoansViewHolder(@NonNull ItemLoanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void bind(Loan loan) {
            binding.setLoan(loan);
            binding.executePendingBindings();
        }
    }
}
