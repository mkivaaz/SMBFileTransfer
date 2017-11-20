package kivaaz.com.smbfiletransfer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muguntan on 11/20/2017.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.myViewHolder>{

    private LayoutInflater inflater;
    List<FileList> files = new ArrayList<>();
    Context context;

    OnItemClick mCallBack;

    public FileAdapter(List<FileList> files, Context context, OnItemClick mCallBack) {
        this.files = files;
        this.context = context;
        this.mCallBack = mCallBack;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.files_adapter,null);
        myViewHolder holder = new myViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        FileList f = files.get(position);
        holder.file_name.setText(f.getFilename());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView file_image;
        TextView file_name;
        LinearLayout file_box;
        public myViewHolder(View itemView) {
            super(itemView);

            file_image = itemView.findViewById(R.id.file_img);
            file_name = itemView.findViewById(R.id.filename);
            file_box = itemView.findViewById(R.id.linear_box);

            file_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    FileList file;
                    file = files.get(position);


                    mCallBack.OnClick(file);
                }
            });
        }
    }

    interface OnItemClick {
        void OnClick(FileList fileList);
    }
}
