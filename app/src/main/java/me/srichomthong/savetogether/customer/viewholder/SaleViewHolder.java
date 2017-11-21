package me.srichomthong.savetogether.customer.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.center.model.Sale;


public class SaleViewHolder extends RecyclerView.ViewHolder {

    public ImageView authorImage;
    public TextView authorName;

    public TextView numStarsView;
    public ImageView saleStar;

    public ImageView saleImage;
    public TextView saleTitle;
    public TextView salePrice;
    public TextView saleDiscount;
    public TextView saleEndTime;
    public TextView saleDetail;

    public SaleViewHolder(View itemView) {
        super(itemView);
        authorImage = itemView.findViewById(R.id.post_author_photo);
        authorName = itemView.findViewById(R.id.sale_author_name);

        saleStar = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.route_num_stars);

        saleImage = itemView.findViewById(R.id.sale_item_photo);
        saleTitle = itemView.findViewById(R.id.sale_item_title);
        salePrice = itemView.findViewById(R.id.sale_item_price);
        saleDiscount = itemView.findViewById(R.id.sale_item_discount);
        saleEndTime = itemView.findViewById(R.id.sale_item_end_sale);
        saleDetail = itemView.findViewById(R.id.sale_item_body);
    }

    public void bindToPost(Sale sale, View.OnClickListener starClickListener) {
        authorName.setText(sale.saleShop);

        numStarsView.setText(String.valueOf(sale.starCount));

        saleTitle.setText(sale.saleName);
        salePrice.setText(sale.salePrice);

        float dis = (sale.salePrice / sale.saleFullPrice) * 100;
        saleDiscount.setText(String.valueOf(dis).concat("%"));
        saleEndTime.setText(String.valueOf(sale.timeRange));
        saleDetail.setText(sale.saleDetail);
    }
}
