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
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

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

    private Set<Dish> getSelected() {
        Set<Dish> v = new HashSet<>();
        for(Object o : dataset) {
            Dish s = (Dish) o;
            if(s.marked) {
                v.add(s);
            }
        }
        return v;
    }

    private int countSelected() {
        int sum = 0;
        Set<Dish> b = getSelected();
        for(Dish c : b) {
            sum += c.getCost();
        }
        return sum;
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
                    if(!holder.dish.marked) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.choose_menu_dialog);


                        final Spinner s = (Spinner) activity.findViewById(R.id.spinner);
                        final int p = (int) s.getSelectedItem();
                        ((TextView) dialog.findViewById(R.id.itemTitle)).setText(
                                 "Cost: " + (p * dish.getCost()) + "\n" + dish.getCost() + " per person"

                        );

                        final TextView b = (TextView) activity.findViewById(R.id.totalCostSum);

                        String costString = b.getText().toString();
                        final int totCost = Integer.parseInt(costString);



                        dialog.findViewById(R.id.chooseBtn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                holder.item.setBackgroundColor(activity.getResources().getColor(android.R.color.holo_red_dark));
                                holder.getTextView().setTextColor(Color.WHITE);
                                holder.dish.marked = true;


                                b.setText("" + (totCost + p * dish.getCost()));

                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                else {
                       // holder.item.setBackgroundColor(Color.WHITE);// holder.getTextView().setTextColor(Color.BLACK);
                    }

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.length;
    }

}

