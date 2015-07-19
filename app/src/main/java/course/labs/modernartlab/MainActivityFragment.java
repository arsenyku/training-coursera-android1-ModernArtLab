package course.labs.modernartlab;

import android.animation.ArgbEvaluator;
import android.app.ActionBar;
import android.graphics.Canvas;
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
import java.util.Random;
import java.util.zip.Inflater;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    final static String LOGTAG = "modartlab";

//    final static int AXIS_VERTICAL = 0;
//    final static int AXIS_HORIZONTAL = 0;

    private RelativeLayout mFragmentView = null;
    private LinearLayout mTilesLayout = null;
    private SeekBar mSeekBar = null;
    final static private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public MainActivityFragment() {
    }


    private int getRandom(int min, int max){
        return (new Random()).nextInt(max - min) + min;
    }



    private void DrawTiles() {
/*    }

    private void foobar(){*/
        LayoutInflater inflater = getActivity().getLayoutInflater();

        LinearLayout left = new LinearLayout(getActivity());
        LinearLayout right = new LinearLayout(getActivity());

        int weight = getRandom(2,8);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, weight);
        mTilesLayout.addView(left, lp);

        lp = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 10-weight);
        mTilesLayout.addView(right, lp);

        View tile1 = inflater.inflate(R.layout.tile, null, false);
        View tile2 = inflater.inflate(R.layout.tile, null, false);
        View tile3 = inflater.inflate(R.layout.tile, null, false);
        View tile4 = inflater.inflate(R.layout.tile, null, false);
        View tile5 = inflater.inflate(R.layout.tile, null, false);

        tile1.setBackgroundColor(getRandom(0xff000000, 0xffffffff));
        tile2.setBackgroundColor(getRandom(0xff000000, 0xffffffff));
        tile3.setBackgroundColor(getRandom(0xff000000, 0xffffffff));
        tile4.setBackgroundColor(getRandom(0xff000000, 0xffffffff));
        tile5.setBackgroundColor(getRandom(0xff000000, 0xffffffff));

        left.setOrientation(LinearLayout.VERTICAL);

        weight = getRandom(1,70);
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
        lp.setMargins(10,10,10,10);
        left.addView(tile1, lp);

        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 100-weight);
        lp.setMargins(10,10,10,10);
        left.addView(tile2, lp);

        right.setOrientation(LinearLayout.VERTICAL);

        weight = getRandom(1,70);
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
        lp.setMargins(10,10,10,10);
        right.addView(tile3, lp);

        int weight2 = getRandom(20, 100 - weight);
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight2);
        lp.setMargins(10,10,10,10);
        right.addView(tile4, lp);

        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 100-weight-weight2);
        lp.setMargins(10,10,10,10);
        right.addView(tile5, lp);

        argbEvaluator.evaluate(mSeekBar.getProgress(), 0, mSeekBar.getMax());


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
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

        });

        return mFragmentView;
    }


}
