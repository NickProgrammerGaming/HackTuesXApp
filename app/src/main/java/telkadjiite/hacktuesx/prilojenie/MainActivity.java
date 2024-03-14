package telkadjiite.hacktuesx.prilojenie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText emailEditText;
    EditText passwordEditText;
    TextView forgotPassword;
    TextView registerText;
    TextView registerTextButton;
    Button loginButton;

    String emailValidationPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       mAuth = FirebaseAuth.getInstance();
       mUser = mAuth.getCurrentUser();
       emailEditText = findViewById(R.id.emailInput);
       passwordEditText = findViewById(R.id.passwordInput);
       forgotPassword = findViewById(R.id.forgotPassword);
       registerText = findViewById(R.id.registerText);
       registerTextButton = findViewById(R.id.registerTextButton);
       loginButton = findViewById(R.id.loginButton);

       registerTextButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SendToRegister();
           }
       });

       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Login(emailEditText.getText().toString(), passwordEditText.getText().toString());
           }
       });

       forgotPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SendToReset();
           }
       });

        
    }

    void Login(String email, String password)
    {
        if(email.isEmpty() || !email.matches(emailValidationPattern))
        {
            emailEditText.setError("Enter your email");
            return;
        }

        if(password.isEmpty())
        {
            passwordEditText.setError("Enter your password");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(!mUser.isEmailVerified())
                    {
                        Toast.makeText(MainActivity.this, "Email Not Verified!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                        SendToMainPage();
                    }


                }
                else
                {
                    Toast.makeText(MainActivity.this, "Unsuccessfully Logged In", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    void SendToMainPage()
    {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);

    }

    void SendToRegister()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    void SendToReset()
    {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }


}
