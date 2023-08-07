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

public final class ExpenseTrackerItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView expenseText;

  @NonNull
  public final TextView memberSumText;

  @NonNull
  public final TextView totalSumText;

  private ExpenseTrackerItemBinding(@NonNull CardView rootView, @NonNull TextView expenseText,
      @NonNull TextView memberSumText, @NonNull TextView totalSumText) {
    this.rootView = rootView;
    this.expenseText = expenseText;
    this.memberSumText = memberSumText;
    this.totalSumText = totalSumText;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ExpenseTrackerItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ExpenseTrackerItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.expense_tracker_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ExpenseTrackerItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.expenseText;
      TextView expenseText = ViewBindings.findChildViewById(rootView, id);
      if (expenseText == null) {
        break missingId;
      }

      id = R.id.memberSumText;
      TextView memberSumText = ViewBindings.findChildViewById(rootView, id);
      if (memberSumText == null) {
        break missingId;
      }

      id = R.id.totalSumText;
      TextView totalSumText = ViewBindings.findChildViewById(rootView, id);
      if (totalSumText == null) {
        break missingId;
      }

      return new ExpenseTrackerItemBinding((CardView) rootView, expenseText, memberSumText,
          totalSumText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}