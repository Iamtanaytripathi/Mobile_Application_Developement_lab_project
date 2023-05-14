package adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focusswipe.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ApplicationInfo> appList;
    private static List<ApplicationInfo> selectedItems;

    public RecyclerViewAdapter(Context context, List<ApplicationInfo> appList) {
        this.context = context;
        this.appList = appList;
        selectedItems = new ArrayList<>();
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

        // Set checkbox state based on selection
        holder.checkBox.setChecked(selectedItems.contains(appInfo));

        // Handle checkbox state changes
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Add app to selected items
                Toast.makeText(context, "Selected: " + appInfo.loadLabel(context.getPackageManager()), Toast.LENGTH_SHORT).show();
                selectedItems.add(appInfo);
            } else {
                // Remove app from selected items
                selectedItems.remove(appInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public static List<ApplicationInfo> getSelectedItems() {
        Log.d("DEBUG", "Selected Items: " + selectedItems.toString());
        return selectedItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIconImageView;
        TextView appNameTextView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appIconImageView = itemView.findViewById(R.id.appIcon);
            appNameTextView = itemView.findViewById(R.id.appName);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
