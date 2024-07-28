package grupo3.proyectoeddg3;

import grupo3.proyectoeddg3.list.DoubleCircularLL;
import grupo3.proyectoeddg3.modelo.Feedback;
import grupo3.proyectoeddg3.PrimaryController;
import grupo3.proyectoeddg3.modelo.Juego;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.PriorityQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Grupo3
 */

public class JuegoController {
    
    @FXML
    private Button btnComprar;

    @FXML
    private Button btnValoracionAbajo;

    @FXML
    private Button btnValoracionArriba;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> cmbOrdenFeedback;

    @FXML
    private ImageView imvJuego;

    @FXML
    private ImageView imvJuego1;

    @FXML
    private ImageView imvJuego2;
    
    @FXML
    private ImageView imvLogoTienda;

    @FXML
    private Label lblDesarrolladores;

    @FXML
    private Label lblDescripcionJuego;

    @FXML
    private Label lblFechaLanz;

    @FXML
    private Label lblGeneroJuego;

    @FXML
    private Label lblPrecioJuego;

    @FXML
    private Label lblTexto1;

    @FXML
    private Label lblTexto2;

    @FXML
    private Label lblTexto3;

    @FXML
    private Label lblTituloJuego;

    @FXML
    private Label lblUsuario1;

    @FXML
    private Label lblUsuario2;

    @FXML
    private Label lblUsuario3;
    @FXML
    private Label lblValoracion1;
    @FXML
    private Label lblValoracion2;
    @FXML
    private Label lblValoracion3;
    
    @FXML
    private VBox vbFondoJuego;
    
    public static int feedbackMostrado = 0;
    
    public static DoubleCircularLL<Feedback> lista;
    
    public static String textoCmbFiltro;
    
    @FXML
    private HBox hboxestrellas1;
    @FXML
    private HBox hboxestrellas2;
    @FXML
    private HBox hboxestrellas3;
    
    @FXML
    private void switchToPrimary() throws IOException {
        feedbackMostrado = 0;
        if(PrimaryController.desdeBusqueda==false && PrimaryController.desdeMisJuegos ==false){
            App.setRoot("primary");
        }else if(PrimaryController.desdeMisJuegos){
            App.setRoot("misjuegos");
        }else{
            App.setRoot("search");
        }
        
    }
    
        
    
    public void initialize(){
        App.setImage("logoTienda","archivos/",imvLogoTienda, 300, 73,".jpg");
        vbFondoJuego.setId(PrimaryController.j.getTitulo());
        cmbOrdenFeedback.getItems().addAll("Positivas primero","Negativas primero");
        cmbOrdenFeedback.setValue(textoCmbFiltro);
        
        App.setImage(PrimaryController.j.getTitulo(),App.pathImagesJuegos,imvJuego,200,280,".jpg");
        lblTituloJuego.setText(PrimaryController.j.getTitulo());
        lblDesarrolladores.setText("Desarrollado por: " + PrimaryController.j.getDesarrolladores());
        lblFechaLanz.setText("Año: "+PrimaryController.j.getFechaLanzamiento());
        lblGeneroJuego.setText("Género: "+PrimaryController.j.getGenero());
        lblDescripcionJuego.setText(PrimaryController.j.getDescripcion());
        
        for(Juego jc:PrimaryController.misJuegos){
            if(jc.getTitulo().equals(PrimaryController.j.getTitulo())){
                btnComprar.setText("Juego comprado");
                btnComprar.setDisable(true);
            }
        }
        
        for(Juego jc:PrimaryController.carrito){
            if(jc.getTitulo().equals(PrimaryController.j.getTitulo())){
                btnComprar.setText("Juego en carrito");
                btnComprar.setDisable(true);
            }
        }
        
        if(PrimaryController.j.getPrecio() >0){
            lblPrecioJuego.setText('$'+String.valueOf(PrimaryController.j.getPrecio()));
        }else{
            lblPrecioJuego.setText("Gratis");
        }
      
        llenarFeedback(lista,feedbackMostrado);
        
        App.setImage("1",App.pathCapturasJuegos+PrimaryController.j.getTitulo()+"/",imvJuego1,320,180,".jpeg");
        App.setImage("2",App.pathCapturasJuegos+PrimaryController.j.getTitulo()+"/",imvJuego2,320,180,".jpeg");

    }    
    
    @FXML
    void filtrarFeedback(ActionEvent event) {
        feedbackOrdenado();
        feedbackMostrado = 0;
        llenarFeedback(lista,feedbackMostrado);
        System.out.println(lista.getIndex(0));

        try {
            App.setRoot("juego");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    void feedbackOrdenado(){
        DoubleCircularLL<Feedback> feedbackOrden = new DoubleCircularLL<>();
        PriorityQueue<Feedback> feedbackPQ;
        
        if(cmbOrdenFeedback.getValue().equals("Positivas primero")){
            textoCmbFiltro = "Positivas primero";
            feedbackPQ = new PriorityQueue<>((Feedback f1, Feedback f2)->{
                return f2.getValoracion() - f1.getValoracion();
            });
        }else{
            textoCmbFiltro = "Negativas primero";
            feedbackPQ = new PriorityQueue<>((Feedback f1, Feedback f2)->{
                return f1.getValoracion() - f2.getValoracion();
            });
        }
        
        for(Feedback l: lista){
            feedbackPQ.offer(l);
        }
        
        while(!feedbackPQ.isEmpty()){
            feedbackOrden.addLast(feedbackPQ.poll());
        }
        
        lista = feedbackOrden;
    }

    @FXML
    private void comprar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar añadir al carrito");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro que deseas añadir este juego al carrito?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            PrimaryController.carrito.addLast(PrimaryController.j);
            try ( BufferedWriter writer = new BufferedWriter(new FileWriter("archivos/carrito.txt",true))) {
                writer.write(PrimaryController.j.toString());
                writer.newLine();
                writer.close();
                System.out.println("GUARDADO EXITOSO");
            } catch (Exception e) {
                System.out.println("no se pudo agregar");
                e.printStackTrace();
            }
            try {
            App.setRoot("juego");
            } catch (IOException ex) {
            ex.printStackTrace();
            }
            
        }
    }
    
    @FXML
    void fbAbajo(ActionEvent event) {
        llenarFeedback(lista, feedbackMostrado);
        feedbackMostrado+=3;
        try {
            App.setRoot("juego");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void fbArriba(ActionEvent event) {
        llenarFeedback(lista,feedbackMostrado);
        feedbackMostrado-=3;
        try {
            App.setRoot("juego");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void llenarFeedback(DoubleCircularLL<Feedback> listaTrabajar, int indice){
        DoubleCircularLL<Feedback> feedbackMostrados = feedbackCargado(listaTrabajar, indice);
        
        lblUsuario1.setText(feedbackMostrados.getIndex(0).getNombreUsuario());
        lblValoracion1.setText(setEstrellasTexto(feedbackMostrados.getIndex(0)));
        lblTexto1.setText(feedbackMostrados.getIndex(0).getDescripcion());
        //setEstrellas(feedbackMostrados.getIndex(0),hboxestrellas1);
        
        lblUsuario2.setText(feedbackMostrados.getIndex(1).getNombreUsuario());
        lblValoracion2.setText(setEstrellasTexto(feedbackMostrados.getIndex(1)));
        lblTexto2.setText(feedbackMostrados.getIndex(1).getDescripcion());
        //setEstrellas(feedbackMostrados.getIndex(1),hboxestrellas2);
        
        lblUsuario3.setText(feedbackMostrados.getIndex(2).getNombreUsuario());
        lblValoracion3.setText(setEstrellasTexto(feedbackMostrados.getIndex(2)));
        lblTexto3.setText(feedbackMostrados.getIndex(2).getDescripcion());
        //setEstrellas(feedbackMostrados.getIndex(2),hboxestrellas3);
    }
    //Devuelve la lista de reseñas de ese juego específico.
    public static DoubleCircularLL<Feedback> feedbackJuego(){
        DoubleCircularLL<Feedback> feedbackJuegoEscogido = new DoubleCircularLL<>();
        for(Feedback fb: PrimaryController.listaFeedback){
            if(fb.getTituloJuego().equals(PrimaryController.j.getTitulo())){
                feedbackJuegoEscogido.addLast(fb);
            }
        }
        
        return feedbackJuegoEscogido;
    }
    
    public static DoubleCircularLL<Feedback> feedbackCargado(DoubleCircularLL<Feedback> listaTrabajar,int indice){
        DoubleCircularLL<Feedback> listaCargada = new DoubleCircularLL<>();
        int tope = indice+2;
        for(int i = indice;i<=tope;i++){
            Feedback fb = listaTrabajar.getIndex(i);
            listaCargada.addLast(fb);
        }
        return listaCargada;
    }
    
    void setEstrellas(Feedback fb, HBox hbox){
        
        for(int a=0; a<fb.getValoracion();a++){
            /*ImageView iv=new ImageView();
            App.setImage("estrella","archivos/",iv,30,30,".png");
            hbox.getChildren().add(iv);
*/
        }
        
    }
    
    String setEstrellasTexto(Feedback fb){
        String estrellas="";
        for(int a=0; a<fb.getValoracion();a++){
            estrellas+="♥ ";
        }
        return estrellas;
    }
    
}