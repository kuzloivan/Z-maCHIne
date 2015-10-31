package z_machine.vkhackathon.com.z_machine.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.utils.SystemUtils;

public final class AddPhotoActivity extends BaseActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final String EVENT_ID = "event_id";

    private static final String EVENT_IMAGE = "event_image";
    private static final String EVENT_TITLE = "event_title";

    public static void start(Activity activity, int eventId,String image,String title) {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(activity, AddPhotoActivity.class);
        bundle.putInt(EVENT_ID, eventId);
        bundle.putString(EVENT_IMAGE, image);
        bundle.putString(EVENT_TITLE, title);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private ClickListener clickListener;
    private ImageView imageView;
    private ImageButton takeBtn;
    private View emptyTv;
    private ImageButton pickBtn;
    private View addPanelView;
    private FloatingActionButton sendBtn;

    private String mCurrentPhotoPath;
    private int eventId;
    private String eventImage;
    private String eventTitle;
    private File targetFile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        clickListener = new ClickListener();
        imageView = (ImageView) findViewById(R.id.result);
        takeBtn = (ImageButton) findViewById(R.id.add_photo_take_btn);
        pickBtn = (ImageButton) findViewById(R.id.add_photo_pick_btn);
        sendBtn = (FloatingActionButton) findViewById(R.id.add_photo_send_btn);
        addPanelView = findViewById(R.id.add_photo_add_panel);
        emptyTv= findViewById(R.id.add_photo_empty_tv);
        homeAsUp();
        sendBtn.setOnClickListener(clickListener);
        takeBtn.setOnClickListener(clickListener);
        pickBtn.setOnClickListener(clickListener);
        final Bundle bundle = getIntent().getExtras();
        eventId = bundle.getInt(EVENT_ID);
        eventImage = bundle.getString(EVENT_IMAGE);
        eventTitle = bundle.getString(EVENT_TITLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                try {
                    if (targetFile != null) {
                        targetFile.deleteOnExit();
                    }
                    targetFile = getTargetFile(data.getData(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mCurrentPhotoPath = targetFile.getPath();
                addPanelView.setVisibility(View.GONE);
                sendBtn.setVisibility(View.VISIBLE);
                emptyTv.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage("file://" + targetFile.getAbsolutePath(), imageView);
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                galleryAddPic();
                addPanelView.setVisibility(View.GONE);
                sendBtn.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage("file://" + targetFile.getAbsolutePath(), imageView);
            }
        }
    }


    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                if (targetFile != null) {
                    targetFile.deleteOnExit();
                }
                targetFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (targetFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(targetFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getString(R.string.app_name));
        storageDir.mkdir();
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = "file:" + image.getCanonicalPath();
        return image;
    }

    private void sendPhoto() {
        showProgressDialog();
        Log.d("upload", "click");
        new Thread(new Runnable() {
            @Override
            public void run() {
                VKRequest request = VKApi.uploadAlbumPhotoRequest(targetFile, appBridge.getSharedHelper().getAlbumId(), 0);
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.d("upload", response.responseString);
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.json.getJSONArray(("response"));
                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++) {
                                VKApiPhoto photo = new VKApiPhoto(jsonArray.getJSONObject(i));
                                VKRequest photoRequest = new VKRequest("photos.edit", VKParameters.from("photo_id", photo.getId(), "caption", SystemUtils.eventHashTagByTitle(eventTitle)));
                                photoRequest.executeWithListener(editPhotoRequestListener);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeDialog();
                    }

                    @Override
                    public void onError(VKError error) {
                        Log.d("upload", error.apiError.errorMessage);
                        closeDialog();
                    }
                });
            }
        }).run();
    }

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_photo_take_btn:
                    dispatchTakePictureIntent();
                    break;
                case R.id.add_photo_pick_btn:
                    pickImage();
                    break;
                case R.id.add_photo_send_btn:
                    sendPhoto();
                    break;
            }
        }
    }

    VKRequest.VKRequestListener editPhotoRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            super.onComplete(response);
            Log.d("upload", response.responseString);
            appBridge.getRestoreManager().saveEvent(eventId,eventImage,eventTitle);
            Toast.makeText(AddPhotoActivity.this, "Successfully completed", Toast.LENGTH_SHORT).show();
            addPanelView.setVisibility(View.VISIBLE);
            sendBtn.setVisibility(View.GONE);
            emptyTv.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(null);
        }

        @Override
        public void onError(VKError error) {
            super.onError(error);
            Log.d("upload", error.apiError.errorMessage);
        }
    };




    public static File getTargetFile(Uri contentUri, Context pContext) throws IOException {
        File targetFile;
        if (contentUri.toString().contains("content")) {
            Log.d("FILE_OO", new File(contentUri.toString()).getAbsolutePath());
            InputStream initialStream = pContext.getContentResolver().openInputStream(contentUri);
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);
            String m = pContext.getContentResolver().getType(contentUri);
            m = m.substring(m.lastIndexOf("/") + 1);
            targetFile = new File(pContext.getCacheDir() + (System.currentTimeMillis() + "." + m));
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);

            initialStream.close();
            outStream.flush();
            outStream.close();

            //   Log.e("IU", "mime = " + FileUtils.getFileMimeType(pContext, contentUri));
        } else {
            targetFile = new File(contentUri.getPath());
        }
        return targetFile;
    }
}
