package io.github.ikws4.treeview.documentfile;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import java.util.Collections;

import io.github.ikws4.treeview.R;
import io.github.ikws4.treeview.TreeItem;
import io.github.ikws4.treeview.TreeView;

class DocumentFileTreeAdapter extends TreeView.Adapter<DocumentFileTreeAdapter.ViewHolder, DocumentFile> implements TreeView.Adapter.OnTreeItemClickListener<DocumentFile> {

    public DocumentFileTreeAdapter(DocumentFile rootFile) {
        setTreeItemClickListener(this);
        TreeItem<DocumentFile> root = new TreeItem<>(rootFile, true);
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

        TreeItem<DocumentFile> item = items.get(position);
        if (item.isExpandable()) {
            holder.icon.setImageResource(R.drawable.ic_folder);
        } else {
            holder.icon.setImageResource(R.drawable.ic_file);
        }

        holder.name.setText(item.getValue().getName());
    }

    @Override
    public void onClick(TreeItem<DocumentFile> item) {
        if (item.isExpandable() && !item.isExpanded()) {
            expand(item);
        }
    }
    
    @Override
    public void onLongClick(TreeItem<DocumentFile> item) {
        
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

    private void expand(TreeItem<DocumentFile> root) {
        if (!root.getChildren().isEmpty()) return;

        for (DocumentFile child : getChildren(root)) {
            TreeItem<DocumentFile> item = new TreeItem<>(child, child.isDirectory());
            root.getChildren().add(item);
        }
        Collections.sort(root.getChildren(), (a, b) -> {
            if (a.isExpandable() && b.isExpandable()) {
                return a.getValue().getName().compareTo(b.getValue().getName());
            } else if (b.isExpandable()) {
                return 1;
            } else {
                return -1;
            }
        });
    }

    private DocumentFile[] getChildren(TreeItem<DocumentFile> file) {
        return file.getValue().listFiles();
    }
}

