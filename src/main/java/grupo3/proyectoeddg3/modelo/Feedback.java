/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo3.proyectoeddg3.modelo;

import grupo3.proyectoeddg3.list.DoubleCircularLL;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author mbravop
 */
public class Feedback {
    private int valoracion;
    private String nombreUsuario;
    private String descripcion;
    private String tituloJuego;

    public int getValoracion() {
        return valoracion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTituloJuego() {
        return tituloJuego;
    }
    
    public Feedback(int valoracion, String nombreUsuario, String descripcion, String tituloJuego) {
        this.valoracion = valoracion;
        this.nombreUsuario = nombreUsuario;
        this.descripcion = descripcion;
        this.tituloJuego = tituloJuego;
    }
   
    public static DoubleCircularLL<Feedback> cargarFeedback(String ruta){
        DoubleCircularLL<Feedback> listaFeedback = new DoubleCircularLL<>();
        try(BufferedReader br=new BufferedReader(new FileReader(ruta)))
         {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(";");
                Feedback fb = new Feedback(Integer.parseInt(datos[0]), datos[1], datos[2], datos[3]);
                listaFeedback.addLast(fb);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return listaFeedback;
    }

    @Override
    public String toString() {
        return "Feedback{" + "valoracion=" + valoracion + ", nombreUsuario=" + nombreUsuario + '}';
    }
    
    
}
