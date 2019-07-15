package sg.edu.np.practical_7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public static final String MY_GLOBAL_PREF = "MyGlobalPref";
    public static final String USER_NAME = "MyUser";
    public static final String PASS = "MyPass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DbHandler dbHandler = new DbHandler(this,null,null,1);
        TextView user_text = findViewById(R.id.Username_Text);
        TextView pass_text = findViewById(R.id.Password_Text);
        final String user = user_text.getText().toString();
        final String pass = pass_text.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(MY_GLOBAL_PREF,MODE_PRIVATE);
        final String correct_user = sharedPreferences.getString(USER_NAME,"not found");
        final String correct_pass = sharedPreferences.getString(PASS,"not found");

        Button btn = findViewById(R.id.NewAcc_btn);
        Button login = findViewById(R.id.Login_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateUser.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correct_pass == "not found" || correct_user == "not found" ) {
                    Toast tt = Toast.makeText(MainActivity.this, "Username or Password does not exist", Toast.LENGTH_LONG);
                    tt.show();
                }
                else if (dbHandler.findUser(user).getMyUserName() == user && dbHandler.findUser(user).getMyPassword() == pass){
                    Toast tt = Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG);
                    tt.show();
                    Intent intent = new Intent(MainActivity.this,ForecastPage.class);
                    startActivity(intent);
                }
                /*else if (correct_pass == pass && correct_user == user){
                    Toast tt = Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG);
                    tt.show();
                    Intent intent = new Intent(MainActivity.this,ForecastPage.class);
                    startActivity(intent);
                }*/
                else if (correct_pass != pass|| correct_user != user ){
                    Toast tt = Toast.makeText(MainActivity.this, "Username or Password does not match", Toast.LENGTH_LONG);
                    tt.show();
                }
            }
        });


       }
}
