package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.*;
import java.util.List;

public class Main extends Application {


    double[] arr, arr1;
    int d;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Random random = new Random();
        Scene scene = new Scene(root, 1200, 600, Color.BISQUE);
        stage.setTitle("YOLO");
        Text text = new Text();
        text.setText("Sort Visualizer");
        text.setX(450);
        text.setY(50);
        text.setFont(Font.font("sample/Fuggles.ttf", 40));
        Line xaxis = new Line();
        xaxis.setStartY(550);
        xaxis.setEndY(550);
        xaxis.setStartX(0);
        xaxis.setEndX(1200);
        Scanner scanner= new Scanner(System.in);
        System.out.println("Enter the size or bar graph");
        int size = scanner.nextInt();
        System.out.println("Enter the delay between each animation in milliseconds");
        d=scanner.nextInt();
        arr = new double[size];
        arr1 = new double[size];
        double x = 50;
        double y = 500;
        double width = (1100 - size - 1) / (double) size;
        double height = 50;
        Rectangle[] rectangle = new Rectangle[size];
        Button button = new Button("Randomize");
        Button button1 = new Button("Bubble\nSort");
        Button button2 = new Button("Quick\nSort");
        button1.setLayoutX(15);
        button1.setLayoutY(10);
        button.setLayoutX(80);
        button.setLayoutY(10);
        button2.setLayoutX(160);
        button2.setLayoutY(10);
        for (int i = 0; i < size; i++) {
            rectangle[i] = new Rectangle();
            rectangle[i].setX(x);
            rectangle[i].setY(y);
            rectangle[i].setHeight(height);
            rectangle[i].setWidth(width);
            x = x + width + 1;

            height = height + (425 / (double) size);
            y = y - (425 / (double) size);
            root.getChildren().add(rectangle[i]);

        }
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                for (int i = 0; i < size; i++) {
                    int rand = random.nextInt(size);
                    swap(i, rand, rectangle);

                }
                for (int i = 0; i < size; i++) {
                    arr[i] = rectangle[i].getX();
                    arr1[i] = rectangle[i].getHeight();
                }
            }
        });
        for (int i = 0; i < size; i++) {
            arr[i] = rectangle[i].getX();
            arr1[i] = rectangle[i].getHeight();
        }
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                    for (int i = 0; i < size - 1; i++) {
                            for (int j = 0; j < size - i - 1; j++) {
                                if (arr1[j] > arr1[j + 1]) {
                                  swap(j, j + 1, rectangle);
                                    try {
                                        Thread.currentThread().sleep(d);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    swap(arr, j, j + 1);
                                    swap(arr1, j, j + 1);
                                }
                            }
                        }

                    }
                }).start();


            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                int l=0,h=size-1;
                int[] stack = new int[h - l + 1];
                int top = -1;
                stack[++top] = l;
                stack[++top] = h;
                while (top >= 0) {
                    h = stack[top--];
                    l = stack[top--];
                    int low=l,high=h;
                    double pivot = rectangle[high].getHeight();
                    int i = (low - 1);
                    for (int j = low; j <= high - 1; j++) {
                        if (rectangle[j].getHeight() <= pivot) {
                            i++;
                            try {
                                Thread.currentThread().sleep(d);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            swap(i,j,rectangle);
                        }
                    }
                    try {
                        Thread.currentThread().sleep(d);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    swap(i+1,high,rectangle);
                    int p=i+1;
                    if (p - 1 > l) {
                        stack[++top] = l;
                        stack[++top] = p - 1;
                    }
                    if (p + 1 < h) {
                        stack[++top] = p + 1;
                        stack[++top] = h;
                    }
                }
                    }
                }).start();
            }
        });

        root.getChildren().add(button2);
        root.getChildren().add(button1);
        root.getChildren().add(button);
        root.getChildren().add(xaxis);
        root.getChildren().add(text);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    void swap(int i, int j, Rectangle[] rectangle) {
        double y1 = rectangle[i].getY();
        double y2 = rectangle[j].getY();
        rectangle[i].setY(y2);
        rectangle[j].setY(y1);
        double h1 = rectangle[i].getHeight();
        double h2 = rectangle[j].getHeight();
        rectangle[i].setHeight(h2);
        rectangle[j].setHeight(h1);
    }

    void swap(double[] ar, int i, int j) {
        double temp = ar[i];
        ar[i] = ar[j];
        ar[j] = temp;
    }




}
