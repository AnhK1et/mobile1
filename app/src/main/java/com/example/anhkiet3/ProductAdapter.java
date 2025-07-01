package com.example.anhkiet3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private boolean showAddToCartButton = true;
    private OnAddToCartListener addToCartListener;
    private OnItemClickListener itemClickListener;

    public interface OnAddToCartListener {
        void onAddToCart(Product product);
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
        this.showAddToCartButton = true;
    }

    public ProductAdapter(List<Product> productList, boolean showAddToCartButton) {
        this.productList = productList;
        this.showAddToCartButton = showAddToCartButton;
    }

    public void setOnAddToCartListener(OnAddToCartListener listener) {
        this.addToCartListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(product.getPrice());
        holder.imgProduct.setImageResource(product.getImageResId());
        if (showAddToCartButton) {
            holder.btnAddToCart.setVisibility(View.VISIBLE);
            holder.btnAddToCart.setOnClickListener(v -> {
                if (addToCartListener != null) addToCartListener.onAddToCart(product);
            });
        } else {
            holder.btnAddToCart.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductPrice;
        ImageButton btnAddToCart;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
} 