package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsImageView;
    private TextView  ingredientsTextView;
    private TextView alsoKnownTextView;
    private TextView descriptionTextView;
    private TextView originTextView;
    private ListView sandwichListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        alsoKnownTextView = findViewById(R.id.also_known_tv);
        ingredientsImageView = findViewById(R.id.image_iv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);

        descriptionTextView = findViewById(R.id.description_tv);
        originTextView = findViewById(R.id.origin_tv);
        sandwichListView = findViewById(R.id.sandwiches_listview);




        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    private void populateUI(Sandwich withSandwich)
    {
        String noTextPlaceHolder = " -- -- ";

        //set Activity Bar Title
        setTitle(withSandwich.getMainName());

        //load image in ImageView
        Picasso.with(this)
                .load(withSandwich.getImage())
                .into(ingredientsImageView);

//                TODO -- append text to alsoKnownTextView
        appendListToTextView(withSandwich.getAlsoKnownAs(),alsoKnownTextView);

//                TODO -- append text to originTextView
        if(!withSandwich.getPlaceOfOrigin().equals(""))
            originTextView.append( withSandwich.getPlaceOfOrigin() + "\n");
        else
            originTextView.append(noTextPlaceHolder);




//                TODO -- append text to ingredients TextView
        appendListToTextView(withSandwich.getIngredients(), ingredientsTextView);

//                TODO -- append text to descriptionTextView
        if(!withSandwich.getDescription().equals(""))
            descriptionTextView.append(withSandwich.getDescription());
        else
            descriptionTextView.append(noTextPlaceHolder);



    }


    void appendListToTextView(List<String> list, TextView tv)
    {
        int length = list.size();
        int i = 0;

        //if item is in list, append & increment index
        //else add -- and return
        if(length > 0)
        {
            tv.append(list.get(i));
            i++;
        }else{
            tv.append("  --  -- ");
            return;
        }

        //if more, iterate, append & add comma
        for(;i<length;i++)
        {
            tv.append( ", " + list.get(i));
        }

        //add newline topic seperator
        tv.append("\n");


    }
}
