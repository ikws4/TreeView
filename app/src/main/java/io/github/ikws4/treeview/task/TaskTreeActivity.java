package io.github.ikws4.treeview.task;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.github.ikws4.treeview.R;
import io.github.ikws4.treeview.TreeItem;
import io.github.ikws4.treeview.TreeView;

public class TaskTreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        TreeView treeView = findViewById(R.id.tree_view);
        TaskTreeAdapter adapter = new TaskTreeAdapter();
        treeView.setAdapter(adapter);

        TreeItem<String> root = new TreeItem<>("Tasks", true);
        TreeItem<String> daily = new TreeItem<>("Daily", true);
        TreeItem<String> weekly = new TreeItem<>("Weekly", true);

        daily.getChildren().add(new TreeItem<>("Wake up at 6:00"));
        daily.getChildren().add(new TreeItem<>("Drink at least 4 cups of water"));

        weekly.getChildren().add(new TreeItem<>("Week review"));
        weekly.getChildren().add(new TreeItem<>("Reward your self"));

        root.setExpanded(true);
        root.getChildren().add(daily);
        root.getChildren().add(weekly);

        adapter.setRoot(root, true);
    }
}
