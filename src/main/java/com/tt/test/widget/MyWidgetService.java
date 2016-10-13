package com.tt.test.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.tt.test.R;

/**
 * Created by hoperun on 9/20/16.
 */

public class MyWidgetService extends RemoteViewsService {
    private static final String TAG = "zhaocheng";
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory");
        return new MyRemoteViewsFactory(getApplicationContext(), intent);
    }
    private static class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private final Context mContext;
        private final int mAppWidgetId;
        private final AppWidgetManager mAppWidgetManager;
        public MyRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
            mAppWidgetManager = AppWidgetManager.getInstance(context);
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate");
        }

        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged");
//            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget);
//            mAppWidgetManager.partiallyUpdateAppWidget(mAppWidgetId, remoteViews);
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy");
        }

        @Override
        public int getCount() {
            Log.d(TAG, "getCount");
            return 10;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            Log.d(TAG, "getViewAt i="+i);
            RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            view.setTextViewText(R.id.itemt_text, "item:"+i);
            Intent intent = new Intent();
            intent.putExtra("extra_position", i);
            view.setOnClickFillInIntent(R.id.widget_item, intent);
            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            Log.d(TAG, "getLoadingView");
            RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.widget_loading);
            view.setTextViewText(
                    R.id.loading_text, "loading_text");
            return view;
        }

        @Override
        public int getViewTypeCount() {
            Log.d(TAG, "getViewTypeCount");
            return 1;
        }

        @Override
        public long getItemId(int i) {
            Log.d(TAG, "getItemId i="+i);
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
