package com.example.komputer.pinit.Adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.komputer.pinit.Model.NoteItem;
import com.example.komputer.pinit.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by komputer on 2017-08-20.
 */

public class NoteItemListAdapter extends RecyclerView.Adapter<NoteItemListAdapter.NoteItemViewHolder>  {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private List<NoteItem> locationList;
    private NoteItemFragmentInteractionInterface activityInterface;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;
    private int colorPrimary;
    private int colorAccent;

    public NoteItemListAdapter (List<NoteItem> locationList){
        this.locationList=locationList;
    }

    @Override
    public NoteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
        activityInterface = (NoteItemFragmentInteractionInterface) parent.getContext();
        simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        calendar = Calendar.getInstance();

        colorAccent = ContextCompat.getColor(parent.getContext(), R.color.googleGreen);
        colorPrimary = ContextCompat.getColor(parent.getContext(), R.color.mediumBlue);
        return new NoteItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NoteItemViewHolder holder, final int position) {
        NoteItem noteItem = locationList.get(position);
        calendar.setTimeInMillis(noteItem.getSystemTime());
        holder.itemName.setText(noteItem.getTitle());
        holder.itemLatitude.setText(String.format("%f", noteItem.getLatitude()));
        holder.itemLongitude.setText(String.format("%f", noteItem.getLongitude()));
        holder.itemTime.setText(simpleDateFormat.format(calendar.getTime()));
        if(noteItem.getNotes()!= null && !noteItem.getNotes().isEmpty()){
            holder.colorBar.setBackgroundColor(colorAccent);}
        else {
            holder.colorBar.setBackgroundColor(colorPrimary);
        }

        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityInterface.itemNoteEdit(holder.getAdapterPosition());
            }
        });
        holder.upImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityInterface.itemNoteUp(holder.getAdapterPosition());
            }
        })
        ;
        holder.downImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityInterface.itemNoteDown(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }



    static class NoteItemViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.row_time)
        TextView itemTime;
        @BindView(R.id.row_latitude)
        TextView itemLatitude;
        @BindView(R.id.row_longitude)
        TextView itemLongitude;
        @BindView(R.id.row_name)
        TextView itemName;
        @BindView(R.id.row_layout)
        CardView rowLayout;
        @BindView(R.id.row_edit_image)
        ImageView editImage;
        @BindView(R.id.row_up_image)
        ImageView upImage;
        @BindView(R.id.row_down_image)
        ImageView downImage;
        @BindView(R.id.row_color_bar)
        LinearLayout colorBar;

        public NoteItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

