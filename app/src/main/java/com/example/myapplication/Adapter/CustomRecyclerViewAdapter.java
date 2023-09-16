package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ChildItem;
import com.example.myapplication.Model.ClassGiaoDich;
import com.example.myapplication.Model.ClassHangMuc;
import com.example.myapplication.R;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.GiaoDichHolder> {
    private List<ClassGiaoDich> GiaoDichList;
    int idImage;

    public CustomRecyclerViewAdapter(List<ClassGiaoDich> GiaoDichList) {
        this.GiaoDichList = GiaoDichList;
    }

    @NonNull
    @Override
    public GiaoDichHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thu_nhi_vi_item, parent, false);
        return new GiaoDichHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaoDichHolder holder, int position) {
        ClassGiaoDich giaoDich = GiaoDichList.get(position);

        holder.txtNgayGiaoDichVi.setText(String.valueOf(giaoDich.getTenHangMuc()));
        holder.txtSoTienGiaoDichItem.setText(giaoDich.calculateTotalAmount().toString());


        // Tạo adapter cho RecyclerView con (HangMucAdapter) và đặt adapter cho nó
        AdapterChiTietHangMucThuNhi giaoDichAdapter = new AdapterChiTietHangMucThuNhi(giaoDich.getChildItemList());
        holder.recyclerViewChiTiet.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerViewChiTiet.setAdapter(giaoDichAdapter);

        // Xử lý sự kiện click để hiển thị hoặc ẩn RecyclerView con
        holder.itemView.setOnClickListener(v -> {
            if (holder.recyclerViewChiTiet.getVisibility() == View.VISIBLE) {
                holder.recyclerViewChiTiet.setVisibility(View.GONE);
            } else {
                holder.recyclerViewChiTiet.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return GiaoDichList.size();
    }

    public class GiaoDichHolder extends RecyclerView.ViewHolder {
        private ImageView imgHangMucItem;
        private TextView txtNgayGiaoDichVi, txtSoTienGiaoDichItem;
        private RecyclerView recyclerViewChiTiet;

        public GiaoDichHolder(@NonNull View itemView) {
            super(itemView);
            txtNgayGiaoDichVi = itemView.findViewById(R.id.txtNgayGiaoDichVi);
            txtSoTienGiaoDichItem = itemView.findViewById(R.id.txtSoTienGiaoDichItem);
            recyclerViewChiTiet = itemView.findViewById(R.id.recyclerViewChiTiet);
        }
    }
}
