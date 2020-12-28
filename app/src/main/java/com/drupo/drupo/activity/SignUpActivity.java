package com.drupo.drupo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.drupo.drupo.R;
import com.drupo.drupo.implementer.SignupPresenterImplementer;
import com.drupo.drupo.networks.NetworkConnectionCheck;
import com.drupo.drupo.utils.AppUtils;
import com.drupo.drupo.view.ISignupView;

import java.util.Objects;

import static com.drupo.drupo.utils.AppUtils.isEmailValid;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, ISignupView {

    private EditText email_et, company_id_et, password_et;
    private Button sigup_btn;
    private TextView terms_tv;
    private String email;
    private CheckBox terms_condition_cb;

    NetworkConnectionCheck networkConnectionCheck;

    SignupPresenterImplementer signupPresenterImplementer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    public void init() {

        View rootView = getWindow().getDecorView().getRootView();

        networkConnectionCheck = new NetworkConnectionCheck(getApplicationContext());

        signupPresenterImplementer = new SignupPresenterImplementer(this, rootView, SignUpActivity.this);
        sigup_btn.setAlpha(0.5f);
        sigup_btn.setClickable(false);
        sigup_btn.setEnabled(false);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.sigup_btn:

                if (networkConnectionCheck.isConnected()) {
                    signupPresenterImplementer.sendRequest();
                    AppUtils.closeKeyboard(this, view);
                }
                break;

            case R.id.terms_tv:

                Intent intent = new Intent(SignUpActivity.this, Terms_ConditonsActivity.class);
                startActivity(intent);

                break;

            case R.id.terms_condition_cb:

                if (terms_condition_cb.isChecked()) {
                    sigup_btn.setAlpha(1f);
                    sigup_btn.setClickable(true);
                    sigup_btn.setEnabled(true);
                } else {
                    sigup_btn.setAlpha(0.5f);
                    sigup_btn.setClickable(false);
                    sigup_btn.setEnabled(false);
                }


                break;

            default:
                break;

        }
    }


    public void TextWatcher() {

        email_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint({"RestrictedApi", "NewApi"})
            @Override
            public void afterTextChanged(Editable editable) {

                email = email_et.getText().toString();

                if (email.length() == 0) {
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#96989f"));
                    email_et.setBackgroundTintList(colorStateList);
                    sigup_btn.setEnabled(false);
                } else if (isEmailValid(email)) {
                    sigup_btn.setEnabled(true);
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#4069E3"));
                    email_et.setBackgroundTintList(colorStateList);

                } else {

                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#f3534a"));
                    email_et.setBackgroundTintList(colorStateList);
                    sigup_btn.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void OnSignUpSuccess() {

    }

    @Override
    public void OnSignUpError() {

    }

    @Override
    public void OnInitView(View view) {


        email_et = view.findViewById(R.id.email_et);
        company_id_et = view.findViewById(R.id.company_id_et);
        password_et = view.findViewById(R.id.password_et);
        sigup_btn = view.findViewById(R.id.sigup_btn);
        terms_tv = view.findViewById(R.id.terms_tv);
        terms_condition_cb = view.findViewById(R.id.terms_condition_cb);

        sigup_btn.setOnClickListener(this);
        terms_tv.setOnClickListener(this);
        terms_condition_cb.setOnClickListener(this);


        TextWatcher();
    }
}
