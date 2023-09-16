package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ClassGiaoDich;
import com.example.myapplication.Model.ClassHangMuc;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChiTietHangMucAdapter extends RecyclerView.Adapter<ChiTietHangMucAdapter.ChiTietGiaoDichViewHolder> {

    private List<ClassGiaoDich> GiaoDichList;

    public  ChiTietHangMucAdapter(List<ClassGiaoDich> giaoDichList){
        this.GiaoDichList = giaoDichList;
    }

    @NonNull
    @Override
    public ChiTietHangMucAdapter.ChiTietGiaoDichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_giao_dich_item, parent, false);
        return new ChiTietGiaoDichViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietHangMucAdapter.ChiTietGiaoDichViewHolder holder, int position) {
        ClassGiaoDich giaoDich = GiaoDichList.get(position);
        String dienGiai = giaoDich.getDienGiai();
        String soTien =  String.valueOf(giaoDich.getSotien());

        holder.txtDienGiai.setText(dienGiai);
        holder.txtSoTienChiTiet.setText(soTien);

        Date ngayGiaoDichDate = giaoDich.getNgayGiaoDich();
        if (ngayGiaoDichDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngayThangNam = dateFormat.format(ngayGiaoDichDate);
            holder.txtNgayGiaoDich.setText(ngayThangNam);
        } else {
            holder.txtNgayGiaoDich.setText("Ngày không khả dụng");
        }
    }


    @Override
    public int getItemCount() {
        return GiaoDichList.size();
    }
    public class ChiTietGiaoDichViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNgayGiaoDich;
        private TextView txtDienGiai;
        private TextView txtSoTienChiTiet;

        public ChiTietGiaoDichViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNgayGiaoDich = itemView.findViewById(R.id.txtNgayGiaoDich);
            txtDienGiai = itemView.findViewById(R.id.txtDienGiai);
            txtSoTienChiTiet = itemView.findViewById(R.id.txtSoTienChiTiet);
        }
    }
}
