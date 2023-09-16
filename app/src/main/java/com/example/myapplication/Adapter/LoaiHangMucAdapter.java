package com.example.myapplication.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ClassLoaiHangMuc;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class LoaiHangMucAdapter extends RecyclerView.Adapter<LoaiHangMucAdapter.LoaiHangMucViewHolder> implements HangMucAdapter.OnHangMucItemClickListener{
    private List<ClassLoaiHangMuc> loaiHangMucList;

    private List<ClassLoaiHangMuc> hangMucList;
    private HangMucAdapter hangMucAdapter; // Thêm biến hangMucAdapter
    public interface OnHangMucItemClickListener {
        void onHangMucItemClick(String tenHangMuc);
    }
    private OnHangMucItemClickListener listener;

    public LoaiHangMucAdapter(List<ClassLoaiHangMuc> loaiHangMucList, OnHangMucItemClickListener listener) {
        this.loaiHangMucList = loaiHangMucList;
        this.listener = listener;
    }


    int idImage;

    public LoaiHangMucAdapter(List<ClassLoaiHangMuc> loaiHangMucList) {
        this.loaiHangMucList = loaiHangMucList;
        hangMucAdapter = new HangMucAdapter(new ArrayList<>());
    }

    // ... (constructor and other methods)


    @NonNull
    @Override
    public LoaiHangMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loai_hang_muc_item_giaodich, parent, false);
        return new LoaiHangMucViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiHangMucViewHolder holder, int position) {
        ClassLoaiHangMuc loaiHangMuc = loaiHangMucList.get(position);
        String tenLoaiHangMuc = loaiHangMuc.getTenLoaiHangMuc();
        idImage = R.drawable.plus;

        if (tenLoaiHangMuc.equals("Ăn uống")) {

            idImage = R.drawable.hamburger;
        } else if (tenLoaiHangMuc.equals("Dịch vụ sinh hoạt")) {

            idImage = R.drawable.bill;
        } else if (tenLoaiHangMuc.equals("Đi lại")) {

            idImage = R.drawable.placeholder;
        } else if (tenLoaiHangMuc.equals("Con cái")) {
            idImage = R.drawable.playtime;
        } else if (tenLoaiHangMuc.equals("Trang phục")) {
            idImage = R.drawable.clothing;
        } else if (tenLoaiHangMuc.equals("Sự kiện")) {
            idImage = R.drawable.party;
        } else if (tenLoaiHangMuc.equals("Sức khoẻ")) {
            idImage = R.drawable.cardiogram;
        } else if (tenLoaiHangMuc.equals("Nhà cửa")) {
            idImage = R.drawable.house;
        } else if (tenLoaiHangMuc.equals("Hưởng thụ")) {
            idImage = R.drawable.vacations;
        } else if (tenLoaiHangMuc.equals("Phát triển bản thân")) {
            idImage = R.drawable.ancestors;
        } else if (tenLoaiHangMuc.equals("Vật nuôi")) {
            idImage = R.drawable.pets;
        } else if (tenLoaiHangMuc.equals("Lương")) {
            idImage = R.drawable.savemoney;
        } else if (tenLoaiHangMuc.equals("Thưởng")) {
            idImage = R.drawable.profits;
        } else if (tenLoaiHangMuc.equals("Tiền lãi")) {
            idImage = R.drawable.moneybag;
        } else if (tenLoaiHangMuc.equals("Khác")) {
            idImage = R.drawable.budget;
        } else {
            idImage = R.drawable.plus;
        }
        // Hiển thị hình ảnh và chữ của loại hạng mục
        holder.imgLoaiHangMuc.setImageResource(idImage);
        holder.txtTenLoaiHangMuc.setText(loaiHangMuc.getTenLoaiHangMuc());

        // Tạo adapter cho RecyclerView con (HangMucAdapter) và đặt adapter cho nó
        HangMucAdapter hangMucAdapter = new HangMucAdapter(loaiHangMuc.getHangMucList());
        hangMucAdapter.setOnHangMucItemClickListener(this);

        holder.recyclerViewHangMuc.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerViewHangMuc.setAdapter(hangMucAdapter);

        // Xử lý sự kiện click để hiển thị hoặc ẩn RecyclerView con
        holder.itemView.setOnClickListener(v -> {
            if (holder.recyclerViewHangMuc.getVisibility() == View.VISIBLE) {
                holder.recyclerViewHangMuc.setVisibility(View.GONE);
            } else {
                holder.recyclerViewHangMuc.setVisibility(View.VISIBLE);
            }
        });
        if (listener != null) {
            listener.onHangMucItemClick(loaiHangMuc.getTenLoaiHangMuc());
        }
    }


    @Override
    public int getItemCount() {
        return loaiHangMucList.size();
    }

    @Override
    public void onHangMucItemClick(String tenHangMuc) {
        Log.d( "onHangMucItemClick: ",tenHangMuc);
    }

    public class LoaiHangMucViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLoaiHangMuc;
        private TextView txtTenLoaiHangMuc;
        private RecyclerView recyclerViewHangMuc;
        private RecyclerView recyclerViewHangMucGiaoDich;

        public LoaiHangMucViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLoaiHangMuc = itemView.findViewById(R.id.imgLoaiHangMuc);
            txtTenLoaiHangMuc = itemView.findViewById(R.id.txtTenLoaiHangMuc);
            recyclerViewHangMuc = itemView.findViewById(R.id.recyclerViewHangMuc);
        }
    }


    // Phương thức trả về adapter của HangMucAdapter từ LoaiHangMucAdapter
    public HangMucAdapter getHangMucAdapter() {
        // Trả về adapter của HangMucAdapter
        return hangMucAdapter;
    }

}
