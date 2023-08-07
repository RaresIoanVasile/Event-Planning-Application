package acs.upb.licenta.aplicatiegrup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Event> eventList;
    private EventAdapter.RecyclerViewClickListener listener;

    public EventAdapter(Context context, ArrayList<Event> eventList, RecyclerViewClickListener listener) {
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

    public RecyclerViewClickListener getListener() {
        return listener;
    }

    public void setListener(RecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);

        return new EventAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        String name = event.getName();
        holder.eventName.setText(name);
        String groupName = "Group: " + event.getGroup();
        holder.group.setText(groupName);
        String date = "Date and hour: " + event.getDate() + ", " + event.getHour();
        holder.date.setText(date);
        holder.location.setText("Location: " + event.getLocation());
        FirebaseDatabase.getInstance().getReference("Users").child(event.getOwner().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.owner.setText("Owner: " + user.getName());
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventName, group, date, owner, location;

        public MyViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventNameText1);
            group = itemView.findViewById(R.id.groupText1);
            date = itemView.findViewById(R.id.dateText1);
            owner = itemView.findViewById(R.id.eventOwnerText1);
            location = itemView.findViewById(R.id.locationText1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
