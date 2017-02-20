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
import android.widget.TextView;

import org.w3c.dom.Text;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
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


    public void add(Dish a) {
        Object[] b = new Object[dataset.length + 1];
        for(int i=0; i<dataset.length; i++) {
            b[i] = dataset[i];
        }
        b[dataset.length] = a;
        dataset = b;
        notifyDataSetChanged();
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
        holder.getImageView().setImageBitmap(dish.getBitmap());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickImage(holder, dish);
            }
        });
    }

    protected void onClickImage(final ViewHolder holder, final Dish dish) {
        final DinnerModel model = ((DinnerPlannerApplication) activity.getApplication()).getModel();
        final TextView b = (TextView) activity.findViewById(R.id.totalCostSum);



        if(!holder.dish.marked) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.choose_menu_dialog);
            TextView title = (TextView) dialog.findViewById(R.id.dialogTitle);
            title.setText(holder.dish.getName());
            model.fetchIngredients(dish, new AsyncData() {

                @Override
                public void onData() {
                    model.fetchInstructions(dish, new AsyncData() {
                        @Override
                        public void onData() {
                            ((TextView) dialog.findViewById(R.id.itemTitle)).setText(
                                    "Cost: " + (model.getNumberOfGuests() * dish.getCost()) + "\n" + dish.getCost() + " per person"
                            );
                            ((ImageView) dialog.findViewById(R.id.itemPic)).setImageBitmap(dish.getBitmap());

                            dialog.findViewById(R.id.chooseBtn).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    model.addDishToMenu(holder.dish);
                                    holder.item.setBackgroundColor(activity.getResources().getColor(android.R.color.holo_red_dark));
                                    holder.getTextView().setTextColor(Color.WHITE);
                                    holder.dish.marked = true;
                                    b.setText("" + (model.getTotalMenuPrice()));
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                }
            });

        } else {
            model.removeDishFromMenu(holder.dish);
            holder.item.setBackgroundColor(activity.getResources().getColor(android.R.color.white));
            holder.getTextView().setTextColor(Color.BLACK);
            b.setText("" + (model.getTotalMenuPrice()));
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.length;
    }

}

