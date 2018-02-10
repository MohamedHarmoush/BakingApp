package com.harmoush.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.harmoush.bakingapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Harmoush on 2/3/2018.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this,intent);
    }
    public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

        List<String> mCollection = new ArrayList<>();
        Context mContext = null;
        String mIngredients;
        public WidgetDataProvider(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
            initData();
        }

        @Override
        public void onDataSetChanged() {
            initData();
        }

        @Override
        public void onDestroy() {
        }
        @Override
        public int getCount() {
            return mCollection.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews view = new RemoteViews(mContext.getPackageName(),
                    android.R.layout.simple_list_item_1);
            view.setTextViewText(android.R.id.text1, mCollection.get(position));
            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private void initData() {
            if(mCollection.size()==0)
                mCollection.clear();
            SharedPreferences preferences = mContext.getSharedPreferences(getString(R.string.FavoriteIngradients), MODE_PRIVATE);
            mIngredients =  preferences.getString(getString(R.string.DesiredIngredients), getString(R.string.defaultValue));
            if(mIngredients != null)
                mCollection= Arrays.asList(mIngredients.split("\n"));
        }

    }

}

