package com.example.user.ringtonetestapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.ringtonetestapp.rest.ApiClient;
import com.example.user.ringtonetestapp.rest.ApiInterface;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<BBRing> BBCeleblist;
    MediaPlayer mPlayer;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        listView = (ListView) findViewById( R.id.ringtonelistview );
        mContext = getApplicationContext();

        check();

    }

    public void check() {
        if (isNetworkAvailable()) {

            showProgDialog();
            getBWceleb();
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getBWceleb() {

        ApiInterface apiService = ApiClient.getClient().create( ApiInterface.class );
        Call<List<BBRing>> call = apiService.getceleb( "1" );
        call.enqueue( new Callback<List<BBRing>>() {
            @Override
            public void onResponse(Call<List<BBRing>> call, Response<List<BBRing>> response) {
                if (pDialog.isShowing())
                    pDialog.dismiss();

                BBCeleblist = response.body();
                if (BBCeleblist != null) {
                    Log.d( TAG, "nmmer of celeb received" + BBCeleblist.size() );
                    BBadpter badpter = new BBadpter( MainActivity.this, BBCeleblist );
                    listView.setAdapter( badpter );
                    listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            String audioUrl = BBCeleblist.get( position ).getPost_audio();
                            mPlayer = new MediaPlayer();
                            mPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC );
                            try {
                                ImageView imageView = (ImageView) view.findViewById( R.id.playimage );
                                ImageView imageView2 = (ImageView) view.findViewById( R.id.plauseimage );
                                imageView.setVisibility( View.INVISIBLE );
                                imageView = (ImageView) view.findViewById( R.id.playimage );
                                imageView2 = (ImageView) view.findViewById( R.id.plauseimage );
                                mPlayer.setDataSource( audioUrl );
                                mPlayer.prepare();
                                mPlayer.start();
                                Toast.makeText( mContext, "Playing", Toast.LENGTH_SHORT ).show();
                                imageView2.setVisibility( View.VISIBLE );
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }

                            mPlayer.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    Toast.makeText( mContext, "End", Toast.LENGTH_SHORT ).show();


                                }
                            } );
                            final ImageView imageView = (ImageView) view.findViewById( R.id.playimage );
                            final ImageView imageView2 = (ImageView) view.findViewById( R.id.plauseimage );

                            ImageView imageView3 = (ImageView) view.findViewById( R.id.settings );
                            imageView3.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent( MainActivity.this, Option.class );
                                    intent.putExtra( "Name", BBCeleblist.get( position ).getPost_title() );
                                    intent.putExtra( "Songs", BBCeleblist.get( position ).getPost_audio() );
                                    startActivity( intent );
                                }
                            } );


                            imageView2.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (mPlayer.isPlaying()) {
                                        mPlayer.pause();
                                        imageView.setVisibility( View.VISIBLE );
                                        imageView2.setVisibility( View.INVISIBLE );
                                    } else {

                                        mPlayer.start();
                                        imageView.setVisibility( View.INVISIBLE );
                                        imageView2.setVisibility( View.VISIBLE );
                                    }

                                }
                            } );

                        }


                    } );
                }
            }

            ;


            @Override
            public void onFailure(Call<List<BBRing>> call, Throwable t) {

            }
        } );

    }

    private void showProgDialog() {
        pDialog = new ProgressDialog( MainActivity.this );
        pDialog.setMessage( "please wait......." );
        pDialog.setCancelable( false );
        pDialog.show();
    }


}
