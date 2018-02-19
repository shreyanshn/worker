package nrega.worker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CameraActivity extends AppCompatActivity {

    ImageButton capture_button,refresh_button,flash_button,rotate_button;
    SurfaceView cameraView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    EditText JobCardNo;
    TextView jno_text,text_view;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraView = (SurfaceView) findViewById(R.id.surface_view_camera);
        jno_text = (TextView) findViewById(R.id.fetch_text_view);
        capture_button = (ImageButton) findViewById(R.id.capture_button);
        JobCardNo = (EditText) findViewById(R.id.job_card_num);
        refresh_button = (ImageButton) findViewById(R.id.refresh_button);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("Text Recogniser:", "Unavailable yet");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(CameraActivity.this,
                                    new String[] {Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size()!=0) {
                        jno_text.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i = 0; i<items.size(); ++i) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                jno_text.setText(stringBuilder.toString());
                            }
                        });
                    }
                }
            });
        }

    capture_button.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String jno = findJobcard(jno_text.getText().toString());
            if(!jno.isEmpty())
                JobCardNo.setText(jno);
            else
                Toast.makeText(CameraActivity.this,"Not found",Toast.LENGTH_SHORT).show();
        }
    });


        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobCardNo.setText("");
            }
        });


    }
    String findJobcard(String OcrInput)
    {
        StringTokenizer Tok = new StringTokenizer(OcrInput);
        String token = "";

        while (Tok.hasMoreElements())
        {
            token = String.valueOf(Tok.nextElement());

            Pattern pattern = Pattern.compile("[A-Za-z][A-Za-z][-]?[0-9][0-9][-]?[0-9][0-9][0-9][-]?[0-9][0-9][0-9][-]?[0-9][0-9][0-9][/][0-9]+");
            Matcher matcher = pattern.matcher(token);

            if (matcher.find())
            {
                return token.replace("-","");
            }
        }
        return "null";
    }
}
