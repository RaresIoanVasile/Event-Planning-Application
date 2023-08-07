// Generated by view binder compiler. Do not edit!
package acs.upb.licenta.aplicatiegrup.databinding;

import acs.upb.licenta.aplicatiegrup.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText birthdate;

  @NonNull
  public final EditText email;

  @NonNull
  public final EditText fullname;

  @NonNull
  public final TextView goLogin;

  @NonNull
  public final EditText password;

  @NonNull
  public final EditText phone;

  @NonNull
  public final ProgressBar progressBarRegister;

  @NonNull
  public final Button registerBtn;

  @NonNull
  public final TextView textView;

  private ActivityRegisterBinding(@NonNull ConstraintLayout rootView, @NonNull EditText birthdate,
      @NonNull EditText email, @NonNull EditText fullname, @NonNull TextView goLogin,
      @NonNull EditText password, @NonNull EditText phone, @NonNull ProgressBar progressBarRegister,
      @NonNull Button registerBtn, @NonNull TextView textView) {
    this.rootView = rootView;
    this.birthdate = birthdate;
    this.email = email;
    this.fullname = fullname;
    this.goLogin = goLogin;
    this.password = password;
    this.phone = phone;
    this.progressBarRegister = progressBarRegister;
    this.registerBtn = registerBtn;
    this.textView = textView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.birthdate;
      EditText birthdate = ViewBindings.findChildViewById(rootView, id);
      if (birthdate == null) {
        break missingId;
      }

      id = R.id.email;
      EditText email = ViewBindings.findChildViewById(rootView, id);
      if (email == null) {
        break missingId;
      }

      id = R.id.fullname;
      EditText fullname = ViewBindings.findChildViewById(rootView, id);
      if (fullname == null) {
        break missingId;
      }

      id = R.id.go_login;
      TextView goLogin = ViewBindings.findChildViewById(rootView, id);
      if (goLogin == null) {
        break missingId;
      }

      id = R.id.password;
      EditText password = ViewBindings.findChildViewById(rootView, id);
      if (password == null) {
        break missingId;
      }

      id = R.id.phone;
      EditText phone = ViewBindings.findChildViewById(rootView, id);
      if (phone == null) {
        break missingId;
      }

      id = R.id.progressBarRegister;
      ProgressBar progressBarRegister = ViewBindings.findChildViewById(rootView, id);
      if (progressBarRegister == null) {
        break missingId;
      }

      id = R.id.register_btn;
      Button registerBtn = ViewBindings.findChildViewById(rootView, id);
      if (registerBtn == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ConstraintLayout) rootView, birthdate, email, fullname,
          goLogin, password, phone, progressBarRegister, registerBtn, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}