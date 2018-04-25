package com.apps.harel.beconomical.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.harel.beconomical.R;
import com.apps.harel.beconomical.adapters.CategoriesAdapter;
import com.apps.harel.beconomical.objects.Category;

import java.util.ArrayList;


public class IncomeDialog extends Dialog implements CategoriesAdapter.ItemClickCallback {

    private ImageView addCategoryImage;
    private Context c;
    private RecyclerView recyclerView;
    private ArrayList<Category> categoryArrayList;
    private TextView selectCategory;


    public IncomeDialog(Context context, TextView textView) {
        super(context);
        this.c = context;
        this.selectCategory = textView;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        findViews();
        initRecycler();
        bindViews();
        bindButton(addCategoryImage);
    }

    private void bindViews() {
        CategoriesAdapter adapter = new CategoriesAdapter(categoryArrayList);
        adapter.setItemClickCallback(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(c.getResources().getDrawable(R.drawable.line_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
    }

    private void findViews() {
        addCategoryImage = findViewById(R.id.add_category);
        recyclerView = findViewById(R.id.categories_recycler);
        categoryArrayList = new ArrayList<>();

    }

    private void bindButton(ImageView add) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "Intent to Manage Categories", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    private void initRecycler() {
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Loan), c.getResources().getDrawable(R.drawable.loan)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Rent), c.getResources().getDrawable(R.drawable.rent)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Salary), c.getResources().getDrawable(R.drawable.salary)));

    }

    @Override
    public void onItemClick(int p) {
        if (selectCategory.getId() == R.id.select_category_expenses || selectCategory.getId() == R.id.select_category_incomes) {
            selectCategory.setText(categoryArrayList.get(p).getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                selectCategory.setTextColor(c.getColor(R.color.black));
            }
            dismiss();
        }else {
            Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
            dismiss();
        }

    }
}



