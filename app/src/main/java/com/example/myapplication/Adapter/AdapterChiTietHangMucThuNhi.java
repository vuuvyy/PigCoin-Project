package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ChildItem;
import com.example.myapplication.Model.ClassGiaoDich;
import com.example.myapplication.R;

import java.util.List;

public class AdapterChiTietHangMucThuNhi extends RecyclerView.Adapter<AdapterChiTietHangMucThuNhi.ChiTietGiaoDichNgayViewHolder>{

    private List<ChildItem> childList;

    public AdapterChiTietHangMucThuNhi(List<ChildItem> childList){
        this.childList = childList;
    }
    @NonNull
    @Override
    public ChiTietGiaoDichNgayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_child_item, parent, false);
        return new ChiTietGiaoDichNgayViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(@NonNull ChiTietGiaoDichNgayViewHolder holder, int position) {
        ChildItem item = childList.get(position);
        String dienGiai = item.getName();
        String soTien =  String.valueOf(item.getAmount());
        holder.tvName.setText(dienGiai);
        holder.tvAmount.setText(soTien);



        int idImage;

        if (dienGiai.equals("Đi chợ/ Siêu thị")) {
            idImage= R.drawable.market;
        } else if (dienGiai.equals("Ăn tiệm")) {

            idImage = R.drawable.fastfood;
        } else if (dienGiai.equals("Cà phê")) {

            idImage = R.drawable.coffeecup;
        } else if (dienGiai.equals("Ăn sáng")) {
            idImage = R.drawable.breakfast;
        } else if (dienGiai.equals("Ăn trưa")) {
            idImage = R.drawable.friedrice;
        } else if (dienGiai.equals("Ăn tối")) {
            idImage = R.drawable.meal;
        } else if (dienGiai.equals("Điện")) {
            idImage = R.drawable.lightning;
        } else if (dienGiai.equals("Nước")) {
            idImage = R.drawable.drop;
        } else if (dienGiai.equals("Wifi")) {
            idImage = R.drawable.wifi;
        } else if (dienGiai.equals("Gas")) {
            idImage = R.drawable.gas;
        } else if (dienGiai.equals("Điện thoại di động")) {
            idImage = R.drawable.smartphone;
        }
        else if (dienGiai.equals("Xăng")) {
            idImage = R.drawable.gasfuel;
        } else if (dienGiai.equals("Bảo hiểm")) {
            idImage = R.drawable.nsurance;
        } else if (dienGiai.equals("Gửi xe")) {
            idImage = R.drawable.parkinglot;
        }
        else if (dienGiai.equals("Thuê xe")) {
            idImage = R.drawable.taxi;
        }
        else if (dienGiai.equals("Học phí")) {
            idImage = R.drawable.fees;
        } else if (dienGiai.equals("Sữa")) {
            idImage = R.drawable.milk;
        } else if (dienGiai.equals("Đồ chơi")) {
            idImage = R.drawable.toys;
        }
        else if (dienGiai.equals("Tiền tiêu vặt")) {
            idImage = R.drawable.money;
        }
        else if (dienGiai.equals("Quần áo")) {
            idImage = R.drawable.laundry;
        } else if (dienGiai.equals("Giày dép")) {
            idImage = R.drawable.sneakers;
        } else if (dienGiai.equals("Phụ kiện")) {
            idImage = R.drawable.diamondring;
        }else if (dienGiai.equals("Thăm hỏi")) {
            idImage = R.drawable.childrent;
        }else if (dienGiai.equals("Biếu tặng")) {
            idImage = R.drawable.love;
        }else if (dienGiai.equals("Khám chữa bệnh")) {
            idImage = R.drawable.medicalteam;
        }else if (dienGiai.equals("Thuốc men")) {
            idImage = R.drawable.drugs;
        }else if (dienGiai.equals("Thể thao")) {
            idImage = R.drawable.sports;
        }else if (dienGiai.equals("Mua sắm nội thất")) {
            idImage = R.drawable.seatersofa;
        }else if (dienGiai.equals("Thuê nhà")) {
            idImage = R.drawable.building;
        }else if (dienGiai.equals("Du lịch")) {
            idImage = R.drawable.hawaiianshirt;
        }else if (dienGiai.equals("Vui chơi giải trí")) {
            idImage = R.drawable.karaoke;
        }else if (dienGiai.equals("Phim ảnh, ca nhạc")) {
            idImage = R.drawable.popcorn;
        }else if (dienGiai.equals("Học hành")) {
            idImage = R.drawable.read;
        }else if (dienGiai.equals("Giao lưu")) {
            idImage = R.drawable.friendship;
        }else if (dienGiai.equals("Spa & Massage")) {
            idImage = R.drawable.massage;
        }else if (dienGiai.equals("Chó")) {
            idImage = R.drawable.dog;
        }else if (dienGiai.equals("Mèo")) {
            idImage = R.drawable.cat;
        }
        else if (dienGiai.equals("Tiền chúc mừng")) {
            idImage = R.drawable.giving;
        }
        else if (dienGiai.equals("Tiền lãi")) {
            idImage = R.drawable.interest;
        } else if (dienGiai.equals("Học bổng")) {
            idImage = R.drawable.scholarship;
        } else if (dienGiai.equals("Tiền trợ cấp")) {
            idImage = R.drawable.generous;
        }
        else if (dienGiai.equals("Kinh doanh riêng")) {
            idImage = R.drawable.business;
        }else if (dienGiai.equals("Thưởng")) {
            idImage = R.drawable.bonus;
        }else if (dienGiai.equals("Lương")) {
            idImage = R.drawable.salary;
        }

        else {
            idImage = R.drawable.plus;
        }

        holder.imgChiTietThuNhi.setImageResource(idImage);

    }

    @Override
    public int getItemCount() {
        return childList.size();
    }
    public class ChiTietGiaoDichNgayViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAmount;
        private ImageView imgChiTietThuNhi;

        public ChiTietGiaoDichNgayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            imgChiTietThuNhi = itemView.findViewById(R.id.imgChiTietThuNhi);
        }
    }
}
