package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ClassLoaiVi;
import com.example.myapplication.R;
import com.example.myapplication.View.FragmentLoaiVi;

import java.util.List;

public class LoaiViAdapter extends RecyclerView.Adapter<LoaiViAdapter.LoaiViViewHolder> {
    private List<ClassLoaiVi> loaiViList;
    int idImgLoaiVi;

    public LoaiViAdapter(List<ClassLoaiVi> loaiViList) {
        this.loaiViList = loaiViList;
    }

    private FragmentLoaiVi.OnItemSelectedListener itemSelectedListener;

    public static void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
    }

    public void setOnItemSelectedListener(FragmentLoaiVi.OnItemSelectedListener listener) {
        this.itemSelectedListener = listener;
    }

    @NonNull
    @Override
    public LoaiViAdapter.LoaiViViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_vi, parent, false);
        return new LoaiViViewHolder(itemView);
    }


    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull LoaiViAdapter.LoaiViViewHolder holder, int position) {
        ClassLoaiVi loaiVi = loaiViList.get(position);
        String tenLoaiVi = loaiVi.getTenLoaiVi();

        if(tenLoaiVi.equals("Tiền mặt")){
            idImgLoaiVi = R.drawable.moneyloaigiaodich;
        } else if (tenLoaiVi.equals("Tài khoản ngân hàng")) {
            idImgLoaiVi = R.drawable.atmloaigiaodich;
        } else if (tenLoaiVi.equals("Thẻ tín dụng")) {
            idImgLoaiVi = R.drawable.cardloaigiaodich;
        } else if (tenLoaiVi.equals("Tài khoản đầu tư")) {
            idImgLoaiVi = R.drawable.digitalloaigiaodich;
        } else if (tenLoaiVi.equals("Ví điện tử")) {
            idImgLoaiVi = R.drawable.digitalloaigiaodich;
        } else if(tenLoaiVi.equals("Khác")) {
            idImgLoaiVi = R.drawable.otherloaigiaodich;
        } else {
            idImgLoaiVi = R.drawable.plus;
        }

        // Đặt hình ảnh vào ImageView từ resource drawable
        holder.imgLoaiVi.setImageResource(idImgLoaiVi);
        holder.tvTenLoaiVi.setText(loaiVi.getTenLoaiVi());

        // Gán sự kiện click vào mỗi mục trong RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu của mục được chọn
                String selectedItem = loaiViList.get(position).getTenLoaiVi();

                // Gọi phương thức onItemSelected của interface
                if (itemSelectedListener != null) {
                    itemSelectedListener.onItemSelected(selectedItem);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return loaiViList.size();
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String selectedItem);
    }


    public class LoaiViViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLoaiVi;
        TextView tvTenLoaiVi;
        public LoaiViViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLoaiVi = itemView.findViewById(R.id.imgLoaiVi);
            tvTenLoaiVi = itemView.findViewById(R.id.tvTenLoaiVi);
        }
    }
}
