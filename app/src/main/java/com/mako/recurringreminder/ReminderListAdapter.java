package com.mako.recurringreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mako.recurringreminder.databasemodel.Reminder;

import java.util.List;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ReminderViewHolder> {

    class ReminderViewHolder extends RecyclerView.ViewHolder {
        private final TextView reminderItemView;

        private ReminderViewHolder(View itemView) {
            super(itemView);
            reminderItemView = itemView.findViewById(R.id.message);
        }
    }

    private final LayoutInflater mInflater;
    private List<Reminder> mReminders; // Cached copy of words

    ReminderListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_reminder, parent, false);
        return new ReminderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        if (mReminders != null) {
            Reminder current = mReminders.get(position);
            holder.reminderItemView.setText(current.getMessage());
            //holder.ReminderItemView.setText(current.getWord());
        }
    }

    void setWords(List<Reminder> words){
        mReminders = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mReminders != null)
            return mReminders.size();
        else return 0;
    }
}
