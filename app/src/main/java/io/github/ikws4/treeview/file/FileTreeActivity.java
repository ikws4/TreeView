package io.github.ikws4.treeview.file;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import io.github.ikws4.treeview.R;
import io.github.ikws4.treeview.TreeView;
import io.github.ikws4.treeview.documentfile.DocumentFileTreeActivity;
import io.github.ikws4.treeview.task.TaskTreeActivity;

public class FileTreeActivity extends AppCompatActivity {
  private static final int EXTERNAL_STORAGE_REQUEST_CODE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tree);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      if (!Environment.isExternalStorageManager()) {
        startActivity(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));
      }
    }

    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQUEST_CODE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == EXTERNAL_STORAGE_REQUEST_CODE) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        TreeView treeView = findViewById(R.id.tree_view);
        treeView.setAdapter(new FileTreeAdapter());
      } else {
        Toast.makeText(this, "You should enable external storage premission to get this app work.", Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.home_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.task_tree) {
      startActivity(new Intent(this, TaskTreeActivity.class));
    } else if (id == R.id.document_file_tree) {
      startActivity(new Intent(this, DocumentFileTreeActivity.class));
    }
    return super.onOptionsItemSelected(item);
  }
}
