package se.kth.csc.iprog.dinnerplanner.android;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Created by emil on 2017-02-02.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private String[] dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout item;
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

    public MenuAdapter(String[] dataset) {
        this.dataset = dataset;
    }

    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitem, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.getTextView().setText("HOLA");
        holder.getImageView().setImageResource(R.drawable.toast);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1; //dataset.length;
    }
}

