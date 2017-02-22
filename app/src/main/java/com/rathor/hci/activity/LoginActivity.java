package com.rathor.hci.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rathor.hci.R;
public class LoginActivity extends AppCompatActivity {
    private EditText mEtEmail, mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Login");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        android.util.Log.d("E_VALUE----------------",reference.toString());

        mEtEmail = (EditText) findViewById(R.id.editText1);
        mEtPassword = (EditText) findViewById(R.id.editText2);

        ImageView ivBack = (ImageView) findViewById(R.id.iv_left);
        ivBack.setVisibility(View.VISIBLE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tvforgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailogResetPassword();
            }
        });
        findViewById(R.id.tvLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
       /* Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();*/

        if (mEtEmail.getText().toString().equals("") || !Patterns.EMAIL_ADDRESS.matcher(mEtEmail.getText().toString()).matches()) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_correct_email_id), Toast.LENGTH_SHORT).show();
        } else if (mEtPassword.getText().toString().length() < 4) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_password), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


    }

    private void dailogResetPassword() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Reset Password");

        alert.setMessage("Please enter the e-mail address for your account.");
        final EditText email = new EditText(this);
        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        lay.addView(email);
        alert.setView(lay);

        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
                        final String emailID = email.getText().toString();

                        if (emailID.equals("") || !Patterns.EMAIL_ADDRESS.matcher(emailID).matches()) {
                            Toast.makeText(getApplicationContext(), "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Your password has send on your register email.", Toast.LENGTH_SHORT).show();
                        }


                    }
                }

        );

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener()

                {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
                    }
                }

        );

        alert.show();
    }
}
