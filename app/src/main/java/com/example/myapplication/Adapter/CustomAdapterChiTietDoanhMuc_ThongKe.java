package com.example.myapplication.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ClassItemChiTietItem;
import com.example.myapplication.R;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapterChiTietDoanhMuc_ThongKe extends RecyclerView.Adapter<CustomAdapterChiTietDoanhMuc_ThongKe.ViewHolder> {
    Context context;
    int layoutItem;
    String tenLoaiDoanhMuc;
    ArrayList<ClassItemChiTietItem> itemChiTiets;
    private ArrayList<String> dataList;

    private OnItemClickListener listener;

    public CustomAdapterChiTietDoanhMuc_ThongKe(ArrayList<String> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    public CustomAdapterChiTietDoanhMuc_ThongKe(Context context, ArrayList<ClassItemChiTietItem> classItemChiTietItems){
        this.context = context;
        this.itemChiTiets = classItemChiTietItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_chi_tiet_thong_ke,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterChiTietDoanhMuc_ThongKe.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ClassItemChiTietItem itemChiTiet = itemChiTiets.get(position);
        BigDecimal soTien = itemChiTiet.getSoTien();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String formattedThu = currencyFormat.format(soTien);
        holder.txtSoTienChiTietThongKe.setText(formattedThu);
        String tenLoaiHangMuc = itemChiTiet.getTenLoaiHangMuc();
        holder.txtItemCategory.setText(tenLoaiHangMuc);
        int idImage;
        Float Percentage = itemChiTiet.getPercent();
        String hexColor;
        if (tenLoaiHangMuc.equals("Ăn uống")) {
            hexColor = "#3B82F6";
            idImage= R.drawable.hamburger;
        } else if (tenLoaiHangMuc.equals("Dịch vụ sinh hoạt")) {
            hexColor = "#22C55E";
           idImage = R.drawable.bill;
        } else if (tenLoaiHangMuc.equals("Đi lại")) {
            hexColor = "#695ACD";
            idImage = R.drawable.placeholder;
        } else if (tenLoaiHangMuc.equals("Con cái")) {
            hexColor = "#F97316";
            idImage = R.drawable.playtime;
        } else if (tenLoaiHangMuc.equals("Trang phục")) {
            hexColor = "#EF4444";
           idImage = R.drawable.clothing;
        } else if (tenLoaiHangMuc.equals("Sự kiện")) {
            hexColor = "#925ACD";
            idImage = R.drawable.party;
        } else if (tenLoaiHangMuc.equals("Sức khoẻ")) {
            hexColor = "#FED979";
            idImage = R.drawable.cardiogram;
        } else if (tenLoaiHangMuc.equals("Nhà cửa")) {
            hexColor = "#925ACD";
            idImage = R.drawable.house;
        } else if (tenLoaiHangMuc.equals("Hưởng thụ")) {
            hexColor = "#EFC744";
            idImage = R.drawable.vacations;
        } else if (tenLoaiHangMuc.equals("Phát triển bản thân")) {
            hexColor = "#83AC0A";
            idImage = R.drawable.ancestors;
        } else if (tenLoaiHangMuc.equals("Vật nuôi")) {
            hexColor = "#EE9CB9";
           idImage = R.drawable.pets;
        }



        else if (tenLoaiHangMuc.equals("Tiền chúc mừng")) {
            hexColor = "#3B82F6";
            idImage = R.drawable.giving;



        }
        else if (tenLoaiHangMuc.equals("Tiền lãi")) {
            hexColor = "#22C55E";
            idImage = R.drawable.interest;


        } else if (tenLoaiHangMuc.equals("Học bổng")) {
            hexColor = "#695ACD";
            idImage = R.drawable.scholarship;



        } else if (tenLoaiHangMuc.equals("Tiền trợ cấp")) {
            hexColor = "#F97316";
            idImage = R.drawable.generous;

        }
        else if (tenLoaiHangMuc.equals("Kinh doanh riêng")) {
            hexColor = "#EF4444";

            idImage = R.drawable.business;

        }else if (tenLoaiHangMuc.equals("Thưởng")) {
            hexColor = "#925ACD";
            idImage = R.drawable.bonus;


        }else if (tenLoaiHangMuc.equals("Lương")) {
            hexColor = "#FED979";
            idImage = R.drawable.salary;


        }

        else {
            hexColor = "#F97316";
           idImage = R.drawable.plus;
        }
        final ClassItemChiTietItem itemChiTietHM = itemChiTiets.get(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position,itemChiTietHM);
                }
            }
        });

        int color = Color.parseColor(hexColor);
        holder.progressBarChiTieu.setProgressTintList(ColorStateList.valueOf(color));

        holder.txtPercentage.setText("(" + Percentage.toString()  + "%)");
        holder.imageLoaiDoanhMuc.setImageResource(idImage);
        holder.progressBarChiTieu.setProgress((int) itemChiTiet.getPercent());

//        final ClassItemChiTietItem itemChiTietHM = itemChiTiets.get(position);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onItemClick(position);
//                }
//            }
//        });

    }
    public  static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtItemCategory,txtSoTienChiTietThongKe,txtPercentage;
        ImageView imageLoaiDoanhMuc;
        ProgressBar progressBarChiTieu;

        public ViewHolder(View view){
            super(view);
            txtItemCategory = view.findViewById(R.id.txtItemCategory);
            txtSoTienChiTietThongKe = view.findViewById(R.id.txtSoTienChiTietThongKe);
            txtPercentage = view.findViewById(R.id.txtPercentage);
            imageLoaiDoanhMuc = view.findViewById(R.id.imageLoaiDoanhMuc);
            progressBarChiTieu = view.findViewById(R.id.progressBarChiTieu);



        }
    }

    @Override
    public int getItemCount() {
        return itemChiTiets.size();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }

//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
public interface OnItemClickListener {
    void onItemClick(int position,ClassItemChiTietItem itemChiTiet);
}

}
