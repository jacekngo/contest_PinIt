package com.example.komputer.pinit.Fragments;

import com.example.komputer.pinit.Model.NoteItem;

/**
 * Created by komputer on 2017-08-21.
 */

public interface AddNoteFragmentInterface {
    void addNote(NoteItem noteItem);
    void editNote(NoteItem noteItem, int position);
    void deleteNote(int position);
}
