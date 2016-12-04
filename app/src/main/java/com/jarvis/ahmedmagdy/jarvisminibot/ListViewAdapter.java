package com.jarvis.ahmedmagdy.jarvisminibot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jarvis.ahmedmagdy.jarvisminibot.Models.ChatBubble;

import java.util.ArrayList;

/**
 * Created by ahmedmagdy on 11/29/16.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ChatBubble> dataSource;

    public ListViewAdapter(Context context, ArrayList<ChatBubble> chats) {
        this.context = context;
        this.dataSource = chats;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView;
        TextView messageTV;
        if (dataSource.get(i).isLeft()) {
            rowView = inflater.inflate(R.layout.view_left_bubble, viewGroup, false);
            messageTV = (TextView) rowView.findViewById(R.id.view_left_bubble_tv);
        } else {
            rowView = inflater.inflate(R.layout.view_right_bubble, viewGroup, false);
            messageTV = (TextView) rowView.findViewById(R.id.view_right_bubble_tv);
        }
        messageTV.setText(dataSource.get(i).getMessage());

        return rowView;
    }

}
