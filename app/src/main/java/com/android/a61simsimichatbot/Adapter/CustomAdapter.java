package com.android.a61simsimichatbot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.a61simsimichatbot.Model.ChatModel;
import com.android.a61simsimichatbot.R;
import com.github.library.bubbleview.BubbleTextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<ChatModel> list_chat_model;
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomAdapter(List<ChatModel> list_chat_model, Context context) {
        this.list_chat_model = list_chat_model;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list_chat_model.size();
    }

    @Override
    public Object getItem(int i) {
        return list_chat_model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            if (list_chat_model.get(i).isSend){
                view = layoutInflater.inflate(R.layout.list_item_message_send,null);
            }else{
                view = layoutInflater.inflate(R.layout.list_item_message_receive,null);
            }

            BubbleTextView bubbleTextView = (BubbleTextView)view.findViewById(R.id.text_message);
            bubbleTextView.setText(list_chat_model.get(i).message);
        }

        return view;
    }
}
