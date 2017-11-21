package me.srichomthong.savetogether.center.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import me.srichomthong.savetogether.R;
import me.srichomthong.savetogether.center.model.Sale;


public class SaleViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView authorImage;
    public TextView authorName;

    public TextView numStarsView;
    public ImageView saleStar;
    public RelativeLayout starView;

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
        starView = itemView.findViewById(R.id.star_layout);

        saleImage = itemView.findViewById(R.id.sale_item_photo);
        saleTitle = itemView.findViewById(R.id.sale_item_title);
        salePrice = itemView.findViewById(R.id.sale_item_price);
        saleDiscount = itemView.findViewById(R.id.sale_item_discount);
        saleEndTime = itemView.findViewById(R.id.sale_item_end_sale);
        saleDetail = itemView.findViewById(R.id.sale_item_body);
    }

    public void bindToSale(Sale sale, View.OnClickListener startClickListener) {
        authorName.setText(sale.saleShopName);
        numStarsView.setText(String.valueOf(sale.starCount));

        saleTitle.setText("Product : " + sale.saleName);
        salePrice.setText("Price : " + String.valueOf(sale.saleFullPrice) + " Baht => " + String.valueOf(sale.salePrice) + "Baht");

        float dis = 100 - (int) (((float) sale.salePrice / (float) sale.saleFullPrice )* (float) 100);
        saleDiscount.setText("Discounted : " + String.valueOf(dis).concat("%"));
        saleEndTime.setText("End sale : " + String.valueOf(sale.timeRange));
        saleDetail.setText(sale.saleDetail);

        starView.setOnClickListener(startClickListener);
    }
}
