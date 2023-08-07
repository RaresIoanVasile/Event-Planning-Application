// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MeMessageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView cardGchatMessageMe;

  @NonNull
  public final LinearLayout layoutGchatContainerMe;

  @NonNull
  public final TextView textGchatMessageMe;

  @NonNull
  public final TextView textGchatTimestampMe;

  private MeMessageBinding(@NonNull ConstraintLayout rootView, @NonNull CardView cardGchatMessageMe,
      @NonNull LinearLayout layoutGchatContainerMe, @NonNull TextView textGchatMessageMe,
      @NonNull TextView textGchatTimestampMe) {
    this.rootView = rootView;
    this.cardGchatMessageMe = cardGchatMessageMe;
    this.layoutGchatContainerMe = layoutGchatContainerMe;
    this.textGchatMessageMe = textGchatMessageMe;
    this.textGchatTimestampMe = textGchatTimestampMe;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static MeMessageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MeMessageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.me_message, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MeMessageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.card_gchat_message_me;
      CardView cardGchatMessageMe = ViewBindings.findChildViewById(rootView, id);
      if (cardGchatMessageMe == null) {
        break missingId;
      }

      id = R.id.layout_gchat_container_me;
      LinearLayout layoutGchatContainerMe = ViewBindings.findChildViewById(rootView, id);
      if (layoutGchatContainerMe == null) {
        break missingId;
      }

      id = R.id.text_gchat_message_me;
      TextView textGchatMessageMe = ViewBindings.findChildViewById(rootView, id);
      if (textGchatMessageMe == null) {
        break missingId;
      }

      id = R.id.text_gchat_timestamp_me;
      TextView textGchatTimestampMe = ViewBindings.findChildViewById(rootView, id);
      if (textGchatTimestampMe == null) {
        break missingId;
      }

      return new MeMessageBinding((ConstraintLayout) rootView, cardGchatMessageMe,
          layoutGchatContainerMe, textGchatMessageMe, textGchatTimestampMe);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}