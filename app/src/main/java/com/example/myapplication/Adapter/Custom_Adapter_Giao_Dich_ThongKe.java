package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ClassGiaoDich;
import com.example.myapplication.R;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Custom_Adapter_Giao_Dich_ThongKe extends RecyclerView.Adapter<Custom_Adapter_Giao_Dich_ThongKe.ViewHolder> {
    Context context;
    int layoutItem;
    TextView txtThu, txtChi, txtTongTien, txtThang;
    ArrayList<ClassGiaoDich> classGiaoDichArrayList;

    public Custom_Adapter_Giao_Dich_ThongKe(Context context, ArrayList<ClassGiaoDich> classGiaoDichArrayList) {
        this.context = context;
        this.classGiaoDichArrayList = classGiaoDichArrayList;
    }
    public Custom_Adapter_Giao_Dich_ThongKe( ArrayList<ClassGiaoDich> classGiaoDichArrayList) {
        this.classGiaoDichArrayList = classGiaoDichArrayList;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_money_per_time, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Custom_Adapter_Giao_Dich_ThongKe.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ClassGiaoDich item = classGiaoDichArrayList.get(position);

        BigDecimal thu = item.getTienThu();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String formattedThu = currencyFormat.format(thu);
        holder.txtThu.setText(formattedThu);

        BigDecimal chi = item.getTienChi();
        String formattedChi = currencyFormat.format(chi);
        holder.txtChi.setText(formattedChi);

        BigDecimal tongTien = item.getTongTien();
        String formattedTongTien = currencyFormat.format(tongTien);

        if(tongTien.compareTo(BigDecimal.ZERO) < 0){
            holder.txtTongTien.setTextColor(Color.parseColor("#F62929"));
        }
        else {
            holder.txtTongTien.setTextColor(Color.parseColor("#FF39F340"));

        }
        holder.txtTongTien.setText(formattedTongTien);


        int thang = item.getThang();
        holder.txtThang.setText("Tháng " + thang);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return classGiaoDichArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtThu, txtChi, txtTongTien, txtThang;

        public ViewHolder(View itemView) {
            super(itemView);
            txtThu = itemView.findViewById(R.id.txttxtItemMoneyPerMonthThu);
            txtChi = itemView.findViewById(R.id.txtItemMoneyPerMonthChi);
            txtTongTien = itemView.findViewById(R.id.txttxtItemMoneyPerMonthTongTien);
            txtThang = itemView.findViewById(R.id.txtItemMoneyPerMonth);
        }
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

}
