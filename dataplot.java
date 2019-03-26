/**
 * Filename: dataplot.java
 * Author: Neve Foresti
 * Email: neve.foresti@gmail.com
 * Description: Contains dataplot class, which using JavaFX and css, plots values in CSV file
 * and creates cool 3-D image
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.Set;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class dataplot extends Application {

  @Override
  public void start(Stage stage) {

    stage.setTitle("Stalactite");
    stage.setWidth(1500);
    stage.setHeight(1000);

    final NumberAxis xAxis = new NumberAxis(1990, 2020, 45);
    final NumberAxis yAxis = new NumberAxis(0, 12000, 12000);
    xAxis.setTickLabelsVisible(false);
    yAxis.setTickLabelsVisible(false);
    final ScatterChart<Number, Number> sc = new ScatterChart<Number, Number>(xAxis, yAxis);

    XYChart.Series series1 = new XYChart.Series();

    BufferedReader reader;
    int[][] data = new int[300 /*input any value, as long as greater than num of csv rows*/][2];
    try {
      reader = new BufferedReader(new FileReader("newdataplot.csv")); /*data file*/

      String line = reader.readLine(); // first line of data. will not plot this

      // variables associated with columns in data
      int year;
      int age;
      int suicides;
      int population;

      String rest = ""; //used for substring later in function
      int counter = 0;

      while ((line = reader.readLine()) != null) {
        //get year
        String splitted[] = line.split(",", 2); 
        year = Integer.parseInt(splitted[0]);
        rest = splitted[1];
        //get age
        splitted = rest.split(",", 2);
        age = Integer.parseInt(splitted[0]);
        rest = splitted[1];
        //get suicides and population
        splitted = rest.split(",", 2);
        suicides = Integer.parseInt(splitted[0]);
        rest = splitted[1];
        population = Integer.parseInt(rest.substring(0));
        population = population / 100000;
        data[counter][0] = population;
        data[counter][1] = age;

        //add data to series
        series1.getData().add(new XYChart.Data(year, suicides));
        counter++;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    sc.getData().addAll(series1);
    
    Scene scene = new Scene(sc, 800, 600, Color.BLACK);
    scene.getStylesheets().add("style.css"); //hide axis information

    stage.setScene(scene);
    stage.show();

    //go through each datapoint, change color and radius
    Set<Node> nodes = sc.lookupAll(".series" + 0);
    int counter = 0;
    for (Node n : nodes) {
      int radius = data[counter][0]; //radius coiincides with population size

      /*rotate b/w black and white fill*/
      String color = "";
      if (counter % 2 == 0) {
        color = "white";
      } else {
        color = "black";
      }

      //change fill color and radius
      n.setStyle("-fx-background-color: #860061, " + color + ";\n" + "    -fx-background-insets: 0, 2;\n"
          + "    -fx-background-radius: " + radius + "px;\n" + "    -fx-padding: " + (radius + 15) + "px;");
      counter++;
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
