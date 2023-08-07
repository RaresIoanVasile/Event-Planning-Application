package acs.upb.licenta.aplicatiegrup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Poll;

public class PollOptionsAdapter extends RecyclerView.Adapter<PollOptionsAdapter.MyViewHolder> {
    private Context context;
    private Poll poll;
    private PollOptionsAdapter.RecyclerViewClickListener listener;

    public PollOptionsAdapter(Context context, Poll poll, RecyclerViewClickListener listener) {
        this.context = context;
        this.poll = poll;
        this.listener = listener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public RecyclerViewClickListener getListener() {
        return listener;
    }

    public void setListener(RecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.poll_option, parent, false);
        return new PollOptionsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ArrayList<Integer> percentages = new ArrayList<>();
        List<String > options = Arrays.asList(poll.getOptions().trim().split(","));
        List<String> perc = Arrays.asList(poll.getPercentages().trim().split(","));
        for (String pp : perc) {
            percentages.add(Integer.parseInt(pp));
        }

        int count = percentages.get(position);
        int total = 0;
        for (int p : percentages) {
            total += p;
        }
        double percentage = 0;
        if (total != 0) {
            percentage = ((double)count / total) * 100;
        }
        String name = options.get(position);
        holder.optionName.setText(name);
        holder.seekBar.setProgress((int)percentage);
        holder.optionPercentage.setText(String.format("%.0f%%", percentage));
    }

    @Override
    public int getItemCount() {
        return poll.getOptions().split(",").length;
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView optionName, optionPercentage;
        SeekBar seekBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            optionName = itemView.findViewById(R.id.option);
            seekBar = itemView.findViewById(R.id.optionBar);
            seekBar.setEnabled(false);
            optionPercentage = itemView.findViewById(R.id.optionPerc);

            List<String> voters = Arrays.asList(poll.getVoters().split(","));
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (voters.contains(uid)) {
                itemView.setClickable(false);
            } else {
                itemView.setClickable(true);
            }

            if (itemView.isClickable()) {
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}