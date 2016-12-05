package com.jarvis.ahmedmagdy.jarvisminibot;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jarvis.ahmedmagdy.jarvisminibot.API.ChatBody;
import com.jarvis.ahmedmagdy.jarvisminibot.API.ChatResponse;
import com.jarvis.ahmedmagdy.jarvisminibot.API.ChatbotService;
import com.jarvis.ahmedmagdy.jarvisminibot.API.WelcomeResponse;
import com.jarvis.ahmedmagdy.jarvisminibot.Models.ChatBubble;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<ChatResponse> {
    private String uuid;
//    private Button sendBtn;
    private EditText chatEt;
    private ChatbotService chatbotService;
    private ListView chatBubblesListView;
    private ArrayList<ChatBubble> chatBubbles;
    private ListViewAdapter adapter;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        sendWelcomeRequest();
    }

    private void sendWelcomeRequest() {
        chatbotService = ChatbotService.retrofit.create(ChatbotService.class);
        Call<WelcomeResponse> call = chatbotService.welcome();
        call.enqueue(new Callback<WelcomeResponse>() {
            @Override
            public void onResponse(Call<WelcomeResponse> call, Response<WelcomeResponse> response) {
                uuid = response.body().uuid;
                chatBubbles.add(new ChatBubble(response.body().message, true, false));
                adapter.notifyDataSetChanged();
                fab.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<WelcomeResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initViews() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        progressBar = (ProgressBar) findViewById(R.id.main_progress_bar);
//        sendBtn = (Button) findViewById(R.id.chat_send_btn);
        chatEt = (EditText) findViewById(R.id.chat_edit_text);
        chatBubblesListView = (ListView) findViewById(R.id.chat_bubbles_lv);
//        sendBtn.setOnClickListener(this);
        fab.setOnClickListener(this);
        chatBubbles = new ArrayList<>();
        adapter = new ListViewAdapter(this, chatBubbles);
        chatBubblesListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        String message = chatEt.getText().toString();
        if (uuid != null) {
            if (!message.equals("")) {
                ChatBody chatBody = new ChatBody("mobileSession/" + message);
                Call<ChatResponse> call = chatbotService.chat(chatBody, uuid);
                call.enqueue(this);
                chatEt.setText("");
                chatBubbles.add(new ChatBubble(message, false, false));
                progressBar.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                chatBubblesListView.smoothScrollToPosition(chatBubbles.size());
            } else {
                Toast.makeText(getApplicationContext(), "Please write a message first", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
        Log.i("hamada", response.code()+ "" );
        if(response.code() < 200 || response.code() > 299){
            try {
                String message = response.errorBody().string();
                progressBar.setVisibility(View.INVISIBLE);
                chatBubbles.add(new ChatBubble(message, true, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String message = response.body().toString();
            progressBar.setVisibility(View.INVISIBLE);
            chatBubbles.add(new ChatBubble(message, true, false));
        }
            adapter.notifyDataSetChanged();
            chatBubblesListView.smoothScrollToPosition(chatBubbles.size());
    }

    @Override
    public void onFailure(Call<ChatResponse> call, Throwable t) {

    }
}
