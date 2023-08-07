package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.ExpenseAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Expense;
import acs.upb.licenta.aplicatiegrup.popups.PopAddExpenses;

public class ExpenseTrackerActivity extends AppCompatActivity {

    ImageButton back;
    Button addExpense;
    RecyclerView recyclerView;
    String eventId;
    ExpenseAdapter adapter;
    ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_tracker);

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        back = findViewById(R.id.backSelectedEvent6);
        addExpense = findViewById(R.id.addExpenseButton);
        recyclerView = findViewById(R.id.expenseList);

        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        expenses = new ArrayList<>();
        adapter = new ExpenseAdapter(this, expenses);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("Expenses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (expenses.size() > 0) {
                    expenses.removeAll(expenses);
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expense expense = dataSnapshot.getValue(Expense.class);
                    if (expense.getEventId().trim().equals(eventId)) {
                        expenses.add(expense);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopAddExpenses.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectedEventActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                finish();
            }
        });
    }
}