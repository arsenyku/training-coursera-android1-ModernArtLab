package course.labs.modernartlab;

import android.animation.ArgbEvaluator;
import android.app.ActionBar;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.nfc.Tag;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.Inflater;


/**
 * A placeholder fragment containing a simple view.
 */
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


    private int getRandom(int min, int max){
        return (new Random()).nextInt(max - min) + min;
    }

    private int randomColor()
    {
        return getRandom(0xff000000, 0xffffffff);
    }

    class TileColors{
        public int StartColor = 0;
        public int EndColor = 0;

        public TileColors(int startColor, int endColor){
            StartColor = startColor;
            EndColor = endColor;
        }
    }

    private void DrawTiles() {

        final int NumTiles = 5;

        LayoutInflater inflater = getActivity().getLayoutInflater();

        LinearLayout left = new LinearLayout(getActivity());
        LinearLayout right = new LinearLayout(getActivity());

        int weight = getRandom(20,80);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, weight);
        mTilesLayout.addView(left, lp);

        lp = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, WEIGHT_SUM-weight);
        mTilesLayout.addView(right, lp);

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

        left.setOrientation(LinearLayout.VERTICAL);

        weight = getRandom(1,70);
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        left.addView(mTiles.get(0), lp);

        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, WEIGHT_SUM-weight);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        left.addView(mTiles.get(1), lp);

        right.setOrientation(LinearLayout.VERTICAL);

        weight = getRandom(1,70);
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        right.addView(mTiles.get(2), lp);

        int weight2 = getRandom(20, 100 - weight);
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight2);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        right.addView(mTiles.get(3), lp);

        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, WEIGHT_SUM-weight-weight2);
        lp.setMargins(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN);
        right.addView(mTiles.get(4), lp);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentView = (RelativeLayout)inflater.inflate(R.layout.fragment_main, container, false);

        mTilesLayout = (LinearLayout)mFragmentView.findViewById(R.id.TilesLayout);
        mSeekBar = (SeekBar)mFragmentView.findViewById(R.id.seekbar);

        DrawTiles();


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                for (View tile : mTiles) {
                    TileColors tc = (TileColors)tile.getTag();
                    int newColor = (Integer)new ArgbEvaluator().evaluate(
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
