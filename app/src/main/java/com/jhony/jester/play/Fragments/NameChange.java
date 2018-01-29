package com.jhony.jester.play.Fragments;

/**
 * Created by JAR on 05-09-2017.
 */

public class NameChange {

    public NameChange(){
        this.listener = null;
    }

    public interface NameChangeListener{
        public void onChanged(String name);
    }

    private NameChangeListener listener;

    public void setListener(NameChangeListener listener) {
        this.listener = listener;
    }
}
