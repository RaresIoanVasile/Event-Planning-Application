// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPopDoubleCheckLeaveGroupBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button no2;

  @NonNull
  public final TextView sure2;

  @NonNull
  public final Button yes2;

  private ActivityPopDoubleCheckLeaveGroupBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button no2, @NonNull TextView sure2, @NonNull Button yes2) {
    this.rootView = rootView;
    this.no2 = no2;
    this.sure2 = sure2;
    this.yes2 = yes2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPopDoubleCheckLeaveGroupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPopDoubleCheckLeaveGroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_pop_double_check_leave_group, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPopDoubleCheckLeaveGroupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.no2;
      Button no2 = ViewBindings.findChildViewById(rootView, id);
      if (no2 == null) {
        break missingId;
      }

      id = R.id.sure2;
      TextView sure2 = ViewBindings.findChildViewById(rootView, id);
      if (sure2 == null) {
        break missingId;
      }

      id = R.id.yes2;
      Button yes2 = ViewBindings.findChildViewById(rootView, id);
      if (yes2 == null) {
        break missingId;
      }

      return new ActivityPopDoubleCheckLeaveGroupBinding((ConstraintLayout) rootView, no2, sure2,
          yes2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
