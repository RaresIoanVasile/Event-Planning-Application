package acs.upb.licenta.aplicatiegrup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.R;

public class EventCalendarAdapter extends RecyclerView.Adapter<EventCalendarAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Event> eventList;
    private EventCalendarAdapter.RecyclerViewClickListener listener;

    public EventCalendarAdapter(Context context, ArrayList<Event> eventList, EventCalendarAdapter.RecyclerViewClickListener listener) {
        this.context = context;
        this.eventList = eventList;
        this.listener = listener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public EventCalendarAdapter.RecyclerViewClickListener getListener() {
        return listener;
    }

    public void setListener(EventCalendarAdapter.RecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.calendar_event_item, parent, false);

        return new EventCalendarAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        String name = "Event: " + event.getName();
        holder.eventName.setText(name);
        String groupName = "Group: " + event.getGroup();
        holder.group.setText(groupName);
        String date = "Date and hour: " + event.getDate() + ", " + event.getHour();
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventName, group, date;

        public MyViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventNameText);
            group = itemView.findViewById(R.id.groupText);
            date = itemView.findViewById(R.id.dateText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
