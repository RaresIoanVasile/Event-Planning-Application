// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityResultPollBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton backToPolls3;

  @NonNull
  public final TextView pollDescription3;

  @NonNull
  public final TextView pollName3;

  @NonNull
  public final RecyclerView pollOptions2;

  private ActivityResultPollBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton backToPolls3, @NonNull TextView pollDescription3,
      @NonNull TextView pollName3, @NonNull RecyclerView pollOptions2) {
    this.rootView = rootView;
    this.backToPolls3 = backToPolls3;
    this.pollDescription3 = pollDescription3;
    this.pollName3 = pollName3;
    this.pollOptions2 = pollOptions2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityResultPollBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityResultPollBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_result_poll, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityResultPollBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backToPolls3;
      ImageButton backToPolls3 = ViewBindings.findChildViewById(rootView, id);
      if (backToPolls3 == null) {
        break missingId;
      }

      id = R.id.pollDescription3;
      TextView pollDescription3 = ViewBindings.findChildViewById(rootView, id);
      if (pollDescription3 == null) {
        break missingId;
      }

      id = R.id.pollName3;
      TextView pollName3 = ViewBindings.findChildViewById(rootView, id);
      if (pollName3 == null) {
        break missingId;
      }

      id = R.id.poll_options2;
      RecyclerView pollOptions2 = ViewBindings.findChildViewById(rootView, id);
      if (pollOptions2 == null) {
        break missingId;
      }

      return new ActivityResultPollBinding((ConstraintLayout) rootView, backToPolls3,
          pollDescription3, pollName3, pollOptions2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
