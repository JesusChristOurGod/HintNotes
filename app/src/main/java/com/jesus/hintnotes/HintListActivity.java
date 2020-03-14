package com.jesus.hintnotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.jesus.hintnotes.dao.hintDao;
import com.jesus.hintnotes.models.Hint;

public class HintListActivity extends AppCompatActivity {

    private ListView hintList;
    public hintDao hintsDao = hintDao.getInstance();
    public HintAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hint_list);

        hintList=findViewById(R.id.hintList);
         adapter = new HintAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<Hint>(hintsDao.getHints().values()));
        hintList.setAdapter(adapter);

        ImageButton addHint= findViewById(R.id.addHint);
        addHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.putExtra("hint",0);
                startActivity(intent);
            }
        });

        ImageButton play= findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private class HintAdapter extends ArrayAdapter<Hint>{


        public HintAdapter(@NonNull Context context, int resource, @NonNull List<Hint> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent){
            final Hint hint = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hint, null);

            HintHolder holder = new HintHolder();
            holder.title = convertView.findViewById(R.id.title);
            holder.settings = convertView.findViewById(R.id.settings);
            holder.delete=convertView.findViewById(R.id.delete);



            holder.title.setText(hint.getTitle());
            convertView.setTag(holder);

            holder.settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), SettingsActivity.class);
                    intent.putExtra("hint",hint.getPosition());
                    startActivity(intent);
                }
            });


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.remove(hint);
                    hintsDao.deleteHint(hint);

                }
            });



            return convertView;
        }
    }

    private static  class HintHolder {
        public TextView title;
        public ImageButton settings;
        public ImageButton delete;

    }
}
