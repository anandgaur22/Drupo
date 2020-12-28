package com.drupo.drupo.implementer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
import com.drupo.drupo.presenter.ISignupPresenter;
import com.drupo.drupo.utils.AppConstant;
import com.drupo.drupo.utils.AppUtils;
import com.drupo.drupo.utils.PrefrenceManager;
import com.drupo.drupo.view.ISignupView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignupPresenterImplementer implements ISignupPresenter {
    private ISignupView iSignupView;
    private View view;
    private Context context;
    private EditText email_et, password_et, company_id_et, name_et;
    private String email, password, name, clientID;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;

    public SignupPresenterImplementer(ISignupView iSignupView, View view, Context context) {
        this.iSignupView = iSignupView;
        this.view = view;
        iSignupView.OnInitView(view);
        this.context = context;

    }

    @Override
    public void sendRequest() {

        if (validationCheck() == true) {

            Signup_request();
        }
    }


    public void Signup_request() {

        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setMessage("Please Wait");

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppGlobalUrl.Signup,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            progressDialog.dismiss();
                            if (jsonObject.optString("error").equalsIgnoreCase("0")) {
                                String error = jsonObject.getString("error");
                                String errorType = jsonObject.getString("errorType");
                                String errorDescription = jsonObject.getString("errorDescription");
                                String name = jsonObject.getString("name");
                                String email = jsonObject.getString("email");
                                company_id_et.setText("");
                                name_et.setText("");
                                email_et.setText("");
                                password_et.setText("");
                                PrefrenceManager prefrenceManager = new PrefrenceManager(context);
                                prefrenceManager.saveRegisterResponseDetails(name, email);
                                prefrenceManager.saveSessionLogin();
                                Intent intent = new Intent(context, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                                Toast.makeText(context, "You register successfully..", Toast.LENGTH_SHORT).show();
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
                postparams.put("clientID", clientID);
                postparams.put("name", name);
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

        company_id_et = view.findViewById(R.id.company_id_et);
        name_et = view.findViewById(R.id.name_et);
        email_et = view.findViewById(R.id.email_et);
        password_et = view.findViewById(R.id.password_et);
        checkBox = view.findViewById(R.id.terms_condition_cb);

        email = email_et.getText().toString();
        password = password_et.getText().toString();
        clientID = company_id_et.getText().toString();
        name = name_et.getText().toString();


        if (check == false) {

            if (clientID.equals("")) {

                company_id_et.requestFocus();
                company_id_et.setError("Client ID should not be blank");

            } else if (name.equals("")) {

                name_et.requestFocus();
                name_et.setError("Name should not be blank");

            } else if (email.equals("")) {

                email_et.requestFocus();
                email_et.setError("Email should not be blank");

            } else if ((AppUtils.isEmailValid(email) == false)) {

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
