//package me.srichomthong.savetogether.customer.viewholder;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.jaylerrs.bikesquad.R;
//import com.jaylerrs.bikesquad.events.models.Post;
//
//
//public class FavoritViewHolder extends RecyclerView.ViewHolder {
//
//    public ImageView authorImage;
//    public TextView saleTitle;
//    public TextView authorView;
//    public ImageView starView;
//    public TextView numStarsView;
//    public TextView salePrice;
//    public TextView saleDiscount;
//
//    public FavoritViewHolder(View itemView) {
//        super(itemView);
//        authorImage = (ImageView) itemView.findViewById(R.id.post_author_photo);
//        saleTitle = (TextView) itemView.findViewById(R.id.post_title);
//        authorView = (TextView) itemView.findViewById(R.id.post_author);
//        starView = (ImageView) itemView.findViewById(R.id.star);
//        numStarsView = (TextView) itemView.findViewById(R.id.route_num_stars);
//        salePrice = (TextView) itemView.findViewById(R.id.post_body);
//        saleDiscount = (TextView) itemView.findViewById(R.id.post_route);
//    }
//
//    public void bindToSale(Post post, View.OnClickListener starClickListener) {
//
//        saleTitle.setText(post.title);
//        authorView.setText(post.author);
//        numStarsView.setText(String.valueOf(post.starCount));
//        salePrice.setText(post.body);
//        saleDiscount.setText(post.name);
//
//        starView.setOnClickListener(starClickListener);
//    }
//}
