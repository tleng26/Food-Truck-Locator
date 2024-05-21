package edu.illinois.cs.cs124.ay2022.mp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import edu.illinois.cs.cs124.ay2022.mp.R;
import edu.illinois.cs.cs124.ay2022.mp.application.FavoritePlacesApplication;
import edu.illinois.cs.cs124.ay2022.mp.models.Place;
import edu.illinois.cs.cs124.ay2022.mp.models.ResultMightThrow;
import edu.illinois.cs.cs124.ay2022.mp.network.Client;

public class AddPlaceActivity extends AppCompatActivity {
  @SuppressWarnings("checkstyle:Indentation")
  @SuppressLint("ResourceType")
  @Override
  protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_addplace);

    Intent returnToMain = new Intent(this, MainActivity.class);
    returnToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

    // Save button
    Button saveButton = findViewById(R.id.save_button);
    saveButton.setOnClickListener(v -> {
      // Get description
      EditText y = findViewById(R.id.description);
      String description = y.getText().toString();
      // Create place object that intakes information passed to this activity
      Map<String, double[]> defaultSchedule = new HashMap<>();
      defaultSchedule.put("08:00", new double[]{40.110, -88.230});
      defaultSchedule.put("08:10", new double[]{40.115, -88.235});
      defaultSchedule.put("08:20", new double[]{40.120, -88.240});
      defaultSchedule.put("08:30", new double[]{40.125, -88.245});

      Place x = new Place(FavoritePlacesApplication.CLIENT_ID,
          "my place",
          Double.parseDouble(getIntent().getStringExtra("latitude")),
          Double.parseDouble(getIntent().getStringExtra("longitude")),
          description,
          defaultSchedule);
      Consumer<ResultMightThrow<Boolean>> callback = booleanResultMightThrow -> {};
      // Add the place to list of places and post to the map
      Client.start().postFavoritePlace(x, callback);
      // Return back to the main activity
      startActivity(returnToMain);
    });

    // Cancel button
    Button cancelButton = findViewById(R.id.cancel_button);
    cancelButton.setOnClickListener(v -> startActivity(returnToMain));

    //Favorite Button
    Button favorite = findViewById(R.id.favorite_button);
    favorite.setOnClickListener(v -> returnToMain.putExtra("favorite", "Favorited!"));
  }
}
