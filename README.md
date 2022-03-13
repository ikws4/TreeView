# TreeView

A general TreeView implementation for android base on RecyclerView.

# Setup

[![](https://jitpack.io/v/ikws4/TreeView.svg)](https://jitpack.io/#ikws4/TreeView)

1. Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```

2. Add the dependency

```gradle
dependencies {
  implementation "com.github.ikws4:TreeView:0.1"
}
```

# Examples

## FileTree

You can check out [FileTreeAdapter](https://github.com/ikws4/TreeView/blob/main/app/src/main/java/io/github/ikws4/treeview/FileTreeAdapter.java) if you want to learn more.

https://user-images.githubusercontent.com/47056144/158051176-6f45652c-5262-407a-a0ca-6c8c30c0f892.mp4

## TaskTree

<img src="./assets/task_tree.png" width="320">

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

`item_tasktree.xml`

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

`activity_main.xml`

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

# Thanks

This library was inspired by [RecyclerTreeView](https://github.com/TellH/RecyclerTreeView)

# License

```
MIT License

Copyright (c) 2022 Devin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
