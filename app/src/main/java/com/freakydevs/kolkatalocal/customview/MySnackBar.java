package com.freakydevs.kolkatalocal.customview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.freakydevs.kolkatalocal.R;

/**
 * Created by PURUSHOTAM on 11/1/2017.
 */

public class MySnackBar {

    public static void show(Context context, ConstraintLayout layout, String message) {

        Snackbar bar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle user action

                    }
                });

        View view = bar.getView();
        view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(context.getResources().getColor(R.color.whitesnow));
        bar.setActionTextColor(context.getResources().getColor(R.color.colorAccentDark));
        bar.show();

    }

}
