package com.me.ilya.smartalarmclock.music;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.ilya.smartalarmclock.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ilya on 6/22/2016.
 */
public class SongListActivity extends AppCompatActivity {
    private ArrayList<Song> songList;
    private RecyclerView songView;

    private int alarmId;
    private final static String EXTRA_ALARM_ID = "alarm_id";
    public static Intent intent(Context context) {
        return new Intent(context, SongListActivity.class);
    }

    public static Intent intent(Context context, int deviceId) {
        Intent intent = new Intent(context, SongListActivity.class);
        intent.putExtra(EXTRA_ALARM_ID, deviceId);
        return intent;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(SongListAdapter.ViewHolder.getMpintro() !=null&& SongListAdapter.ViewHolder.getMpintro() .isPlaying()){
            SongListAdapter.ViewHolder.getMpintro().stop();
            SongListAdapter.ViewHolder.getMpintro() .release();
            SongListAdapter.ViewHolder.setMpintro(null);}
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list_activity);
        alarmId = getIntent().getIntExtra(EXTRA_ALARM_ID,-1);


        songView=(RecyclerView)findViewById(R.id.song_recycler_view);
        songList = new ArrayList<Song>();
        getSongList();
        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getName().compareTo(b.getName());
            }
        });
        songView.setLayoutManager(new LinearLayoutManager(this));
        SongListAdapter songListAdapter=new SongListAdapter(songList,getApplicationContext(),alarmId,this);
        songView.setAdapter(songListAdapter);
    }



    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int lengthColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
             int uri=musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisLength = musicCursor.getString(lengthColumn);
                songList.add(new Song(thisId, thisTitle,musicCursor.getString(uri),thisLength));
            }
            while (musicCursor.moveToNext());
        }
        musicCursor.close();
    }
}
