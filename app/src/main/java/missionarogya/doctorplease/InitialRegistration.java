package missionarogya.doctorplease;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InitialRegistration extends AppCompatActivity {
    User user = User.getUserObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_registration);

        final EditText txtName = (EditText)findViewById(R.id.editName);
        final EditText txtPhone = (EditText)findViewById(R.id.editPhone);
        final EditText txtEmail = (EditText)findViewById(R.id.editEmail);
        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        final RadioButton radioDoctor = (RadioButton)findViewById(R.id.doctor);
        final RadioButton radioPatient = (RadioButton)findViewById(R.id.patient);
        final Button btnRegister = (Button)findViewById(R.id.btnInitialRegister);

        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        txtEmail.setText(accounts[0].name.trim().toLowerCase());

        TelephonyManager tMgr = (TelephonyManager)InitialRegistration.this.getSystemService(Context.TELEPHONY_SERVICE);
        txtPhone.setText(tMgr.getLine1Number());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)findViewById(checkedId);
                String userType = rb.getText().toString().trim().toLowerCase();
                if(userType.equals("doctor")){
                    radioDoctor.setTextColor(Color.BLUE);
                    radioPatient.setTextColor(Color.BLACK);
                }else if(userType.equals("patient")){
                    radioDoctor.setTextColor(Color.BLACK);
                    radioPatient.setTextColor(Color.BLUE);
                }else{
                    radioDoctor.setTextColor(Color.BLACK);
                    radioPatient.setTextColor(Color.BLACK);
                }
                user.setType(userType);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    String email = txtEmail.getText().toString().trim();
                    String phone = txtPhone.getText().toString().trim();
                    String name = txtName.getText().toString().trim();
                    if (email.length() == 0 || name.length() == 0 || phone.length() == 0 || selectedId == -1) {
                        Toast.makeText(InitialRegistration.this, "Please fill in your name, phone, email and user type to register.", Toast.LENGTH_SHORT).show();
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(InitialRegistration.this, "Invalid email address.", Toast.LENGTH_SHORT).show();
                    } else if (!android.util.Patterns.PHONE.matcher(phone).matches()) {
                        Toast.makeText(InitialRegistration.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
                    } else {
                        user.setEmail(email);
                        user.setName(name);
                        user.setPhoneNumber(phone);
                        User.setUserObject(user);
                        Toast.makeText(getApplicationContext(), "Welcome "+user.getType()+"!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InitialRegistration.this, ApplicationWebView.class);
                        InitialRegistration.this.startActivity(intent);
                    }
                }catch(Exception e){
                    Toast.makeText(InitialRegistration.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initial_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
