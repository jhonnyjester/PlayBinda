package com.jhony.jester.play.Utils;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JAR on 21-01-2018.
 */

public abstract class Preferences extends PreferenceActivity {
    private AppCompatDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getdelegate().installViewFactory();
        getdelegate().onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getdelegate().onPostCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return getdelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable android.support.v7.widget.Toolbar toolbar) {
        getdelegate().setSupportActionBar(toolbar);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return getdelegate().getMenuInflater();
    }

    @Override
    public void setContentView(int layoutResID) {
        getdelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getdelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getdelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getdelegate().addContentView(view, params);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getdelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getdelegate().onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getdelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getdelegate().onDestroy();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getdelegate().setTitle(title);
    }

    @Override
    public void invalidateOptionsMenu() {
        getdelegate().invalidateOptionsMenu();
    }

    private AppCompatDelegate getdelegate() {
        if (delegate == null) {
            delegate = AppCompatDelegate.create(this, null);
        }
        return delegate;
    }

}

