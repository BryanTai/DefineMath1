package com.example.DefineMath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class DefineMath extends Activity {
    Button newGameBtn;
    View.OnClickListener newGameHandler;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        InitializeApp();
    }

    private void InitializeApp() {
        //Set up handler to start new game.
        newGameBtn = (Button) findViewById(R.id.newGameButton);

        newGameHandler = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(DefineMath.this,DefineGame.class);
                //Begin a new Game.
                DefineMath.this.startActivity(intentMain);
                Log.i("Content ","Main Layout");
            }
        };
        newGameBtn.setOnClickListener(newGameHandler);

    }
}
