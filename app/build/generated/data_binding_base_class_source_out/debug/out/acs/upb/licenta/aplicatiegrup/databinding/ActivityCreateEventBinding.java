// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCreateEventBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton back;

  @NonNull
  public final Button buttonCreateEventPart2;

  @NonNull
  public final TextView chosenDate;

  @NonNull
  public final Button chosenHour;

  @NonNull
  public final EditText eventLocation;

  @NonNull
  public final EditText eventName;

  @NonNull
  public final Spinner spinnerGroupName;

  private ActivityCreateEventBinding(@NonNull ConstraintLayout rootView, @NonNull ImageButton back,
      @NonNull Button buttonCreateEventPart2, @NonNull TextView chosenDate,
      @NonNull Button chosenHour, @NonNull EditText eventLocation, @NonNull EditText eventName,
      @NonNull Spinner spinnerGroupName) {
    this.rootView = rootView;
    this.back = back;
    this.buttonCreateEventPart2 = buttonCreateEventPart2;
    this.chosenDate = chosenDate;
    this.chosenHour = chosenHour;
    this.eventLocation = eventLocation;
    this.eventName = eventName;
    this.spinnerGroupName = spinnerGroupName;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCreateEventBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCreateEventBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_create_event, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCreateEventBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back;
      ImageButton back = ViewBindings.findChildViewById(rootView, id);
      if (back == null) {
        break missingId;
      }

      id = R.id.buttonCreateEventPart2;
      Button buttonCreateEventPart2 = ViewBindings.findChildViewById(rootView, id);
      if (buttonCreateEventPart2 == null) {
        break missingId;
      }

      id = R.id.chosenDate;
      TextView chosenDate = ViewBindings.findChildViewById(rootView, id);
      if (chosenDate == null) {
        break missingId;
      }

      id = R.id.chosenHour;
      Button chosenHour = ViewBindings.findChildViewById(rootView, id);
      if (chosenHour == null) {
        break missingId;
      }

      id = R.id.eventLocation;
      EditText eventLocation = ViewBindings.findChildViewById(rootView, id);
      if (eventLocation == null) {
        break missingId;
      }

      id = R.id.eventName;
      EditText eventName = ViewBindings.findChildViewById(rootView, id);
      if (eventName == null) {
        break missingId;
      }

      id = R.id.spinnerGroupName;
      Spinner spinnerGroupName = ViewBindings.findChildViewById(rootView, id);
      if (spinnerGroupName == null) {
        break missingId;
      }

      return new ActivityCreateEventBinding((ConstraintLayout) rootView, back,
          buttonCreateEventPart2, chosenDate, chosenHour, eventLocation, eventName,
          spinnerGroupName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}