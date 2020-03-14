package com.jesus.hintnotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jesus.hintnotes.dao.hintDao;
import com.jesus.hintnotes.models.Hint;

public class SettingsActivity extends AppCompatActivity {

    public hintDao hintsDao = com.jesus.hintnotes.dao.hintDao.getInstance();
    public Hint hint;
    public EditText editTitle;
    public EditText editTime;
    public EditText editHintText;
    public Button addButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);


        editTitle = findViewById(R.id.editTitle);
        editTime = findViewById(R.id.editTime);
        editHintText = findViewById(R.id.editHintText);

        Intent intent = this.getIntent();
        hint = hintsDao.getHint(intent.getIntExtra("hint", Integer.MAX_VALUE));
        if (hint != null) {
            editTitle.setText(hint.getTitle());
            editTime.setText(String.valueOf(hint.getMillisInFuture()));
            editHintText.setText(hint.getText());

        } else {
            hint = new Hint();
            hint.setPosition(hintsDao.getHints().size() + 1);
        }
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (!TextUtils.isEmpty(editTitle.getText().toString()) && !TextUtils.isEmpty(editTime.getText().toString()) && !TextUtils.isEmpty(editHintText.getText().toString())) {
                    Intent intent1 = new Intent(getApplicationContext(), HintListActivity.class);
                    startActivity(intent1);
                    hint.setText(editHintText.getText().toString());
                    hint.setTitle(editTitle.getText().toString());
                    hint.setMillisInFuture(Long.valueOf(editTime.getText().toString()));
                    hintsDao.updateHint(hint);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введите данные во все поля!",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }

            }
        });
    }
}