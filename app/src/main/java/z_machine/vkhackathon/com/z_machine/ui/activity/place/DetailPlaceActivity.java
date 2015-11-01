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
import android.widget.ScrollView;
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

public final class DetailPlaceActivity extends BaseActivity{

    private static final int GET_PLACE = 2;
    private static final int GET_EVENTS = 3;

    private static final String ID_KEY = DetailPlaceActivity.class.getSimpleName() + "id";
    private static final String TITLE_KEY = DetailPlaceActivity.class.getSimpleName() + "title";
    private ListView lvEvents;
    private ScrollView scrollView;

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
        lvEvents = (ListView) findViewById(R.id.lvEvents);
        lvEvents.setAdapter(eventByPlaceAdapter);
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event item = eventByPlaceAdapter.getItem(position);
                DetailEventActivity.start(DetailPlaceActivity.this, item.getId(), item.getTitle());
            }
        });
        scrollView = (ScrollView)findViewById(R.id.scrollView);
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
            if(eventsBodyResponse.getCount() != 0) {
                findViewById(R.id.tvEventText).setVisibility(View.VISIBLE);
                findViewById(R.id.viewBelowLine).setVisibility(View.VISIBLE);
            }

            int verticalScrollbarPosition = (int) scrollView.getX();
            eventByPlaceAdapter.add(eventsBodyResponse.getEvents());
            setListViewHeightBasedOnChildren(lvEvents);
            scrollView.scrollTo(verticalScrollbarPosition,0);
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
