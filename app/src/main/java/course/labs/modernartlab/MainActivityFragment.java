/**************************************************
 MainActivityFragment.java

 Implementation of the screen component containing the
 primary user interface for the ModernArtLab project
 for Coursera's Programming Mobile Applications for
 Android Handheld Systems: Part 1

 This fragment implements the random layout of coloured
 rectangles displayed to the user as well as the slider
 that lets the user change their colours.

 Author: arsenyku
 Copyright (c) 2015

 **************************************************/

package course.labs.modernartlab;

import android.animation.ArgbEvaluator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import java.util.ArrayList;
import java.util.Random;



// MainActivityFragment
//
// Displays a Mondrian-inspired screen layout containing
// coloured shapes which change when a slider is moved on
// screen.
//
public class MainActivityFragment extends Fragment {

    final static String LOGTAG = "modartlab";

    final int TILE_MARGIN = 10;
    final int WEIGHT_SUM = 100;
    final int WHITE = 0xffffffff;

    private RelativeLayout mFragmentView = null;
    private LinearLayout mTilesLayout = null;
    private SeekBar mSeekBar = null;
    private ArrayList<View> mTiles = new ArrayList<View>();
    final static private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public MainActivityFragment() {
    }


    // getRandom
    // Gets a random integer between the specified min and max values.
    //
    // Parameters:
    // min - the minimum value possible for the resulting random integer.
    // max - the maximum value possible for the resulting random integer.
    //
    private int getRandom(int min, int max){
        return (new Random()).nextInt(max - min) + min;
    }


    // randomColor
    // Generates an integer representing a random color
    //
    private int randomColor()
    {
        return getRandom(0xff000000, 0xffffffff);
    }


    // TileColors
    // A class to contain the start and enp points of a color
    // spectrum.
    //
    class TileColors{
        public int StartColor = 0;
        public int EndColor = 0;

        public TileColors(int startColor, int endColor){
            StartColor = startColor;
            EndColor = endColor;
        }
    }


    // DrawTiles
    // Draws rectangles on screen that form part of the Mondrian-inspired
    // layout being displayed for the user.  There will be 5 tiles drawn
    // with random dimensions and random colours, except for one random tile
    // which will have a fixed white colour.
    //
    private void DrawTiles() {

        final int NumTiles = 5;   // Always 5 tiles will be drawn

        LayoutInflater inflater = getActivity().getLayoutInflater();

        LinearLayout left = new LinearLayout(getActivity());
        LinearLayout right = new LinearLayout(getActivity());

        // Screen will be split vertically first.

        // Left side will occupy between 20-80% of width of screen

        int weight = getRandom(20,80);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, weight);
        mTilesLayout.addView(left, lp);

        // Right side will occupy whatever remains of the screen width
        // after the width of the left side has been determined.

        lp = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, WEIGHT_SUM-weight);
        mTilesLayout.addView(right, lp);

        // Create the tiles and add them to the UI

        for (int i=0; i<NumTiles; i++){
            View tile = inflater.inflate(R.layout.tile, null, false);
            TileColors tc = new TileColors(randomColor(), randomColor());
            tile.setBackgroundColor(tc.StartColor);
            tile.setTag(tc);
            mTiles.add(tile);
        }

        // Determine which tile will be WHITE
        int whiteTile =getRandom(0,NumTiles);  // Generate a number from 0 to 4
        View tile = mTiles.get(whiteTile);
        tile.setBackgroundColor(WHITE);
        ((TileColors)tile.getTag()).StartColor = WHITE;
        ((TileColors)tile.getTag()).EndColor = WHITE;

        // Left side will have 2 tiles stacked vertically.

        left.setOrientation(LinearLayout.VERTICAL);

        // First tile will occupy 10-70% of screen height.
        weight = getRandom(10,70);
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        left.addView(mTiles.get(0), lp);

        // Second tile will occupy whatever remains of screen height.
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, WEIGHT_SUM-weight);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        left.addView(mTiles.get(1), lp);

        // Right side will have 3 tiles stacked vertically.

        right.setOrientation(LinearLayout.VERTICAL);

        // First tile will randomly occupy 10-70% of screen height.
        weight = getRandom(10,70);
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        right.addView(mTiles.get(2), lp);

        // Second tile will randomly occupy between 20-80% of height
        // depending on the height of the first tile.
        int weight2 = getRandom(20, WEIGHT_SUM - weight - 10);
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight2);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        right.addView(mTiles.get(3), lp);

        // Third tile will randomly occupy between 10-70% of screen height
        // depending on the heights of the other tiles.
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, WEIGHT_SUM-weight-weight2);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        right.addView(mTiles.get(4), lp);

    }

    // OnCreateView
    // Method called by the system to create and populate the UI fragment.
    // The fragment is first created from a Layout then the required colour tiles
    // are created dynamically.
    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentView = (RelativeLayout)inflater.inflate(R.layout.fragment_main, container, false);

        mTilesLayout = (LinearLayout)mFragmentView.findViewById(R.id.TilesLayout);
        mSeekBar = (SeekBar)mFragmentView.findViewById(R.id.seekbar);

        DrawTiles();


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // OnProgressChanged
            // Change the colours of the tiles when the seekbar's progress value changes.
            //
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                for (View tile : mTiles) {
                    TileColors tc = (TileColors)tile.getTag();
                    int newColor = (Integer)argbEvaluator.evaluate(
                            (float)progress/seekBar.getMax(),
                            tc.StartColor, tc.EndColor);

                    tile.setBackgroundColor(newColor);
                }



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        return mFragmentView;
    }


}
