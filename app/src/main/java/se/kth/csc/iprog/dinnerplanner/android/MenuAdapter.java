package se.kth.csc.iprog.dinnerplanner.android;


import android.app.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import se.kth.csc.iprog.dinnerplanner.model.Dish;


/**
 * Created by emil on 2017-02-02.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Object[] dataset;
    private Activity activity;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout item;
        public Dish dish;
        public ViewHolder(LinearLayout v) {
            super(v);
            item = v;
        }

        public TextView getTextView() {
            return (TextView) item.findViewById(R.id.testtext);
        }
        public ImageView getImageView() {
            return (ImageView) item.findViewById(R.id.testpic);
        }
    }

    public MenuAdapter(Activity a, Object[] dataset) {
        this.dataset = dataset;
        this.activity = a;
    }



    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final  ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Dish dish = (Dish) dataset[position];
        holder.dish = dish;
        holder.getTextView().setText(dish.getName());
        holder.getImageView().setImageResource(dish.getImageId());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickImage(holder, dish);
            }
        });
    }

    protected void onClickImage(final ViewHolder holder, final Dish dish) {
        final TextView b = (TextView) activity.findViewById(R.id.totalCostSum);
        final Spinner s = (Spinner) activity.findViewById(R.id.spinner);
        final int p = (int) s.getSelectedItem();
        final int[] totCost = new int[1];

        if(!holder.dish.marked) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.choose_menu_dialog);

            ((TextView) dialog.findViewById(R.id.itemTitle)).setText(
                    "Cost: " + (p * dish.getCost()) + "\n" + dish.getCost() + " per person"
            );

            String costString = b.getText().toString();
            totCost[0] = Integer.parseInt(costString);

            dialog.findViewById(R.id.chooseBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.item.setBackgroundColor(activity.getResources().getColor(android.R.color.holo_red_dark));
                    holder.getTextView().setTextColor(Color.WHITE);
                    holder.dish.marked = true;
                    String costString = b.getText().toString();
                    totCost[0] = Integer.parseInt(costString);
                    b.setText("" + (totCost[0] + p * dish.getCost()));
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else {
            holder.dish.marked = false;
            holder.item.setBackgroundColor(activity.getResources().getColor(android.R.color.white));
            holder.getTextView().setTextColor(Color.BLACK);
            String costString = b.getText().toString();
            totCost[0] = Integer.parseInt(costString);
            b.setText("" + (totCost[0] - p * dish.getCost()));


        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.length;
    }

}

