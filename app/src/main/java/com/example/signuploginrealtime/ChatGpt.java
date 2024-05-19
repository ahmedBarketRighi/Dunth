package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatGpt extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textwelcome;
    EditText edtMessage;

    ImageButton btnsend;
    List<Message> messageList;

    MessageAdapter messageAdapter;

    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_gpt);

        recyclerView = findViewById(R.id.chat_rv);
        textwelcome = findViewById(R.id.txtWelcome);
        edtMessage = findViewById(R.id.message_edit_text);
        btnsend = findViewById(R.id.send_btn);


        ImageView btnlogoutgpt = findViewById(R.id.btnlogoutgpt);


        btnlogoutgpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        messageList = new ArrayList<>();

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = edtMessage.getText().toString().trim();
                addToChat(question, Message.SENT_BY_ME);
                edtMessage.setText("");
                CallAPI(question);
                textwelcome.setVisibility(View.GONE);
            }
        });


    }

    void addToChat(String message, String sentby) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                messageList.add(new Message(message, sentby));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());


            }
        });


    }


    void Addresponse(String response) {

        messageList.remove(messageList.size() - 1);
        addToChat(response, Message.SENT_BY_BOT);
    }


    void CallAPI(String question) {
        messageList.add(new Message("typing ...", Message.SENT_BY_BOT));
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("model", "gpt-3.5-turbo");
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("role", "user");
            jsonObject1.put("content", question); // Include the user's question
            jsonArray.put(jsonObject1);
            jsonObject.put("messages", jsonArray);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Rest of your code...


        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer sk-proj-UQeZZwx0LuDyEpUbRbvhT3BlbkFJFcPPx3GDTbrV350RFAwh")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Addresponse("falied to load message due to " + e.getMessage());
            }


            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                int responseCode = response.code();
                String responseBody = response.body().string();
                Log.d("API_RESPONSE", "Response code: " + responseCode);
                Log.d("API_RESPONSE", "Response body: " + responseBody);

                if (response.isSuccessful()) {
                    // Process successful response
                    try {
                        JSONObject jsonObject1 = new JSONObject(responseBody);
                        JSONArray jsonArray = jsonObject1.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        Addresponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else if (responseCode == 429) {
                    // Too many requests, retry after a delay
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    CallAPI(question);
                                }
                            }, 1000); // Retry after 1 second
                        }
                    });
                } else {
                    // Process unsuccessful response
                    Addresponse("Failed to get response. Response code: " + responseCode);
                }
            }

        });


    }


}