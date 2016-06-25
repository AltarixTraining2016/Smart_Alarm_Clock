package com.me.ilya.smartalarmclock.music;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.me.ilya.smartalarmclock.Dto;

/**
 * Created by Ilya on 6/22/2016.
 */
public class Song extends Dto{

    static final String _ID = "_id";
    static final String TITLE = "title";
    static final String PATH= "path";
    public static final String[] COLUMN_NAMES = {
            _ID,
            TITLE,
            PATH
    };
    public static Song fromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(_ID));
        String title = cursor.getString(cursor.getColumnIndex(TITLE));
        String path = cursor.getString(cursor.getColumnIndex(PATH));
        return new Song(id,title,path);
    }

    private long id;
    private String title;
   private String uri;
    private String length;
    public Song(long songID, String songTitle,String uri, String songLength) {
        id=songID;
        title=songTitle;
        length=songLength;
        this.uri=uri;
    }
    public Song(long songID, String songTitle, String uri) {
        id=songID;
        title=songTitle;
        this.uri=uri;
    }
    public String getUri() {
        return uri;
    }

    public long getID(){return id;}
    public String getName(){return title;}
    public String getLength(){return length;}
    public void addToCursor(MatrixCursor c) {
        c.addRow(new Object[]{id,title,uri,length });
    }
}
