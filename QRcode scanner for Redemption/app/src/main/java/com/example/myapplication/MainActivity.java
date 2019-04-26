package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Button button01;
    private Button button02;
    private Button button03;
    private TextView textView01;
    private TextView tvAndroid123;
    private EditText num;
    private EditText Day;
    private EditText ymym;
    public String ym;
    final String url=  "https://www.cnblogs.com/qdhxhz/p/9230805.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                GoScan();
            }
        });
        button01 = (Button)findViewById(R.id.Button01);


        tvAndroid123 = (TextView)findViewById(R.id.tvCWJ);
        tvAndroid123.setMovementMethod(ScrollingMovementMethod.getInstance());


        button01.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {
                //tvAndroid123.setText(readInfo("a.txt"));
                try {
                    ymym = (EditText) findViewById(R.id.ymym);
                    String ymym2 = ymym.getText().toString();
                    // TODO Auto-generated method stub
                    //String detail = "品名\t\t"+" 數量\t\t"+"單價\n"+ readInfo("a.txt");
                    String dnt = readInfo("a.txt");
                    String ok[] = dnt.split("\n");
                    int ctt = 0;
                    String ans = "";
                    int monthcost = 0;
                    for (String okok : ok) {


                        //ans+=okok;
                        String detail[] = okok.split("\t");
                        if (ymym2.equals(detail[0].substring(0, 5))) {
                            ans += "日期:" + detail[0] + "\t";
                            ans += "發票編號:" + detail[1] + "\t";
                            ans += "總金額:" + detail[2] + "\n";
                            monthcost += Integer.parseInt(detail[2]);
                        }
                        ctt++;
                    }
                    //ans+=ymym2;
                    if (ymym2.length() == 0) {
                        tvAndroid123.setText("未輸入查詢條件年月YYYMM");
                    } else {
                        if (ans.length() == 0) ans = "No Record!";
                        else {
                            ans = "總支出:" + String.valueOf(monthcost) + "\n\n" + ans;
                        }
                        tvAndroid123.setText(ans);
                    }
                }
                catch(Exception e)
                {
                    tvAndroid123.setText("尚未掃描過!");
                }
            }

        });
        button02 = (Button)findViewById(R.id.Button02);
        button02.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                tvAndroid123.setText("");

            }

        });
        button03 = (Button)findViewById(R.id.gocheck);
        button03.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {
                num = (EditText)findViewById(R.id.num);
                String number=num.getText().toString();
                Day = (EditText)findViewById(R.id.day);
                String day = Day.getText().toString();
                String context = "";

                int jud=0;
                int range=0;
                try {
                    context = check(number);
                    String[] ct2 = context.split(",|、");

                    int enter = 0;
                    jud = 0;

                    if(number.length()==8&&day.length()==5) {
                        if (day.equals(ym.substring(0, 5)) || day.equals(ym.substring(5, 10)) || day.equals(ym.substring(10, 15)) || day.equals(ym.substring(15, 20))) {
                            if (day.equals(ym.substring(0, 5)) || day.equals(ym.substring(5, 10))) {
                                range = 1;
                            } else {
                                range = 2;
                            }
                            int cnt = 0;
                            for (String target : ct2) {
                                if (target.length() == 3) {
                                    if (target.equals(number.substring(5, 8))) {
                                        context = "增開六獎";
                                        break;
                                    }
                                    cnt = -1;
                                } else {
                                    if (cnt == 0) {

                                        if (enter == 0 && range == 1) {
                                            jud = 1;
                                        } else if (enter == 1 && range == 2) {
                                            jud = 1;
                                        }
                                        enter++;
                                        //     context = target + "," + number+','+Integer.toString(enter) ;
                                        if (target.equals(number) && jud == 1) {
                                            context = "特別獎";
                                            break;
                                        }
                                    } else if (cnt == 1) {
                                        if (target.equals(number) && jud == 1) {
                                            context = "特獎";
                                            break;
                                        }
                                    } else if (cnt == 2 || cnt == 3 || cnt == 4) {
                                        if (target.equals(number) && jud == 1) {
                                            context = "頭獎";
                                            break;
                                        } else if (target.substring(1, 8).equals(number.substring(1, 8)) && jud == 1) {
                                            context = "二獎";
                                            break;
                                        } else if (target.substring(2, 8).equals(number.substring(2, 8)) && jud == 1) {
                                            context = "三獎";
                                            break;
                                        } else if (target.substring(3, 8).equals(number.substring(3, 8)) && jud == 1) {
                                            context = "四獎";
                                            break;
                                        } else if (target.substring(4, 8).equals(number.substring(4, 8)) && jud == 1) {
                                            context = "五獎";
                                            break;
                                        } else if (target.substring(5, 8).equals(number.substring(5, 8)) && jud == 1) {
                                            context = "六獎";
                                            break;
                                        } else {
                                            if (jud == 1)
                                                context = "未中獎";
                                        }
                                    }
                                }

                                cnt++;
                            }
                        } else {
                            if (day.substring(0, 3).equals(ym.substring(5, 8))) {
                                if (Integer.valueOf(day.substring(3, 5)) > Integer.valueOf(ym.substring(8, 10))) {
                                    context = "媽祖叫你別急，兌獎時間還沒到!";
                                }

                            } else if (Integer.valueOf(day.substring(0, 3)) > Integer.valueOf(ym.substring(5, 8))) {
                                context = "媽祖叫你別急，兌獎時間還沒到!";
                            } else if (Integer.valueOf(day.substring(0, 3)) < Integer.valueOf(ym.substring(10, 13))) {
                                context = "過期啦QQ";
                            } else if (Integer.valueOf(day.substring(0, 3)) == Integer.valueOf(ym.substring(10, 13))) {
                                if (Integer.valueOf(day.substring(3, 5)) < Integer.valueOf(ym.substring(13, 15))) {
                                    context = "過期啦QQ";
                                }
                            }
                            // context = "未在兌獎期限內";
                        }
                        if (range == 1)
                            context = ym.substring(0, 3) + '年' + ym.substring(3, 5) + '-' + ym.substring(8, 10) + '月' + ' ' + context;
                        if (range == 2)
                            context = ym.substring(10, 13) + '年' + ym.substring(13, 15) + '-' + ym.substring(18, 20) + '月' + ' ' + context;
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }


                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Result:");
                if(number.length()==0&&day.length()==0)
                {
                    dialog.setMessage("未輸入發票號碼和日期");
                }
                else if(number.length()==0&&day.length()!=0)
                {
                    dialog.setMessage("未輸入發票號碼");
                }
                else if(number.length()!=0&&day.length()==0)
                {
                    dialog.setMessage("未輸入日期");
                }
                else if(number.length()!=8)
                {
                    dialog.setMessage("發票號碼需輸入8個數字");
                }
                else if(day.length()!=5)
                {
                    dialog.setMessage("日期格式為YYYMM 共5個數字");
                }
                else
                    dialog.setMessage(context);
                dialog.show();


            }

        });

    }



    public void GoScan()
    {
        new IntentIntegrator(this)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                .setPrompt("請對準二維碼")// 设置提示语
                .setCameraId(0)// 选择摄像头,可使用前置或者后置
                .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
                .setBarcodeImageEnabled(true)// 扫完码之后生成二维码的图片
                .initiateScan();// 初始化扫码
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
   //             Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                String response;
                String number = result.getContents().substring(2, 10); // 統一發票 8碼
                String anumber = result.getContents().substring(0, 10); // 統一發票 10碼
                String day   =  result.getContents().substring(10, 15); // 年月

                String date  = result.getContents().substring(10, 17); // 完整日期
                String tl = result.getContents().substring(29,37); // 總金額
                //writeInfo("a.txt",number);

                int j= 1;
                int sum = 0;
                for(int i=7;i>=0;i--)
                {
                    String temp = tl.substring(i,i+1);
                    if(temp.equals("a")||temp.equals("A"))
                    {
                        temp = "10";
                    }
                    if(temp.equals("b")||temp.equals("B"))
                    {
                        temp = "11";
                    }
                    if(temp.equals("c")||temp.equals("C"))
                    {
                        temp = "12";
                    }
                    if(temp.equals("d")||temp.equals("D"))
                    {
                        temp = "13";
                    }
                    if(temp.equals("e")||temp.equals("E"))
                    {
                        temp = "14";
                    }
                    if(temp.equals("f")||temp.equals("F"))
                    {
                        temp = "15";
                    }
                    int intvalue = Integer.valueOf(temp);
                    sum += (intvalue*j);
                    j*=16;
                }
                String  sumsum = String.valueOf(sum);
                response = date+"\t"+anumber+"\t"+sumsum+"\n";

                int flag = 1;
                try {
                    String repeatCheck = readInfo("a.txt");

                    String line[] = repeatCheck.split("\n");

                    for (String i : line) {
                        String jj[] = i.split("\t");
                        if (anumber.equals(jj[1])) {
                            flag = 0;
                            break;
                        }
                    }
                }
                catch(Exception e)
                {
                    // do nothing
                }
                if(flag==1)
                    writeInfo("a.txt",response);

                //Toast.makeText(this, "Load: " + tl, Toast.LENGTH_LONG).show();

                String context = null;
                int jud=0;
                int range=0;
                try {
                    context = check(number);
                    String[] ct2 = context.split(",|、");

                    int enter = 0;
                    jud = 0;

                    //test = day + ","+ ym;
                    if(day.equals(ym.substring(0,5))||day.equals(ym.substring(5,10))||day.equals(ym.substring(10,15))||day.equals(ym.substring(15,20))) {
                        if (day.equals(ym.substring(0, 5)) || day.equals(ym.substring(5, 10))) {
                            range = 1;
                        }
                        else
                        {
                            range = 2;
                        }
                        int cnt = 0;
                        for (String target : ct2) {
                            if (target.length() == 3) {
                                if (target.equals(number.substring(5, 8))) {
                                    context = "增開六獎";
                                    break;
                                }
                                cnt = -1;
                            } else {
                                if (cnt == 0) {

                                    if (enter == 0 && range == 1) {
                                        jud = 1;
                                    }
                                    else if (enter == 1 && range == 2) {
                                        jud = 1;
                                    }
                                    enter++;
                                    //     context = target + "," + number+','+Integer.toString(enter) ;
                                    if (target.equals(number)&&jud==1) {
                                        context = "特別獎";
                                        break;
                                    }
                                } else if (cnt == 1) {
                                    if (target.equals(number)&&jud == 1) {
                                        context = "特獎";
                                        break;
                                    }
                                } else if (cnt == 2 || cnt == 3 || cnt == 4) {
                                    if (target.equals(number)&&jud == 1) {
                                        context = "頭獎";
                                        break;
                                    } else if (target.substring(1, 8).equals(number.substring(1, 8))&&jud == 1) {
                                        context = "二獎";
                                        break;
                                    } else if (target.substring(2, 8).equals(number.substring(2, 8))&&jud == 1) {
                                        context = "三獎";
                                        break;
                                    } else if (target.substring(3, 8).equals(number.substring(3, 8))&&jud == 1) {
                                        context = "四獎";
                                        break;
                                    } else if (target.substring(4, 8).equals(number.substring(4, 8))&&jud == 1) {
                                        context = "五獎";
                                        break;
                                    } else if (target.substring(5, 8).equals(number.substring(5, 8))&&jud == 1) {
                                        context = "六獎";
                                        break;
                                    }
                                    else {
                                        if(jud == 1)
                                            context = "未中獎";
                                    }
                                }
                            }

                            cnt++;
                        }
                    }
                    else
                    {
                        if(day.substring(0,3).equals(ym.substring(5,8)))
                        {
                            if(Integer.valueOf(day.substring(3,5))>Integer.valueOf(ym.substring(8,10)))
                            {
                                context = "媽祖叫你別急，兌獎時間還沒到!";
                            }

                        }
                        else if(Integer.valueOf(day.substring(0,3))>Integer.valueOf(ym.substring(5,8)))
                        {
                            context = "媽祖叫你別急，兌獎時間還沒到!";
                        }
                        else if(Integer.valueOf(day.substring(0,3))<Integer.valueOf(ym.substring(10,13)))
                        {
                            context = "過期啦QQ";
                        }
                        else if(Integer.valueOf(day.substring(0,3))==Integer.valueOf(ym.substring(10,13)))
                        {
                            if(Integer.valueOf(day.substring(3,5))<Integer.valueOf(ym.substring(13,15)))
                            {
                                context = "過期啦QQ";
                            }
                        }
                        // context = "未在兌獎期限內";
                    }



                }catch (IOException e) {
                    e.printStackTrace();
                }

                if(range==1)
                    context=ym.substring(0,3)+'年'+ym.substring(3,5)+'-'+ym.substring(8,10)+'月'+' '+context;
                if(range==2)
                    context=ym.substring(10,13)+'年'+ym.substring(13,15)+'-'+ym.substring(18,20)+'月'+' '+context;

                if(flag==0)
                {
                    context = "重複掃描~~~\n"+ context ;
                }


                //writeInfo("a.txt",tl);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Result:");
                dialog.setMessage( context);
                dialog.show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void writeInfo(String fileName, String strWrite)
    {
        try {

            String fullPath = getFilesDir().getAbsolutePath();
            String savePath = fullPath + File.separator + "/"+fileName+".txt";

            File file = new File(savePath);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(strWrite);


            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readInfo(String fileName){

        BufferedReader br = null;
        String response = null;

        try {
            StringBuffer output = new StringBuffer();
            String fullPath = getFilesDir().getAbsolutePath();
            String savePath = fullPath + File.separator + "/"+fileName+".txt";

            br = new BufferedReader(new FileReader(savePath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line +"\n");
            }
            response = output.toString();
            br.close();

        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return response;
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

            return  str;
        }catch(Exception e) {
            //Log.i("mytag", e.toString());
            return e.toString();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
