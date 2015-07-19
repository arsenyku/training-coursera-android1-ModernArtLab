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


public class MainActivity extends AppCompatActivity {

    // IDs for menu items
    private static final int MENU_MOREINFO = Menu.FIRST;

    private static final String MOMA_SITE = "http://www.moma.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_MOREINFO, Menu.NONE, R.string.more_info);

        return true;
    }

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

    private void VisitMomaWebPage(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(MOMA_SITE));

        // Launch the Activity using the intent
        startActivity(intent);
    }
}
