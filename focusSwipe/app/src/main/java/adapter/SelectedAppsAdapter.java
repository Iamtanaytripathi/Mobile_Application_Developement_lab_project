package adapter;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectedAppsAdapter extends RecyclerView.Adapter<SelectedAppsAdapter.ViewHolder> {

    private final List<ApplicationInfo> selectedApps;

    public SelectedAppsAdapter(List<ApplicationInfo> selectedApps) {
        this.selectedApps = selectedApps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_selected_app, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApplicationInfo appInfo = selectedApps.get(position);
        String appName = appInfo.loadLabel(holder.itemView.getContext().getPackageManager()).toString();
        Drawable appIcon = appInfo.loadIcon(holder.itemView.getContext().getPackageManager());

        holder.appNameTextView.setText(appName);
        holder.appIconImageView.setImageDrawable(appIcon);
    }

    @Override
    public int getItemCount() {
        return selectedApps.size();
    }

    public List<String> getSelectedAppPackageNames() {
        List<String> selectedAppPackageNames = new ArrayList<>();
        for (ApplicationInfo appInfo : selectedApps) {
            selectedAppPackageNames.add(appInfo.packageName);
        }
        return selectedAppPackageNames;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIconImageView;
        TextView appNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appIconImageView = itemView.findViewById(R.id.selectedAppIcon);
            appNameTextView = itemView.findViewById(R.id.selectedAppName);
        }
    }
}

