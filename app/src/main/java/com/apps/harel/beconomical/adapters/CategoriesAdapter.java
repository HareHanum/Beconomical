package com.apps.harel.beconomical.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.apps.harel.beconomical.R;
import com.apps.harel.beconomical.objects.Category;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private List<Category> moviesList;

    private ItemClickCallback itemClickCallback;

    //INTERFACE
    public interface ItemClickCallback {

        void onItemClick(int p);

    }

    public CategoriesAdapter(List<Category> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category category = moviesList.get(position);
        holder.title.setText(category.getTitle());
        holder.image.setImageDrawable(category.getImageView());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView image;
        View itemContainer;

        MyViewHolder(View view) {
            super(view);
            itemContainer = view.findViewById(R.id.container);
            itemContainer.setOnClickListener(this);

            title = view.findViewById(R.id.category_title);
            image = view.findViewById(R.id.category_image);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.container){
                itemClickCallback.onItemClick(getAdapterPosition());
            }
        }
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }
}
