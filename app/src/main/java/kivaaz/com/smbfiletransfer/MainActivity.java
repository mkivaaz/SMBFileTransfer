package kivaaz.com.smbfiletransfer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class MainActivity extends AppCompatActivity {
    EditText IPet, userEt,passEt;
    Button connectBtn, clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IPet = (EditText) findViewById(R.id.IPEt);
        userEt = (EditText) findViewById(R.id.usernameEt);
        passEt = (EditText) findViewById(R.id.passEt);

        connectBtn = (Button) findViewById(R.id.btnConnect);
        clearBtn = (Button) findViewById(R.id.btnClear);

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SMBAsync().execute();
            }
        });


    }

    private void ConnectingSMBServer() {
        try{
            String PASSWORD = "45314523";
            String USERNAME ="wakerz";
            String IP_ADDRESS = "192.168.1.67";
            String PATH = "smb://" + IP_ADDRESS;

            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,USERNAME,PASSWORD);


            final SmbFile smbFile = new SmbFile(PATH,auth);
            smbFile.connect();
            Log.e("Connected","Yes");
            final String nameoffile = smbFile.getName();
            final String pathoffile = smbFile.getPath();

            if(smbFile.exists()){
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SmbFile[] files = smbFile.listFiles();
                            Toast.makeText(getBaseContext(), pathoffile + files[3].getName(),Toast.LENGTH_SHORT).show();
                            List<FileList> fileList = new ArrayList<FileList>();
                            for (SmbFile file : files){
                                FileList file_p = new FileList();
                                file_p.setFilename(file.getName());
                                file_p.setFilepath(file.getCanonicalPath());
                                fileList.add(file_p);
                            }

                            Gson gson = new Gson();
                            String json = gson.toJson(fileList);
                            Log.d("JSON", json);

                            Intent intent = new Intent(getBaseContext(),file_view.class);
                            intent.putExtra("FILES", json);
                            startActivity(intent);

                        } catch (SmbException e) {
                            e.printStackTrace();
                        }

                    }
                });


            }



        } catch (MalformedURLException e) {
            Log.e("Connected",e.getMessage() + " MalformedURLException");
            e.printStackTrace();

        } catch (IOException e) {
            Log.e("Connected",e.getMessage() + " IOException");
            e.printStackTrace();
        }
    }

    private class SMBAsync extends AsyncTask<Void, Void, Void> {
            @Override
        protected Void doInBackground(Void... voids) {
            ConnectingSMBServer();
            return null;
        }
    }

}
