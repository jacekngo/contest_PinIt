package com.example.komputer.pinit.Fragments;


import android.app.DialogFragment;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.komputer.pinit.Model.NoteItem;
import com.example.komputer.pinit.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddNoteFragment extends DialogFragment implements OnMapReadyCallback{



    private NoteItem noteItem;
    private boolean editMode;
    private int editNotePosition;
    private GoogleMap googleMap;
    private MapView mapView;
    private View fragmentView;
    private AddNoteFragmentInterface fragmentInterface;

    @BindView(R.id.fragment_addOrEdit)
    TextView addOrEdit;

    @BindView(R.id.fragment_add_title)
    EditText editTitle;

    @BindView(R.id.fragment_add_description)
    EditText editDescription;





    public static AddNoteFragment newInstance(Location location, int id) {
        AddNoteFragment fragment = new AddNoteFragment();
        fragment.setNoteItem(new NoteItem(id, location.getTime(),
                location.getLatitude(), location.getLongitude(), location.getAccuracy(),"",""));
        return fragment;
    }

    public static AddNoteFragment newInstance(NoteItem noteItem, int position) {
        AddNoteFragment fragment = new AddNoteFragment();
        fragment.setNoteItem(noteItem);
        fragment.setEditMode(true);
        fragment.setEditNotePosition(position);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentInterface = (AddNoteFragmentInterface) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_add_note, container, false);
        ButterKnife.bind(this, fragmentView );

        selectMode();

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = fragmentView.findViewById(R.id.fragment_map);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    private void selectMode() {
        if(editMode){addOrEdit.setText("Edit Note");
        editTitle.setText(noteItem.getTitle());
        editDescription.setText(noteItem.getNotes());}
        else {addOrEdit.setText("Add Note");}
    }


    @OnClick(R.id.fragment_save_button)
    void onSaveButtonClicked(){
        noteItem.setTitle(editTitle.getText().toString());
        noteItem.setNotes(editDescription.getText().toString());
        if(editMode)
            {fragmentInterface.editNote(noteItem, editNotePosition);}
        else {fragmentInterface.addNote(noteItem);}
        this.dismiss();
    }

    @OnClick(R.id.fragment_delete_button)
    void onDeleteButtonClicked(){
        if(editMode){
            fragmentInterface.deleteNote(editNotePosition);
        }
        this.dismiss();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(noteItem.getLatitude(), noteItem.getLongitude())).title(noteItem.getTitle()));
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(noteItem.getLatitude(), noteItem.getLongitude())).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    //setters
    public void setNoteItem(NoteItem noteItem) {
        this.noteItem = noteItem;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setEditNotePosition(int editNotePosition) {
        this.editNotePosition = editNotePosition;
    }
}
