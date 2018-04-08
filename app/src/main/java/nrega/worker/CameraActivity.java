package nrega.worker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.*;

import nrega.worker.Constants.Constants;
import nrega.worker.Model.JobCard;
import nrega.worker.Utils.Utils;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_ON;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;
import static nrega.worker.CameraSource.CAMERA_FACING_BACK;
import static nrega.worker.CameraSource.CAMERA_FACING_FRONT;

public class CameraActivity extends AppCompatActivity {

    final int RequestCameraPermissionID = 1001;
    ImageButton capture_button,refresh_button,flash_button,rotate_camera_button,next_activity_button;
    SurfaceView cameraView;
    CameraSource cameraSource;
    EditText JobCardNo;
    TextView jno_text,text_view;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    Context context;
    String url;
    ProgressBar markerProgress;
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
        url = Constants.base_url + "jobcard";
        markerProgress = (ProgressBar) findViewById(R.id.marker_progress);
        cameraView = (SurfaceView) findViewById(R.id.surface_view_camera);
        jno_text = (TextView) findViewById(R.id.fetch_text_view);
        capture_button = (ImageButton) findViewById(R.id.capture_button);
        JobCardNo = (EditText) findViewById(R.id.job_card_num);
        refresh_button = (ImageButton) findViewById(R.id.refresh_button);
        flash_button = (ImageButton)findViewById(R.id.flash_button);
        rotate_camera_button = (ImageButton)findViewById(R.id.rotate_camera);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        next_activity_button = (ImageButton) findViewById(R.id.next_activity);
        context=this;
        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("Text Recogniser:", "Unavailable yet");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CAMERA_FACING_BACK)
                    .setRequestedFps(2.0f)
                    .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
                    .setFlashMode(Camera.Parameters.FLASH_MODE_AUTO)
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

        next_activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                markerProgress.setVisibility(View.VISIBLE);
                AQuery aq = new AQuery(context);
                String jobcardno = JobCardNo.getText().toString();
                Map<String,Object> params = new HashMap<String,Object>();

                try {
                    params.put("jobcardNum", jobcardno);
                    aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            if (object != null) {
                                try {
                                    markerProgress.setVisibility(View.INVISIBLE);
                                    String key = object.getString("error");
                                    if (key.equals("false") && object.getString("jobcardNum")!=null) {
                                        //code here to parse the json object
                                        String jobcardNum = object.getString("jobcardNum");
                                        Intent i = new Intent(CameraActivity.this,MainActivity.class);

                                        Utils.setSharedPreference(context,"JobCardNo",object.getString("jobcardNum"));
                                        Utils.setSharedPreference(context,"panchayatCode",object.getString("panchayatCode"));
                                        Utils.setSharedPreference(context,"address",object.getString("address"));
                                        Utils.setSharedPreference(context,"familyId",object.getString("familyId"));
                                        Utils.setSharedPreference(context,"category",object.getString("category"));
                                        Utils.setSharedPreference(context,"daysLeft",object.getString("daysLeft"));
                                        Toast.makeText(CameraActivity.this, object.toString(), Toast.LENGTH_LONG).show();
//error, jobcardNum, headOfHousehold, father, category, dateOfRegistration, address, village, panchayat, block, district, bpl, familyId

                                        final JobCard jobCard = new JobCard(object.getString("jobcardNum"),
                                                object.getString("familyId"),
                                                object.getString("headOfHousehold"),
                                                object.getString("father"),
                                                object.getString("category"),
                                                object.getString("address"),
                                                object.getString("village"),
                                                object.getString("panchayat"),
                                                object.getString("block"),
                                                object.getString("district"),
                                                object.getInt("daysLeft"));



                                        AlertDialog.Builder pinAuth = new AlertDialog.Builder(context);
                                        LayoutInflater inflater = LayoutInflater.from(context);
                                        View view = inflater.inflate(R.layout.dialog_pin, null);

                                        pinAuth.setTitle("PIN required.");
//                pinAuth.setCancelable(false);
                                        pinAuth.setView(view);

                                        final EditText pin = (EditText) view.findViewById(R.id.pin_edit_text);
                                        //pin.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);

                                        final AlertDialog alertDialog = pinAuth.create();
                                        alertDialog.show();

                                        Button ok_button = (Button) view.findViewById(R.id.ok_button);
                                        ok_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                if(pin.getText().toString().equals("1234"))
                                                {
                                                    alertDialog.cancel();
                                                    Intent i = new Intent(CameraActivity.this,MainActivity.class);
                                                    i.putExtra("JobCard",jobCard);

                                                    startActivity(i);
                                                }
                                                else
                                                {
                                                    alertDialog.cancel();
                                                    Toast.makeText(CameraActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    } else {
                                        Toast.makeText(CameraActivity.this, object.getString("message") + "object not null", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(CameraActivity.this, "hbghvhjtvyjjb", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                markerProgress.setVisibility(View.INVISIBLE);
                                if (status.getCode() == AjaxStatus.NETWORK_ERROR) {

                                    Toast.makeText(CameraActivity.this, "Please Check internet Connection", Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(CameraActivity.this, status.getCode() + "zxcvbnm", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(CameraActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

//                markerProgress.setVisibility(View.INVISIBLE);

            }
        });

        flash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flash_mode = cameraSource.getFlashMode();

                if(flash_mode!=null && flash_mode.equals("torch")){
                    cameraSource.setFlashMode(FLASH_MODE_OFF);
                    flash_button.setBackgroundResource(R.drawable.ic_flash_off_black_24dp);
                }
                else{
                    cameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    flash_button.setBackgroundResource(R.drawable.ic_flash_on_black_24dp);
                }
            }
        });

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

        rotate_camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CameraActivity.this, "rotation needs to be completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.setFlashMode(FLASH_MODE_OFF);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean b = scaleGestureDetector.onTouchEvent(e);
        return b || super.onTouchEvent(e);
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

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if (cameraSource != null) {
                cameraSource.doZoom(detector.getScaleFactor());
            }
        }
    }

}
