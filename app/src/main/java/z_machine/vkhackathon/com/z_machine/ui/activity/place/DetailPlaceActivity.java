package z_machine.vkhackathon.com.z_machine.ui.activity.place;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import me.relex.circleindicator.CircleIndicator;
import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.core.bus.BusProvider;
import z_machine.vkhackathon.com.z_machine.core.bus.event.BaseEvent;
import z_machine.vkhackathon.com.z_machine.core.bus.event.ErrorEvent;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.ui.activity.BaseActivity;
import z_machine.vkhackathon.com.z_machine.ui.adapter.place.PlacePagerAdapter;
import z_machine.vkhackathon.com.z_machine.utils.SystemUtils;

public final class DetailPlaceActivity extends BaseActivity {

    private static final int GET_PLACE = 2;

    private static final String ID_KEY = DetailPlaceActivity.class.getSimpleName() + "id";

    public static void start(Activity activity, int placeId) {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(activity, DetailPlaceActivity.class);
        bundle.putInt(ID_KEY, placeId);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private int eventId;
    private CircleIndicator circleIndicator;
    private ViewPager viewPager;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvBody;
    private TextView tvHashTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        viewPager = (ViewPager) findViewById(R.id.pagerPlaces);
        circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
        tvDescription = (TextView) findViewById(R.id.tvPlaceDescription);
        tvBody = (TextView) findViewById(R.id.tvPlaceBody);
        tvTitle = (TextView) findViewById(R.id.tvPlaceTitle);
        tvHashTag = (TextView) findViewById(R.id.tvPlaceHashTag);
        final Bundle bundle = getIntent().getExtras();
        eventId = bundle.getInt(ID_KEY);
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        appBridge.getNetBridge().getPlace(GET_PLACE, eventId);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void networkEventListener(BaseEvent event) {
        if (event.getRequestId() == GET_PLACE) {
            Place placeBodyResponse = (Place) event.getBody();
            tvTitle.setText(placeBodyResponse.getTitle());
            tvHashTag.setText(SystemUtils.placeHashTagById(placeBodyResponse.getId()));

            tvBody.setText(Html.fromHtml(placeBodyResponse.getDescription()));
            tvDescription.setText(placeBodyResponse.getAddress());
            viewPager.setAdapter(new PlacePagerAdapter(placeBodyResponse.getImages(),
                    getApplicationContext()));
            circleIndicator.setViewPager(viewPager);
        }
    }

    @Subscribe
    public void networkErrorEventListener(ErrorEvent errorEvent) {
        Log.e("Places", "requestId: " + errorEvent.getRequestId() + " message: "
                + errorEvent.getThrowable().getMessage());
    }

}
