package telkadjiite.hacktuesx.prilojenie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference database = FirebaseDatabase.getInstance("https://hacktuesxprilojenie-10166-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText passwordConfirmInput;
    Button registerButton;

    String emailValidationPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInputRegister);
        passwordInput = findViewById(R.id.passwordInputRegister);
        passwordConfirmInput = findViewById(R.id.passwordConfirmInput);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register(firstNameInput.getText().toString(), lastNameInput.getText().toString(),
                        emailInput.getText().toString(), passwordInput.getText().toString(),
                        passwordConfirmInput.getText().toString()
                );
            }
        });

    }

    void Register(String fName, String lName, String email, String password, String confirmPassword)
    {
        if(fName.isEmpty())
        {
            firstNameInput.setError("Enter a first name");
            return;
        }

        if(lName.isEmpty())
        {
            lastNameInput.setError("Enter a last name");
            return;
        }

        if(email.isEmpty() || !email.matches(emailValidationPattern))
        {
            emailInput.setError("Enter an email");
            return;
        }

        if(password.isEmpty())
        {
            passwordInput.setError("Enter a password");
            return;
        }

        if(confirmPassword.isEmpty())
        {
            passwordConfirmInput.setError("Confirm your password");
            return;
        }

        if(!Objects.equals(password, confirmPassword))
        {
            passwordConfirmInput.setError("Passwords must match!");
            return;
        }

       mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()) {
                   mUser = mAuth.getCurrentUser();

                   mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(RegisterActivity.this, "A Verification Email Has Been Sent To You", Toast.LENGTH_LONG).show();
                           }
                           else
                           {
                               Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                   User newUser = new User(fName, lName);
                   AddUserToDB(mUser, newUser);
                   SendToLogin();

               }else {

                   Toast.makeText(RegisterActivity.this, "Failed To Create User", Toast.LENGTH_SHORT).show();
                   return;
               }

           }
       });


    }

    void AddUserToDB(FirebaseUser currentFbUser, User user)
    {
        database.child("users").child(currentFbUser.getUid()).child("firstName").setValue(user.firstName);
        database.child("users").child(currentFbUser.getUid()).child("lastName").setValue(user.lastName);
        database.child("users").child(currentFbUser.getUid()).child("description").setValue(user.description);
        database.child("users").child(currentFbUser.getUid()).child("hobbies").setValue(user.hobbies);
        database.child("users").child(currentFbUser.getUid()).child("joinedEvents").setValue(user.joinedEvents);
    }
    void SendToLogin()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}