// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAdminEventsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton backAdminEvents;

  @NonNull
  public final RecyclerView recyclerViewEvents2;

  private ActivityAdminEventsBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton backAdminEvents, @NonNull RecyclerView recyclerViewEvents2) {
    this.rootView = rootView;
    this.backAdminEvents = backAdminEvents;
    this.recyclerViewEvents2 = recyclerViewEvents2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAdminEventsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAdminEventsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_admin_events, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAdminEventsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backAdminEvents;
      ImageButton backAdminEvents = ViewBindings.findChildViewById(rootView, id);
      if (backAdminEvents == null) {
        break missingId;
      }

      id = R.id.recyclerViewEvents2;
      RecyclerView recyclerViewEvents2 = ViewBindings.findChildViewById(rootView, id);
      if (recyclerViewEvents2 == null) {
        break missingId;
      }

      return new ActivityAdminEventsBinding((ConstraintLayout) rootView, backAdminEvents,
          recyclerViewEvents2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
