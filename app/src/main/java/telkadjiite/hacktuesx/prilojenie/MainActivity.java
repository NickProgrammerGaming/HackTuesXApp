package telkadjiite.hacktuesx.prilojenie;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText emailEditText;
    EditText passwordEditText;
    TextView forgotPassword;
    TextView registerText;
    Button loginButton;

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
       emailEditText = findViewById(R.id.emailInput);
       passwordEditText = findViewById(R.id.passwordInput);
       forgotPassword = findViewById(R.id.forgotPassword);
       registerText = findViewById(R.id.registerText);
       loginButton = findViewById(R.id.loginButton);

       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Login
           }
       });

        
    }


}
