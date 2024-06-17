# Food Truck Locator
This project was created using a template from CS 124, a computer science course at UIUC.

### Changes
The location points of the food trucks on campus are displayed using basic longitude and latitude coordinates and the schedules of each food truck in a 24 hour-time format in places.csv. This csv file is then read in MainActivity.java by parsing through the ID of each food truck and retrieving each all elements, consisting of an online review/description, time schedule, and coordinates on the map. Then each element is serialized into JSON format to display on a text box when hovering over the marker. We used a map of the UIUC campus from tiles.cs124.org and markers from the CS 124 package file.
