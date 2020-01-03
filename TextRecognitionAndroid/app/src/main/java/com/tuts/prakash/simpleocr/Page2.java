package com.tuts.prakash.simpleocr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Page2 extends AppCompatActivity {
    private Button button01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        button01 = (Button)findViewById(R.id.button);
        button01.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                //textView01.setText("Stay Hungry, Stay Foolish. ");
                Intent intent = new Intent();
                intent.setClass(Page2.this  , MainActivity.class);
                startActivity(intent);
            }

        });
    }


}
