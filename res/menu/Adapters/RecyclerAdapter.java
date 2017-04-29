package com.jjkbashlord.menu.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jjkbashlord.menu.BobaDrink;
import com.jjkbashlord.menu.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PhotoHolder> {
  public ArrayList<ArrayList<BobaDrink>> bobas;
  Context context;
  int currPage, numOfPages;
  Typeface tf;

  public static class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mItemImage;
    private TextView mItemDate;
    private TextView mItemDescription;
    public BobaDrink drink;

    public PhotoHolder(View v) {
      super(v);
      mItemImage = (ImageView) v.findViewById(R.id.item_image);
      mItemDate = (TextView) v.findViewById(R.id.item_date);
      mItemDescription = (TextView) v.findViewById(R.id.item_description);
      mItemDate.setTextColor(Color.argb(255, 255, 255, 255));
      mItemDescription.setTextColor(Color.argb(255, 255, 255, 255));
      v.setOnClickListener(this);
    }

    //5
    @Override
    public void onClick(View v) {
      Context context = itemView.getContext();

    }

    public void bindPhoto(BobaDrink photo, Context c, Typeface tf) {
      drink = photo;
      //Image
      mItemDate.setTextSize(22);
      mItemDate.setText( "$"+photo.getSmall()+"/$"+photo.getMed());
      mItemDescription.setTypeface(tf);
      if(photo.getName().equals("Cappuccino"))
        mItemDescription.setTextSize(45);
      else
        mItemDescription.setTextSize(50);
      mItemDescription.setText(photo.getName());
      Glide.with(c).load(photo.getImageId()).diskCacheStrategy(DiskCacheStrategy.ALL)
              .fitCenter()
              .override(500,500)
              .into(mItemImage);
    }
  }

  public RecyclerAdapter(ArrayList<ArrayList<BobaDrink>> photos, Typeface tff, Context c) {
    bobas = photos;
    context = c;
    currPage = 0;
    numOfPages = 5;
    tf = tff;
  }

  @Override
  public RecyclerAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflatedView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.recyclerview_item_row, parent, false);
    return new PhotoHolder(inflatedView);
  }

  @Override
  public void onBindViewHolder(RecyclerAdapter.PhotoHolder holder, int position) {
    BobaDrink b = bobas.get(currPage).get(position);
    holder.bindPhoto(b, context, tf);
  }

  @Override
  public int getItemCount() {
    return bobas.get(currPage).size();
  }

  public int pageLeft(){
    if(currPage > 0){
      currPage--;
    }
    return currPage;
  }

  public int pageRight(){
    if(currPage < numOfPages-1){
      currPage++;
    }
    return  currPage;
  }
}
