package adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focusswipe.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ApplicationInfo> appList;
    private Set<Integer> selectedPositions;


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
        holder.checkBox.setChecked(selectedPositions.contains(position));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedPositions.add(position);
            } else {
                selectedPositions.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public List<ApplicationInfo> getSelectedItems() {
        List<ApplicationInfo> selectedItems = new ArrayList<>();
        for (int position : selectedPositions) {
            selectedItems.add(appList.get(position));
        }
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
            checkBox = itemView.findViewById(R.id.radioButton);
        }
    }
}
