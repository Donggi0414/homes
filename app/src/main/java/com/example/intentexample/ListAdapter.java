package com.example.intentexample;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.intentexample.R.id;
import com.example.intentexample.R.layout;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ListAdapter extends BaseAdapter {
    @NotNull
    private final Context context;
    @NotNull
    private final ArrayList aptList;

    public int getCount() {
        return this.aptList.size();
    }

    @NotNull
    public Object getItem(int position) {
        return this.aptList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @NotNull
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = LayoutInflater.from(this.context).inflate(layout.checklist_item, (ViewGroup) null);
        TextView aptname = view.findViewById(id.aptname_tv);
        ApartmentInfo apt = (ApartmentInfo) this.aptList.get(position);
        System.out.println("aaaaaaaaaaaaa " + apt.aptname);
        CardView cardView = view.findViewById(id.cardview);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WriteCheckList.class);
                intent.putExtra("aptName", apt.aptname);
                v.getContext().startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));

            }
        });
        aptname.setText(apt.aptname);
        return view;
    }

    @NotNull
    public final Context getContext() {
        return this.context;
    }

    @NotNull
    public final ArrayList getUserList() {
        return this.aptList;
    }

    public ListAdapter(@NotNull Context context, @NotNull ArrayList UserList) {
        super();
        this.context = context;
        this.aptList = UserList;
    }
}
