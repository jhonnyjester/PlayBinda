package com.jhony.jester.play.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhony.jester.play.R;

import static com.jhony.jester.play.Utils.Constants.HOST;
import static com.jhony.jester.play.Utils.Constants.JOIN;


/**
 * Created by JAR on 14-06-2017.
 */

public class CustomDialog extends DialogFragment {

    static Context context;
    static String wh;

    public CustomDialog() {

    }

    public static CustomDialog newInstance(Context con, String which) {
        context = con;
        wh = which;
        return new CustomDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        switch (wh) {
            case HOST:
                return inflater.inflate(R.layout.hosting_layout, container);
            case JOIN:
                return inflater.inflate(R.layout.joining_layout, container);
            default: return null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
