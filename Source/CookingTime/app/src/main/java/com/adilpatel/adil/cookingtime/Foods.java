package com.adilpatel.adil.cookingtime;
import io.realm.RealmObject;

/**
 * Created by Adil on 2/21/2015.
 */

public class Foods extends RealmObject{

    private String _id;
    private String _foodName;
    private int time;

    public String get_foodName(){
        return _foodName;
    }

    public void set_foodName(String name){
        this._foodName = name;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }

    public String get_id(){
        return _id;
    }

    public void set_id(String id){
        this._id=id;
    }
   /* public Foods(String foodName) {

        this._foodName = foodName;
    }

    public void set_id (int _id){

        this._id= _id;
    }

    public void set_foodName (String _foodName){

        this._foodName = _foodName;
    }

    public int get_id(){

        return _id;
    }

    public String get_foodName(){

        return _foodName;
    } */
}
