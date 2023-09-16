package com.example.myapplication.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ClassVi;
import com.example.myapplication.R;

import java.math.BigDecimal;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletViewHolder> {
    private Context context;


    private List<ClassVi> walletList;
    ItemClickListener itemClickListener;
    public WalletAdapter(List<ClassVi> walletList) {
        this.walletList = walletList; // Initialize the adapter's walletList with the provided data
    }

    public interface ItemClickListener {
        void onItemClick(ClassVi item);
    }


    @NonNull
    @Override
    public WalletAdapter.WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_thongke_vidangsudung, parent, false);
        return new WalletViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.WalletViewHolder holder, int position) {
        ClassVi loaiVi = walletList.get(position);

        // Cập nhật dữ liệu cho các thành phần trong view holder
        //holder.textView.setText(loaiVi.getTenLoaiVi());
        String tenLoaiVi = loaiVi.getTenLoaiVi();
        int idImg;
        if(tenLoaiVi.equals("Ví tiền mặt")){
            idImg = R.drawable.wallet;
        }
        else if (tenLoaiVi.equals("Tài khoản thẻ")){
            idImg = R.drawable.credit;
        }
        else {
            idImg = R.drawable.credit;

        }
        BigDecimal sotien = loaiVi.getTienTrongVi();
        String tenVi= loaiVi.getTenVi();

        // Định dạng lại số trước khi hiển thị
        // Kiểm tra giá trị số trước khi định dạng
        double sotienDouble = sotien.doubleValue();
        String sotienFormatted;
        if (Math.abs(sotienDouble) >= 1_000_000_000) {
            // Nếu số quá lớn, sử dụng định dạng mặc định
            sotienFormatted = String.valueOf(sotienDouble);
        } else {
            // Sử dụng định dạng "#,##0" nếu số nhỏ hơn 1 tỷ
            sotienFormatted = String.format("%,.0f", sotienDouble);
        }
        // Hiển thị hình ảnh và chữ của loại hạng mục
        holder.imgLoaiVi.setImageResource(idImg);
        holder.tvTen.setText(tenVi);
        holder.tvTien.setText(sotienFormatted);

        // Set the click listener for each item
        holder.imgLoaiVi.setImageResource(idImg);
        holder.tvTen.setText(tenVi);

        // Đặt sự kiện click cho itemView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    int position = holder.getAdapterPosition();
                    itemClickListener.onItemClick(walletList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public void setData(List<ClassVi> newList) {
        walletList.clear();
        walletList.addAll(newList);
        notifyDataSetChanged();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Gắn layout cho fragment này
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        return view;
    }
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }
    static class WalletViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLoaiVi;
        private TextView tvTen, tvTien;
        List<ClassVi> walletList;
        ItemClickListener itemClickListener;


        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các thành phần trong view holder từ itemView
            imgLoaiVi = itemView.findViewById(R.id.imgLoaiVi);
            tvTen = itemView.findViewById(R.id.tvTen);  //tên loại ví
            tvTien = itemView.findViewById(R.id.tvTien);


        }


    }


}
