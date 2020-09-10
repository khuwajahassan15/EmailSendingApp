package khuwaja.sis.sendemailfromapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sis.sendemailfromapp.R;

import java.util.Objects;

import khuwaja.sis.sendemailfromapp.MainHelpers.GMailSender;

public class MainActivity extends AppCompatActivity {
    EditText etContent, etRecipient,email,password;
    Button btnSend;
    Dialog getLoginInFoDialog;
    GMailSender gMailSender;
    static  String senderEmail=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etContent = (EditText) findViewById(R.id.etContent);
        etRecipient = (EditText)findViewById(R.id.etRecipient);
        btnSend = (Button) findViewById(R.id.btnSend);
        getSenderInfo();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gMailSender!=null)
                {

                    if(!etContent.getText().toString().isEmpty()&&!etRecipient.getText().toString().isEmpty())
                    {
                        sendMailToAll();
                    }
                    else
                    {Toast.makeText(getApplicationContext(),"Enter the Receiver Email and Message",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    getSenderInfo();
                }
            }
        });

    }

    private void sendMailToAll() {
        if(senderEmail!=null)
        {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait...");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gMailSender.sendMail("CLight App",
                            etContent.getText().toString(),
                            senderEmail,
                            etRecipient.getText().toString());
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();

        }

    }

    private void getSenderInfo() {

            int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
            int height = Resources.getSystem().getDisplayMetrics().heightPixels;
            getLoginInFoDialog = new Dialog(MainActivity.this);
            getLoginInFoDialog.setContentView(R.layout.dialoglayoutadditem);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getLoginInFoDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        getLoginInFoDialog.getWindow().setLayout((int) (width / 1.2f), (int) (height / 1.3));
            getLoginInFoDialog.setCancelable(false);
            final EditText email = getLoginInFoDialog.findViewById(R.id.email);
            final EditText password = getLoginInFoDialog.findViewById(R.id.editTextTextPassword);
            final  Button login=getLoginInFoDialog.findViewById(R.id.btn_add);
            final ImageView ivBack = getLoginInFoDialog.findViewById(R.id.iv_back);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLoginInFoDialog.dismiss();
                }
            });

        login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String mail = email.getText().toString();
                    final String pas = password.getText().toString();
                    boolean status=true;

                    if (mail.isEmpty()) {
                       Toast.makeText(getApplicationContext(),"Email is Empty",Toast.LENGTH_SHORT).show();
                        status=false;
                        return;
                    }
                    if (pas.isEmpty()) {
                        Toast.makeText(getApplicationContext(),"Password is Empty",Toast.LENGTH_SHORT).show();
                        status=false;
                        return;
                    }
                   // usman ali ansari 123456     saabansari624@gmail.com
                    if(status)
                    {
                        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                        dialog.setTitle("Confirming");
                        dialog.setMessage("Please wait...");
                        dialog.show();
                        Thread sender = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    gMailSender = new GMailSender(mail, pas);
                                    senderEmail=mail;
                                    getLoginInFoDialog.dismiss();
                                    dialog.dismiss();
                                } catch (Exception e) {
                                    Log.e("mylog", "Error: " + e.getMessage());
                                    //Toast.makeText(getApplicationContext(),"Error:"+e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        sender.start();

                    }

                }
            });
            getLoginInFoDialog.show();

    }

//    private void sendMessage() {
//
//
//
//
//        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//        dialog.setTitle("Sending Email");
//        dialog.setMessage("Please wait");
//        dialog.show();
//        Thread sender = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    GMailSender sender = new GMailSender("saabansari624@gmail.com", "usman ali ansari 123456");
//                    sender.sendMail("EmailSender App",
//                            etContent.getText().toString(),
//                            "saabansari624@gmail.com",
//                            etRecipient.getText().toString());
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    Log.e("mylog", "Error: " + e.getMessage());
//                }
//            }
//        });
//        sender.start();
//    }
}