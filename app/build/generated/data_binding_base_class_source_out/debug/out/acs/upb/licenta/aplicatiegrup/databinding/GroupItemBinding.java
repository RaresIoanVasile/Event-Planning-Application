// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class GroupItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView groupNameText;

  @NonNull
  public final TextView ownerText;

  @NonNull
  public final TextView totalMembersText;

  private GroupItemBinding(@NonNull CardView rootView, @NonNull TextView groupNameText,
      @NonNull TextView ownerText, @NonNull TextView totalMembersText) {
    this.rootView = rootView;
    this.groupNameText = groupNameText;
    this.ownerText = ownerText;
    this.totalMembersText = totalMembersText;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static GroupItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static GroupItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.group_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static GroupItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.groupNameText;
      TextView groupNameText = ViewBindings.findChildViewById(rootView, id);
      if (groupNameText == null) {
        break missingId;
      }

      id = R.id.ownerText;
      TextView ownerText = ViewBindings.findChildViewById(rootView, id);
      if (ownerText == null) {
        break missingId;
      }

      id = R.id.totalMembersText;
      TextView totalMembersText = ViewBindings.findChildViewById(rootView, id);
      if (totalMembersText == null) {
        break missingId;
      }

      return new GroupItemBinding((CardView) rootView, groupNameText, ownerText, totalMembersText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}