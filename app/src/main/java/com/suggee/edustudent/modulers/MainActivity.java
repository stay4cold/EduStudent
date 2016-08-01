package com.suggee.edustudent.modulers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.suggee.edustudent.R;
import com.suggee.edustudent.base.loading.LoadingViewController;
import com.suggee.edustudent.base.rx.RxBus;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.bean.OauthUser;
import com.suggee.edustudent.event.NetChangedEvent;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.functions.Action1;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.list)
    RecyclerView mreview;

    @BindView((R.id.tmain))
    TextView mview;

    private ArrayList<String> mDataList = new ArrayList<>();
    private DataAdapter mAdapter;
    int a = 1;

    int b = 0;

    private LoadingViewController helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OauthUser user = Realm.getDefaultInstance().where(OauthUser.class).equalTo("logined", true).findFirst();
        Log.e("ministorm", "Appcontext id = " + user.getId() + " token = " + user.getToken());
        helper = new LoadingViewController(getLoadingTargetView());

        for (int i = 0; i < 50; i++) {
            mDataList.add("" + i);
        }

        mAdapter = new DataAdapter(this);
        mAdapter.setData(mDataList);
        mreview.setAdapter(mAdapter);
        mreview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b++;

                if (b % 4 == 0) {
                    helper.showLoading();
                } else if (b % 4 == 1) {
                    helper.showEmpty();
                } else if (b % 4 == 2) {
                    helper.showError();
                } else {
                    helper.restore();
                }
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                int statusHeight = rect.top;

                int content = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

                int titleHeight = content - statusHeight;

                File file = new File("sd");

//                RxBus.getDefault().post(new NetChangedEvent("" + a++));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addSubscription(RxBus.getDefault().toObserverable(NetChangedEvent.class)
                .subscribe(new Action1<NetChangedEvent>() {
                    @Override
                    public void call(NetChangedEvent netChangedEvent) {
                        Snackbar.make(mToolbar, netChangedEvent.getA(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected View getLoadingTargetView() {
        return mreview;
    }

    @Override
    protected void onNetworkConnected(ConnectivityStatus status) {
        Snackbar.make(mToolbar, "" + status, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onNetworkDisConnected() {
        Snackbar.make(mToolbar, "没有连接网络", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    static class DataAdapter extends RecyclerView.Adapter {

        private LayoutInflater mLayoutInflater;
        private ArrayList<String> mDataList = new ArrayList<>();

        public DataAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        public void setData(ArrayList<String> list) {
            this.mDataList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.cm_footer_error, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            String item = mDataList.get(position);

            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.error.setText(item);


        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.error)
        TextView error;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public Bitmap decode(Resources resources, int resId, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);

        options.inSampleSize = 2;

        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(resources, resId, options);
    }

    public int getSample(BitmapFactory.Options options, int width, int height) {
        int w = options.outWidth;
        int h = options.outHeight;

        int size = 1;

        if (w > width || h > height) {

        }
        return 1;
    }
}
