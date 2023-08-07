// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ShoppingCartItemBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final CheckBox checked;

  @NonNull
  public final TextView item;

  @NonNull
  public final ImageView remove;

  private ShoppingCartItemBinding(@NonNull RelativeLayout rootView, @NonNull CheckBox checked,
      @NonNull TextView item, @NonNull ImageView remove) {
    this.rootView = rootView;
    this.checked = checked;
    this.item = item;
    this.remove = remove;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ShoppingCartItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ShoppingCartItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.shopping_cart_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ShoppingCartItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.checked;
      CheckBox checked = ViewBindings.findChildViewById(rootView, id);
      if (checked == null) {
        break missingId;
      }

      id = R.id.item;
      TextView item = ViewBindings.findChildViewById(rootView, id);
      if (item == null) {
        break missingId;
      }

      id = R.id.remove;
      ImageView remove = ViewBindings.findChildViewById(rootView, id);
      if (remove == null) {
        break missingId;
      }

      return new ShoppingCartItemBinding((RelativeLayout) rootView, checked, item, remove);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
