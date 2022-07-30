package io.github.ikws4.treeview.documentfile;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import com.lazygeniouz.filecompat.file.DocumentFileCompat;

import java.util.Collections;
import java.util.List;

import io.github.ikws4.treeview.R;
import io.github.ikws4.treeview.TreeItem;
import io.github.ikws4.treeview.TreeView;

class DocumentFileTreeAdapter extends TreeView.Adapter<DocumentFileTreeAdapter.ViewHolder, DocumentFileCompat> implements TreeView.Adapter.OnTreeItemClickListener<DocumentFileCompat> {

    public DocumentFileTreeAdapter(DocumentFileCompat rootFile) {
        setTreeItemClickListener(this);
        TreeItem<DocumentFileCompat> root = new TreeItem<>(rootFile, true);
        expand(root);
        setRoot(root);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_filetree;
    }

    @Override
    public ViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        TreeItem<DocumentFileCompat> item = items.get(position);
        if (item.isExpandable()) {
            holder.icon.setImageResource(R.drawable.ic_folder);
        } else {
            holder.icon.setImageResource(R.drawable.ic_file);
        }

        holder.name.setText(item.getValue().getName());
    }

    @Override
    public void onClick(TreeItem<DocumentFileCompat> item) {
        if (item.isExpandable() && !item.isExpanded()) {
            expand(item);
        }
    }

    static class ViewHolder extends TreeView.ViewHolder {
        ImageView icon;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
        }
    }

    private void expand(TreeItem<DocumentFileCompat> root) {
        if (!root.getChildren().isEmpty()) return;

        for (DocumentFileCompat child : getChildren(root)) {
            TreeItem<DocumentFileCompat> item = new TreeItem<>(child, !child.isFile());
            root.getChildren().add(item);
        }

        Collections.sort(root.getChildren(), (a, b) -> {
            int res = Boolean.compare(b.isExpandable(), a.isExpandable());
            if (res == 0) return a.getValue().getName().compareToIgnoreCase(b.getValue().getName());
            return res;
        });
    }

    private List<DocumentFileCompat> getChildren(TreeItem<DocumentFileCompat> file) {
        return file.getValue().listFiles();
    }
}

