package photoapp.mca.mk.appphoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class MyAdapter extends Adapter<ActivityInfoItem> {

    public static final String IMG_URL = "imgUrl";
    public static final String TITLE = "title";
    public static final String POSITION = "POSITION";
    public static final int REQUEST_CODE = 1;

    private List<ApiModel> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    private Context mContext;

    public MyAdapter(Context context) {

        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ActivityInfoItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = layoutInflater.inflate(R.layout.acitivity_info_item, null);
        ActivityInfoItem holder = new ActivityInfoItem(rootView);
        holder.parent = rootView;
        holder.icon = rootView.findViewById(R.id.item_icon_image);
        holder.name = rootView.findViewById(R.id.item_name);
        rootView.setTag(holder);
        mContext = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(final ActivityInfoItem holder, int position) {
        final ApiModel model = data.get(position);

        holder.name.setText(model.getmTitle());

        Glide.with(context)
                .load(model.getThumbnailUrl())
                .into(holder.icon);

        Intent startSecondActivity = new Intent(context, SecondActivity.class);
        startSecondActivity.putExtra(IMG_URL, model.getUrl());
        startSecondActivity.putExtra(TITLE, model.getmTitle());
        startSecondActivity.putExtra(POSITION, position);

        holder.parent.setOnClickListener(v -> {

            ((Activity)mContext).startActivityForResult(startSecondActivity, REQUEST_CODE);


        });
        holder.parent.setOnLongClickListener((View.OnLongClickListener) view -> {


           removeAt(position);
        return false;
        });
    }

    public void removeAt(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    public void remove(String userName) {
        for (ApiModel model: data ) {
            if(model.getmTitle() .equals(userName)) {
                data.remove(model);
                notifyDataSetChanged();
                Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ApiModel> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }
    public void changeUserName(String oldUserName, String newUsername){
        for (ApiModel model: data  ) {
            if(model.getmTitle().equals(oldUserName)){
                model.setTitle(newUsername);
                this.notifyDataSetChanged();
                Toast.makeText(context, "Title edited", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Context getContext() {
        return context;
    }


}
