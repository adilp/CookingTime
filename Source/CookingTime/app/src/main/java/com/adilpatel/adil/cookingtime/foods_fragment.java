package com.adilpatel.adil.cookingtime;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Adil on 2/18/2015.
 */
public class foods_fragment extends Fragment implements dialog_foods.foodsDialogListener {
        Button addButton;
        Button deleteButton;
        int time;
        String name;
        private Realm realm;
    int i;
    ArrayList<Foods> foods = new ArrayList<>();
    private MyListAdapter mAdapter;




        public static foods_fragment newInstance(){

            foods_fragment fragment = new foods_fragment();
            return fragment;
        }
    public foods_fragment(){


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_foods, container, false);

        realm = Realm.getInstance(getActivity());
        ListView foodsListView = (ListView) rootView.findViewById(R.id.mFoodListView);

        loadData();
        mAdapter = new MyListAdapter(foods, getActivity());
        foodsListView.setAdapter(mAdapter);


        foodsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Foods p = foods.get(position);
                String pos = p.get_id();
                delete(pos);

            }
        });

       // realm = Realm.getInstance(getActivity());
        //basicCRUD(realm);
        //basicQuery(realm);


        addButton = (Button)rootView.findViewById(R.id.mAddButton);

        deleteButton = (Button)rootView.findViewById(R.id.mDeleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete("0");

            }});

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonPressed();
                testToast();


                //loadData();

            }});



        String[] foods = {"Food", "Ham", "tuna"};

       // ListAdapter myAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, foods);


        //ListView foodsListView = (ListView) rootView.findViewById(R.id.mFoodListView);

        //foodsListView.setAdapter(myAdapter);
        return rootView;
    }
    //dialog_foods activity = (dialog_foods) getActivity();
    public void addButtonPressed(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialog_foods dialog1 = new dialog_foods();
        // dialog1.show(getSupportFragmentManager(), "my_dialog");
        //dialog1.show(ft, "acknowledgements");
        dialog1.setTargetFragment(this, 0);
        //dialog1.show(ft, "acknoledgments");
        dialog1.show(fm, "acknowledgements");

        //Toast.makeText(getActivity().getApplicationContext(), "Add Button Pressed",
          //      Toast.LENGTH_LONG).show();
        i++;




    }

    public void loadData(){
        Realm realm = Realm.getInstance(getActivity());

        //RealmResults<Foods> query = realm.where(Foods.class).findAll();



        RealmResults<Foods> query = realm.where(Foods.class).findAll();



         for (Foods p : query){
            foods.add(p);
        }


    }
    public void addNameTime(){


        realm.beginTransaction();

        //Add item
        Foods foodItem = realm.createObject(Foods.class);
        //foodItem.set_id(i);
        foodItem.set_foodName(name);
        foodItem.setTime(time);

        realm.commitTransaction();
    }

    public void delete(String pos){
        Realm realm = Realm.getInstance(getActivity());


        //RealmResults<Foods> query = realm.where(Foods.class).findAll();
       // Foods delete = realm.where(Foods.class).findAll();
        //Foods delete = realm.where(Foods.class).equalTo("_id", pos).findFirst();

        realm.beginTransaction();


        //delete.removeFromRealm();
        realm.allObjects(Foods.class).remove(pos);



        realm.commitTransaction();


    }
    public void add(){
        Realm realm = Realm.getInstance(getActivity());

        realm.beginTransaction();

        Foods p = realm.createObject(Foods.class);
        p.setTime(time);
        p.set_foodName(name);
        p.set_id(UUID.randomUUID().toString());
       // Toast.makeText(getActivity().getApplicationContext(), "realm added",
         //       Toast.LENGTH_LONG).show();

        realm.commitTransaction();
        foods.add(p);
        mAdapter.notifyDataSetChanged();

        //loadData();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void basicCRUD(Realm realm){
        realm.beginTransaction();

        //Add item
        Foods foodItem = realm.createObject(Foods.class);
        //foodItem.set_id(i);
        foodItem.set_foodName(name);
        foodItem.setTime(time);

        realm.commitTransaction();

        int sizeOfRealm = realm.allObjects(Foods.class).size();



        //String[]String[] Foodname = new String[]{foodItem.get_foodName()};

        int[] toViewTime = new int[]{foodItem.getTime()};
        //SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(getActivity(),R.layout.fragment_foods,sizeOfRealm, fromNames, toViewTime );

    }

    private void basicQuery(Realm realm){
       int sizeOfRealm = realm.allObjects(Foods.class).size();

        String[] fromNames = new String[]{};

        int[] toViewIDs = new int[]{};
       // SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(2);
    }
    public void testToast(){
       // Toast.makeText(getActivity().getApplicationContext(), time + " " + name,
         //       Toast.LENGTH_LONG).show();
    }
    @Override
    public void nameTimeSubmit(int time, String name) {
        this.time = time;
        this.name = name;
        add();

    }
}

