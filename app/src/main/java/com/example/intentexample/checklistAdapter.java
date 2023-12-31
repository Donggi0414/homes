package com.example.intentexample;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;


public class checklistAdapter extends BaseAdapter{

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
        View view = LayoutInflater.from(this.context).inflate(R.layout.checklist_item2, (ViewGroup) null);
        TextView aptname = view.findViewById(R.id.aptname_tv);
        ApartmentInfo apt = (ApartmentInfo) this.aptList.get(position);
        System.out.println("checklist apt name " + apt.aptname);
        System.out.println("checklist comment " + apt.comment);
        CardView cardView = view.findViewById(R.id.cardview);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SharedChecklist.class);
                intent.putExtra("aptName", apt.aptname);
                intent.putExtra("user", apt.user);
                v.getContext().startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));

            }
        });
        aptname.setText(apt.comment);
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

    public checklistAdapter(@NotNull Context context, @NotNull ArrayList UserList) {
        super();
        this.context = context;
        this.aptList = UserList;
    }


}
