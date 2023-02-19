/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package notepadPkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author alifathy99
 */
public class notepad extends Application {
    MenuBar mb;  
    StackPane root;
    MenuItem NewItem,OpenItem,SaveItem,ExitItem,UndoItem,CutItem,CopyItem,PasteItem,DeleteItem,SelectAllItem,AboutItem;
    Menu FileMenu;
    Menu EditMenu;
    Menu HelpMenu;
    BorderPane borderP;
    TextArea area;
    FileChooser filechooser;
    Label fileStatus;
    
    @Override
    public void init() throws Exception {
        super.init();
        mb = new MenuBar();
        FileMenu= new Menu("File");
        EditMenu = new Menu("Edit");
        HelpMenu = new Menu("Help");
        borderP = new BorderPane();
        //dialog = new Dialog<String>();
        root = new StackPane();
        NewItem = new MenuItem("New");
        NewItem.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
        OpenItem = new MenuItem("Open");
        OpenItem.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        SaveItem = new MenuItem("Save");
        SaveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        ExitItem = new MenuItem("Exit");
        ExitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+e"));
        
        UndoItem = new MenuItem("Undo");
        UndoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+z"));
        CutItem = new MenuItem("Cut");
        CutItem.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));
        CopyItem = new MenuItem("Copy");
        CopyItem.setAccelerator(KeyCombination.keyCombination("Ctrl+c"));
        PasteItem = new MenuItem("Paste");
        PasteItem.setAccelerator(KeyCombination.keyCombination("Ctrl+v"));
        DeleteItem = new MenuItem("Delete");
        DeleteItem.setAccelerator(KeyCombination.keyCombination("Ctrl+d"));
        SelectAllItem = new MenuItem("Select All");
        SelectAllItem.setAccelerator(KeyCombination.keyCombination("Ctrl+a"));
        AboutItem = new MenuItem("About"); 
        AboutItem.setAccelerator(KeyCombination.keyCombination("Ctrl+h"));
        area = new TextArea();
        area.setPrefHeight(450);
        area.setPrefWidth(600);
        filechooser = new FileChooser();
        fileStatus = new Label("no files selected");
    }
    @Override
    public void start(Stage primaryStage) {
        MenuItem fileNewWindow = new MenuItem("new Window");
        fileNewWindow.setAccelerator(KeyCombination.keyCombination("Ctrl + Shift + n"));
        NewItem.setOnAction(Action -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            Optional<ButtonType> result = alert.showAndWait();
            alert.setTitle("Save file");
            String s ="Are you sure to clear data of this file without saving it ? if you want to save it press save button  ";
            alert.setHeaderText(s);
            if(result.get() == ButtonType.OK){
                FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Text files", "*.txt");
                File saveFile = filechooser.showSaveDialog(null);
                try {
                    FileWriter fileWrite = new FileWriter(saveFile);
                    fileWrite.write(area.getText());
                    fileWrite.close();
                    area.setText(" ");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }else if(result.get() == ButtonType.CANCEL){
                alert.close();
            }
            alert.show();
            
        });
        OpenItem.setOnAction(Action -> {
               
                File file = filechooser.showOpenDialog(primaryStage);
                FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Text files", "*.");
                try (BufferedReader reader = new BufferedReader(new FileReader(new File(file.getAbsolutePath())))){
                    if (file != null) {   
                        fileStatus.setText(file.getAbsolutePath()+ "  selected");
                        String line;
                        while ((line = reader.readLine()) != null)
                            area.setText(line);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

        });

        SaveItem.setOnAction(Action -> {
            FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Text files", "*.txt");
            File saveFile = filechooser.showSaveDialog(null);
            try {
                FileWriter fileWrite = new FileWriter(saveFile);
                fileWrite.write(area.getText());
                fileWrite.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        });
        ExitItem.setOnAction(Action -> {
            primaryStage.close();
        });
        //Edit Items
        UndoItem.setOnAction(Action -> {
            area.undo();
        });
        CutItem.setOnAction(Action -> {
            area.cut();
        });
        CopyItem.setOnAction(Action -> {
            area.copy();
        });
        PasteItem.setOnAction(Action -> {
            area.paste();
        });
        DeleteItem.setOnAction(Action -> {
            area.replaceSelection("");
        });
        SelectAllItem.setOnAction(Action -> {
            area.selectAll();
        });
        //Help Items
        AboutItem.setOnAction(Action -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("About Notepad");
            String s ="© Copyrights, this Nodepad made by Ali Ahmed Fathi  ";
            alert.setHeaderText(s);
            alert.show();
        });
        FileMenu.getItems().addAll(NewItem,OpenItem,SaveItem,ExitItem);
        EditMenu.getItems().addAll(UndoItem,CutItem,CopyItem,PasteItem,DeleteItem,SelectAllItem);
        HelpMenu.getItems().add(AboutItem);
        mb.getMenus().addAll(FileMenu,EditMenu,HelpMenu);
        borderP.setTop(mb);
        borderP.setCenter(area);
        root.getChildren().addAll(borderP);
       
        Scene scene = new Scene(root, 600, 450);
        primaryStage.setTitle("Ali Ahmed Notpad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
    
}
