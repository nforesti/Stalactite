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

  @Override public void start(Stage stage) {
    stage.setTitle("Scatter Chart Sample");
    final NumberAxis xAxis = new NumberAxis(1975, 2020, 45);
    final NumberAxis yAxis = new NumberAxis(0, 12000, 12000);
    final ScatterChart<Number,Number> sc = new
    ScatterChart<Number,Number>(xAxis,yAxis);

    XYChart.Series series1 = new XYChart.Series();




    BufferedReader reader;
    int[][] data = new int[430][2];
    try {
      reader = new BufferedReader(new FileReader(
      "C:\\Users\\nevef\\Documents\\Portfolio\\newdataplot.csv"));

      String line = reader.readLine();
      int year;
      int age;
      int counter = 0;
      int suicides;
      int population;

      String rest = "";
      while ((line = reader.readLine())!=null) {
        String splitted[] =line.split(",",2);
        year = Integer.parseInt(splitted[0]);
        rest = splitted[1];

        splitted=rest.split(",",2);
        age = Integer.parseInt(splitted[0]);
        rest = splitted[1];

        splitted =rest.split(",",2);
        suicides = Integer.parseInt(splitted[0]);
        rest = splitted[1];
        population = Integer.parseInt(rest.substring(0));
        population = population / 100000;
        data[counter][0] = population;
        data[counter][1] = age;

        series1.getData().add(new XYChart.Data(year, suicides));
        counter++;
      }
      System.out.println("out");
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }



    sc.getData().addAll(series1);
    Scene scene  = new Scene(sc, 800, 600, Color.BLACK);

    stage.setScene(scene);
    stage.show();

    Set<Node> nodes = sc.lookupAll(".series" + 0);
    int counter = 0;
    for (Node n : nodes) {
      if (counter < 430){
        System.out.println(counter);
        int radius = data[counter][0];
        String color = "";
        if (counter % 2 == 0){
          color = "white";
        }
        else{
          color = "black";
        }
        System.out.println("radius " + radius);
        n.setStyle("-fx-background-color: #860061, "+color+";\n"
        + "    -fx-background-insets: 0, 2;\n"
        + "    -fx-background-radius: " + radius + "px;\n"
        + "    -fx-padding: " + radius * .6+ "px;");
      }
      counter++;
    }
  }

  public static void main(String[] args) {
    launch(args);

  }
}
