package com.jjkbashlord.menu;

import java.util.ArrayList;
import java.util.Arrays;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by JJK on 4/19/17.
 */

public class Constants {

    public static final ArrayList<String> drinkCategories = new ArrayList<String>(Arrays.asList(new String[] {"smoothies","slushes", "icedfruit","icedcoffeetea", "frappes","hot"}));
    public static final ArrayList<String> drinkKeys = new ArrayList<String>(Arrays.asList(new String[] {"Iced0","Iced1","Hot0", "Hot1", "Hot2", "Hot3","FlavoredMilkTea","Etc", "Traditional", "Frappes", "FruitTea","Slushies", "Smoothies"}));

    public static final ArrayList<Integer> drinkIndex = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12));

    public static final ArrayList<Integer> prices0 = new ArrayList<Integer>(Arrays.asList(5,8,9,10,11,12));
    public static final ArrayList<Integer> prices1 = new ArrayList<Integer>(Arrays.asList(1,6));
    public static final ArrayList<Integer> prices2 = new ArrayList<Integer>(Arrays.asList(0,2));
    public static final ArrayList<Integer> prices3 = new ArrayList<Integer>(Arrays.asList(3));
    public static final ArrayList<Integer> prices4 = new ArrayList<Integer>(Arrays.asList(4));

    public static final ArrayList<String> drinkImgUrls = new ArrayList<String>(Arrays.asList(new String[] {"almond","kiwi","peanutbutter","americano","lemonade","pineapple","banana","lychee","pistachio","blacktea","mango","raspberry","cafelatte","matcha","redbean","cafemocha","melon","strawberry","caramel","milktea","taro","caramelmacchiato","mintchocolatechip","thaitea","chai","mixedgrain","vanilla","coconut","mocha","watermelon","coffee","oreo","whitecafemocha","greenapple","passionfruit", "yogurt", "peach","greentea","cap","coco", "seng","ginger", "juju","jobs", "citron", "hazel"}));
}
