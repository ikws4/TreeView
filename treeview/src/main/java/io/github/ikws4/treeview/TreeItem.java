package io.github.ikws4.treeview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TreeItem<T> {
  private final T value;
  private final List<TreeItem<T>> children;
  private boolean expanded;
  private final boolean expandable;
  TreeItem<T> parent;

  public TreeItem(@NonNull T value) {
    this(value, false);
  }

  public TreeItem(@NonNull T value, boolean expandable) {
    this.value = value;
    this.expandable = expandable;
    this.children = new TreeItemChildrenList<T>(this);
  }

  public boolean isExpanded() {
    return expanded;
  }

  /**
   * @throws IllegalStateException if item is not expandable
   */
  public void setExpanded(boolean expanded) {
    ensureSelfIsExpandable();
    this.expanded = expanded;
  }

  public boolean isExpandable() {
    return expandable;
  }

  public T getValue() {
    return value;
  }

  @Nullable
  public TreeItem<T> getParent() {
    return parent;
  }

  @NonNull
  public List<TreeItem<T>> getChildren() {
    return children;
  }

  int getDepth() {
    if (parent == null) return 0;
    return parent.getDepth() + 1;
  }

  @Override
  public String toString() {
    return "TreeItem(" +
        "value=" + value +
        ", expanded=" + expanded +
        ')';
  }

  private void ensureSelfIsExpandable() {
    if (!expandable) {
      throw new IllegalStateException(this.toString() + " is not expandable.");
    }
  }
}
