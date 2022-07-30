package io.github.ikws4.treeview.file;


import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.Collections;

import io.github.ikws4.treeview.R;
import io.github.ikws4.treeview.TreeItem;
import io.github.ikws4.treeview.TreeView;

class FileTreeAdapter extends TreeView.Adapter<FileTreeAdapter.ViewHolder, String> implements TreeView.Adapter.OnTreeItemClickListener<String> {

  public FileTreeAdapter() {
    setTreeItemClickListener(this);
    File rootFile = Environment.getExternalStorageDirectory();
    TreeItem<String> root = new TreeItem<>(rootFile.getPath(), true);
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

    TreeItem<String> item = items.get(position);
    if (item.isExpandable()) {
      holder.icon.setImageResource(R.drawable.ic_folder);
    } else {
      holder.icon.setImageResource(R.drawable.ic_file);
    }

    holder.name.setText(item.getValue());
  }

  @Override
  public void onClick(TreeItem<String> item) {
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

  private void expand(TreeItem<String> root) {
    if (!root.getChildren().isEmpty()) return;

    for (File child : getChildren(new File(getFilePath(root)))) {
      TreeItem<String> item = new TreeItem<>(child.getName(), child.isDirectory());
      root.getChildren().add(item);
    }
    Collections.sort(root.getChildren(), (a, b) -> {
      int res = Boolean.compare(b.isExpandable(), a.isExpandable());
      if (res == 0) return a.getValue().compareToIgnoreCase(b.getValue());
      return res;
    });
  }

  private String getFilePath(TreeItem<String> root) {
    if (root == null) return "";

    return getFilePath(root.getParent()) + "/" + root.getValue();
  }

  private File[] getChildren(File file) {
    File[] children = file.listFiles();
    return children == null ? new File[0] : children;
  }
}
