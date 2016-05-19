package me.didik.signindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Scope;

public class MainActivity extends AppCompatActivity {
    private SignInProvider mSignInProvider;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);

        mSignInProvider = new SignInProvider(this);

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(new Scope[]{new Scope(Scopes.PLUS_LOGIN)});
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignInProvider.signOut();
                mSignInProvider.signIn();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSignInProvider.handleActivityResult(requestCode, data, new SignInProvider.Callbacks() {
            @Override
            public void onSuccess(String name, String email, String photo) {
                String str = "Name: " + name + "\n"
                        + "Email: " + email + "\n"
                        + "Photo: " + photo;
                txt.setText(str);
                updateUI(true);
            }

            @Override
            public void onFailed() {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                updateUI(false);
            }
        });
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.txt).setVisibility(View.VISIBLE);
        } else {

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.txt).setVisibility(View.GONE);
        }
    }
}
