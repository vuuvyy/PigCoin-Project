package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ClassVi;
import com.example.myapplication.R;

import java.math.BigDecimal;
import java.util.List;

public class LoaiViGiaoDichAdapter extends RecyclerView.Adapter<LoaiViGiaoDichAdapter.LoaiViGDViewHolder> {
    private List<ClassVi> loaiViList;

    TextView tvTen;
    TextView tvTien;
    int idImage;

    public LoaiViGiaoDichAdapter(List<ClassVi> loaiViList) {
        this.loaiViList = loaiViList;
    }

    @NonNull
    @Override
    public LoaiViGDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo view holder bằng cách inflate layout cho view holder từ file XML
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaivi_giaodich, parent, false);
        return new LoaiViGDViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull LoaiViGDViewHolder holder, int position) {
        // Lấy giá trị tienTrongVi của đối tượng ClassVi tại vị trí position
        BigDecimal tienTrongVi = loaiViList.get(position).getTienTrongVi();

        // Lấy đối tượng ClassLoaiVi từ danh sách dựa vào vị trí
        ClassVi loaiVi = loaiViList.get(position);

        // Cập nhật dữ liệu cho các thành phần trong view holder
        //holder.textView.setText(loaiVi.getTenLoaiVi());
        String tenLoaiVi = loaiVi.getTenLoaiVi();
        int idImg;
        if (tenLoaiVi.equals("Ví tiền mặt")) {
            idImg = R.drawable.wallet;
        } else {
            idImg = R.drawable.credit;
        }
        BigDecimal sotien = loaiVi.getTienTrongVi();
        String tenVi = loaiVi.getTenVi();
        // Hiển thị hình ảnh và chữ của loại hạng mục
        holder.imgLoaiVi.setImageResource(idImg);
        holder.tvTen.setText(tenVi);
        holder.tvTien.setText(sotien.toString());

        // Định dạng giá trị tienTrongVi và hiển thị lên ViewHolder
        holder.tvTien.setText(loaiViList.get(position).getFormattedTienTrongVi());

        // Gán sự kiện click cho item (holder.itemView)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tên ví của item tại vị trí đã click
                int clickedPosition = holder.getAdapterPosition();
                String tenVi = loaiViList.get(clickedPosition).getTenVi();

                // Kiểm tra xem listener sự kiện click đã được gán và gọi nó
                if (viItemClickListener != null) {
                    viItemClickListener.onViItemClick(tenVi);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return loaiViList.size();
    }

    // Tạo lớp LoaiViGDViewHolder bên trong lớp LoaiViGiaoDichAdapter
    public class LoaiViGDViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLoaiVi;
        private TextView tvTen, tvTien;

        public LoaiViGDViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các thành phần trong view holder từ itemView
            imgLoaiVi = itemView.findViewById(R.id.imgLoaiVi);
            tvTen = itemView.findViewById(R.id.tvTen);  //tên loại ví
            tvTien = itemView.findViewById(R.id.tvTien);
        }
    }
    // Tạo một biến để lưu trữ listener sự kiện click của item
    private OnViItemClickListener viItemClickListener;

    // Tạo một phương thức setter để gán listener sự kiện click cho item
    public void setOnViItemClickListener(OnViItemClickListener listener) {
        this.viItemClickListener = listener;
    }

    // Interface cho sự kiện click của item
    public interface OnViItemClickListener {
        void onViItemClick(String tenVi);
    }
}