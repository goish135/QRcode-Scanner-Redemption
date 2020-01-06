package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Page2 extends AppCompatActivity {
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
    }
    */

    //  begin  //
    SurfaceView mCameraView;
    TextView mTextView;
    CameraSource mCameraSource;
    private int lastX,lastY;
    private String target = "";
    private String targetv= "";

    private static final String TAG = "MainActivity";
    private static final int requestPermissionID = 101;
    public String ym;

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                Log.e("手指接觸螢幕的座標: ","("+x+"',"+y+")");
                AlertDialog.Builder builder = new AlertDialog.Builder(Page2.this);


                String[] tokens = target.split("\\r?\\n");

                int has = 0;
                for(String token:tokens){
                    Log.i("token",token);
                    if(token.matches("[A-Z]{2}-[0-9]{8}")|| token.matches("[A-Z]{2}\\s[0-9]{8}") ) {
                        targetv = token;
                        has = 1;
                    }
                }
                if(has==1) {
                    builder.setMessage("是不是這串發票號碼?" + targetv);
                }else{
                    builder.setMessage("找不到發票格式!");
                }

                //targetv =  target;
                if(has==1) {
                    builder.setPositiveButton("沒錯", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String str = "12345678";

                            try {
                                String rv = check(str);
                                String context = "";
                                int cnt = 0;
                                int jud = 0;

                                Log.i("ican", targetv);
                                Log.i("ican", targetv);

                                //
                                String[] ct2 = rv.split(",|、");
                                String sr = targetv.substring(3, 11);
                                for (String item : ct2) {
                                    if (item.length() == 3) {
                                        if (item.equals(sr.substring(5, 8))) {
                                            context = "增開六獎";
                                            // break;
                                            jud = 1;
                                        }
                                        //cnt = -1;
                                    } else {
                                        if (cnt == 0) {


                                            if (item.equals(sr)) {
                                                context = "特別獎";
                                                break;
                                            }
                                        } else if (cnt == 1) {
                                            if (item.equals(sr)) {
                                                context = "特獎";
                                                break;
                                            }
                                        } else if (cnt == 2 || cnt == 3 || cnt == 4) {
                                            if (item.equals(sr) ) {
                                                context = "頭獎";
                                                break;
                                            } else if (item.substring(1, 8).equals(sr.substring(1, 8))) {
                                                context = "二獎";
                                                break;
                                            } else if (item.substring(2, 8).equals(sr.substring(2, 8))) {
                                                context = "三獎";
                                                break;
                                            } else if (item.substring(3, 8).equals(sr.substring(3, 8))) {
                                                context = "四獎";
                                                break;
                                            } else if (item.substring(4, 8).equals(sr.substring(4, 8))) {
                                                context = "五獎";
                                                break;
                                            } else if (item.substring(5, 8).equals(sr.substring(5, 8))) {
                                                context = "六獎";
                                                break;
                                            } else {
                                                if (jud == 0)
                                                    context = "未中獎";
                                            }
                                        }
                                    }

                                    cnt++;
                                }
                                //
                                /*
                                AlertDialog.Builder dialog2 = new AlertDialog.Builder(MainActivity.this);
                                dialog2.setTitle("Result:");
                                dialog2.setMessage(context);
                                dialog2.show();
                                */

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(Page2.this);
                                builder2.setMessage(context);

                                builder2.setPositiveButton("繼續尋找1000萬", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int id) {

                                    }
                                });
                                builder2.setNegativeButton("休息一下,等等繼續", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int id) {
                                        Intent intent = new Intent();
                                        intent.setClass(Page2.this  , MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                /*
                                builder2.setPositiveButton("休息一下等等繼續", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int id) {
                                        Intent intent = new Intent();
                                        intent.setClass(MainActivity.this  , Page2.class);
                                        startActivity(intent);
                                    }
                                });

                                 */
                                AlertDialog dialog2 = builder2.create();
                                dialog2.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            /*
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this  , Page2.class);
                            startActivity(intent);
                             */
                        }
                    });
                    builder.setNegativeButton("不是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {
                        }
                    });
                } else{
                    builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {
                        }
                    });
                }
                AlertDialog dialog = builder.create();
                dialog.show();
                // Log.i("result",target);

                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY  = y - lastY;
                Log.e("手指滑動的x距離:",offsetX+"");
                Log.e("手指滑動的y距離:",offsetY+"");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("手指鬆開:","("+x+","+y+")");
                break;
        }
        return true;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mCameraView = findViewById(R.id.surfaceView);
        mTextView = findViewById(R.id.text_view);
        /*
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,1,5,15,40);
        Intent intent = new Intent();
        intent.setClass(this,AlarmReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
        */
        start();
        startCameraSource();
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,20);
        cal_alarm.set(Calendar.MINUTE,32);
        cal_alarm.set(Calendar.SECOND,0);
        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
        }

        Intent myIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        manager.set(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(), pendingIntent);
    }

    public String check(String number) throws IOException {
        try {

            Document doc = Jsoup.connect("http://invoice.etax.nat.gov.tw/").get();

            Elements ele = doc.select("span[class=t18Red]");
            Elements ele2 = doc.select("h2");
            String str ="";
            for(Element a:ele )
            {
                str += (a.text()+",");
            }


            //String ym="";
            ym = "";
            for(Element b:ele2)
            {
                if((b.text().substring(0,1).equals("0")||b.text().substring(0,1).equals("1")||b.text().substring(0,1).equals("2")))
                    ym += b.text().substring(0,3)+b.text().substring(4,6)+b.text().substring(0,3)+b.text().substring(7,9);

            }
            Log.i("check",str);
            return  str;
        }catch(Exception e) {
            //Log.i("mytag", e.toString());
            Log.i("what",e.toString());
            return e.toString();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != requestPermissionID) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mCameraSource.start(mCameraView.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startCameraSource() {

        //Create the TextRecognizer
        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Log.w(TAG, "Detector dependencies not loaded yet");
        } else {

            //Initialize camerasource to use high resolution and set Autofocus on.
            mCameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(2.0f)
                    .build();

            /**
             * Add call back to SurfaceView and check if camera permission is granted.
             * If permission is granted we can start our cameraSource and pass it to surfaceView
             */
            mCameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(Page2.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    requestPermissionID);
                            return;
                        }
                        mCameraSource.start(mCameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    mCameraSource.stop();
                }


            });


            //Set the TextRecognizer's Processor.
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                }

                /**
                 * Detect all the text from camera using TextBlock and the values into a stringBuilder
                 * which will then be set to the textView.
                 * */
                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0 ){

                        mTextView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i=0;i<items.size();i++){
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                // Log.i("result",stringBuilder.toString());
                                target = stringBuilder.toString();

                                mTextView.setText(stringBuilder.toString());
                            }
                        });
                    }
                }
            });
        }
    }
    // end //
}
