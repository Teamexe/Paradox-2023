package com.exe.paradox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.exe.paradox.Tools.Method;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.response.CreateUserRP;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements OnConnectionFailedListener, GoogleApiClient.OnConnectionFailedListener {

    private FrameLayout googleLoginBtn;
    private ProgressBar progressBar;
    private BeginSignInRequest signInRequest;

    private static final int REQ_ONE_TAP = 902;
    private boolean showOneTapUI = true;
    private SignInClient oneTapClient;
    //Todo: Remove deprecated stuff and move to OneTap Completely
    private GoogleApiClient googleApiClient;
    private static final int REQ_DEFAULT_GOOGLE = 901;

    private static final String TAG = "LoginInfo";

    private FirebaseAuth mAuth;

    public void onStart() {
        super.onStart();
        if (mAuth == null){
            mAuth = FirebaseAuth.getInstance();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null){
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                        mAuth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().getAdditionalUserInfo().isNewUser()){
                                                APIMethods.createUser(new APIResponseListener<CreateUserRP>() {
                                                    @Override
                                                    public void success(CreateUserRP response) {
                                                        LoginActivity.this.success("Login Successful");
                                                        updateUI(mAuth.getCurrentUser());
                                                    }

                                                    @Override
                                                    public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                                                        progressBar.setVisibility(View.GONE);
                                                        Method.showFailedAlert(LoginActivity.this, code + " - " +  message);
                                                    }
                                                });
                                                return;
                                            }
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                                            updateUI(null);
                                        }
                                    }
                                });
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            case REQ_DEFAULT_GOOGLE:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
                break;
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String idToken = account.getIdToken();
            String name = account.getDisplayName();
            String email = account.getEmail();
            // you can store user data to SharedPreference
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuthWithGoogle(credential);
        }else{
            // Google Sign In failed, update UI appropriately
            Log.e(TAG, "Login Unsuccessful. "+result);
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(AuthCredential credential){

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){
                            if (task.getResult().getAdditionalUserInfo().isNewUser()){
                                APIMethods.createUser(new APIResponseListener<CreateUserRP>() {
                                    @Override
                                    public void success(CreateUserRP response) {
                                        LoginActivity.this.success("Login Successful");
                                        updateUI(mAuth.getCurrentUser());
                                    }

                                    @Override
                                    public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                                        progressBar.setVisibility(View.GONE);
                                        Method.showFailedAlert(LoginActivity.this, code + " - " +  message);
                                    }
                                });
                                return;
                            }
                            updateUI(mAuth.getCurrentUser());
                        }else{
                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            error("Auth Failed: "+ task.getException().getMessage());
                        }

                    }
                });
    }

    private void stopProgress(){
        progressBar.setVisibility(View.GONE);
    }

    private void startProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void error(String error){
        Toast.makeText(this, "Some error occurred: " + error, Toast.LENGTH_SHORT).show();
        stopProgress();
    }

    private void success(String msg){
        Toast.makeText(this, "Task successful: " + msg, Toast.LENGTH_SHORT).show();
        stopProgress();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        setListeners();
        setUpGoogleLogin();
    }

    private void setUpGoogleLogin() {
        //Default Login

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,LoginActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        //One Tap Login
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        startOneTapLogin();
    }

    private void startOneTapLogin() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d(TAG, e.getLocalizedMessage());
                    }
                });
    }

    private void setListeners() {
        googleLoginBtn.setOnClickListener(view -> initiateGoogleLogin());
    }

    private void initiateGoogleLogin() {
        startProgress();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_DEFAULT_GOOGLE);
    }


    private void findViews() {
        googleLoginBtn = findViewById(R.id.googleLoginBtn);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        error(connectionResult.getErrorMessage());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}