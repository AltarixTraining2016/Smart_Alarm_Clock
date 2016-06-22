package com.me.ilya.smartalarmclock.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ilya.smartalarmclock.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ilya on 6/22/2016.
 */
public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {
    private ArrayList<Song> songs;
    Context context;
    public SongListAdapter(ArrayList<Song> itemsData,Context context) {
        this.songs = itemsData;
        this.context=context;
    }

    public SongListAdapter(){}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_item, null);

        ViewHolder vh=new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       holder.txtViewName.setText(songs.get(position).getName());
       holder.tvLength.setText(songs.get(position).getLength());
        holder.fill(songs.get(position),context);
    }


    @Override
    public int getItemCount() {
        return songs.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Song song;
        private static MediaPlayer mpintro;
        public TextView txtViewName;
        public TextView tvLength;
        private void process(final Context context) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mpintro!=null&& mpintro.isPlaying()){
                    mpintro.stop();
                    mpintro.release();}
                    mpintro = new MediaPlayer();
                    try {

                        mpintro.setDataSource(song.getUri());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mpintro.setLooping(true);
                    try {
                        mpintro.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mpintro.start();
                }
            });
        }
        public void fill(Song song,Context context) {
            this.song = song;
            process(context);
        }
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewName = (TextView) itemLayoutView.findViewById(R.id.song_name);
            tvLength = (TextView) itemLayoutView.findViewById(R.id.song_length);

        }
    }
}
