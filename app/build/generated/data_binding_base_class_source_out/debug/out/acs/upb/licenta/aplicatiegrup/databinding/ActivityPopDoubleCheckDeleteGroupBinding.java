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

public final class ActivityPopDoubleCheckDeleteGroupBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button no3;

  @NonNull
  public final TextView sure3;

  @NonNull
  public final Button yes3;

  private ActivityPopDoubleCheckDeleteGroupBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button no3, @NonNull TextView sure3, @NonNull Button yes3) {
    this.rootView = rootView;
    this.no3 = no3;
    this.sure3 = sure3;
    this.yes3 = yes3;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPopDoubleCheckDeleteGroupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPopDoubleCheckDeleteGroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_pop_double_check_delete_group, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPopDoubleCheckDeleteGroupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.no3;
      Button no3 = ViewBindings.findChildViewById(rootView, id);
      if (no3 == null) {
        break missingId;
      }

      id = R.id.sure3;
      TextView sure3 = ViewBindings.findChildViewById(rootView, id);
      if (sure3 == null) {
        break missingId;
      }

      id = R.id.yes3;
      Button yes3 = ViewBindings.findChildViewById(rootView, id);
      if (yes3 == null) {
        break missingId;
      }

      return new ActivityPopDoubleCheckDeleteGroupBinding((ConstraintLayout) rootView, no3, sure3,
          yes3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
