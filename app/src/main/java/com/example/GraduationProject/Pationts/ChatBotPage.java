package com.example.GraduationProject.Pationts;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.GraduationProject.Adapters.chatbotadapter;
import com.example.GraduationProject.R;
import com.example.GraduationProject.chatbot.ChatRequest;
import com.example.GraduationProject.chatbot.ChatResponse;
import com.example.GraduationProject.chatbot.OPenAi;
import com.example.GraduationProject.chatbot.RetrofitClient;
import com.example.GraduationProject.modules.chatbotpatient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatBotPage extends AppCompatActivity {


    private RecyclerView chatRecyclerView;
    private EditText inputMessage;
    private Button sendButton;
    private chatbotadapter chatAdapter;
    private List<chatbotpatient> chatMessages;

    private static final String OPENAI_API_KEY = "My_Test_Key"; // Replace with your API key
    private static final String MODEL = "gpt-3.5-turbo"; // Use the GPT-3.5 model
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_bot_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        inputMessage = findViewById(R.id.input_message);
        sendButton = findViewById(R.id.send_button);

        // Set up RecyclerView
        chatMessages = new ArrayList<>();
        chatAdapter = new chatbotadapter(chatMessages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);


        sendButton.setOnClickListener(view -> {
            String message = inputMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                // Add user message to the list
                chatMessages.add(new chatbotpatient(message, true));
                chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                chatRecyclerView.scrollToPosition(chatMessages.size() - 1);

                // Clear the input field
                inputMessage.setText("");

                // Simulate a bot response
                simulateBotResponse(message);
            }
        });
    }
    private void simulateBotResponse(String userMessage) {
        List<ChatRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatRequest.Message("user", userMessage));

        ChatRequest request = new ChatRequest(MODEL,messages);

        // Make the API call
        OPenAi api = RetrofitClient.getClient().create(OPenAi.class);
        Call<ChatResponse> call = api.getChatResponse("Bearer " + OPENAI_API_KEY, request);

        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String botResponse = response.body().getChoices().get(0).getMessage().getContent();

                    // Add bot response to the list
                    chatMessages.add(new chatbotpatient(botResponse, false));
                    chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                    chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
                } else {
                    Log.e("ChatBotActivity", "API call failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                Log.e("ChatBotActivity", "API call failed: " + t.getMessage());
            }
        });
    }
    }