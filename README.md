# TreeView

A general TreeView implementation for android base on RecyclerView.

# Demo

## FileTree

<video width="400" controls>
  <source src="./assets/demo.mp4" type="video/mp4">
</video>

## TaskTree

<img src="./assets/task_tree.png" height="400">

# Usage

Create a custom adapter by extends `TreeView.Adapter`.

```java
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
```

in your `item_tasktree.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="center_vertical"
    android:orientation="horizontal">

    <TextView
        android:id="@+id/name"
        android:layout_marginLeft="4dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>
```

in `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <io.github.ikws4.treeview.TreeView
        android:id="@+id/tree_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

Bind TreeView with your custom adapter by calling `treeView.setAdapter()`

```java
TreeView treeView = findViewById(R.id.tree_view);
TaskTreeAdapter adapter = new TaskTreeAdapter();
treeView.setAdapter(adapter);

TreeItem<String> root = new TreeItem<>("Tasks");
TreeItem<String> daily = new TreeItem<>("Daily", true);
TreeItem<String> weekly = new TreeItem<>("Weekly", true);

daily.getChildren().add(new TreeItem<>("Wake up at 6:00"));
daily.getChildren().add(new TreeItem<>("Drink at least 4 cups of water"));

weekly.getChildren().add(new TreeItem<>("Week review"));
weekly.getChildren().add(new TreeItem<>("Reward your self"));

root.getChildren().add(daily);
root.getChildren().add(weekly);

adapter.setRoot(root);
```
