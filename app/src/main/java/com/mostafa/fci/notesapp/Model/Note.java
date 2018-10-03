package com.mostafa.fci.notesapp.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by ravi on 20/02/18.
 */

public class Note extends RealmObject {


    @PrimaryKey
    private String timestamp;
    @Required
    private String note;

    public Note() { }

    public Note(String note, String timestamp) {
        this.note = note;
        this.timestamp = timestamp;
    }


    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
