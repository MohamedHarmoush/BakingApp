package com.harmoush.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harmoush.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harmoush on 2/9/2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private Context context;
    private ArrayList<Step> mSteps;
    final private StepAdapter.ListItemClickListner mListItemClickListner;

    public StepAdapter(ArrayList<Step> mSteps, StepAdapter.ListItemClickListner mListItemClickListner) {
        this.mSteps = mSteps;
        this.mListItemClickListner = mListItemClickListner;
    }

    public interface ListItemClickListner {
        void onListItemClickListener(int clikedItemIndex);
    }
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.step_list_item,parent,false);
        StepViewHolder stepViewHolder = new StepViewHolder(rootView);
        return stepViewHolder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        final Step step = mSteps.get(position);
        holder.stepName.setText("Step "+(position+1) +": "+step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return (mSteps!=null) ? mSteps.size(): 0 ;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_step)
        TextView stepName;
        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mListItemClickListner.onListItemClickListener(pos);
        }
    }
}