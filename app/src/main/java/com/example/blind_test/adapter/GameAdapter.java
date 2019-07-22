package com.example.blind_test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.blind_test.R;
import com.example.blind_test.model.Lobbies;

import java.util.List;

public class GameAdapter extends ArrayAdapter<Lobbies> {

    public GameAdapter(Context context, List<Lobbies> lobbies) {
        super(context, 0, lobbies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lobby_card, parent, false);
        }

        GameViewHolder viewHolder = (GameViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new GameViewHolder();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.description = convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);

        }

        Lobbies lobby = getItem(position);

        viewHolder.title.setText(lobby.getTheme_title());
        viewHolder.description.setText(lobby.getTheme_description());


        return convertView;
    }

    private class GameViewHolder{
        public TextView title;
        public TextView description;
    }
}