package io.github.ikws4.treeview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeViewAdapter<VH extends TreeViewAdapter.ViewHolder, T> extends RecyclerView.Adapter<VH> {
  private int indentationWidth;
  public OnTreeItemClickListener<T> listener;
  protected List<TreeItem<T>> items = new ArrayList<>();

  public abstract VH onCreateViewHolder(View view);

  public abstract int getLayoutRes();

  @NonNull
  @Override
  public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.item_treeview, parent, false);
    inflater.inflate(getLayoutRes(), view.findViewById(R.id.content), true);
    return onCreateViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull VH holder, int position) {
    TreeItem<T> item = items.get(position);
    View itemView = holder.itemView;
    ImageView leadingIcon = holder.leadingIcon;

    itemView.setPadding(indentationWidth * calculateDepth(item), 0, 0, 0);

    if (item.isExpandable()) {
      if (item.isExpanded()) {
        leadingIcon.setRotation(90);
      } else {
        leadingIcon.setRotation(0);
      }
      leadingIcon.setVisibility(View.VISIBLE);
    } else {
      leadingIcon.setVisibility(View.GONE);
    }

    itemView.setOnClickListener((v) -> {
      if (listener != null) {
        listener.onClick(item);
      }

      // click with debound
      long lastClickTime = (long) itemView.getTag();
      if (System.currentTimeMillis() - lastClickTime < 250) {
        return;
      }
      itemView.setTag(System.currentTimeMillis());

      if (item.isExpandable()) {
        if (item.isExpanded()) {
          int removedCount = removeAllExpandedItemRecursive(item, holder.getLayoutPosition() + 1);
          notifyItemRangeRemoved(holder.getLayoutPosition() + 1, removedCount);
        } else {
          item.setExpanded(true);
          int insertedCount = insertAllExpandedItemRecursive(item, holder.getLayoutPosition() + 1);
          notifyItemRangeInserted(holder.getLayoutPosition() + 1, insertedCount);
          item.setExpanded(false);
        }

        leadingIcon.animate()
            .setDuration(250)
            .rotationBy(item.isExpanded() ? -90 : 90)
            .start();

        item.setExpanded(!item.isExpanded());
      }
    });
  }

  private int calculateDepth(TreeItem<T> item) {
    int depth = item.getDepth();
    if (hasExpandableChild(item.parent) && item.isExpandable()) depth--;
    return depth;
  }


  private boolean hasExpandableChild(TreeItem<T> root) {
    for (TreeItem<T> child : root.getChildren()) {
      if (child.isExpandable()) return true;
    }
    return false;
  }

  private int removeAllExpandedItemRecursive(TreeItem<T> root, int index) {
    if (root == null || !root.isExpanded()) return 0;

    int cnt = 0;
    for (TreeItem<T> child : root.getChildren()) {
      items.remove(index);
      cnt += removeAllExpandedItemRecursive(child, index) + 1;
    }

    return cnt;
  }

  private int insertAllExpandedItemRecursive(TreeItem<T> root, int index) {
    if (root == null || !root.isExpanded()) return 0;

    int cnt = 0;
    for (TreeItem<T> child : root.getChildren()) {
      items.add(index + cnt, child);
      cnt += insertAllExpandedItemRecursive(child, index + cnt + 1) + 1;
    }

    return cnt;
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public void setRoot(TreeItem<T> root) {
    items.addAll(root.getChildren());
  }

  public void setTreeItemClickListener(OnTreeItemClickListener<T> listener) {
    this.listener = listener;
  }

  void setIdentationWidth(int width) {
    this.indentationWidth = width;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView leadingIcon;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      leadingIcon = itemView.findViewById(R.id.leading_icon);
      itemView.setTag(System.currentTimeMillis());
    }
  }

  public interface OnTreeItemClickListener<T> {
    void onClick(TreeItem<T> item);
  }
}
