package com.example.user.ringtonetestapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BBadpter extends BaseAdapter {
    private Context context;
    List<BBRing> BBcelebList;

    public BBadpter(Context context, List<BBRing> BBcelebList) {
        this.context = context;
        this.BBcelebList = BBcelebList;
    }

    @Override
    public int getCount() {
        return BBcelebList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View listview;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if(view==null){
            listview = new View( context );
            listview=inflater.inflate( R.layout.single_ringtone,null );
            TextView textView = (TextView)listview.findViewById( R.id.textview );
            textView.setText( BBcelebList.get( position ).getPost_title() );}
            else{listview=view;}


        return listview;
    }
}
