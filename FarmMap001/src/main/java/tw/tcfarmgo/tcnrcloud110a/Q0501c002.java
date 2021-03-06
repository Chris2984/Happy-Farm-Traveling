package tw.tcfarmgo.tcnrcloud110a;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Q0501c002 extends AppCompatActivity implements View.OnClickListener {

    private Intent intent = new Intent();
    private Intent intent02=new Intent();

    private EditText e101,e102,e103,e104,e111;
    private Button b101,b102,b103,b104,b105,b106,b107,b108,b109,b110;


    private Q0501DBhlper dbHelper;
    private static final String DB_File = "friends.db";
    private static final String DB_TABLE = "q0501table";
    private static final int DBversion = 1;

    //    private static ContentResolver mContRes;
    private String[] MYCOLUMN = new String[]{"id", "name", "tel", "text1", "text2"};
    int tcount;

    // ------------------
    public static String myselection = "";
    public static String myorder = "id ASC"; // ????????????
    public static String myargs[] = new String[]{};

    private ArrayList<String> recSet;
    private int index=0;
    private String msg;
    private ListView lv001;
    private TextView tsub,t112,t114,t115;
    private RelativeLayout rl01;
    private LinearLayout ll32,ll34;
    private String e_id,e_name,e_tel,e_text1,e_text2;
    private int old_index;
    private String sqlctl;
    private int rowsAffected;
    private int servermsgcolor;
    private String ser_msg;
    private Spinner s002;
    private Intent intent023=new Intent();
    private String t_name,t_tel,t_text1,t_text2;
    private Button b111,b112;
    private Geocoder geocoder;
    private int nowposition;
    private double latitude,longitude;
    private Uri utel;
    private Intent Utel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableStrictMode(this);//use ????????????????????????????????????
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q0501_c001);
        //----?????????page??????---
        Intent intent02=getIntent();
        setTitle(intent02.getStringExtra("class_title"));

        Intent intent032=getIntent();
        setTitle(intent032.getStringExtra("class_title"));

        Intent intent52=getIntent();
        setTitle(intent52.getStringExtra("class_title"));

        setupViewComponent();
    }


    private void setupViewComponent() {

        //--editview,????????????
        e101=(EditText)findViewById(R.id.q0501_e101);//??????
        e102=(EditText)findViewById(R.id.q0501_e102);//??????
        e103=(EditText)findViewById(R.id.q0501_e103);//??????1
        e104=(EditText)findViewById(R.id.q0501_e104);//??????2
        e111=(EditText)findViewById(R.id.q0501_e111);//ID

        lv001 = (ListView) findViewById(R.id.q0501_lv001);//in the page of searching, as listview
        tsub = (TextView) findViewById(R.id.q0501_c001_subtitle);//in the page of searching, as subtitle
        t112 = (TextView) findViewById(R.id.q0501_t112); //textView, "total"
        t114 = (TextView)findViewById(R.id.q0501_t114); //server message, if connect well...
        t115 = (TextView)findViewById(R.id.q0501_t115); //title,
        rl01 = (RelativeLayout) findViewById(R.id.q0501_rl01); //the page of tying wonderlist
        ll32 = (LinearLayout) findViewById(R.id.q0501_ll32);//the page of searching
        ll34 = (LinearLayout) findViewById(R.id.q0501_ll34);//the page of after searching

        b101=(Button)findViewById(R.id.q0501_b101);//??????
        b102=(Button)findViewById(R.id.q0501_b102);//??????
        b103=(Button)findViewById(R.id.q0501_b103);//??????
        b104=(Button)findViewById(R.id.q0501_b104);//??????
        b105=(Button)findViewById(R.id.q0501_b105);//?????????
        b106=(Button)findViewById(R.id.q0501_b106);//?????????
        b107=(Button)findViewById(R.id.q0501_b107);//??????
        b108=(Button)findViewById(R.id.q0501_b108);//??????
        b109=(Button)findViewById(R.id.q0501_b109);//??????
        b110=(Button)findViewById(R.id.q0501_b110);//??????
        b111=(Button)findViewById(R.id.q0501_b111);//??????????????????
        b112=(Button)findViewById(R.id.q0501_b112);//?????????

        b101.setOnClickListener(this);
        b102.setOnClickListener(this);
        b103.setOnClickListener(this);
        b104.setOnClickListener(this);
        b105.setOnClickListener(this);
        b106.setOnClickListener(this);
        b107.setOnClickListener(this);
        b108.setOnClickListener(this);
        b109.setOnClickListener(this);
        b110.setOnClickListener(this);
        b111.setOnClickListener(this);
        b112.setOnClickListener(this);

        //??????Geocorder??????,??????????????????????????????(most important)
        geocoder=new Geocoder(this, Locale.TAIWAN);

        //spinner???clicklistener
        s002 = (Spinner)findViewById(R.id.q0501_spinner);
        s002.setOnItemSelectedListener(mSpnNameOnItemSelLis);
        s002.setVisibility(View.VISIBLE);//????????????????????????????????????

        // ?????????????????? ?????????????????????????????????
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int buttonwidth = displayMetrics.widthPixels /4;
        int buttonwidth2 = displayMetrics.widthPixels /2;

        b103.getLayoutParams().width=buttonwidth;
        b102.getLayoutParams().width=buttonwidth;
        b111.getLayoutParams().width=buttonwidth2;
        b112.getLayoutParams().width=buttonwidth2;

        u_layout_def2();// the first page layout appearance
        initDB();
        showRec(index);

        //?????????ID??????
        e111.setTextColor(ContextCompat.getColor(this, R.color.Red));
        e111.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow_softness));

        //--text1, text2 clean, ??????,???spinner?????????
        e103.setText("");
        e104.setText("");
        e101.setText("");
        e102.setText("");

        //tvtitle. ???????????? (????????????/?????????)
        t115.setTextColor(ContextCompat.getColor(this, R.color.Teal));
        t115.setText("??????????????? ???" + tcount + " ???");

        u_setspinner();
        rl01.setVisibility(View.VISIBLE);
        ll32.setVisibility(View.INVISIBLE);
    }

    private void u_setspinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        for (int i = 0; i < recSet.size(); i++) {
            String[] fld = recSet.get(i).split("#");
            adapter.add(fld[0] + " " + fld[1] + " " + fld[2] + " " + fld[3]+ " " + fld[4]);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s002.setAdapter(adapter);
        s002.setOnItemSelectedListener(mSpnNameOnItemSelLis);
    }

    private void initDB() {
        if (dbHelper == null) {
            dbHelper = new Q0501DBhlper(this, DB_File, null, DBversion);//???????????????
        }
        dbmysql();
        recSet = dbHelper.getRecSet_Q0501();
    }

    private void u_layout_def2() {
        b101.setVisibility(View.INVISIBLE);//??????
        b102.setVisibility(View.VISIBLE);//??????
        b103.setVisibility(View.VISIBLE);//??????
        b108.setVisibility(View.INVISIBLE);//??????
        b109.setVisibility(View.INVISIBLE);//??????
        b104.setVisibility(View.INVISIBLE);
        b105.setVisibility(View.INVISIBLE);
        b106.setVisibility(View.INVISIBLE);
        b107.setVisibility(View.INVISIBLE);
        s002.setVisibility(View.VISIBLE); //??????spinner
        t115.setVisibility(View.VISIBLE); //??????

        e111.setEnabled(false); //ID????????????
        s002.setEnabled(false); //spinner??????

    }

    private AdapterView.OnItemSelectedListener mSpnNameOnItemSelLis = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int iSelect = s002.getSelectedItemPosition();//???????????????
            String[] fld = recSet.get(iSelect).split("#");
            String s = "??????:???" + recSet.size() + "???," + "????????????" + String.valueOf(iSelect + 1) + "???";//?????????0
            t115.setText(s);
            index = position;
            showRec(index);
            iSelect = index;

            if (b103.getVisibility() == View.VISIBLE) { //b103?????????
                e101.setText("");
                e102.setText("");
                e103.setText("");
                e104.setText("");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {  //parent,
            // TODO Auto-generated method stub
            e101.setText("");
            e102.setText("");
            e103.setText("");
            e104.setText("");
        }
    };

    private void showRec(int index) {

        msg = "";
        if (recSet.size() != 0) {
            String stHead = "?????????????????? " + (index + 1) + " ??? / ??? " + recSet.size() + " ???";
            msg = getString(R.string.q0501_t112) + recSet.size() + "???";
            t115.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow_softness));
            t115.setTextColor(ContextCompat.getColor(this, R.color.Teal));
            t115.setText(stHead);

            String[] fld = recSet.get(index).split("#");
            e111.setTextColor(ContextCompat.getColor(this, R.color.Red));
            e111.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow));
            e111.setText(fld[0]);//ID
            e101.setText(fld[1]);//??????
            e102.setText(fld[2]);//??????
            e103.setText(fld[3]);//??????1
            e104.setText(fld[4]);//??????2

            if(index == -1){
                Toast.makeText(getApplicationContext(), "int index is null", Toast.LENGTH_SHORT).show();
            }else {
                s002.setSelection(index, true);
            }
        } else {
            String stHead = "???????????????0 ???";
            t115.setText(stHead);
            e111.setText("");//ID
            e101.setText("");//??????
            e102.setText("");//??????
            e103.setText("");//??????1
            e104.setText("");//??????2
        }
        t112.setText(msg);
    }

    private void enableStrictMode(Context context) {
        //-------------????????????????????????????????????------------------------------
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                detectDiskReads().
                detectDiskWrites().
                detectNetwork().
                penaltyLog().
                build());
        StrictMode.setVmPolicy(
                new
                        StrictMode.
                                VmPolicy.
                                Builder().
                        detectLeakedSqlLiteObjects().
                        penaltyLog().
                        penaltyDeath().
                        build());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.q0501_b102://??????
                e101.setText("");
                e102.setText("");
                e103.setText("");
                e104.setText("");
                break;

            case R.id.q0501_b103://??????
                initDB();
                t_name = e101.getText().toString().trim();
                t_tel = e102.getText().toString().trim();
                t_text1 = e103.getText().toString().trim();
                t_text2 = e104.getText().toString().trim();
                msg = null;
                recSet = dbHelper.getRecSet_query_Q0501c002q(t_name,t_tel,t_text1,t_text2);

                //--------???SQLite ??????---------------
                List<Map<String, Object>> mList;
                mList = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < recSet.size(); i++) {
                    Map<String, Object> item = new HashMap<String, Object>();
                    String[] fld = recSet.get(i).split("#");
                    item.put("q0501_img020", R.drawable.land01);
                    item.put("txtView", "id:" + fld[0] + "\n??????:" + fld[1] + "\n??????:" + fld[2] + "\n??????1:" + fld[3] + "\n??????2:" + fld[4]);
                    mList.add(item);
                }
//=========??????listview========
                rl01.setVisibility(View.INVISIBLE);
                ll32.setVisibility(View.VISIBLE);

                SimpleAdapter adapter = new SimpleAdapter(
                        this,
                        mList,
                        R.layout.q0501_c002_list,
                        new String[]{"q0501_img020", "txtView"},
                        new int[]{R.id.q0501_img020, R.id.txtView}
                );
                lv001.setAdapter(adapter);
                lv001.setTextFilterEnabled(true);
                lv001.setOnItemClickListener(listviewOnItemClkLis);
                break;


            case R.id.q0501_b110://????????????
                initDB();
                tsub.setText("");
                old_index=s002.getSelectedItemPosition();
                recSet=dbHelper.getRecSet_Q0501();
                rl01.setVisibility(View.VISIBLE);
                ll32.setVisibility(View.INVISIBLE);
                rl01.setVisibility(View.VISIBLE);
                ll32.setVisibility(View.INVISIBLE);
                ll34.setVisibility(View.GONE);
                index=old_index;
                showRec(index);//??????spinner?????????????????????
                ctlLast();

                break;

            case R.id.q0501_b111: //??????????????????
                msg = null;
                recSet = dbHelper.getRecSet_query_Q0501c002n(t_name,t_tel,t_text1,t_text2);
                String addressName=recSet.get(nowposition);
                try{
                    //??????????????????GPS??????
                    //??????????????????????????????list??????
                    List<Address> listGPSAddress=geocoder.getFromLocationName(addressName,1);
                    // ????????????????????????
                    if (listGPSAddress != null) {
                        latitude = listGPSAddress.get(0).getLatitude();
                        longitude = listGPSAddress.get(0).getLongitude();

                    }
                }catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),"??????:"+ex.toString(),Toast.LENGTH_SHORT).show();
                }

                //??????google?????????????????????????????????
                // ??????URI??????
                String uri = String.format("geo:%f,%f?z=17", latitude, longitude);
                // ??????Intent??????
                Intent geoMap = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(geoMap);  // ????????????

                break;

            case R.id.q0501_b112: //????????????????????????
                recSet = dbHelper.getRecSet_query_Q0501c002t(t_tel);
                String call=recSet.get(nowposition);
                utel = Uri.parse("tel:"+call);
                Utel = new Intent(Intent.ACTION_DIAL, utel);
                startActivity(Utel);

                break;

        }

    }

    private ListView.OnItemClickListener listviewOnItemClkLis = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String s = "???????????? " + Integer.toString(position + 1) + "???"
                    + ((TextView) view.findViewById(R.id.txtView))
                    .getText()
                    .toString();
            ll34.setVisibility(View.VISIBLE);
            tsub.setText(s);
            //??????listview?????????
            nowposition=position;

        }
    };

    private void dbmysql() {
        sqlctl = "SELECT * FROM q0501table ORDER BY id ASC";
        ArrayList<String> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(sqlctl);
        try {
            String result = Q0501DBConnector.executeQuery_Q0501(nameValuePairs);
//----------------
            chk_httpstate();  //?????? ????????????
//-------------------------------------
            JSONArray jsonArray = new JSONArray(result);
            // -------------------------------------------------------
            if (jsonArray.length() > 0) { // MySQL ?????????????????????
                rowsAffected = dbHelper.clearRec_Q0501();                 // ?????????,????????????SQLite??????
                // ??????JASON ????????????????????????
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    ContentValues newRow = new ContentValues();
                    // --(1) ?????????????????? --?????? jsonObject ????????????("key","value")-----------------------
                    Iterator itt = jsonData.keys();
                    while (itt.hasNext()) {
                        String key = itt.next().toString();
                        String value = jsonData.getString(key); // ??????????????????
                        if (value == null) {
                            continue;
                        } else if ("".equals(value.trim())) {
                            continue;
                        } else {
                            jsonData.put(key, value.trim());
                        }
                        // ------------------------------------------------------------------
                        newRow.put(key, value.toString()); // ???????????????????????????
                        // -------------------------------------------------------------------
                    }
                    // ---(2) ????????????????????????---------------------------
                    // newRow.put("id", jsonData.getString("id").toString());
                    // newRow.put("name",
                    // jsonData.getString("name").toString());
                    // newRow.put("grp", jsonData.getString("grp").toString());
                    // newRow.put("address", jsonData.getString("address")
                    // -------------------??????SQLite---------------------------------------
                    long rowID = dbHelper.insertRec_m_Q0501(newRow);
                    //Toast.makeText(getApplicationContext(), "????????? " + Integer.toString(jsonArray.length()) + " ?????????", Toast.LENGTH_SHORT).show();
                }
               // Toast.makeText(getApplicationContext(), "????????? " + Integer.toString(jsonArray.length()) + " ?????????", Toast.LENGTH_SHORT).show();
                // ---------------------------
            } else {
                Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_LONG).show();
            }
            recSet = dbHelper.getRecSet_Q0501();  //????????????SQLite
            u_setspinner();
            // --------------------------------------------------------
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    private void chk_httpstate() {
//**************************************************
//*       ??????????????????
//**************************************************
        //?????????????????? DBConnector01.httpstate ?????????????????? 200(??????????????????)
        servermsgcolor = ContextCompat.getColor(this, R.color.Navy);

        if (Q0501DBConnector.httpstate == 200) {
            ser_msg = "?????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
            servermsgcolor = ContextCompat.getColor(this, R.color.Navy);
//                Toast.makeText(getBaseContext(), "???????????????????????? ",
//                        Toast.LENGTH_SHORT).show();
        } else {
            int checkcode = Q0501DBConnector.httpstate / 100;
            switch (checkcode) {
                case 1:
                    ser_msg = "????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
                case 2:
                    ser_msg = "????????????????????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
                case 3:
                    ser_msg = "??????????????????????????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
                case 4:
                    ser_msg = "???????????????????????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
                case 5:
                    ser_msg = "?????????error responses??????????????????(code:" + Q0501DBConnector.httpstate + ") ";
                    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
                    break;
            }
//                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
        }

        if (Q0501DBConnector.httpstate == 0) {
            ser_msg = "?????????????????????(code:" + Q0501DBConnector.httpstate + ") ";
            servermsgcolor = ContextCompat.getColor(this, R.color.Red);
        }
        t114.setText(ser_msg);
        t114.setTextColor(servermsgcolor);
        //    servermsgcolor = ContextCompat.getColor(this, R.color.Red);
        //-------------------------------------------------------------------
    }

    private void ctlLast() {
        //???????????? origin recSet.size()-1
        index = recSet.size()-1 ;
        showRec(index);
    }
////---------------------------------


    //------????????????-------
    @Override
    protected void onStop() {
        super.onStop();
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDB();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //------------------------------------------Menu--------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.q0501_menu_sub ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.q0501_msub_action_settings:
                this.finish();
                break;
            case R.id.q0501_msub_r001://?????????  with ??????
                Toast.makeText(getApplicationContext(), "??????????????????~", Toast.LENGTH_SHORT).show();
                break;

            case R.id.q0501_msub_u001://????????????
                    intent023.putExtra("class_title",getString(R.string.q0501_t903));
                    intent023.setClass(Q0501c002.this, Q0501c003.class);
                    startActivity(intent023);
                    this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}