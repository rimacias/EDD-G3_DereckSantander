/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package grupo3.proyectoeddg3;

import static grupo3.proyectoeddg3.PrimaryController.juegoLabel;
import grupo3.proyectoeddg3.list.DoubleCircularLL;
import grupo3.proyectoeddg3.modelo.Juego;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Grupo3
 */

public class SearchController{

    @FXML
    private Button btnBusqDer;

    @FXML
    private Button btnBusqIzq;
    
    @FXML private Button btn_buscar;
    @FXML private Button btnVolver;
    @FXML private Label lblResultado;
    @FXML private FlowPane resultadosPane;
    @FXML private ImageView imvLogoTienda;
    
    public static int busquedaMostrada= 0;
    public void initialize() {
        App.setImage("logoTienda","archivos/",imvLogoTienda, 300, 73,".jpg");
        PrimaryController.desdeBusqueda = true;
        
        if(PrimaryController.busquedaC){
            lblResultado.setText(PrimaryController.categoríaSeleccionada);
        }else{
            lblResultado.setText(PrimaryController.busqueda);
            }
        
        if(PrimaryController.juegosEncontrados.largo()<=14){
            btnBusqDer.setDisable(true);
            btnBusqIzq.setDisable(true);
        }
        
        llenarResultados();
    }    

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    void busqDer(ActionEvent event) {
        busquedaMostrada+=14;
        try {
            App.setRoot("search");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void busqIzq(ActionEvent event) {
        busquedaMostrada-=14;
        try {
            App.setRoot("search");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    void llenarResultados(){
        
        for(Juego j:Juego.busquedaCargada(busquedaMostrada)){
               VBox vbox=new VBox();
               vbox.setAlignment(Pos.CENTER);
               ImageView ivJuego=new ImageView();
               App.setImage(j.getTitulo(),App.pathImagesJuegos,ivJuego,137,192,".jpg");
               Label lbl=new Label(j.getTitulo());
               lbl.setMaxWidth(137);
               lbl.setAlignment(Pos.CENTER);
               
               
               vbox.setMargin(ivJuego,new Insets(0,10,0,10));
               vbox.setMargin(lbl,new Insets(5,10,0,10));
               
               vbox.getChildren().addAll(ivJuego,lbl);
               resultadosPane.getChildren().add(vbox);
               resultadosPane.setMargin(vbox,new Insets(0,0,10,0));
               
               ivJuego.setOnMouseClicked(ev ->juegoLabel(lbl));
               lbl.setOnMouseClicked(ev ->juegoLabel(lbl));
           }
    }
    
}
