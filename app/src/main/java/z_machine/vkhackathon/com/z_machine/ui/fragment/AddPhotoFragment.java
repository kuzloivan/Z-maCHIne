package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import z_machine.vkhackathon.com.z_machine.R;

public final class AddPhotoFragment extends BaseFragment {

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_PICK_PHOTO = 2;

    private ClickListener clickListener;
    private Bitmap bitmap;
    private ImageView imageView;
    private Button takeBtn;
    private Button pickBtn;
    private Button sendBtn;

    private String mCurrentPhotoPath;
    private Uri capturedImageUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clickListener = new ClickListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.result);
        takeBtn = (Button) view.findViewById(R.id.add_photo_take_btn);
        pickBtn = (Button) view.findViewById(R.id.add_photo_pick_btn);
        sendBtn = (Button) view.findViewById(R.id.add_photo_send_btn);
        sendBtn.setOnClickListener(clickListener);
        takeBtn.setOnClickListener(clickListener);
        pickBtn.setOnClickListener(clickListener);
        search();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                try {
                    // We need to recyle unused bitmaps
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    InputStream stream = getActivity().getContentResolver().openInputStream(
                            data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    stream.close();
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                //setPic();
                try {
                    bitmap= MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), capturedImageUri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                galleryAddPic();
            }
        }
    }


    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                capturedImageUri = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public static Fragment getInstance() {
        return new AddPhotoFragment();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),getString(R.string.app_name));
        storageDir.mkdir();
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = "file:" + image.getCanonicalPath();
        return image;
    }

    private void setPic() {
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        imageView.setImageBitmap(bitmap);
    }

    private void search(){
        String hashtag = "&#hackathon";
        VKRequest searchRequest = new VKRequest("photos.search",VKParameters.from("q",hashtag));
        searchRequest.executeWithListener(searchPhotoRequestListener);
    }

    private void sendPhoto(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Log.d("upload", "start");
                bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/4,bitmap.getHeight()/4,false);
                VKRequest request = VKApi.uploadAlbumPhotoRequest(new VKUploadImage(bitmap, VKImageParameters.pngImage()), appBridge.getSharedHelper().getAlbumId(), 0);

                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        Log.d("upload", response.responseString);
                    }

                    @Override
                    public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                        super.onProgress(progressType, bytesLoaded, bytesTotal);
                        Log.d("upload", bytesLoaded + "//" + bytesTotal);
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }

                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                        super.attemptFailed(request, attemptNumber, totalAttempts);
                    }

//                    @Override
//                    public void onComplete(VKResponse response) {
//                        Log.d("upload", response.responseString);
//                        JSONArray jsonArray = null;
//                        try {
//                            jsonArray = response.json.getJSONArray(("response"));
//                            int length = jsonArray.length();
//                            for (int i = 0; i < length; i++) {
//                                VKApiPhoto photo = new VKApiPhoto(jsonArray.getJSONObject(i));
//                                VKRequest photoRequest = new VKRequest("photos.edit", VKParameters.from("photo_id", photo.getId(), "caption", "#hackathon"));
//                                photoRequest.executeWithListener(uploadPhotoRequestListener);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(VKError error) {
//                        Log.d("upload", error.apiError.errorMessage);
//                    }
                });
            }
        });

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

    VKRequest.VKRequestListener uploadPhotoRequestListener = new VKRequest.VKRequestListener(){
        @Override
        public void onComplete(VKResponse response) {
            super.onComplete(response);
            Log.d("upload", response.responseString);
            Toast.makeText(getActivity(),"Successfully completed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(VKError error) {
            super.onError(error);
            Log.d("upload", error.apiError.errorMessage);
        }
    };

    VKRequest.VKRequestListener searchPhotoRequestListener = new VKRequest.VKRequestListener(){
        @Override
        public void onComplete(VKResponse response) {
            super.onComplete(response);
            Log.d("search", response.responseString);
            JSONArray jsonArray = null;
            List<VKApiPhoto> vkApiPhotos = new ArrayList<>();
            try {
                jsonArray = response.json.getJSONObject("response").getJSONArray("items");
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    VKApiPhoto photo= new VKApiPhoto(jsonArray.getJSONObject(i));
                    vkApiPhotos.add(photo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(VKError error) {
            super.onError(error);
            Log.d("upload", error.apiError.errorMessage);
        }
    };
}
