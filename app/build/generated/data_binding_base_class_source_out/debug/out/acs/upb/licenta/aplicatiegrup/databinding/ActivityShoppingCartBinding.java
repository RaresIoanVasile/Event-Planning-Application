// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityShoppingCartBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton addItem;

  @NonNull
  public final ImageButton backSelectedEvent2;

  @NonNull
  public final RelativeLayout header;

  @NonNull
  public final EditText input;

  @NonNull
  public final ListView shoppingList;

  private ActivityShoppingCartBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton addItem, @NonNull ImageButton backSelectedEvent2,
      @NonNull RelativeLayout header, @NonNull EditText input, @NonNull ListView shoppingList) {
    this.rootView = rootView;
    this.addItem = addItem;
    this.backSelectedEvent2 = backSelectedEvent2;
    this.header = header;
    this.input = input;
    this.shoppingList = shoppingList;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityShoppingCartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityShoppingCartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_shopping_cart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityShoppingCartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addItem;
      ImageButton addItem = ViewBindings.findChildViewById(rootView, id);
      if (addItem == null) {
        break missingId;
      }

      id = R.id.backSelectedEvent2;
      ImageButton backSelectedEvent2 = ViewBindings.findChildViewById(rootView, id);
      if (backSelectedEvent2 == null) {
        break missingId;
      }

      id = R.id.header;
      RelativeLayout header = ViewBindings.findChildViewById(rootView, id);
      if (header == null) {
        break missingId;
      }

      id = R.id.input;
      EditText input = ViewBindings.findChildViewById(rootView, id);
      if (input == null) {
        break missingId;
      }

      id = R.id.shoppingList;
      ListView shoppingList = ViewBindings.findChildViewById(rootView, id);
      if (shoppingList == null) {
        break missingId;
      }

      return new ActivityShoppingCartBinding((ConstraintLayout) rootView, addItem,
          backSelectedEvent2, header, input, shoppingList);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
