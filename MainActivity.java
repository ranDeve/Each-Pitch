package com.apps.rannable.eachpitch;

import android.app.Fragment;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    TextView textView2;
    int[] notesArray = new int[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //progressBar.setMax(1000);

        setPage();
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final int pitchInHz = (int) result.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView text = (TextView) findViewById(R.id.textView);
                        text.setText("Pitch:\n" + pitchInHz);
                        //progressBar.setProgress(pitchInHz);




                /*
                        switch (pitchInHz) {
                            case 16:case 32:case 65:case 130:case 261:case 523:case 1046:case 2093:case 4186:
                                textView2.setText("C");
                                break;
                            case 17:case 34:case 69:case 138:case 277:case 554:case 1108:case 2217:case 4434:
                                textView2.setText("C#");
                                break;
                            case 18:case 36:case 73:case 146:case 293:case 587:case 1174 :case 2349:case 4698:
                                textView2.setText("D");
                                break;
                            case 19 :case 38 :case 77 :case 155 :case 311 :case 622 :case 1244 :case 2489 :case 4978 :
                                textView2.setText("Eb");
                                break;
                            case 20 :case 41 :case 82 :case 164 :case 329 :case 659 :case 1318 :case 2637 :case 5274 :
                                textView2.setText("E");
                                break;
                            case 21 :case 43 :case 87 :case 174 :case 349 :case 698 :case 1396 :case 2793 :case 5587 :
                                textView2.setText("F");
                                break;
                            case 23 :case 46 :case 92 :case 185 :case 369 :case 739 :case 1479 :case 2959 :case 5919 :
                                textView2.setText("f#");
                                break;
                            case 24 :case 49 :case 98 :case 196 :case 392 :case 783 :case 1567 :case 3135 :case 6271 :
                                textView2.setText("G");
                                break;
                            case 25 :case 51 :case 103 :case 207 :case 415 :case 830 :case 1661 :case 3322 :case 6644 :
                                textView2.setText("G#");
                                break;
                            case 27 :case 55 :case 110 :case 220 :case 440 :case 880 :case 1760 :case 3520 :case 7040 :
                                textView2.setText("A");
                                break;
                            case 29 :case 58 :case 116 :case 233 :case 466 :case 932 :case 1864 :case 3729 :case 7458 :
                                textView2.setText("Bb");
                                break;
                            case 30 :case 61 :case 123 :case 246 :case 493 :case 987 :case 1975 :case 3951 :case 7902 :
                                textView2.setText("B");
                                break;
                        }
*/
                    }
                });
            }
        };
        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);
        new Thread(dispatcher,"Audio Dispatcher").start();



    }

    private void setPage(){

        webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.youtube.com/");
        webView.setWebViewClient(new WebViewClient());

    }

    @Override
    public void onBackPressed() {
       if(webView.canGoBack()){
           webView.goBack();
       }
        else
        super.onBackPressed();

    }
}
