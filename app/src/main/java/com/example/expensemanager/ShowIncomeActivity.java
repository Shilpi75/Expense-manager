package com.example.expensemanager;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class ShowIncomeActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    TextView mTextView;
    DatabaseHandler handler;
    Toolbar toolbar;
    List<IncomeModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_income);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mTextView = (TextView)findViewById(R.id.textView);
        handler = new DatabaseHandler(this);
        list = handler.getIncome();
        if (list.isEmpty()) {
            Toast.makeText(this, "No income to show.", Toast.LENGTH_SHORT).show();
        }

        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, mManager.getOrientation()));

        CustomIncomeAdapter mAdapter = new CustomIncomeAdapter(list);

        mRecyclerView.setAdapter(mAdapter);

        double sum = handler.getTotalIncome();
        DecimalFormat df = new DecimalFormat("####0.00");
        mTextView.setText("Total Income:  " + df.format(sum));

    }

    interface ItemClickListener{
        void onClick(View view, int position, boolean isLongClick);
    }

    class CustomIncomeAdapter extends RecyclerView.Adapter<CustomIncomeAdapter.ViewHolder> {
        List<IncomeModel> list;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
            TextView mAmount, mCategory, mDate;
            ImageView mImage;
            ItemClickListener itemClickListener;
            public ViewHolder(View itemView) {
                super(itemView);
                mAmount = (TextView)itemView.findViewById(R.id.tv_amount);
                mDate = (TextView)itemView.findViewById(R.id.tv_date);
                mCategory =(TextView)itemView.findViewById(R.id.tv_category);
                mImage= (ImageView)itemView.findViewById(R.id.imageView);

                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);

            }

            public void setClickListener(ItemClickListener itemClickListener)
            {
                this.itemClickListener = itemClickListener;
            }
            @Override
            public boolean onLongClick(View view) {
                itemClickListener.onClick(view, getPosition(),true);
                return true;
            }

            @Override
            public void onClick(View view) {
                itemClickListener.onClick(view ,getPosition(), false);

            }
        }

        public CustomIncomeAdapter(List<IncomeModel> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view,parent,false);
            return (new ViewHolder(itemView));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            IncomeModel obj = list.get(position);
            holder.mCategory.setText(obj.getSource()+"");
            holder.mImage.setImageResource(R.drawable.income_bag);
            double amount=obj.getAmount();
            DecimalFormat df = new DecimalFormat("####0.00");
            holder.mAmount.setText("Rs. " + df.format(amount));
            holder.mDate.setText(obj.getDate()+"/"+obj.getMonth()+"/"+obj.getYear());

            holder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if(!isLongClick)
                    {
                        IncomeModel obj = list.get(position);
                        String id = obj.getId();
                       Intent intent = new Intent(ShowIncomeActivity.this, ViewItem.class);
                        intent.putExtra("isIncome",true);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(ShowIncomeActivity.this, "Short" + position, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

}

