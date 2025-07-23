package com.example.anhkiet3;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Button;
public class OrderHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        ListView lvOrders = findViewById(R.id.lvOrders);
        Button btnBack = findViewById(R.id.btnBackOrderHistory);
        btnBack.setOnClickListener(v -> finish());
        // Dữ liệu mẫu
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, "01/07/2024", 2000000, java.util.Arrays.asList("iPhone 15 Pro Max", "Samsung S24 Ultra")));
        orders.add(new Order(2, "15/06/2024", 1500000, java.util.Arrays.asList("Xiaomi 14T Pro")));
        orders.add(new Order(3, "01/06/2024", 3000000, java.util.Arrays.asList("Oppo A78", "iPhone 14 Plus")));
        lvOrders.setAdapter(new OrderAdapter(orders));
    }
}
// Custom Adapter
class OrderAdapter extends BaseAdapter {
    private List<Order> orders;
    public OrderAdapter(List<Order> orders) { this.orders = orders; }
    @Override public int getCount() { return orders.size(); }
    @Override public Object getItem(int i) { return orders.get(i); }
    @Override public long getItemId(int i) { return orders.get(i).getId(); }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);
        Order order = orders.get(position);
        text1.setText("Đơn #" + order.getId() + " - " + order.getDate() + " - " + String.format("%,.0f", order.getTotal()) + "đ");
        text2.setText("Sản phẩm: " + android.text.TextUtils.join(", ", order.getProducts()));
        return convertView;
    }
} 