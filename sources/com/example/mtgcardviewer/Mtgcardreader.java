package com.example.mtgcardviewer;

import android.app.Activity;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Mtgcardreader extends Activity implements OnItemSelectedListener {
    String additional = "&";
    String artifact = "[Artifact]";
    String baseurl = "http://gatherer.wizards.com/Pages/Search/Default.aspx?output=spoiler&method=text&";
    String black = "[B]";
    CheckBox blackBox;
    String blue = "[U]";
    CheckBox blueBox;
    WebView browser;
    String cardColour = "color=";
    String cardFormat = "format=";
    String cardSet = "set=";
    String cardSubtype = "subtype=+";
    String cardText = "text=+";
    String cardType = "type=+";
    Elements content;
    String creature = "[Creature]";
    Document doc;
    Thread downloadThread;
    String enchantment = "[Enchantment]";
    String finalUrl;
    String green = "[G]";
    CheckBox greenBox;
    Elements group;
    String instant = "[Instant]";
    String land = "[Land]";
    CheckBox matchExactly;
    EditText name;
    Elements pageNums;
    String planeswalker = "[Planeswalker]";
    String red = "[R]";
    CheckBox redBox;
    String sorcery = "[Sorcery]";
    Spinner spinnerFormat;
    Spinner spinnerSet;
    Spinner spinnerType;
    RadioButton textFieldName;
    RadioButton textFieldText;
    RadioButton textFieldType;
    WebViewClient webClient = new C00551();
    String white = "[W]";
    CheckBox whiteBox;

    /* renamed from: com.example.mtgcardviewer.Mtgcardreader$1 */
    class C00551 extends WebViewClient {
        C00551() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    public class backgroundDATA extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... params) {
            try {
                Mtgcardreader.this.doc = Jsoup.connect(params[0]).timeout(3000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Mtgcardreader.this.browser.loadData("<html><body bgcolor=\"#dcd9c8\"><center><h2>Loading...</h2></center></body></html>", "text/html", null);
        }

        protected void onPostExecute(Void result) {
            if (Mtgcardreader.this.doc.toString().contains("div class=\"textspoiler\">")) {
                Mtgcardreader.this.content = Mtgcardreader.this.doc.select("div.textspoiler");
                String linksFixed = Mtgcardreader.this.content.toString().replace("../Card", "http://gatherer.wizards.com/Pages/Card");
                Log.d("htmlcode", linksFixed);
                Mtgcardreader.this.browser.setBackgroundColor(Color.parseColor("#dcd9c8"));
                Mtgcardreader.this.browser.loadData(linksFixed, "text/html; charset=UTF-8", null);
            } else if (Mtgcardreader.this.doc.toString().contains("table class=\"cardDetails\"")) {
                Mtgcardreader.this.browser.loadUrl(Mtgcardreader.this.finalUrl);
            } else {
                Mtgcardreader.this.browser.loadData("<html><body bgcolor=\"#dcd9c8\"><h3>Your search was not successful</h3></body></html>", "text/html", null);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0056R.layout.activity_mtgcardreader);
        this.spinnerType = (Spinner) findViewById(C0056R.id.spinner1);
        this.spinnerFormat = (Spinner) findViewById(C0056R.id.spinner2);
        this.spinnerSet = (Spinner) findViewById(C0056R.id.spinner3);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, C0056R.array.card_types, 17367048);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, C0056R.array.card_formats, 17367048);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, C0056R.array.card_sets, 17367048);
        adapter1.setDropDownViewResource(17367049);
        adapter2.setDropDownViewResource(17367049);
        adapter3.setDropDownViewResource(17367049);
        this.spinnerType.setAdapter(adapter1);
        this.spinnerFormat.setAdapter(adapter2);
        this.spinnerSet.setAdapter(adapter3);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0056R.menu.mtgcardreader, menu);
        return true;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void search(View view) throws IOException {
        if (isOnline()) {
            this.finalUrl = getUrl();
            setContentView(C0056R.layout.search_results);
            this.browser = (WebView) findViewById(C0056R.id.webView1);
            this.browser.setWebViewClient(this.webClient);
            this.browser.getSettings().setDefaultTextEncodingName("utf-8");
            new backgroundDATA().execute(new String[]{this.finalUrl});
            return;
        }
        Toast.makeText(getApplicationContext(), "No internet connection", 1).show();
    }

    public void goBack(View view) {
        setContentView(C0056R.layout.activity_mtgcardreader);
        this.spinnerType = (Spinner) findViewById(C0056R.id.spinner1);
        this.spinnerFormat = (Spinner) findViewById(C0056R.id.spinner2);
        this.spinnerSet = (Spinner) findViewById(C0056R.id.spinner3);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, C0056R.array.card_types, 17367048);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, C0056R.array.card_formats, 17367048);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, C0056R.array.card_sets, 17367048);
        adapter1.setDropDownViewResource(17367049);
        adapter2.setDropDownViewResource(17367049);
        adapter3.setDropDownViewResource(17367049);
        this.spinnerType.setAdapter(adapter1);
        this.spinnerFormat.setAdapter(adapter2);
        this.spinnerSet.setAdapter(adapter3);
    }

    public boolean isOnline() {
        NetworkInfo netInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public String getUrl() {
        String temp = this.baseurl;
        this.name = (EditText) findViewById(C0056R.id.cardName);
        this.blackBox = (CheckBox) findViewById(C0056R.id.checkBox1);
        this.blueBox = (CheckBox) findViewById(C0056R.id.checkBox2);
        this.greenBox = (CheckBox) findViewById(C0056R.id.checkBox3);
        this.redBox = (CheckBox) findViewById(C0056R.id.checkBox4);
        this.whiteBox = (CheckBox) findViewById(C0056R.id.checkBox5);
        this.matchExactly = (CheckBox) findViewById(C0056R.id.match_exactly);
        this.textFieldName = (RadioButton) findViewById(C0056R.id.radioButton1);
        this.textFieldType = (RadioButton) findViewById(C0056R.id.radioButton2);
        this.textFieldText = (RadioButton) findViewById(C0056R.id.radioButton3);
        if (this.name.getText().length() > 0) {
            String nameFixed = this.name.getText().toString().replaceAll(" ", "]+[");
            String firstPart = "";
            if (this.textFieldName.isChecked()) {
                firstPart = "name=+";
            }
            if (this.textFieldType.isChecked()) {
                firstPart = "subtype=+";
            }
            if (this.textFieldText.isChecked()) {
                firstPart = "text=+";
            }
            temp = new StringBuilder(String.valueOf(temp)).append(firstPart).append("[").append(nameFixed).append("]&").toString();
        }
        if (!this.spinnerType.getSelectedItem().equals("Card Type")) {
            temp = new StringBuilder(String.valueOf(temp)).append("type=+[").append(this.spinnerType.getSelectedItem().toString()).append("]&").toString();
        }
        if (!this.spinnerFormat.getSelectedItem().equals("Card Format")) {
            temp = new StringBuilder(String.valueOf(temp)).append("format=[").append("\"").append(this.spinnerFormat.getSelectedItem().toString().replaceAll(" ", "%20")).append("\"").append("]&").toString();
        }
        if (!this.spinnerSet.getSelectedItem().equals("Card Set")) {
            temp = new StringBuilder(String.valueOf(temp)).append("set=[").append("\"").append(this.spinnerSet.getSelectedItem().toString().replaceAll(" ", "%20")).append("\"").append("]&").toString();
        }
        if (this.blackBox.isChecked() || this.blueBox.isChecked() || this.greenBox.isChecked() || this.redBox.isChecked() || this.whiteBox.isChecked()) {
            temp = new StringBuilder(String.valueOf(temp)).append("color=").toString();
            String modifier = "|";
            if (this.matchExactly.isChecked()) {
                modifier = "+";
            }
            if (this.blackBox.isChecked()) {
                temp = new StringBuilder(String.valueOf(temp)).append(modifier).append(this.black).toString();
            }
            if (this.blueBox.isChecked()) {
                temp = new StringBuilder(String.valueOf(temp)).append(modifier).append(this.blue).toString();
            }
            if (this.greenBox.isChecked()) {
                temp = new StringBuilder(String.valueOf(temp)).append(modifier).append(this.green).toString();
            }
            if (this.redBox.isChecked()) {
                temp = new StringBuilder(String.valueOf(temp)).append(modifier).append(this.red).toString();
            }
            if (this.whiteBox.isChecked()) {
                temp = new StringBuilder(String.valueOf(temp)).append(modifier).append(this.white).toString();
            }
        }
        Log.d("url", temp);
        return temp;
    }
}
