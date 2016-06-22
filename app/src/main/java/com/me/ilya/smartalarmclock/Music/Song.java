package com.me.ilya.smartalarmclock.music;

import android.net.Uri;

/**
 * Created by Ilya on 6/22/2016.
 */
public class Song {
    private long id;
    private String title;
    String uri;
    private String length;
    public Song(long songID, String songTitle, String songLength,String uri) {
        id=songID;
        title=songTitle;
        length=songLength;
        this.uri=uri;
    }

    public String getUri() {
        return uri;
    }

    public long getID(){return id;}
    public String getName(){return title;}
    public String getLength(){return length;}
}
