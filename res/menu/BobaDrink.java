package com.jjkbashlord.menu;

import io.realm.RealmObject;

/**
 * Created by JJK on 4/19/17.
 */

public class BobaDrink extends RealmObject {
    private String name;
    private Double price_med, price_small;

    public BobaDrink(){
        this.name = "Boba";
        price_med = price_small =0.0;
    }

    public BobaDrink(String name, Double med, Double small){
        this.name = name;
        this.price_med = med;
        this.price_small = small;
    }

    public String getName(){
        return this.name;
    }

    public Double getPrice(int flag){
        if (flag == 0){
            return this.price_small;
        }else{
            return this.price_med;
        }
    }

}
