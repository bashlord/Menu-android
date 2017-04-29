package com.jjkbashlord.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jjkbashlord.menu.Adapters.RecyclerAdapter;
import com.jjkbashlord.menu.Animations.ResizeAnimation;
import com.jjkbashlord.menu.CustomViews.CustomImageButton;
import com.jjkbashlord.menu.CustomViews.OutlinedTextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String fontPath_Helve = "fonts/HelveticaNeue-Bold.ttf";
    String fontPath_HelveNueve = "fonts/helvetica-condensed-black.ttf";

    TextView textView0, textView1,backgroundTextView;
    OutlinedTextView topTextView;
    Typeface tf0,tf1;

    CustomImageButton bLeft, bRight;
    float width, height, dpHeight;
    int textViewPagerWidth, textViewFlag;

    ConstraintLayout constraintLayout;
    private ConstraintLayout.LayoutParams rightLayoutParam, leftLayoutParam;
    private ConstraintSet applyConstraintSet = new ConstraintSet();
    private ConstraintSet resetConstraintSet = new ConstraintSet();

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public ArrayList<ArrayList<BobaDrink>> drinks;
    Map<String,Integer> imageToId =  new HashMap<String,Integer>();
    Map<String,Integer> drinkToImg =  new HashMap<String,Integer>();
    public int currPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = new ConstraintLayout(this);
        constraintLayout = (ConstraintLayout)  findViewById(R.id.activity_main);
        resetConstraintSet.clone(constraintLayout);
        applyConstraintSet.clone(constraintLayout);

        // if textViewFlag == 0, currently used textview is 0
        //   and 1 needs to be used for the transition animation
        currPage = textViewFlag = 0;
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels; //displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        topTextView = (OutlinedTextView) findViewById(R.id.topTextView);
        backgroundTextView = (TextView) findViewById(R.id.backgroundTextView);
        bLeft = (CustomImageButton) findViewById(R.id.leftButton);
        bRight = (CustomImageButton)  findViewById(R.id.rightButton);
        textView0 = (TextView) findViewById(R.id.textView0);
        // save the value for future pages
        textViewPagerWidth = (int) (width-(2*Constants.convertDpToPixel(50, this)));
        textView0.getLayoutParams().width = textViewPagerWidth;
        textView0.requestLayout();
        textView1 = (TextView)findViewById(R.id.textView1);
        dpHeight = Constants.convertDpToPixel(50, this);

        bLeft.setOnClickListener(this);
        bRight.setOnClickListener(this);
        tf0 = Typeface.createFromAsset(getAssets(), fontPath_Helve);
        tf1 = Typeface.createFromAsset(getAssets(), fontPath_HelveNueve);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 2);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        drinks = new ArrayList<ArrayList<BobaDrink>>();
        for(int i = 0; i < 6; i++)
            drinks.add(new ArrayList<BobaDrink>());

        setImageIds();
        initPhotos();
        adapter = new RecyclerAdapter(drinks, tf1,this);
        recyclerView.setAdapter(adapter);

        textView0.setText( Constants.titles.get(currPage) );
        topTextView.setTextSize(50);

        rightLayoutParam = (ConstraintLayout.LayoutParams) bRight.getLayoutParams();
        leftLayoutParam = (ConstraintLayout.LayoutParams) bLeft.getLayoutParams();
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
                    pageLabel(0);
                }
                break;
            case R.id.rightButton:
                temp = adapter.pageRight();
                if(temp != currPage){
                    currPage = temp;
                    adapter.notifyDataSetChanged();
                    pageLabel(1);
                }
                break;
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
            drinks.get(0).add(new BobaDrink(str, small,med, drinkToImg.get(str)));
        }

        // 1
        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: slushies){
            drinks.get(1).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: frappes){
            drinks.get(1).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        // 2
        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: fruitTea){
            drinks.get(2).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        // 3
        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: flavorMilkTea){
            drinks.get(3).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(2,1);
        small = priceForFlag(2,0);
        for(String str: iced0){
            drinks.get(3).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(1,1);
        small = priceForFlag(1,0);
        for(String str: iced1){
            drinks.get(3).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        // 4
        med = priceForFlag(2,1);
        small = priceForFlag(2,0);
        for(String str: hot0){
            drinks.get(4).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(3,1);
        small = priceForFlag(3,0);
        for(String str: hot1){
            drinks.get(4).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }
        med = priceForFlag(4,1);
        small = priceForFlag(4,0);
        for(String str: hot2){
            drinks.get(4).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }

        med = priceForFlag(0,1);
        small = priceForFlag(0,0);
        for(String str: hot3){
            drinks.get(4).add(new BobaDrink(str, small,med, drinkToImg.get(str) ));
        }
    }

    public void setImageIds(){
        Context context = getApplicationContext();
        int index = 0;
        // mapping url, which is the .jpg filenames, to the resource int id
        for(String url: Constants.drinkImgUrls ) {
            int id = context.getResources().getIdentifier(url, "drawable", context.getPackageName());
            imageToId.put(url,id);
            index++;
        }
        // mapping the drinks to their categories
        String categoryName = "";
        TypedArray images = context.getResources().obtainTypedArray(R.array.images);
        int n = images.length();
        for (int i = 0; i < n; ++i) {
            int id = images.getResourceId(i, 0);
            if (id > 0) {
                String[] arr = context.getResources().getStringArray(id);
                categoryName = (context.getResources().getResourceName(id)).split("/")[1];
                for(String s: arr){
                    drinkToImg.put(s, imageToId.get(categoryName) );
                }
            }
        }
        images.recycle();
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////  pager left<->right    //////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void pageLabel(final int flag){
        // flag == 0 paging from the left
        // flag == 1 paging from the right

        if(textViewFlag == 0){
            if(flag == 0) {//  page left ->>>>>
                unbindConstraint(0);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(R.id.textView1, ConstraintSet.TOP, R.id.topTextView, ConstraintSet.BOTTOM, 0);
                constraintSet.connect(R.id.textView1, ConstraintSet.LEFT, R.id.leftButton, ConstraintSet.RIGHT, 0);
                constraintSet.connect(R.id.textView1, ConstraintSet.RIGHT, R.id.textView0, ConstraintSet.LEFT, 0);

                constraintSet.connect(R.id.textView0, ConstraintSet.RIGHT, R.id.rightButton, ConstraintSet.LEFT, 0);
                constraintSet.applyTo(constraintLayout);
            }else {// page from rightside <<<----
                unbindConstraint(1);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(R.id.textView1, ConstraintSet.TOP, R.id.topTextView, ConstraintSet.BOTTOM, 0);
                constraintSet.connect(R.id.textView1, ConstraintSet.RIGHT, R.id.rightButton, ConstraintSet.LEFT, 0);
                constraintSet.connect(R.id.textView1, ConstraintSet.LEFT, R.id.textView0, ConstraintSet.RIGHT, 0);

                constraintSet.connect(R.id.textView0, ConstraintSet.LEFT, R.id.leftButton, ConstraintSet.RIGHT, 0);
                constraintSet.applyTo(constraintLayout);
            }

            ViewGroup.LayoutParams lp = textView0.getLayoutParams();
            ResizeAnimation a = new ResizeAnimation(textView0);
            a.setDuration(500);
            a.setParams(lp.width,0);
            textView0.startAnimation(a);
            textView0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ConstraintSet constraintSet1 = new ConstraintSet();
                    constraintSet1.clone(constraintLayout);
                    constraintSet1.clear(R.id.textView0);
                    textView0.setText("");
                    if(flag == 0)
                        constraintSet1.connect(R.id.textView1, ConstraintSet.RIGHT, R.id.rightButton, ConstraintSet.LEFT, 0);
                    else
                        constraintSet1.connect(R.id.textView1, ConstraintSet.LEFT, R.id.leftButton, ConstraintSet.RIGHT, 0);
                    constraintSet1.applyTo(constraintLayout);
                    textView1.getLayoutParams().width = textViewPagerWidth;
                    textView1.requestLayout();
                }
            }, 500);
            textView1.setText(Constants.titles.get(currPage));
            textViewFlag = 1;
        }else{
            if(flag == 0) {//  page left
                unbindConstraint(0);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(R.id.textView0, ConstraintSet.TOP,R.id.topTextView, ConstraintSet.BOTTOM, 0);
                constraintSet.connect(R.id.textView0, ConstraintSet.LEFT, R.id.leftButton, ConstraintSet.RIGHT, 0);
                constraintSet.connect(R.id.textView0, ConstraintSet.RIGHT, R.id.textView1, ConstraintSet.LEFT, 0);

                constraintSet.connect(R.id.textView1, ConstraintSet.RIGHT, R.id.rightButton, ConstraintSet.LEFT, 0);
                constraintSet.applyTo(constraintLayout);
            }else {// page from rightside

                unbindConstraint(1);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(R.id.textView0, ConstraintSet.TOP,R.id.topTextView, ConstraintSet.BOTTOM, 0);
                constraintSet.connect(R.id.textView0, ConstraintSet.RIGHT, R.id.rightButton, ConstraintSet.LEFT, 0);
                constraintSet.connect(R.id.textView0, ConstraintSet.LEFT, R.id.textView1, ConstraintSet.RIGHT, 0);

                constraintSet.connect(R.id.textView1, ConstraintSet.LEFT, R.id.leftButton, ConstraintSet.RIGHT, 0);
                constraintSet.applyTo(constraintLayout);
            }

            ViewGroup.LayoutParams lp = textView1.getLayoutParams();
            ResizeAnimation a = new ResizeAnimation(textView1);
            a.setDuration(500);
            a.setParams(lp.width,0);
            textView1.startAnimation(a);
            textView1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ConstraintSet constraintSet1 = new ConstraintSet();
                    constraintSet1.clone(constraintLayout);
                    constraintSet1.clear(R.id.textView1);
                    textView1.setText("");
                    if(flag == 0)
                        constraintSet1.connect(R.id.textView0, ConstraintSet.RIGHT, R.id.rightButton, ConstraintSet.LEFT, 0);
                    else
                        constraintSet1.connect(R.id.textView0, ConstraintSet.LEFT, R.id.leftButton, ConstraintSet.RIGHT, 0);
                    constraintSet1.applyTo(constraintLayout);
                    textView0.getLayoutParams().width = textViewPagerWidth;
                    textView0.requestLayout();
                }
            }, 500);

            textView0.setText(Constants.titles.get(currPage));
            textViewFlag = 0;
        }
    }

    public void unbindConstraint(int flag0){
        // unbind constraints on the textView coming in
        if(textViewFlag == 1){
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView0.getLayoutParams();
            layoutParams.rightToLeft = ConstraintLayout.LayoutParams.UNSET;
            layoutParams.leftToRight = ConstraintLayout.LayoutParams.UNSET;
            textView0.setLayoutParams(layoutParams);

            // flag that determines which way the page is animating
            // flag0 == 0  page left  ->>
            // flag0 == 1  page right <<-
            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) textView1.getLayoutParams();
            if( flag0 == 0){
                layoutParams1.leftToRight = ConstraintLayout.LayoutParams.UNSET;
                layoutParams1.rightToLeft = rightLayoutParam.leftMargin;
                textView1.setLayoutParams(layoutParams1);
            }else{
                layoutParams1.leftToRight = leftLayoutParam.rightMargin;
                layoutParams1.rightToLeft = ConstraintLayout.LayoutParams.UNSET;
            }
            textView1.setLayoutParams(layoutParams1);
        }else{

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView1.getLayoutParams();
            layoutParams.rightToLeft = ConstraintLayout.LayoutParams.UNSET;
            layoutParams.leftToRight = ConstraintLayout.LayoutParams.UNSET;
            textView1.setLayoutParams(layoutParams);

            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) textView0.getLayoutParams();

            if( flag0 == 0){
                layoutParams1.leftToRight = ConstraintLayout.LayoutParams.UNSET;
                layoutParams1.rightToLeft = rightLayoutParam.leftMargin;
            }else{
                layoutParams1.leftToRight = leftLayoutParam.rightMargin;
                layoutParams1.rightToLeft = ConstraintLayout.LayoutParams.UNSET;
            }

            textView0.setLayoutParams(layoutParams1);
        }
    }

    public String String(int i){
        return Integer.toString(i);
    }
    public String String(float i){
        return Float.toString(i);
    }
}
