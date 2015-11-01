package z_machine.vkhackathon.com.z_machine.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.core.bus.BusProvider;
import z_machine.vkhackathon.com.z_machine.core.bus.event.BaseEvent;
import z_machine.vkhackathon.com.z_machine.core.bus.event.ErrorEvent;
import z_machine.vkhackathon.com.z_machine.model.Event;
import z_machine.vkhackathon.com.z_machine.ui.adapter.ImageGridAdapter;
import z_machine.vkhackathon.com.z_machine.ui.adapter.place.PlacePagerAdapter;
import z_machine.vkhackathon.com.z_machine.utils.SystemUtils;

public final class DetailEventActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_EVENT = 7;

    private static final String ID_KEY = DetailEventActivity.class.getSimpleName() + "id";
    private static final String TITLE_KEY = DetailEventActivity.class.getSimpleName() + "title";
    private GridView gridView;

    public static void start(Activity activity, int eventId, String title) {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(activity, DetailEventActivity.class);
        bundle.putInt(ID_KEY, eventId);
        bundle.putString(TITLE_KEY, title);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private int eventId;
    private CircleIndicator circleIndicator;
    private ViewPager viewPager;
    private TextView tvBody;
    private Event eventBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        final Bundle bundle = getIntent().getExtras();
        eventId = bundle.getInt(ID_KEY);
        String title = bundle.getString(TITLE_KEY);
        homeAsUp();
        setTitle(title);
        viewPager = (ViewPager) findViewById(R.id.pagerEvents);
        circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
        tvBody = (TextView) findViewById(R.id.tvEventsBody);
        gridView = (GridView) findViewById(R.id.photoEventGrid);
        gridView.setAdapter(new ImageGridAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageGridAdapter gridAdapter = (ImageGridAdapter) parent.getAdapter();
                VKApiPhoto photo = gridAdapter.getItem(position);
                FullScreenActivity.start(DetailEventActivity.this, position, gridAdapter.getUrls());
            }
        });
        findViewById(R.id.activity_detail_photo_btn).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        appBridge.getNetBridge().getEvent(GET_EVENT, eventId);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void networkEventListener(BaseEvent event) {
        if (event.getRequestId() == GET_EVENT) {
            eventBody = (Event) event.getBody();
            tvBody.setText(Html.fromHtml(eventBody.getDescription()));
            viewPager.setAdapter(new PlacePagerAdapter(eventBody.getImages(),
                    getApplicationContext()));
            circleIndicator.setViewPager(viewPager);
            search();
        }
    }

    @Subscribe
    public void networkErrorEventListener(ErrorEvent errorEvent) {
        Log.e("Places", "requestId: " + errorEvent.getRequestId() + " message: "
                + errorEvent.getThrowable().getMessage());
    }

    private void search() {
        final String hashtag = "&" + SystemUtils.eventHashTagByTitle(eventBody.getTitle());
        final int time = (int) ((System.currentTimeMillis() / 1000) - (24 * 60 * 60));
        final VKRequest searchRequest = new VKRequest("photos.search", VKParameters.from("q", hashtag, "start_time", time));
        searchRequest.executeWithListener(searchPhotoRequestListener);
    }

    VKRequest.VKRequestListener searchPhotoRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            super.onComplete(response);
            Log.d("search", response.responseString);
            JSONArray jsonArray;
            List<VKApiPhoto> vkApiPhotos = new ArrayList<>();
            try {
                jsonArray = response.json.getJSONObject("response").getJSONArray("items");
                int length = jsonArray.length();
                if (length > 0) {
                    findViewById(R.id.tvEventText).setVisibility(View.VISIBLE);
                    findViewById(R.id.viewBelowLine).setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < length; i++) {
                    VKApiPhoto photo = new VKApiPhoto(jsonArray.getJSONObject(i));
                    vkApiPhotos.add(photo);
                }

                ((ImageGridAdapter) gridView.getAdapter()).setmThumbIds(vkApiPhotos);
                setListViewHeightBasedOnChildren(gridView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(VKError error) {
            super.onError(error);
            Log.d("upload", "((((");
        }
    };

    public static void setListViewHeightBasedOnChildren(GridView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (int) (Math.ceil((float) listAdapter.getCount() / 3) * listView.getColumnWidth());
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_detail_photo_btn:
                AddPhotoActivity.start(DetailEventActivity.this, eventId, eventBody.getImages().get(0).getImage(), eventBody.getTitle());
                break;
        }
    }

}
