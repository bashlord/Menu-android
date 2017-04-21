package com.jjkbashlord.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mattluedke on 5/11/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PhotoHolder> {

  private ArrayList<Photo> mPhotos;
  private ArrayList<ArrayList<BobaDrink>> bobas;
  //private Map<String,Bitmap> images;
  Context context;
  int currPage, numOfPages;

  //1
  public static class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //2
    private ImageView mItemImage;
    private TextView mItemDate;
    private TextView mItemDescription;

    private Photo mPhoto;
    private BobaDrink drink;

    //private Context context;

    //3
    private static final String PHOTO_KEY = "PHOTO";

    //4
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
      Intent showPhotoIntent = new Intent(context, PhotoActivity.class);
      showPhotoIntent.putExtra(PHOTO_KEY, mPhoto);
      context.startActivity(showPhotoIntent);
    }

    public void bindPhoto(BobaDrink photo, Context c) {
      drink = photo;

      //Image
      //Picasso.with(mItemImage.getContext()).load(photo.getUrl()).into(mItemImage);
      mItemDate.setText( photo.getSmall()+"/"+photo.getMed());
      mItemDescription.setText(photo.getName());
      Glide.with(c).load(photo.getImageId()).diskCacheStrategy(DiskCacheStrategy.ALL)
              .fitCenter()
              .override(500,500)
              .into(mItemImage);

      //Picasso.with(c).load(photo.getImageId()).into(mItemImage);
      //mItemImage.setImageBitmap(bm);
      //mItemImage.setImageResource( photo.getImageId());
      //mItemImage.setImageBitmap( BitmapFactory.decodeFile(  ) );
    }

  }

  public RecyclerAdapter(ArrayList<ArrayList<BobaDrink>> photos, Context c) {
    bobas = photos;
    context = c;
    currPage = 0;
    numOfPages = 5;
  }

  @Override
  public RecyclerAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflatedView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.recyclerview_item_row, parent, false);
    return new PhotoHolder(inflatedView);
  }

  @Override
  public void onBindViewHolder(RecyclerAdapter.PhotoHolder holder, int position) {
    //Photo itemPhoto = mPhotos.get(position);
    //holder.bindPhoto(itemPhoto);
    BobaDrink b = bobas.get(currPage).get(position);
    holder.bindPhoto(b, context);
    //holder.mItemImage.

    //if( holder.mItemImage.getHeight() < holder.itemView.getHeight()){
      //Log.d("JJK","item height/holder height: "+ holder.mItemImage.getHeight()+"/"+ holder.itemView.getHeight() );
      //holder.itemView.setMinimumHeight(holder.mItemImage.getHeight());
    //}
    //Log.d("JJK","item height/holder height: "+ holder.mItemImage.getHeight()+"/"+ holder.itemView.getHeight() );

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
    if(currPage < numOfPages-2){
      currPage++;
    }
    return  currPage;
  }

}
