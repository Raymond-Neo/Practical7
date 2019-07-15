package sg.edu.np.practical_7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser extends AppCompatActivity {
    public static final String MY_GLOBAL_PREF = "MyGlobalPref";
    public static final String USER_NAME = "MyUser";
    public static final String PASS = "MyPass";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        sharedpreferences = getSharedPreferences(MY_GLOBAL_PREF,MODE_PRIVATE);
        final DbHandler dbHandler = new DbHandler(this,null,null,1);
        final Intent intent = new Intent(CreateUser.this,MainActivity.class);

        Button cancel = findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        Button create = findViewById(R.id.create_btn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView username = findViewById(R.id.Create_UserText);
                TextView password = findViewById(R.id.Create_PasswordText);
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String user_regex = "^[0-9a-zA-z]{6,12}$";
                String pass_regex = "^(?=.*[!@#%&*+=])(?=.*[0-9])(?=.*[a-zA-z])[!@#%&*+=0-9a-zA-z].{6,12}$";
                Pattern user_pattern = Pattern.compile(user_regex);
                Matcher user_matcher = user_pattern.matcher(user);
                Pattern pass_pattern = Pattern.compile(pass_regex);
                Matcher pass_matcher = pass_pattern.matcher(pass);
                if(user_matcher.matches() && pass_matcher.matches()) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(USER_NAME,user);
                    editor.putString(PASS,pass);
                    editor.apply();
                    UserData u = new UserData(user,pass);
                    dbHandler.addUser(u);
                    Toast tt = Toast.makeText(CreateUser.this,"account created successfully", Toast.LENGTH_LONG);
                    tt.show();
                    startActivity(intent);

                }
                else{
                    Toast tt = Toast.makeText(CreateUser.this,"Invalid username or password", Toast.LENGTH_LONG);
                    tt.show();
                }
            }
        });



    }
}
