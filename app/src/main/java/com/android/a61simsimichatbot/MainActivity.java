package com.android.a61simsimichatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.android.a61simsimichatbot.Adapter.CustomAdapter;
import com.android.a61simsimichatbot.Helper.HttpDataHelper;
import com.android.a61simsimichatbot.Model.ChatModel;
import com.android.a61simsimichatbot.Model.SimsimModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<ChatModel> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listMessage);
        editText = (EditText) findViewById(R.id.message);
        btn_send_message = (FloatingActionButton) findViewById(R.id.fab);

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                ChatModel chatModel = new ChatModel(text,true);
                list_chat.add(chatModel);
                new SimsimiAPI().execute(list_chat);

                //remove user message
                editText.setText("'");
            }
        });

    }

    private class SimsimiAPI extends AsyncTask<List<ChatModel>,Void,String> {

        String stream = null;
        List<ChatModel> models;
        String text = editText.getText().toString();

        @Override
        protected String doInBackground(List<ChatModel>... lists) {
            String url = String.format("http://sandbox.api.simsimi.com/request.p?key=%s&lc=id&ft=1.0&text=%s",getString(R.string.api));
            models = lists[0];
            HttpDataHelper httpDataHelper = new HttpDataHelper();
            stream = httpDataHelper.GetHttpData(url);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            SimsimModel response = gson.fromJson(s,SimsimModel.class);

            ChatModel chatModel = new ChatModel(response.getResponse(),false);
            models.add(chatModel);
            CustomAdapter adapter = new CustomAdapter(models,getApplicationContext());
            listView.setAdapter(adapter);
        }
    }
}