package z_machine.vkhackathon.com.z_machine.ui.activity.place;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import me.relex.circleindicator.CircleIndicator;
import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.core.bus.BusProvider;
import z_machine.vkhackathon.com.z_machine.core.bus.event.BaseEvent;
import z_machine.vkhackathon.com.z_machine.core.bus.event.ErrorEvent;
import z_machine.vkhackathon.com.z_machine.model.Event;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.network.rest.response.GetEvents;
import z_machine.vkhackathon.com.z_machine.ui.activity.BaseActivity;
import z_machine.vkhackathon.com.z_machine.ui.activity.DetailEventActivity;
import z_machine.vkhackathon.com.z_machine.ui.adapter.event.EventByPlaceAdapter;
import z_machine.vkhackathon.com.z_machine.ui.adapter.place.PlacePagerAdapter;

public final class DetailPlaceActivity extends BaseActivity {

    private static final int GET_EVENTS = 3;

    private static final String ID_KEY = DetailPlaceActivity.class.getSimpleName() + "id";

    public static void start(Activity activity, int placeId) {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(activity, DetailPlaceActivity.class);
        bundle.putInt(ID_KEY, placeId);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private int placeId;
    private EventByPlaceAdapter eventByPlaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        eventByPlaceAdapter = new EventByPlaceAdapter(getApplicationContext());
        final Bundle bundle = getIntent().getExtras();
        placeId = bundle.getInt(ID_KEY);
        Place place = appBridge.getRestoreManager().getPlace(placeId);
        homeAsUp();
        setTitle(place.getTitle());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pagerPlaces);
        final CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
        final TextView tvDescription = (TextView) findViewById(R.id.tvPlaceDescription);
        final TextView tvBody = (TextView) findViewById(R.id.tvPlaceBody);
        tvBody.setText(Html.fromHtml(place.getBodyText()));
        tvDescription.setText(place.getAddress());
        viewPager.setAdapter(new PlacePagerAdapter(place.getImages(),
                getApplicationContext()));
        circleIndicator.setViewPager(viewPager);
        final ListView lvEvents = (ListView) findViewById(R.id.lvEvents);
        lvEvents.setAdapter(eventByPlaceAdapter);
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event item = eventByPlaceAdapter.getItem(position);
                DetailEventActivity.start(DetailPlaceActivity.this, item.getId(), item.getTitle());
            }
        });
        setListViewHeightBasedOnChildren(lvEvents);
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        appBridge.getNetBridge().getEventsByPlace(GET_EVENTS, placeId);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void networkEventListener(BaseEvent event) {
        if (event.getRequestId() == GET_EVENTS) {
            final GetEvents eventsBodyResponse = (GetEvents) event.getBody();
            if (eventsBodyResponse.getCount() != 0) {
                findViewById(R.id.tvEventText).setVisibility(View.VISIBLE);
                findViewById(R.id.viewBelowLine).setVisibility(View.VISIBLE);
            }
            eventByPlaceAdapter.add(eventsBodyResponse.getEvents());
        }
    }

    @Subscribe
    public void networkErrorEventListener(ErrorEvent errorEvent) {
        Log.e("Places", "requestId: " + errorEvent.getRequestId() + " message: "
                + errorEvent.getThrowable().getMessage());
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
