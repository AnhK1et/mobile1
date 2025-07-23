package com.example.anhkiet3;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.bumptech.glide.Glide;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    public interface OnCartActionListener {
        void onQuantityChanged();
        void onItemRemoved(int position);
    }

    private List<Product> cartList;
    private OnCartActionListener listener;

    public CartAdapter(List<Product> cartList, OnCartActionListener listener) {
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartList.get(position);
        String name = (product.getName() != null && !product.getName().isEmpty()) ? product.getName() : product.getTitle();
        holder.tvProductName.setText(name);
        String price = (product.getPrice() != null && !product.getPrice().isEmpty()) ? product.getPrice() : String.valueOf(product.getPriceDouble());
        holder.tvProductPrice.setText(price);
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            Glide.with(holder.imgProduct.getContext()).load(product.getImage()).into(holder.imgProduct);
        } else {
            holder.imgProduct.setImageResource(product.getImageResId());
        }
        // Giá gốc: demo lấy từ product.getOldPrice() hoặc hardcode
        holder.tvProductOldPrice.setText(product.getOldPrice() != null ? product.getOldPrice() : "");
        holder.tvProductOldPrice.setPaintFlags(holder.tvProductOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        // Số lượng
        holder.tvQuantity.setText(String.valueOf(product.getQuantity()));
        // Tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            product.setQuantity(product.getQuantity() + 1);
            notifyItemChanged(position);
            if (listener != null) listener.onQuantityChanged();
        });
        // Giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                notifyItemChanged(position);
                if (listener != null) listener.onQuantityChanged();
            }
        });
        // Xóa sản phẩm
        holder.btnDelete.setOnClickListener(v -> {
            cartList.remove(position);
            notifyItemRemoved(position);
            if (listener != null) listener.onItemRemoved(position);
        });
        // Chọn khuyến mãi
        holder.radioGroupPromo.setOnCheckedChangeListener(null);
        if (product.getPromoIndex() == 0) {
            holder.radioPromo1.setChecked(true);
        } else if (product.getPromoIndex() == 1) {
            holder.radioPromo2.setChecked(true);
        } else {
            holder.radioGroupPromo.clearCheck();
        }
        holder.radioGroupPromo.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == holder.radioPromo1.getId()) {
                product.setPromoIndex(0);
            } else if (checkedId == holder.radioPromo2.getId()) {
                product.setPromoIndex(1);
            } else {
                product.setPromoIndex(-1);
            }
        });
        CheckBox checkboxItem = holder.checkboxItem;
        checkboxItem.setChecked(product.isSelected());
        checkboxItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            product.setSelected(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductPrice, tvProductOldPrice, tvQuantity;
        ImageButton btnDelete, btnIncrease, btnDecrease;
        RadioGroup radioGroupPromo;
        RadioButton radioPromo1, radioPromo2;
        CheckBox checkboxItem;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductOldPrice = itemView.findViewById(R.id.tvProductOldPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            radioGroupPromo = itemView.findViewById(R.id.radioGroupPromo);
            radioPromo1 = itemView.findViewById(R.id.radioPromo1);
            radioPromo2 = itemView.findViewById(R.id.radioPromo2);
            checkboxItem = itemView.findViewById(R.id.checkboxItem);
        }
    }
} 