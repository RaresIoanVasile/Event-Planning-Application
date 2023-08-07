package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.ShoppingListAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class ShoppingListActivity extends AppCompatActivity {

    static String eventId;
    static ListView listView;
    EditText input;
    ImageButton addItem;
    static ShoppingListAdapter adapter;
    static ArrayList<String> items = new ArrayList<>();
    static ArrayList<String> checks = new ArrayList<>();
    static Context context;
    static Toast t;
    ImageButton back;
    String ss = "";
    String ssChecks = "";
    static DataSnapshot currentDataSnapshot;

    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Events");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        listView = findViewById(R.id.shoppingList);
        input = findViewById(R.id.input);
        addItem = findViewById(R.id.addItem);
        back = findViewById(R.id.backSelectedEvent2);
        context = getApplicationContext();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().trim().equals(eventId.trim())) {
                        currentDataSnapshot = dataSnapshot;
                        if (currentDataSnapshot.getValue(Event.class).getShoppingList() != null &&
                        currentDataSnapshot.getValue(Event.class).getCheckedList() != null) {
                            String[] itemsArray = currentDataSnapshot.getValue(Event.class).getShoppingList().split(",");
                            if (!items.isEmpty()) {
                                items.removeAll(items);
                            }
                            for (String s : itemsArray) {
                                if (s != null) {
                                    if (!s.equals("")) {
                                        items.add(s);
                                    }
                                }
                            }
                            String[] checksArray = currentDataSnapshot.getValue(Event.class).getCheckedList().split(",");
                            if (!checks.isEmpty()) {
                                checks.removeAll(checks);
                            }
                            for (String s : checksArray) {
                                if (s != null) {
                                    if (!s.equals("")) {
                                        checks.add(s);
                                    }
                                }
                            }
                        }
                        listView.setAdapter(adapter);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        adapter = new ShoppingListAdapter(this, items);
        listView.setAdapter(adapter);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user.getKid().equals("0")) {
                            String text = input.getText().toString().trim();
                            if (text == null || text.length() == 0) {
                                makeToast("Enter an item.");
                            } else {
                                if (currentDataSnapshot.getValue(Event.class).getShoppingList() == null) {
                                    ss = "";
                                } else {
                                    ss = currentDataSnapshot.getValue(Event.class).getShoppingList();
                                }
                                ss += "," + text;
                                input.setText("");
                                if (currentDataSnapshot.getValue(Event.class).getCheckedList() == null) {
                                    ssChecks = "";
                                } else {
                                    ssChecks = currentDataSnapshot.getValue(Event.class).getCheckedList();
                                }
                                ssChecks += ",false";
                                databaseReference.child(eventId).child("shoppingList").setValue(ss);
                                databaseReference.child(eventId).child("checkedList").setValue(ssChecks);
                                listView.setAdapter(adapter);
                                makeToast("Added " + text);
                            }
                        } else {
                            Toast toast = Toast.makeText(ShoppingListActivity.context, "Kids cannot add to shopping lists!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
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

    public static void removeItem(int position) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.getKid().equals("0")) {
                    makeToast("Removed: " + items.get(position));
                    String result = "";
                    String resultChecks = "";
                    String[] itemsArray = currentDataSnapshot.getValue(Event.class).getShoppingList().split(",");
                    String[] checksArray = currentDataSnapshot.getValue(Event.class).getCheckedList().split(",");
                    List<String> s = new ArrayList<>();
                    List<String> sChecks = new ArrayList<>();
                    for (String ss : itemsArray) {
                        if (!ss.equals("")) {
                            s.add(ss);
                        }
                    }
                    for (String ss : checksArray) {
                        if (!ss.equals("")) {
                            sChecks.add(ss);
                        }
                    }
                    s.remove(position);
                    sChecks.remove(position);
                    for (String ss : s) {
                        result += ss + ",";
                    }
                    for (String ss : sChecks) {
                        resultChecks += ss + ",";
                    }
                    databaseReference.child(eventId).child("shoppingList").setValue(result);
                    databaseReference.child(eventId).child("checkedList").setValue(resultChecks);
                    listView.setAdapter(adapter);
                } else {
                    Toast toast = Toast.makeText(ShoppingListActivity.context, "Kids cannot remove from shopping lists!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public static void checkItem(int position) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.getKid().equals("0")) {
                    String resultChecks = "";
                    String[] checksArray = currentDataSnapshot.getValue(Event.class).getCheckedList().split(",");
                    List<String> sChecks = new ArrayList<>();
                    for (String ss : checksArray) {
                        if (!ss.equals("")) {
                            sChecks.add(ss);
                        }
                    }
                    if (sChecks.get(position).equals("true")) {
                        sChecks.set(position, "false");
                    } else {
                        sChecks.set(position, "true");
                    }
                    for (String ss : sChecks) {
                        resultChecks += ss + ",";
                    }
                    databaseReference.child(eventId).child("checkedList").setValue(resultChecks);
                } else {
                    Toast toast = Toast.makeText(ShoppingListActivity.context, "Kids cannot check items!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public static boolean getCheckSituation(int position) {
        String[] checksArray = currentDataSnapshot.getValue(Event.class).getCheckedList().split(",");
        List<String> sChecks = new ArrayList<>();
        for (String ss : checksArray) {
            if (!ss.equals("")) {
                sChecks.add(ss);
            }
        }
        boolean finalValue;
        if (sChecks.get(position).equals("true")) {
            finalValue = true;
        } else {
            finalValue = false;
        }
        return finalValue;
    }

    private static void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }
}