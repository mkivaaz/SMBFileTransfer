package kivaaz.com.smbfiletransfer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class file_view extends AppCompatActivity implements FileAdapter.OnItemClick{

    RecyclerView file_recycle;
    FileAdapter adapter;
    List<FileList> allFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);

        file_recycle = findViewById(R.id.file_recycle);
        String extra = getIntent().getStringExtra("FILES");
        Gson gson =new Gson();
        Type listType = new TypeToken<List<FileList>>() {
        }.getType();
        List<FileList> files  = gson.fromJson(extra,listType);
        Log.d("LIST",files.toString());

        adapter = new FileAdapter(files, getBaseContext(), new FileAdapter.OnItemClick() {
            @Override
            public void OnClick(FileList fileList) {

            }
        });
        file_recycle.setAdapter(adapter);
        file_recycle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void OnClick(FileList fileList) {

    }
}
