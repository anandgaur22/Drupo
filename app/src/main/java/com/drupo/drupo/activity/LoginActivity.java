package com.drupo.drupo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.drupo.drupo.R;
import com.drupo.drupo.implementer.LoginPresenterImplementer;
import com.drupo.drupo.networks.NetworkConnectionCheck;
import com.drupo.drupo.utils.AppUtils;
import com.drupo.drupo.view.ILoginView;

import static com.drupo.drupo.utils.AppUtils.isEmailValid;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {


    private Button sigin_btn;
    private EditText username_et, password_et;
    private LoginPresenterImplementer loginPresenterImplementer;
    private NetworkConnectionCheck networkConnectionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public void init() {

        View rootView = getWindow().getDecorView().getRootView();

        networkConnectionCheck = new NetworkConnectionCheck(getApplicationContext());

        loginPresenterImplementer = new LoginPresenterImplementer(this, rootView, LoginActivity.this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.sigin_btn:
                if (networkConnectionCheck.isConnected()) {
                    loginPresenterImplementer.sendRequest();
                    AppUtils.closeKeyboard(this, view);
                }
                break;

            default:
                break;
        }
    }


    @Override
    public void OnLoginSuccess() {

    }

    @Override
    public void OnLoginError() {

    }

    @Override
    public void OnInitView(View view) {

        sigin_btn = view.findViewById(R.id.sigin_btn);
        username_et = view.findViewById(R.id.username_et);
        password_et = view.findViewById(R.id.password_et);

        sigin_btn.setOnClickListener(this);
    }
}
