package com.tt.test.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.tt.test.MainActivity;
import com.tt.test.R;

/**
 * Created by hoperun on 9/20/16.
 */

public class MyWidgetProvider extends AppWidgetProvider {
    private final String TAG = "zhaocheng";
    public MyWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "MyWidgetProvider onReceive");
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i=0;i<appWidgetIds.length;i++) {
            updateWidget(context, appWidgetIds[i]);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateWidget(Context context, int appWidgetId) {
        Log.d(TAG, "updateWidget appWidgetId:"+appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        final Intent intent = new Intent(context, MyWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(R.id.conversation_list, intent);

        remoteViews.setTextViewText(R.id.widget_label, context.getString(R.string.app_label));

        PendingIntent clickIntent;
        final Intent convIntent = new Intent(context, MainActivity.class);
        clickIntent = PendingIntent.getActivity(
                context, 0, convIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_header, clickIntent);

        remoteViews.setOnClickPendingIntent(R.id.widget_compose, clickIntent);

        remoteViews.setPendingIntentTemplate(R.id.conversation_list, clickIntent);

        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, remoteViews);
    }
}
