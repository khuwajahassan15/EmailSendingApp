package khuwaja.sis.sendemailfromapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.opencsv.CSVWriter;
import com.sis.sendemailfromapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.FileWriter;
import java.io.IOException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


import khuwaja.sis.sendemailfromapp.MainHelpers.GMailSender;
import khuwaja.sis.sendemailfromapp.MainHelpers.Sale;


public class MainActivity extends AppCompatActivity {
    EditText etContent, etRecipient,email,password;
    Button btnSend;
    Dialog getLoginInFoDialog;
    GMailSender gMailSender;
    static  String senderEmail=null;
    public static final int REQUEST_WRITE_PERMISSION = 786;
    static File folderpath=null;
    ArrayList<Sale> mSaleDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etContent = (EditText) findViewById(R.id.etContent);
        etRecipient = (EditText)findViewById(R.id.etRecipient);
        btnSend = (Button) findViewById(R.id.btnSend);
        loadData();
        requestPermission();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                       // getCSV();




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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // check whether storage permission granted or not.
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // do what you want;
                         getSenderInfo();
                    }
                }
                break;
            default:
                break;
        }
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {

               getSenderInfo();

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


    public static File getCSV() throws FileNotFoundException {
        //Create Folder
        File file=null;
        boolean t=createDirIfNotExists("/CLightData/Sales");
        if(t)
        {
            String extStorageDirectory = folderpath.toString();

            String fileName="Sale"+getUnixTimeStamp()+".csv";
              file = new File(extStorageDirectory,fileName);
            try {
                // create FileWriter object with file as parameter
                FileWriter outputfile = new FileWriter(file);

                // create CSVWriter object filewriter object as parameter
                CSVWriter writer = new CSVWriter(outputfile);

                List<String[]> data2 = toStringArrayFromSale(mSaleDataSource);
                // create a List which contains String array
                writer.writeAll(data2);
                // closing writer connection
                writer.close();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(MainActivity.this,"Error Making Folder", Toast.LENGTH_LONG).show();

        }
        return file;
    }
    private static List<String[]> toStringArrayFromSale(List<Sale> emps) {
        List<String[]> records = new ArrayList<String[]>();

        // adding header record
        records.add(new String[] { "label", "msatoshi", "status", "paid_at","payment_preimage","description"});
        Iterator<Sale> it = emps.iterator();
        while (it.hasNext()) {
            Sale emp = it.next();
            records.add(new String[] { emp.getLabel(), excatFigure(emp.getMsatoshi()), emp.getStatus(), String.valueOf(emp.getPaid_at()),emp.getPayment_preimage(),emp.getDescription() });
        }
        return records;
    }

    public static File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "testimage.jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }
    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;
         folderpath = new File(Environment.getExternalStorageDirectory(), path);
        if (!folderpath.exists()) {
            if (!folderpath.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                ret = false;
            }
        }
        return ret;
    }
    public  String getUnixTimeStamp() {
        Long tsLong = System.currentTimeMillis()/1000;
        String uNixtimeStamp=tsLong.toString();
        return  uNixtimeStamp;
    }
    public static String excatFigure(double value) {
        BigDecimal d = new BigDecimal(String.valueOf(value));

        return  d.toPlainString();
    }
    private void loadData() {

        mSaleDataSource=new ArrayList<>();
        mSaleDataSource.add(new Sale("sale1",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));
        mSaleDataSource.add(new Sale("sale2",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));
        mSaleDataSource.add(new Sale("sale3",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));
        mSaleDataSource.add(new Sale("sale4",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));
        mSaleDataSource.add(new Sale("sale5",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));
        mSaleDataSource.add(new Sale("sale6",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));
        mSaleDataSource.add(new Sale("sale7",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));
        mSaleDataSource.add(new Sale("sale8",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));
        mSaleDataSource.add(new Sale("sale9",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));
        mSaleDataSource.add(new Sale("sale10",20000000,"paid",1600071908,"abcdefghijklmnopqrstuvwxyz","AppleSaleDone"));

    }
}