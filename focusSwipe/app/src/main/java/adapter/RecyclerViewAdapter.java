package adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focusswipe.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ApplicationInfo> appList;

    public RecyclerViewAdapter(Context context, List<ApplicationInfo> appList) {
        this.context = context;
        this.appList = appList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApplicationInfo appInfo = appList.get(position);
        String appName = appInfo.loadLabel(context.getPackageManager()).toString();
        Drawable appIcon = appInfo.loadIcon(context.getPackageManager());

        holder.appNameTextView.setText(appName);
        holder.appIconImageView.setImageDrawable(appIcon);
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIconImageView;
        TextView appNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appIconImageView = itemView.findViewById(R.id.appIcon);
            appNameTextView = itemView.findViewById(R.id.appName);
        }
    }
}
