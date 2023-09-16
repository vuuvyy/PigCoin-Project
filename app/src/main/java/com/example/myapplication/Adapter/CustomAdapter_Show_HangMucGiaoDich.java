package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ClassHangMuc;
import com.example.myapplication.R;

import java.util.List;

public class CustomAdapter_Show_HangMucGiaoDich extends RecyclerView.Adapter<CustomAdapter_Show_HangMucGiaoDich.HangMucGiaoDichHolder> {
    private List<ClassHangMuc> HangMucList;
    int idImage;

    public CustomAdapter_Show_HangMucGiaoDich(List<ClassHangMuc> HangMucList) {
        this.HangMucList = HangMucList;
    }

    @NonNull
    @Override
    public HangMucGiaoDichHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hang_muc_item, parent, false);
        return new HangMucGiaoDichHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HangMucGiaoDichHolder holder, int position) {
        ClassHangMuc hangMuc = HangMucList.get(position);
        String tenHangMuc = hangMuc.getTenHangMuc();

        int idImage;

        if (tenHangMuc.equals("Đi chợ/ Siêu thị")) {
            idImage= R.drawable.market;
        } else if (tenHangMuc.equals("Ăn tiệm")) {

            idImage = R.drawable.fastfood;
        } else if (tenHangMuc.equals("Cà phê")) {

            idImage = R.drawable.coffeecup;
        } else if (tenHangMuc.equals("Ăn sáng")) {
            idImage = R.drawable.breakfast;
        } else if (tenHangMuc.equals("Ăn trưa")) {
            idImage = R.drawable.friedrice;
        } else if (tenHangMuc.equals("Ăn tối")) {
            idImage = R.drawable.meal;
        } else if (tenHangMuc.equals("Điện")) {
            idImage = R.drawable.lightning;
        } else if (tenHangMuc.equals("Nước")) {
            idImage = R.drawable.drop;
        } else if (tenHangMuc.equals("Wifi")) {
            idImage = R.drawable.wifi;
        } else if (tenHangMuc.equals("Gas")) {
            idImage = R.drawable.gas;
        } else if (tenHangMuc.equals("Điện thoại di động")) {
            idImage = R.drawable.smartphone;
        }
        else if (tenHangMuc.equals("Xăng")) {
            idImage = R.drawable.gasfuel;
        } else if (tenHangMuc.equals("Bảo hiểm")) {
            idImage = R.drawable.nsurance;
        } else if (tenHangMuc.equals("Gửi xe")) {
            idImage = R.drawable.parkinglot;
        }
        else if (tenHangMuc.equals("Thuê xe")) {
            idImage = R.drawable.taxi;
        }
        else if (tenHangMuc.equals("Học phí")) {
            idImage = R.drawable.fees;
        } else if (tenHangMuc.equals("Sữa")) {
            idImage = R.drawable.milk;
        } else if (tenHangMuc.equals("Đồ chơi")) {
            idImage = R.drawable.toys;
        }
        else if (tenHangMuc.equals("Tiền tiêu vặt")) {
            idImage = R.drawable.money;
        }
        else if (tenHangMuc.equals("Quần áo")) {
            idImage = R.drawable.laundry;
        } else if (tenHangMuc.equals("Giày dép")) {
            idImage = R.drawable.sneakers;
        } else if (tenHangMuc.equals("Phụ kiện")) {
            idImage = R.drawable.diamondring;
        }else if (tenHangMuc.equals("Thăm hỏi")) {
            idImage = R.drawable.childrent;
        }else if (tenHangMuc.equals("Biếu tặng")) {
            idImage = R.drawable.love;
        }else if (tenHangMuc.equals("Khám chữa bệnh")) {
            idImage = R.drawable.medicalteam;
        }else if (tenHangMuc.equals("Thuốc men")) {
            idImage = R.drawable.drugs;
        }else if (tenHangMuc.equals("Thể thao")) {
            idImage = R.drawable.sports;
        }else if (tenHangMuc.equals("Mua sắm nội thất")) {
            idImage = R.drawable.seatersofa;
        }else if (tenHangMuc.equals("Thuê nhà")) {
            idImage = R.drawable.building;
        }else if (tenHangMuc.equals("Du lịch")) {
            idImage = R.drawable.hawaiianshirt;
        }else if (tenHangMuc.equals("Vui chơi giải trí")) {
            idImage = R.drawable.karaoke;
        }else if (tenHangMuc.equals("Phim ảnh, ca nhạc")) {
            idImage = R.drawable.popcorn;
        }else if (tenHangMuc.equals("Học hành")) {
            idImage = R.drawable.read;
        }else if (tenHangMuc.equals("Giao lưu")) {
            idImage = R.drawable.friendship;
        }else if (tenHangMuc.equals("Spa & Massage")) {
            idImage = R.drawable.massage;
        }else if (tenHangMuc.equals("Chó")) {
            idImage = R.drawable.dog;
        }else if (tenHangMuc.equals("Mèo")) {
            idImage = R.drawable.cat;
        }
        else if (tenHangMuc.equals("Tiền chúc mừng")) {
            idImage = R.drawable.giving;
        }
        else if (tenHangMuc.equals("Tiền lãi")) {
            idImage = R.drawable.interest;
        } else if (tenHangMuc.equals("Học bổng")) {
            idImage = R.drawable.scholarship;
        } else if (tenHangMuc.equals("Tiền trợ cấp")) {
            idImage = R.drawable.generous;
        }
        else if (tenHangMuc.equals("Kinh doanh riêng")) {
            idImage = R.drawable.business;
        }else if (tenHangMuc.equals("Thưởng")) {
            idImage = R.drawable.bonus;
        }else if (tenHangMuc.equals("Lương")) {
            idImage = R.drawable.salary;
        }

        else {
            idImage = R.drawable.plus;
        }

        holder.imgHangMucItem.setImageResource(idImage);

        holder.txtTenHangMucItem.setText(hangMuc.getTenHangMuc());
        holder.txtSoTienHangMucItem.setText(String.valueOf(hangMuc.getSoTien()));


        // Tạo adapter cho RecyclerView con (HangMucAdapter) và đặt adapter cho nó
        ChiTietHangMucAdapter hangMucAdapter = new ChiTietHangMucAdapter(hangMuc.getGiaoDichList());
        holder.recyclerViewNgayGio.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerViewNgayGio.setAdapter(hangMucAdapter);

        // Xử lý sự kiện click để hiển thị hoặc ẩn RecyclerView con
        holder.itemView.setOnClickListener(v -> {
            if (holder.recyclerViewNgayGio.getVisibility() == View.VISIBLE) {
                holder.recyclerViewNgayGio.setVisibility(View.GONE);
            } else {
                holder.recyclerViewNgayGio.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return HangMucList.size();
    }

    public class HangMucGiaoDichHolder extends RecyclerView.ViewHolder {
        private ImageView imgHangMucItem;
        private TextView txtTenHangMucItem, txtSoTienHangMucItem;
        private RecyclerView recyclerViewNgayGio;

        public HangMucGiaoDichHolder(@NonNull View itemView) {
            super(itemView);
            imgHangMucItem = itemView.findViewById(R.id.imgHangMucItem);
            txtTenHangMucItem = itemView.findViewById(R.id.txtTenHangMucItem);
            txtSoTienHangMucItem = itemView.findViewById(R.id.txtSoTienHangMucItem);
            recyclerViewNgayGio = itemView.findViewById(R.id.recyclerViewNgayGio);
        }
    }
}
