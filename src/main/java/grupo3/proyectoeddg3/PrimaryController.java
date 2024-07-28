package grupo3.proyectoeddg3;


import java.io.IOException;
import javafx.event.ActionEvent;
import grupo3.proyectoeddg3.list.DoubleCircularLL;
import grupo3.proyectoeddg3.modelo.Feedback;
import grupo3.proyectoeddg3.modelo.Juego;
import java.io.FileInputStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Grupo3
 */

public class PrimaryController {
    
    @FXML private Button btnCarrDer;
    @FXML private Button btnCarrIzq;
    @FXML private Button btnCarrito;
    @FXML private Button btnGenDown;
    @FXML private Button btnGenUp;
    @FXML private Button btnTusJuegos;
    @FXML private HBox hbCarrusel;
    @FXML private HBox hbGeneros;
    @FXML private ImageView imv1;
    @FXML private ImageView imv2;
    @FXML private ImageView imv3;
    @FXML private ImageView imv4;
    @FXML private ImageView imvC1;
    @FXML private ImageView imvC2;
    @FXML private ImageView imvC3;
    @FXML private ImageView imvC4;
    @FXML private Label lbl1;
    @FXML private Label lbl2;
    @FXML private Label lbl3;
    @FXML private Label lbl4;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAño;
    @FXML private Label lblC1;
    @FXML private Label lblC2;
    @FXML private Label lblC3;
    @FXML private Label lblC4;
    @FXML private Button btnBuscar;
    @FXML private ImageView imvLogoTienda;
    
    public static Juego j;
    public static DoubleCircularLL<Juego> listaJuegos = Juego.cargarJuegos("archivos/juegos.txt");
    public static DoubleCircularLL<Juego> listaCategorias = Juego.cargarJuegos("archivos/categorias.txt");
    public static DoubleCircularLL<Juego> misJuegos = Juego.cargarJuegos("archivos/misjuegos.txt");
    public static DoubleCircularLL<Juego> carrito = Juego.cargarJuegos("archivos/carrito.txt");
    public static DoubleCircularLL<Juego> juegosEncontrados= new DoubleCircularLL();
    public static DoubleCircularLL<Feedback> listaFeedback = Feedback.cargarFeedback("archivos/FeedbackProyecto.txt");
    public static boolean busquedaC;
    public static String categoríaSeleccionada;
    public static String busqueda;
    public static int juegoMostrado = 0;
    public static int categoriaMostrada = 0;
    
    public static boolean desdeBusqueda = false;
    public static boolean desdeMisJuegos = false;
    
    public void initialize(){
        App.setImage("logoTienda","archivos/",imvLogoTienda, 250, 73,".jpg");
        desdeBusqueda = false;
        desdeMisJuegos = false;
        llenarCarr(juegoMostrado);
        llenarCategoria(categoriaMostrada);
        btnCarrito.setText("Carrito ("+carrito.largo()+")");
        if(carrito.largo()==0){
            btnCarrito.setDisable(true);
        }
        
    }
    
    @FXML
    void CarrDer() {
        //llenarCarr(juegoMostrado);
        PrimaryController.juegoMostrado+=4;
        try {
            App.setRoot("primary");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    void CarrIzq() {
        //llenarCarr(juegoMostrado);
        PrimaryController.juegoMostrado-=4;
        try {
            App.setRoot("primary");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    void catAbajo(ActionEvent event) {
        llenarCategoria(categoriaMostrada);
        PrimaryController.categoriaMostrada+=4;
        try {
            App.setRoot("primary");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    @FXML
    void catArriba(ActionEvent event) {
        llenarCategoria(categoriaMostrada);
        PrimaryController.categoriaMostrada-=4;
        try {
            App.setRoot("primary");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    void llenarCarr(int indice){
        DoubleCircularLL<Juego> juegosMostrados = Juego.juegosCargados(indice);
        App.setImage(juegosMostrados.getIndex(0).getTitulo(),App.pathImagesJuegos,imvC1,200,280,".jpg");
        App.setImage(juegosMostrados.getIndex(1).getTitulo(),App.pathImagesJuegos,imvC2,200,280,".jpg");
        App.setImage(juegosMostrados.getIndex(2).getTitulo(),App.pathImagesJuegos,imvC3,200,280,".jpg");
        App.setImage(juegosMostrados.getIndex(3).getTitulo(),App.pathImagesJuegos,imvC4,200,280,".jpg");
        
        lblC1.setText(juegosMostrados.getIndex(0).getTitulo());
        lblC2.setText(juegosMostrados.getIndex(1).getTitulo());
        lblC3.setText(juegosMostrados.getIndex(2).getTitulo());
        lblC4.setText(juegosMostrados.getIndex(3).getTitulo());
        
        imvC1.setOnMouseClicked(ev ->juegoLabel(lblC1));
        imvC2.setOnMouseClicked(ev ->juegoLabel(lblC2));
        imvC3.setOnMouseClicked(ev ->juegoLabel(lblC3));
        imvC4.setOnMouseClicked(ev ->juegoLabel(lblC4));
        lblC1.setOnMouseClicked(ev ->juegoLabel(lblC1));
        lblC2.setOnMouseClicked(ev ->juegoLabel(lblC2));
        lblC3.setOnMouseClicked(ev ->juegoLabel(lblC3));
        lblC4.setOnMouseClicked(ev ->juegoLabel(lblC4));
    }
    
    void llenarCategoria(int indice){
        DoubleCircularLL<Juego> categoriasMostradas = Juego.categoriasCargadas(indice);
        App.setImage(categoriasMostradas.getIndex(0).getTitulo(),App.pathImagesJuegos,imv1,200,280,".jpg");
        App.setImage(categoriasMostradas.getIndex(1).getTitulo(),App.pathImagesJuegos,imv2,200,280,".jpg");
        App.setImage(categoriasMostradas.getIndex(2).getTitulo(),App.pathImagesJuegos,imv3,200,280,".jpg");
        App.setImage(categoriasMostradas.getIndex(3).getTitulo(),App.pathImagesJuegos,imv4,200,280,".jpg");
        
        
        lbl1.setText(categoriasMostradas.getIndex(0).getGenero());
        lbl2.setText(categoriasMostradas.getIndex(1).getGenero());
        lbl3.setText(categoriasMostradas.getIndex(2).getGenero());
        lbl4.setText(categoriasMostradas.getIndex(3).getGenero());
        
        imv1.setOnMouseClicked(ev ->categoriaLabel(lbl1));
        imv2.setOnMouseClicked(ev ->categoriaLabel(lbl2));
        imv3.setOnMouseClicked(ev ->categoriaLabel(lbl3));
        imv4.setOnMouseClicked(ev ->categoriaLabel(lbl4));
        lbl1.setOnMouseClicked(ev ->categoriaLabel(lbl1));
        lbl2.setOnMouseClicked(ev ->categoriaLabel(lbl2));
        lbl3.setOnMouseClicked(ev ->categoriaLabel(lbl3));
        lbl4.setOnMouseClicked(ev ->categoriaLabel(lbl4));
    }
    
    @FXML
    private void misJuegos(){
        MisJuegosController.listaMisJuegos = misJuegos;
        MisJuegosController.textoCmbFiltroMisJuegos = "";
        try {
            App.setRoot("misjuegos");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    void irACarrito() {
        try {
                App.setRoot("carrito");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
    }

    @FXML
    private void buscar(ActionEvent event) {
            busquedaC=false;
            if(txtAño.getText().length()==0 && txtTitulo.getText().length()==0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al intentar la búsqueda");
                alert.setHeaderText(null);
                alert.setContentText("Ingrese algún parámetro");
                alert.showAndWait();
            } else {
                System.out.println("Buscando");
                Juego.juegosEncontrados(txtTitulo.getText(),txtAño.getText()); 
                if(juegosEncontrados.largo()>0){
                    try {
                        App.setRoot("search");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("No se encontraron resultados");
                    alert1.setHeaderText(null);
                    alert1.setContentText("No se han encontrado resultados que concuerden con los parámetros buscados. \nIntente nuevamente.");
                    alert1.showAndWait();
                }
                
            }
            
    }
    
    public static void juegoLabel(Label label){
            busquedaC=false;
        
            j= Juego.buscarPorTitulo(label.getText());
            JuegoController.lista = JuegoController.feedbackJuego();
            JuegoController.textoCmbFiltro = "";
            try {
                App.setRoot("juego");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
    }
    
    public void categoriaLabel(Label label){
        busquedaC=true;
        categoríaSeleccionada=label.getText();
        Juego.juegosGenero(label.getText());
            try {
                App.setRoot("search");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
      
}
