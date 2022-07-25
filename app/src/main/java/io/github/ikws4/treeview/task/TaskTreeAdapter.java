package io.github.ikws4.treeview.task;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import io.github.ikws4.treeview.R;
import io.github.ikws4.treeview.TreeView;

class TaskTreeAdapter extends TreeView.Adapter<TaskTreeAdapter.ViewHolder, String> {

  @Override
  public ViewHolder onCreateViewHolder(View view) {
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.name.setText(items.get(position).getValue());
  }

  @Override
  public int getLayoutRes() {
    return R.layout.item_tasktree;
  }

  static class ViewHolder extends TreeView.ViewHolder {
    TextView name;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.name);
    }
  }
}
