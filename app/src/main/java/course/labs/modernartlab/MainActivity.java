/**************************************************
 MainActivity.java

 Implementation of the initial screen for the
 ModernArtLab project for Coursera's Programming
 Mobile Applications for Android Handheld Systems:
 Part 1

 The screen will display a Mondrian-inspired layout of coloured
 rectangles whose colours change gradually when the user moves
 a slider.  The dimensions and colours of the rectangles are
 generated randomly when the application is loaded.

 The options menu gives the user the ability to visit the website
 of the Museum of Modern Art (MOMA) on the device's browser.

 Author: arsenyku
 Copyright (c) 2015

 **************************************************/

package course.labs.modernartlab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import static android.content.DialogInterface.*;

// MainActivity
//
// Handles the options menu for the application and contains
// the UI fragment which implements the functionality required
// by the ModernArtLab project.
//
public class MainActivity extends AppCompatActivity {

    // IDs for menu items
    private static final int MENU_MOREINFO = Menu.FIRST;

    private static final String MOMA_SITE = "http://www.moma.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // onCreateOptionsMenu
    // Populates the Options menu with approriate entries
    //
    // Parameters:
    // menu - a reference to the menu being populated.
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_MOREINFO, Menu.NONE, R.string.more_info);

        return true;
    }


    // onOptionsItemSelected
    // Triggered when the user selects an entry in the Options menu.
    //
    // Parameters:
    // item - an object indicating which option the user selected.
    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_MOREINFO:
                ShowMoreInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ShowMoreInfo
    //
    // Performs the actions required when the user selects the
    // More Information options menu item.
    //
    private void ShowMoreInfo(){

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.activity_more_info);

        // set the custom dialog components
        Button laterButton = (Button) dialog.findViewById(R.id.LaterButton);
        // if button is clicked, close the custom dialog
        laterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        Button goButton = (Button) dialog.findViewById(R.id.GoToMomaButton);
        // if button is clicked, open MOMA webpage
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                VisitMomaWebPage();
            }
        });


        dialog.show();
    }

    // VisitMomaWebPage
    //
    // Performs the actions to start a browser and display
    // the greeting page of the MOMA web site.
    //
    private void VisitMomaWebPage(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(MOMA_SITE));

        // Launch the Activity using the intent
        startActivity(intent);
    }
}
