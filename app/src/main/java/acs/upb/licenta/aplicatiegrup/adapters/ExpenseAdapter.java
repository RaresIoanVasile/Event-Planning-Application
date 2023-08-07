package acs.upb.licenta.aplicatiegrup.adapters;

import android.annotation.SuppressLint;
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

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Expense;
import acs.upb.licenta.aplicatiegrup.classes.Group;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
    Context context;
    ArrayList<Expense> expenses;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.expense_tracker_item, parent, false);

        return new ExpenseAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        FirebaseDatabase.getInstance().getReference("Events").child(expense.getEventId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Event event = snapshot.getValue(Event.class);
                FirebaseDatabase.getInstance().getReference("Groups").child(event.getGroupId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Group group = snapshot.getValue(Group.class);
                        String[] members = group.getMembers().split(",");
                        String[] notAttending = event.getNotAttending().split(",");
                        int cnt = 0;
                        for (String member : members) {
                            if (!member.trim().equals("")) {
                                cnt++;
                            }
                        }
                        for (String n : notAttending) {
                            if (!n.trim().equals("")) {
                                cnt--;
                            }
                        }
                        holder.name.setText("Pay for: " + expense.getName());
                        holder.cost.setText("Total amount to pay: " + expense.getCost());
                        int total = Integer.parseInt(expense.getCost());
                        holder.costPerPerson.setText("Amount to pay per person: " + Integer.toString(total / cnt));
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, cost, costPerPerson;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.expenseText);
            cost = itemView.findViewById(R.id.totalSumText);
            costPerPerson = itemView.findViewById(R.id.memberSumText);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
