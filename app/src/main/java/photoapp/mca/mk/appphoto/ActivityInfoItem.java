package photoapp.mca.mk.appphoto;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ActivityInfoItem extends ViewHolder {

    public ImageView icon;
    public TextView name;
    public TextView packageName;
    public TextView activity;
    public View parent;


    public ActivityInfoItem(View itemView) {
        super(itemView);
    }
}
