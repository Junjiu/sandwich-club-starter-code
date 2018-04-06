package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView originTv;
    TextView descriptionTv;
    TextView ingredientsTv;
    TextView also_knownTv;
    TextView mainName;
    ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        also_knownTv = findViewById(R.id.also_known_tv);
        mainName = findViewById(R.id.main_name);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            Log.e("sandwich","EXTRA_POSITION not found in intent");
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            Log.e("sandwich","sandwich is null");
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Log.v("image",sandwich.getImage());
        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mainName.setText(sandwich.getMainName());
        originTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());
        ingredientsTv.setText(sandwich.getIngredients().toString());
        also_knownTv.setText(sandwich.getAlsoKnownAs().toString());
    }
}
