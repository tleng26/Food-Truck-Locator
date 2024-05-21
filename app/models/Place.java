package edu.illinois.cs.cs124.ay2022.mp.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/*
 * Model storing information about a place retrieved from the backend server.
 *
 * You will need to understand some of the code in this file and make changes starting with MP1.
 */
@SuppressWarnings("unused")
public final class Place {
  /*
   * The Jackson JSON serialization library that we are using requires an empty constructor.
   * So don't remove this!
   */
  public Place() {}

  public Place(
      final String setId,
      final String setName,
      final double setLatitude,
      final double setLongitude,
      final String setDescription,
      final Map<String, double[]> setSchedule) {
    id = setId;
    name = setName;
    latitude = setLatitude;
    longitude = setLongitude;
    description = setDescription;
    schedule = setSchedule;
    visible = false;
  }

  // ID of the place
  private String id;

  public String getId() {
    return id;
  }

  // Name of the person who submitted this favorite place
  private String name;

  public String getName() {
    return name;
  }

  // Latitude and longitude of the place
  private double latitude = 1000;

  public double getLatitude() {
    return latitude;
  }

  private double longitude = 1000;

  public double getLongitude() {
    return longitude;
  }

  // Description of the place
  private String description;

  public String getDescription() {
    return description;
  }

  private Map<String, double[]> schedule;
  public Map<String, double[]> getSchedule() {
    return schedule;
  }

  public void setLat(final double a) {
    this.latitude = a;
  }
  public void setLon(final double a) {
    this.longitude = a;
  }

  private boolean visible;
  public boolean isVisible() {
    return visible;
  }

  public void updateLocationBasedOnSchedule(final String currentTime) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
      Date current = sdf.parse(currentTime);

      for (Map.Entry<String, double[]> entry : schedule.entrySet()) {
        String[] times = entry.getKey().split("-");
        Date startTime = sdf.parse(times[0]);
        Date endTime = sdf.parse(times[1]);

        assert current != null;
        if (current.after(startTime) && current.before(endTime)) {
          double[] coordinates = entry.getValue();
          this.latitude = coordinates[0];
          this.longitude = coordinates[1];
          this.visible = true;
          System.out.println("Place " + name + " is visible at " + currentTime);
          return;
        }
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }

    this.visible = false;

  }

  public static Map<String, double[]> parseSchedule(final String scheduleString) {
    Map<String, double[]> schedule = new HashMap<>();
    String[] entries = scheduleString.split(";");
    for (String entry : entries) {
      String[] parts = entry.split("=");
      String time = parts[0];
      String[] coordinates = parts[1].split(",");
      double lat = Double.parseDouble(coordinates[0].trim());
      double lon = Double.parseDouble(coordinates[1].trim());
      schedule.put(time, new double[]{lat, lon});
    }
    return schedule;
  }


  public static List<Place> search(final List<Place> places, final String search) {
    // Reject null and empty values for both parameters
    if (places == null || search == null) {
      throw new IllegalArgumentException();
    } else if (search.trim().length() == 0 || places.size() == 0) {
      return places;
    }
    // Create a new list that will eventually contain the specified information
    List<Place> newPlaces = new ArrayList<>();
    // Trim the string parameter
    String trimSearch = search.trim().toLowerCase();
    // Go through each place in the list
    for (int i = 0; i < places.size(); i++) {
      // Get the description of each place
      String des = places.get(i).getDescription();
      // Create a new description with the correct modifications
      StringBuilder newDes = new StringBuilder();
      // Go through each character in the description
      for (int j = 0; j < des.length(); j++) {
        char x = des.charAt(j);
        // Change the characters to appropriate values
        if (x == '.' || x == '!' || x == '?' || x == ',' || x == ':' || x == ';' || x == '/') {
          newDes.append(' ');
        } else if (Character.isAlphabetic(x) || x == ' ' || Character.isDigit(x)) {
          newDes.append(x);
        }
      }
      // Change the new description to all lowercase
      String y = newDes.toString().toLowerCase();
      // Split the lines of the description into individual strings
      String[] z = y.split("\n");
      for (String j : z) {
        // Split the individual strings into words
        String[] a = j.split(" ");
        for (String k : a) {
          // Add the place into the list if the word and string parameter are equal
          if (k.equals(trimSearch)) {
            newPlaces.add(places.get(i));
            // Immediately exit the loop once one instance of the word is found
            break;
          }
        }
      }
    }
    return newPlaces;
  }
}

