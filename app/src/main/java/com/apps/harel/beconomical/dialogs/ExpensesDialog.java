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


public class ExpensesDialog extends Dialog implements CategoriesAdapter.ItemClickCallback {

    private ImageView addCategoryImage;
    private Context c;
    private RecyclerView recyclerView;
    private ArrayList<Category> categoryArrayList;
    private TextView selectCategory;


    public ExpensesDialog(Context context, TextView textView) {
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
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Accessories), c.getResources().getDrawable(R.drawable.sunglasses)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Bill), c.getResources().getDrawable(R.drawable.invoice)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Books), c.getResources().getDrawable(R.drawable.library)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Car), c.getResources().getDrawable(R.drawable.car)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Children), c.getResources().getDrawable(R.drawable.cubes)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Clothing), c.getResources().getDrawable(R.drawable.garment)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Cosmetics), c.getResources().getDrawable(R.drawable.makeup)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Donation), c.getResources().getDrawable(R.drawable.donation)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Education), c.getResources().getDrawable(R.drawable.presentation)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Fitness), c.getResources().getDrawable(R.drawable.exercise)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Food_Drinks), c.getResources().getDrawable(R.drawable.hamburger)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Fuel), c.getResources().getDrawable(R.drawable.gas_station)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Games), c.getResources().getDrawable(R.drawable.gamepad)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Gifts), c.getResources().getDrawable(R.drawable.gift)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Health), c.getResources().getDrawable(R.drawable.hospital)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Holidays_Hotels), c.getResources().getDrawable(R.drawable.hotel)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Investment), c.getResources().getDrawable(R.drawable.investment)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Other), c.getResources().getDrawable(R.drawable.question_mark)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.PC), c.getResources().getDrawable(R.drawable.computer)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Personal), c.getResources().getDrawable(R.drawable.team)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Pets), c.getResources().getDrawable(R.drawable.family)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Rent), c.getResources().getDrawable(R.drawable.rent)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Tax), c.getResources().getDrawable(R.drawable.taxes)));
        categoryArrayList.add(new Category(c.getResources().getString(R.string.Transport), c.getResources().getDrawable(R.drawable.school_bus)));

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



