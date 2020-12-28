package com.drupo.drupo.implementer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.drupo.drupo.R;
import com.drupo.drupo.activity.HomeActivity;
import com.drupo.drupo.networks.AppGlobalUrl;
import com.drupo.drupo.networks.VolleySingleton;
import com.drupo.drupo.presenter.ILoginPresenter;
import com.drupo.drupo.utils.AppConstant;
import com.drupo.drupo.utils.AppUtils;
import com.drupo.drupo.utils.PrefrenceManager;
import com.drupo.drupo.view.ILoginView;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import static com.drupo.drupo.utils.AppUtils.isEmailValid;

public class LoginPresenterImplementer implements ILoginPresenter {
    private ILoginView iLoginView;
    private View view;
    private Context context;
    private EditText email_et, password_et;
    private String email, password;
    private ProgressDialog progressDialog;

    public LoginPresenterImplementer(ILoginView context, View view, Context context1) {
        this.iLoginView = context;
        this.context = context1;
        iLoginView.OnInitView(view);
        this.view = view;
    }

    @Override
    public void sendRequest() {
        if (validationCheck() == true) {
            Login_request();
        }
    }

    public void Login_request() {
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setMessage("Please Wait");
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppGlobalUrl.Login,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            progressDialog.dismiss();

                            if (jsonObject.optString("status").equalsIgnoreCase("0")) {

                                String name = jsonObject.getString("name");
                                String siteCode = jsonObject.getString("siteCode");
                                String siteURL = jsonObject.getString("siteURL");

                                PrefrenceManager prefrenceManager = new PrefrenceManager(context);
                                prefrenceManager.saveLoginResponseDetails(name, siteURL, siteCode);
                                prefrenceManager.saveSessionLogin();

                                Intent intent = new Intent(context, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                                Toast.makeText(context, "Login successfully..", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(context, "" + jsonObject.optString("errorDescription"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> postparams = new HashMap<String, String>();
                postparams.put("email", email);
                postparams.put("password", password);
                postparams.put("securecode", AppConstant.securecode);

                return postparams;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public boolean validationCheck() {
        boolean check = false;
        email_et = view.findViewById(R.id.username_et);
        password_et = view.findViewById(R.id.password_et);
        email = email_et.getText().toString();
        password = password_et.getText().toString();

        if (check == false) {

            if (email.equals("")) {

                email_et.requestFocus();
                email_et.setError("Email should not be blank");

            } else if ((isEmailValid(email) == false)) {

                email_et.requestFocus();
                email_et.setError("Please enter correct email");

            } else if (password.equals("")) {
                password_et.requestFocus();
                password_et.setError("Password should not be blank");
            } else {
                check = true;
            }
        }
        return check;
    }

}