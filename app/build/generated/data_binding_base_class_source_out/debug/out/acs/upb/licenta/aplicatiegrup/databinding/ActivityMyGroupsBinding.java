// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMyGroupsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button createGroup;

  @NonNull
  public final RecyclerView groupList;

  @NonNull
  public final Button joinGroup;

  private ActivityMyGroupsBinding(@NonNull ConstraintLayout rootView, @NonNull Button createGroup,
      @NonNull RecyclerView groupList, @NonNull Button joinGroup) {
    this.rootView = rootView;
    this.createGroup = createGroup;
    this.groupList = groupList;
    this.joinGroup = joinGroup;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMyGroupsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMyGroupsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_my_groups, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMyGroupsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.create_group;
      Button createGroup = ViewBindings.findChildViewById(rootView, id);
      if (createGroup == null) {
        break missingId;
      }

      id = R.id.groupList;
      RecyclerView groupList = ViewBindings.findChildViewById(rootView, id);
      if (groupList == null) {
        break missingId;
      }

      id = R.id.join_group;
      Button joinGroup = ViewBindings.findChildViewById(rootView, id);
      if (joinGroup == null) {
        break missingId;
      }

      return new ActivityMyGroupsBinding((ConstraintLayout) rootView, createGroup, groupList,
          joinGroup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
