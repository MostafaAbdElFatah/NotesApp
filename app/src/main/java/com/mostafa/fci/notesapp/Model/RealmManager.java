package com.mostafa.fci.notesapp.Model;

import android.content.Context;
import android.util.Log;

import com.mostafa.fci.notesapp.View.OnDataChangeListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RealmManager {

    private Realm mRealm;
    private OnDataChangeListener listener;

    public RealmManager(Context context) {
        listener = (OnDataChangeListener) context;
        this.mRealm = Realm.getDefaultInstance();
    }

    //check if Note.class is empty
    public boolean hasBooks() {
        return !mRealm.where(Note.class).findAll().isEmpty();
    }


    public void save(final Note note){
        // asynchronous Request
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(note);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onDataChange();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.v("REALM_DB", "onError: "+ error.getMessage());
            }
        });

        // synchronous Request
        /*
        mRealm.beginTransaction();
        mRealm.insert(note);
        mRealm.commitTransaction();
        */
    }
    public void save(final List<Note> noteList){
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(noteList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onDataChange();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.v("REALM_DB", "onError: "+ error.getMessage());
            }
        });

    }


    //find all objects in the Note.class
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        RealmResults<Note> results = mRealm.where(Note.class).findAll();
        for (Note note:results)
            notes.add(note);
        return notes;
    }

    //query a single item with the given id
    public Note getNote(Note note) {
        return mRealm.where(Note.class).equalTo("timestamp"
                , note.getTimestamp()).findFirst();
    }


    public void updateNote(final Note updatedNote) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(updatedNote);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onDataChange();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.v("REALM_DB", "onError: "+ error.getMessage());
            }
        });
    }

    public void deleteNote(final Note note){
        final String timestamp = note.getTimestamp();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Note> rows = realm.where(Note.class).equalTo("timestamp"
                        , timestamp).findAll();
                rows.deleteAllFromRealm();
                //note.deleteFromRealm();

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onDataChange();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.v("REALM_DB", "onError:deleteNote, "+ error.getMessage());
            }
        });
    }


    public  void deleteAllNotes() {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onDataChange();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.v("REALM_DB", "onError: "+ error.getMessage());
            }
        });
    }

}
