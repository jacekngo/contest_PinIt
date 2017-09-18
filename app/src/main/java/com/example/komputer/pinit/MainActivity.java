package com.example.komputer.pinit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.komputer.pinit.Adapters.NoteItemFragmentInteractionInterface;
import com.example.komputer.pinit.Adapters.NoteItemListAdapter;
import com.example.komputer.pinit.Fragments.AddNoteFragment;
import com.example.komputer.pinit.Fragments.AddNoteFragmentInterface;
import com.example.komputer.pinit.Model.NoteItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements AddNoteFragmentInterface, NoteItemFragmentInteractionInterface {

    public static final int PERMISSIONS_REQUEST_CODE = 25;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location currentLocation;

    @BindView(R.id.main_recycler_layout)
    RecyclerView recyclerView;

    private List<NoteItem> allNotes;
    private Realm realm;
    private NoteItemListAdapter listAdapter;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        allNotes = new ArrayList<>();
        loadDataFromDB();
        setRecyclerView();
    }

    private void setRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new NoteItemListAdapter(allNotes);
        recyclerView.setAdapter(listAdapter);
    }

    private void loadDataFromDB() {
        realm = Realm.getDefaultInstance();
        allNotes = realm.copyFromRealm(realm.where(NoteItem.class).findAll());
        realm.close();
    }

    @OnClick(R.id.bnt_saveLocation)
    void onSaveButtonPressed () {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                  currentLocation = location;
                    AddNoteFragment.newInstance(currentLocation, 0 ).show(getFragmentManager(),"add note dialog fragment");
                } else  {makeToast("nie udało się pobrać lokacji");}
            }
        });}
        else checkPermissions();
    }



    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String [] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET }, PERMISSIONS_REQUEST_CODE);
        }
    }


     void validateAndSaveToDB () {
        for(int i = 0; i<allNotes.size(); i++){
            allNotes.get(i).setId(i);
        }
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(NoteItem.class);
        realm.copyToRealm(allNotes);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    protected void onStop() {
        validateAndSaveToDB();
        super.onStop();
    }

// interface add/edit note fragmentu
    @Override
    public void addNote(final NoteItem noteItem) {
        allNotes.add(noteItem.getId(), noteItem);
        listAdapter.notifyItemInserted(noteItem.getId());
        layoutManager.scrollToPosition(noteItem.getId());
    }

    @Override
    public void editNote(final NoteItem noteItem, final int position){
        allNotes.set(position, noteItem);
        listAdapter.notifyItemChanged(position);
        layoutManager.scrollToPosition(position);
    }

    @Override
    public void deleteNote(int position) {
        allNotes.remove(position);
        listAdapter.notifyItemRemoved(position);
    }
// koniec interface add/edit note fragmentu

// interface adaptera
    @Override
    public void itemNoteEdit(int position) {
        AddNoteFragment.newInstance(allNotes.get(position), position).show(getFragmentManager(),"edit note dialog fragment");
    }

    @Override
    public void itemNoteUp(int position) {
        if (position > 0) {
            Collections.swap(allNotes,position, position-1);
            listAdapter.notifyItemMoved(position, position - 1);
            layoutManager.scrollToPosition(position - 1);
        } else makeToast("notatka jest pierwsza na liście");
    }

    @Override
    public void itemNoteDown(int position) {
        if (position < allNotes.size() - 1) {
            Collections.swap(allNotes,position, position+1);
            listAdapter.notifyItemMoved(position, position + 1);
            layoutManager.scrollToPosition(position + 1);
        } else makeToast("notatka jest ostatnia na liście");
    }
// koniec interface adaptera
    protected void makeToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
