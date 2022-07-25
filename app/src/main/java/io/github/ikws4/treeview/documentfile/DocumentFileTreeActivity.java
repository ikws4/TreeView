package io.github.ikws4.treeview.documentfile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import io.github.ikws4.treeview.R;
import io.github.ikws4.treeview.TreeView;

public class DocumentFileTreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        TreeView treeView = findViewById(R.id.tree_view);

        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Uri uri = result.getData().getData();
                        DocumentFile documentFile = DocumentFile.fromTreeUri(DocumentFileTreeActivity.this, uri);
                        treeView.setAdapter(new DocumentFileTreeAdapter(documentFile));
                    }
                }
            }
        }).launch(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE));
    }
}
