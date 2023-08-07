package acs.upb.licenta.aplicatiegrup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Group> groupList;
    private RecyclerViewClickListener listener;
    String ownerName;

    public GroupAdapter(Context context, ArrayList<Group> groupList, RecyclerViewClickListener listener) {
        this.context = context;
        this.groupList = groupList;
        this.listener = listener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<Group> groupList) {
        this.groupList = groupList;
    }

    public RecyclerViewClickListener getListener() {
        return listener;
    }

    public void setListener(RecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Group group = groupList.get(position);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.getKey();
                    if (id.equals(group.getAdmin())) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            ownerName = user.getName();
                        }
                        String owner = "Group owner: " + ownerName;
                        holder.owner.setText(owner);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        String name = group.getName().trim();
        holder.groupName.setText(name);
        String members = group.getMembers();
        int total;
        String[] m = members.split(",");
        total = 0;
        for (String member : m) {
            if (!member.equals("")) {
                total += 1;
            }
        }
        String totalStr = "Number of members: " + Integer.toString(total);
        holder.totalMembers.setText(totalStr);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView groupName, owner, totalMembers;

        public MyViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.groupNameText);
            owner = itemView.findViewById(R.id.ownerText);
            totalMembers = itemView.findViewById(R.id.totalMembersText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}

