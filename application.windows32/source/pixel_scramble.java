import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class pixel_scramble extends PApplet {

PImage input; // input image
int counter = 0;
String fileName; // image file name

public void setup() {
  
  frame.setResizable(true);
  selectInput("Select an image file (.png, .jpg, .gif) to process:", "selectFile");
}

public void draw() {
  background(255);
  if(input != null) {
    image(input, (width/2 - input.width/2), (height/2 - input.height/2));
  }
}

/*
 * Select an image.
 */

public void selectFile(File selection) {
  if (selection == null) {
    println("No image file has been selected.");
  } else {
    input = loadImage(selection.getAbsolutePath());
    println(selection.getAbsolutePath() + " has been selected.");
  }
}

/*
 * Generate a new image scrambling the pixels of the selected one.
 */

public void mouseClicked() {
  image(input, (displayWidth/2 - input.width/2), (displayHeight/2 - input.height/2));
  input.loadPixels();
  
  for (int area = input.height*input.width; area > 1; area--) {
    int random = PApplet.parseInt(random(area));
    int h = input.pixels[random];
    
    input.pixels[random] = input.pixels[area-1];
    input.pixels[area-1] = h;
  }
  
  input.updatePixels();
}

/*
 * Save the new image as a .png.
 */

public void keyPressed() {
  if(key == RETURN || key == ENTER) {
    fileName= "image_" + counter;
    input.save("outputs/" + fileName + ".png");
    println(fileName + " has been saved successfully.");
    counter++;
  }
}
  public void settings() {  size(400, 300); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pixel_scramble" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
