package io.github.ikws4.treeview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TreeView extends RecyclerView {

  public TreeView(@NonNull Context context) {
    super(context);
    init();
  }

  public TreeView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public TreeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setLayoutManager(new LinearLayoutManager(getContext()));
  }

  public <VH extends TreeViewAdapter.ViewHolder, T> void setAdapter(@Nullable TreeViewAdapter<VH, T> adapter) {
    super.setAdapter(adapter);

    if (adapter != null) {
      adapter.setIdentationWidth(getResources().getDimensionPixelSize(R.dimen.treeview_default_indentation_width));
    }
  }
}
