package z_machine.vkhackathon.com.z_machine.ui.activity.place;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import me.relex.circleindicator.CircleIndicator;
import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.core.bus.BusProvider;
import z_machine.vkhackathon.com.z_machine.core.bus.event.BaseEvent;
import z_machine.vkhackathon.com.z_machine.core.bus.event.ErrorEvent;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.network.rest.response.GetEvents;
import z_machine.vkhackathon.com.z_machine.ui.activity.BaseActivity;
import z_machine.vkhackathon.com.z_machine.ui.adapter.event.EventByPlaceAdapter;
import z_machine.vkhackathon.com.z_machine.ui.adapter.place.PlacePagerAdapter;

public final class DetailPlaceActivity extends BaseActivity {

    private static final int GET_PLACE = 2;
    private static final int GET_EVENTS = 3;

    private static final String ID_KEY = DetailPlaceActivity.class.getSimpleName() + "id";
    private static final String TITLE_KEY = DetailPlaceActivity.class.getSimpleName() + "title";

    public static void start(Activity activity, int placeId, String title) {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(activity, DetailPlaceActivity.class);
        bundle.putInt(ID_KEY, placeId);
        bundle.putString(TITLE_KEY, title);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private int eventId;
    private CircleIndicator circleIndicator;
    private ViewPager viewPager;
    private TextView tvDescription;
    private TextView tvBody;
    private EventByPlaceAdapter eventByPlaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        eventByPlaceAdapter = new EventByPlaceAdapter(getApplicationContext());
        final Bundle bundle = getIntent().getExtras();
        eventId = bundle.getInt(ID_KEY);
        String title = bundle.getString(TITLE_KEY);
        homeAsUp();
        setTitle(title);
        viewPager = (ViewPager) findViewById(R.id.pagerPlaces);
        circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
        tvDescription = (TextView) findViewById(R.id.tvPlaceDescription);
        tvBody = (TextView) findViewById(R.id.tvPlaceBody);
        ListView lvEvents = (ListView) findViewById(R.id.lvEvents);
        lvEvents.setAdapter(eventByPlaceAdapter);
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
            final Place placeBodyResponse = (Place) event.getBody();
            tvBody.setText(Html.fromHtml(placeBodyResponse.getBodyText()));

            tvDescription.setText(placeBodyResponse.getAddress());
            viewPager.setAdapter(new PlacePagerAdapter(placeBodyResponse.getImages(),
                    getApplicationContext()));
            circleIndicator.setViewPager(viewPager);
            appBridge.getNetBridge().getEventsByPlace(GET_EVENTS, placeBodyResponse.getId());
        }
        if (event.getRequestId() == GET_EVENTS) {
            final GetEvents eventsBodyResponse = (GetEvents) event.getBody();
            eventByPlaceAdapter.add(eventsBodyResponse.getEvents());
        }
    }

    @Subscribe
    public void networkErrorEventListener(ErrorEvent errorEvent) {
        Log.e("Places", "requestId: " + errorEvent.getRequestId() + " message: "
                + errorEvent.getThrowable().getMessage());
    }

}
