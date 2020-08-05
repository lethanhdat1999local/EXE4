package com.example.appnhac;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Activity_bai_hat extends AppCompatActivity {

    TextView txt_tencasi, txt_tenbaihatdangphat, txt_tgdangphat, txt_tgketthuc;
    ImageButton IMGB_play, IMGB_next, IMGB_re;
    ListView listView_baihat;
    SeekBar seekBar;
    LinearLayout activity_dangphat;
    int vitri;

    ArrayList<list_baihat> list_baihats;

    MediaPlayer player = new MediaPlayer();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_hat);

        Intent intent = getIntent();
        int vt = intent.getIntExtra("position", 0);
        String cs = intent.getStringExtra("tencasi");
        activity_dangphat = findViewById(R.id.activitybaihat_phatnhac);
        txt_tencasi = (TextView) findViewById(R.id.txt_casi);
        txt_tenbaihatdangphat = (TextView) findViewById(R.id.txt_dang_phat);
        txt_tgdangphat = (TextView) findViewById(R.id.txt_time_play);
        txt_tgketthuc = (TextView) findViewById(R.id.txt_time_out);
        IMGB_next = (ImageButton) findViewById(R.id.bt_next);
        IMGB_play = (ImageButton) findViewById(R.id.bt_pause);
        IMGB_re = (ImageButton) findViewById(R.id.bt_re);

        listView_baihat = (ListView) findViewById(R.id.list_nhac);
        seekBar = (SeekBar) findViewById(R.id.seebar);

        txt_tencasi.setText("Ca sĩ: " + cs);

        list_baihats = new ArrayList<list_baihat>();

        switch (vt){
            case 0:
                list_baihats.add(new list_baihat("BigcityBoi",time(R.raw.binzbigboicity),R.raw.binzbigboicity));
                list_baihats.add(new list_baihat("OK",time(R.raw.binzok),R.raw.binzok));
                list_baihats.add(new list_baihat("Sofar",time(R.raw.binzsofar),R.raw.binzsofar));
                activity_dangphat.setBackgroundResource(R.drawable.binz);
                break;
            case 1:
                list_baihats.add(new list_baihat("Mày đang giấu cái gì đấy",time(R.raw.denvaumaydanggiaucaigi),R.raw.denvaumaydanggiaucaigi));
                list_baihats.add(new list_baihat("Vì yêu cứ đâm đầu",time(R.raw.denvauviyeucudamdau),R.raw.denvauviyeucudamdau));
                activity_dangphat.setBackgroundResource(R.drawable.dendo);
                break;
            case 2:
                list_baihats.add(new list_baihat("Hoa nở không màu",time(R.raw.hoailamhoanokhongmau),R.raw.hoailamhoanokhongmau));
                list_baihats.add(new list_baihat("Buồn làm chi em ơi",time(R.raw.hoailambuonlamchiemoi),R.raw.hoailambuonlamchiemoi));
                list_baihats.add(new list_baihat("Như những phút ban đầu",time(R.raw.hoailamnhunhungphutbandau),R.raw.hoailamnhunhungphutbandau));
                activity_dangphat.setBackgroundResource(R.drawable.hoailam);
                break;
            case 3:
                list_baihats.add(new list_baihat("Bạc phận",time(R.raw.jackbacphan),R.raw.jackbacphan));
                list_baihats.add(new list_baihat("Hồng nhan",time(R.raw.jackhongnhan),R.raw.jackhongnhan));
                list_baihats.add(new list_baihat("Sóng gió",time(R.raw.jacksonggio),R.raw.jacksonggio));
                activity_dangphat.setBackgroundResource(R.drawable.jack);
                break;

        }

        Adapter_listbaihat adapter_listbaihat = new Adapter_listbaihat(Activity_bai_hat.this, R.layout.list_bai_hat, list_baihats);
        listView_baihat.setAdapter(adapter_listbaihat);
        khoitao();
        player.start();
        IMGB_play.setImageResource(R.drawable.pause);
        thoigian_hientai();
        listView_baihat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (player.isPlaying()) {
                    player.stop();
                }
                else {
                    vitri = position;
                    khoitao();
                }
                IMGB_play.setImageResource(R.drawable.pause);
            }
        });

        IMGB_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying()) {
                    player.pause();
                    IMGB_play.setImageResource(R.drawable.play);
                }
                else {
                    player.start();
                    IMGB_play.setImageResource(R.drawable.pause);
                }
            }
        });
        IMGB_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitri++;
                if (vitri > (list_baihats.size() -1)) {
                    vitri = 0;
                }
                player.stop();
                khoitao();
            }
        });
        IMGB_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitri--;
                if (vitri < 0) {
                    vitri = list_baihats.size()-1;
                }
                player.stop();
                khoitao();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
            }
        });
    }

    void thoigian_hientai() {
        final Handler handler = new Handler();
        boolean b = handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                txt_tgdangphat.setText(simpleDateFormat.format(player.getCurrentPosition()));
                seekBar.setProgress(player.getCurrentPosition());
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        vitri++;
                        if (vitri > list_baihats.size() - 1) {
                            vitri = 0;
                            player.stop();
                            IMGB_play.setImageResource(R.drawable.play);
                        }
                        else {
                            player.stop();
                            khoitao();
                            player.start();
                        }
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);

    }

    private void khoitao() {
        player = MediaPlayer.create(Activity_bai_hat.this, list_baihats.get(vitri).bathat);
        txt_tenbaihatdangphat.setText("Đang phát: " + list_baihats.get(vitri).tenbaihat);
        txt_tgketthuc.setText(time(list_baihats.get(vitri).bathat));
        seekBar.setMax(player.getDuration());
        player.start();
    }

    private  String time(int baihat) {
        String t;
        MediaPlayer player = new MediaPlayer();
        player = MediaPlayer.create(Activity_bai_hat.this, baihat);
        SimpleDateFormat tg = new SimpleDateFormat("mm:ss");
        t = tg.format(player.getDuration());
        return t;
    }

    @Override
    protected void onStop() {
        player.stop();
        super.onStop();
    }
}
