package acs.upb.licenta.aplicatiegrup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Poll;

public class PollListAdapter extends RecyclerView.Adapter<PollListAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Poll> pollList;
    private PollListAdapter.RecyclerViewClickListener listener;

    public PollListAdapter(Context context, ArrayList<Poll> pollList, RecyclerViewClickListener listener) {
        this.context = context;
        this.pollList = pollList;
        this.listener = listener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Poll> getPollList() {
        return pollList;
    }

    public void setPollList(ArrayList<Poll> pollList) {
        this.pollList = pollList;
    }

    public RecyclerViewClickListener getListener() {
        return listener;
    }

    public void setListener(RecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.poll_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Poll poll = pollList.get(position);
        holder.pollName.setText(poll.getPollName());
        String[] percentages = poll.getPercentages().split(",");
        int max = 0;
        ArrayList<Integer> winnerIndices = new ArrayList<>();
        for (String p : percentages) {
            if (Integer.parseInt(p) > max) {
                max = Integer.parseInt(p);
            }
        }
        for (int i = 0; i < percentages.length; i++) {
            if (Integer.parseInt(percentages[i]) == max) {
                winnerIndices.add(i);
            }
        }
        String[] options = poll.getOptions().split(",");
        ArrayList<String> winners = new ArrayList<>();
        for (int index : winnerIndices) {
            winners.add(options[index]);
        }
        String winner = "Current winner: ";
        if (poll.getVoters().equals("")) {
            winner += "no votes yet";
        } else {
            for (int i = 0; i < winners.size(); i++) {
                if (i == 0) {
                    winner += winners.get(i);
                } else {
                    winner += "," + winners.get(i);
                }
            }
        }
        holder.winnerText.setText(winner);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().trim();
        if (poll.getVoters() != null) {
            if (poll.getVoters().contains(uid)) {
                holder.status.setText("Already voted!");
            } else {
                holder.status.setText("Vote!");
            }
        } else {
            holder.status.setText("Vote!");
        }
    }

    @Override
    public int getItemCount() {
        return pollList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pollName, winnerText, status;

        public MyViewHolder(View itemView) {
            super(itemView);
            pollName = itemView.findViewById(R.id.pollNameText);
            winnerText = itemView.findViewById(R.id.winnerText);
            status = itemView.findViewById(R.id.statusText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
