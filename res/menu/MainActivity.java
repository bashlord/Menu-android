package com.jjkbashlord.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String fontPath_Helve = "fonts/HelveticaNeue-Bold.ttf";
    TextView textView0, textView1, topTextView,backgroundTextView;
    CustomImageButton bLeft, bRight;

    private ArrayList<Photo> mPhotosList;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter adapter;

    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;


    public ArrayList<ArrayList<BobaDrink>> drinks;
    Map<String,Integer> imageToId =  new HashMap<String,Integer>();
    Map<String,Integer> drinkToImg =  new HashMap<String,Integer>();
    Map<String,Bitmap> drinkToBits =  new HashMap<String,Bitmap>();
    public int currPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currPage = 0;
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        topTextView = (TextView) findViewById(R.id.topTextView);
        backgroundTextView = (TextView) findViewById(R.id.backgroundTextView);
        textView0 = (TextView) findViewById(R.id.textView0);
        bLeft = (CustomImageButton) findViewById(R.id.leftButton);
        bRight = (CustomImageButton)  findViewById(R.id.rightButton);

        bLeft.setOnClickListener(this);
        bRight.setOnClickListener(this);
        topTextView.setTypeface( getHelve() );
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //recyclerView.setLayoutManager(linearLayoutManager);

        mPhotosList = new ArrayList<>();
        drinks = new ArrayList<ArrayList<BobaDrink>>();
        for(int i = 0; i < 6; i++)
            drinks.add(new ArrayList<BobaDrink>());

        setImageIds();
        initPhotos();
        //Log.d("JJK", "number of pages in drinks " + Integer.toString( drinks.size()) );

        setRecyclerViewScrollListener();
        setRecyclerViewItemTouchListener();

        adapter = new RecyclerAdapter(drinks, this);
        recyclerView.setAdapter(adapter);
        //recyclerView.
        //recyclerView.
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                /*if (!mImageRequester.isLoadingData() && totalItemCount == getLastVisibleItemPosition() + 1) {
                    requestPhoto();
                }*/
            }
        });
    }

    private void setRecyclerViewItemTouchListener() {

        //1
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                //2
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //3
                int position = viewHolder.getAdapterPosition();
                mPhotosList.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);
            }
        };

        //4
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public Typeface getHelve(){
        //Typeface tf = Typeface.createFromAsset(getAssets(), fontPath_Helve);
        return Typeface.createFromAsset(getAssets(), fontPath_Helve);
    }

    public int getDrinkPageIndex(String name){
        if(name == "Smoothies"){
            return 0;
        }else if( name == "Slushies"){
            return 1;
        }else if (name == "FruitTea"){
            return 2;
        }else if (name == "FlavorMilkTea" || (name == "Iced0") || name == "Iced1"){
            return 3;
        }else if( name == "Frappes"){
            return 4;
        }else{
            return 5;
        }
    }

    public Double priceForFlag(int flag, int size){
        if(flag == 4){
            if (size == 0) {
                return 3.49;
            }else{
                return 3.99;
            }
        }else if(flag == 3){
            if (size == 0) {
                return 2.99;
            }else{
                return 3.49;
            }
        }else if(flag == 1){
            if (size == 0) {
                return 3.49;
            }else{
                return 4.49;
            }
        }else if(flag == 0){
            if (size == 0) {
                return 3.99;
            }else{
                return 4.99;
            }
        }else if(flag == 2){
            if (size == 0) {
                return 2.99;
            }else{
                return 3.89;
            }
        }
        return -1.0;
    }

    public void initPhotos(){
        //page index 0
        String[] smoothies = getResources().getStringArray(R.array.Smoothies);

        //page index 1
        String[] slushies = getResources().getStringArray(R.array.Slushies);
        String[] frappes = getResources().getStringArray(R.array.Frappes);

        //pageindex 2
        String[] fruitTea = getResources().getStringArray(R.array.FruitTea);

        //pageindex3
        String[] flavorMilkTea = getResources().getStringArray(R.array.FlavorMilkTea);
        String[] iced0 = getResources().getStringArray(R.array.Iced0);
        String[] iced1 = getResources().getStringArray(R.array.Iced1);

        //pageIndex 4
        String[] hot0 = getResources().getStringArray(R.array.Hot0);
        String[] hot1 = getResources().getStringArray(R.array.Hot1);
        String[] hot2 = getResources().getStringArray(R.array.Hot2);
        String[] hot3 = getResources().getStringArray(R.array.Hot3);

        /*
            0 drinkPage  Hot3 Traditional Frappes FruitTea Slushies Smoothies
            1 drinkPage  Iced1 FlavoredMilkTea
            2 drinkPage  Iced0 Hot0
            3 drinkPage  Hot1
            4 drinkPage  Hot2
        * */
        Double med, small;
        // 0
        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: smoothies){
            //Log.d("JJK", str);
            drinks.get(0).add(new BobaDrink(str, small,med, drinkToImg.get(str)));
        }

        // 1
        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: slushies){
            //Log.d("JJK", str);
            drinks.get(1).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: frappes){
            //Log.d("JJK", str);
            drinks.get(1).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        // 2
        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: fruitTea){
            //Log.d("JJK", str);
            drinks.get(2).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        // 3
        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: flavorMilkTea){
            //Log.d("JJK", str);
            drinks.get(3).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(2,1);
        small = priceForFlag(2,0);
        for(String str: iced0){
            //Log.d("JJK", str);
            drinks.get(3).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(1,1);
        small = priceForFlag(1,0);
        for(String str: iced1){
            //Log.d("JJK", str);
            drinks.get(3).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        // 4
        med = priceForFlag(2,1);
        small = priceForFlag(2,0);
        for(String str: hot0){
            //Log.d("JJK", str);
            drinks.get(4).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(3,1);
        small = priceForFlag(3,0);
        for(String str: hot1){
            //Log.d("JJK", str);
            drinks.get(4).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }
        med = priceForFlag(4,1);
        small = priceForFlag(4,0);
        for(String str: hot2){
            //Log.d("JJK", str);
            drinks.get(4).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: hot3){
            //Log.d("JJK", str);
            drinks.get(4).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        for(ArrayList<BobaDrink> list: drinks){
            Log.d("JJK", "Drink count: "+String(list.size()));
        }
    }

    public void setImageIds(){
        Context context = getApplicationContext();
        int index = 0;
        // mapping url, which is the .jpg filenames, to the resource int id
        for(String url: Constants.drinkImgUrls ) {
            int id = context.getResources().getIdentifier(url, "drawable", context.getPackageName());
            Log.d("JJK", "pathname/id: "+ url+"/"+String(id));
            //Drawable draw = context.getResources().getDrawable(id,null);
            //Bitmap anImage = scaleBitmap(((BitmapDrawable) draw).getBitmap());
            imageToId.put(url,id);
            //drinkToBits.put(url,anImage);
            index++;
        }
        Log.d("JJK", "photo ids: " + String(imageToId.size()));

        // mapping the drinks to their categories
        String categoryName = "";
        TypedArray images = context.getResources().obtainTypedArray(R.array.images);  ///R.array.images
        int n = images.length();
        for (int i = 0; i < n; ++i) {
            int id = images.getResourceId(i, 0);
            //String name = images.getRe
            if (id > 0) {
                // String extensionRemoved = filename.split("\\.")[0];
                String[] arr = context.getResources().getStringArray(id);
                categoryName = (context.getResources().getResourceName(id)).split("/")[1];
                //Log.d("JJK", String(id)+" "+ String(arr.length)+" "+categoryName );
                for(String s: arr){
                    //Log.d("JJK",s+" "+ imageToId.get(categoryName).toString());
                    drinkToImg.put(s, imageToId.get(categoryName) );
                }
            } else {
                // something wrong with the XML
                Log.d("JJK", "fail");
            }
        }
        images.recycle();
    }

    private void changeLayoutManager() {
        if (recyclerView.getLayoutManager().equals(linearLayoutManager)) {
            //1
            recyclerView.setLayoutManager(gridLayoutManager);
            //2
            if (mPhotosList.size() == 1) {
                //requestPhoto();
            }
        } else {
            //3
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    public Bitmap scaleBitmap(Bitmap bitmapOrg){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //Bitmap bitmapOrg = new BitmapDrawable(getResources(), new  ByteArrayInputStream(imageThumbnail)).getBitmap();

        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();

        float scaleWidth = metrics.scaledDensity;
        float scaleHeight = metrics.scaledDensity;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        return Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);

    }

    public String String(int i){
        return Integer.toString(i);
    }

    @Override
    public void onClick(View view) {
        int temp = -1;
        switch (view.getId()){
            case R.id.leftButton:
                temp = adapter.pageLeft();
                if(currPage != temp){
                    currPage = temp;
                    adapter.notifyDataSetChanged();
                    Log.d("JJK", "adapter refreshed, itemCount: "+String(adapter.getItemCount()) );
                }else{
                    Log.d("JJK", "adapter not refreshed, itemCount: "+String(adapter.getItemCount()) );
                }
                break;
            case R.id.rightButton:
                temp = adapter.pageRight();
                if(temp != currPage){
                    currPage = temp;
                    adapter.notifyDataSetChanged();
                    Log.d("JJK", "adapter refreshed, itemCount: "+String(adapter.getItemCount()) );
                }else{
                    Log.d("JJK", "adapter not refreshed, itemCount: "+String(adapter.getItemCount()) );
                }
                break;
        }
    }
}
