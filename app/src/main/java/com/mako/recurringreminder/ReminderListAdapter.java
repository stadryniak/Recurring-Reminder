package com.mako.recurringreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mako.recurringreminder.databasemodel.Reminder;

import java.util.List;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ReminderViewHolder> {

    class ReminderViewHolder extends RecyclerView.ViewHolder {
        private final TextView reminderItemMessage;
        private final TextView itemMinutes;
        private final TextView itemHours;
        private final TextView itemDays;
        private final Button itemDelete;

        private ReminderViewHolder(View itemView) {
            super(itemView);
            reminderItemMessage = itemView.findViewById(R.id.message);
            itemMinutes = itemView.findViewById(R.id.minutes);
            itemHours = itemView.findViewById(R.id.hours);
            itemDays = itemView.findViewById(R.id.days);
            itemDelete = itemView.findViewById(R.id.deleteButton);
        }
    }

    private final LayoutInflater mInflater;
    private List<Reminder> mReminders; // Cached copy of words
    private ReminderViewModel mViewModel;

    ReminderListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    void setViewModel(ReminderViewModel viewModel) {
        mViewModel = viewModel;
    }

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
            holder.reminderItemMessage.setText(current.getMessage());
            holder.itemMinutes.setText(String.valueOf(current.getIntervalMinutes()));
            holder.itemHours.setText(String.valueOf(current.getIntervalHours()));
            holder.itemDays.setText(String.valueOf(current.getIntervalDays()));
            //set delete button
            holder.itemDelete.setOnClickListener(v -> mViewModel.delete(mReminders.get(position)));
        }
    }

    void setReminders(List<Reminder> reminders) {
        mReminders = reminders;
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
