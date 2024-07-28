/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo3.proyectoeddg3.list;

import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author mbravop
 */
public class DoubleCircularLL<E> implements Iterable<E>{

    int longitud;
    Nodo<E> first;
    Nodo<E> last;
    
    private class Nodo<E>{
        private E contenido;
        Nodo<E> sig;
        Nodo<E> ant;
        
        public Nodo(E e){
            this.contenido = e;
            this.sig = null;
            this.ant = null;
        }
        
        public void mostrarContenido(){
        System.out.print(this.contenido+" ");
        }
    }
    
    
    
    private class MyIterator<E> implements Iterator<E>{
        Nodo<E> i;
        int contador = 0;
        
        public MyIterator(DoubleCircularLL<E> listaDoblementeEnlazadaC){
            i = (Nodo<E>)listaDoblementeEnlazadaC.first;
        }
        
        public E next(){
            E tmp = i.contenido;
            i = i.sig;
            return tmp;
        }
        
        public boolean hasNext(){
            contador++;
            return (i!=null && contador<=longitud);
        }
    }
    
    public Iterator<E> iterator(){
        return new MyIterator(this);
    } 
    
    
    public E encontrar(Comparator<E> cmp, E obj){
        for(E e: this){
            if(cmp.compare(e,obj) == 0){
                return e;
            }
        }
        return null;
    }
    
    public DoubleCircularLL<E> encontrarTodo(Comparator<E> cmp, E obj){
        DoubleCircularLL<E> listaRetornar = new DoubleCircularLL<>();
        for(E e: this){
            if(cmp.compare(e,obj) == 0){
                listaRetornar.addLast(e);
            }
        }
        return listaRetornar;
    }
    
    public DoubleCircularLL(){
        this.first = null;
        this.last = null;
        this.longitud = 0;
    }
    
    public int largo(){
        return longitud;
    }
    
    public boolean estaVacia(){
        return longitud==0;
    }
    
    public boolean addFirst(E contenido){
        Nodo nuevoNodo = new Nodo(contenido);
        if(estaVacia()){
            first=last=nuevoNodo;
            first.sig=first.ant=last;
            last.sig=last.ant=first;
        }else{
            nuevoNodo.sig=first;
            last.sig=nuevoNodo;
            nuevoNodo.ant=last;
            first.ant=nuevoNodo;
            first=nuevoNodo;
        }
        longitud++;
        return true;
    }
    
    public boolean addLast(E contenido){
        Nodo nuevoNodo = new Nodo(contenido);
        if(estaVacia()){
            first = nuevoNodo;
            last = nuevoNodo;
        }
        nuevoNodo.sig = first;
        last.sig = nuevoNodo;
        nuevoNodo.ant = last;
        last = nuevoNodo;
        first.ant = last;
        longitud++;
        return true;
    }
    
    public boolean add(int index, E contenido){
        Nodo nuevoNodo = new Nodo(contenido);
        if(index<0 || index>longitud) return false;
        if(contenido == null) return false;
        if(index == 0){
            addFirst(contenido);
        }else if(index==longitud){
            addLast(contenido);
        }else{
            Nodo i = first;
            for(int j=0;j<index-1;j++){
                i=i.sig;
            }
            nuevoNodo.sig = i.sig;
            nuevoNodo.ant = i;
            i.sig = nuevoNodo;
            nuevoNodo.sig.ant = nuevoNodo;
            longitud++;
        }
        return true;
    }
    
    public boolean addAll(DoubleCircularLL<E> l){
        for (E e:l){
            this.addLast(e);
        }
        return true;
    }
    
    public boolean removeFirst(){
        if(longitud>1){
            first.sig.ant = null;
            first = first.sig;
            first.ant = last;
            last.sig = first;
        }else{
            first = null;
            last = null;
        }
        longitud--;
        return true;
    }
    
    public boolean removeLast(){
        if(longitud>1){
            last.ant.sig = null;
            last = last.ant;
            last.sig = first;
            first.ant = last;
        }else{
            first = null;
            last = null;
        }
        longitud--;
        return true;
    }
    
    public boolean remove(int index){
        if(index<longitud && index>=0){
            if(index==0){
                this.removeFirst();
            }else if(index==longitud-1){
                this.removeLast();
            }else{
                Nodo i = first;
                for(int j=0;j<index;j++){
                    i = i.sig;
                }
                i.ant.sig = i.sig;
                i.sig.ant = i.ant;
                longitud--;
            }
        }
        return true;
    }
    
    public boolean remove(E e){
        
        for(int a=0; a<this.longitud;a++){
            if (this.getIndex(a).equals(e)){
                this.remove(a);
            }    
        }
        return true;
    }
    
    public E getFirst(){
        return first.contenido;
    }
    
    public E getLast(){
        return last.contenido;
    }
    
    public E getIndex(int index){
        if(index>=0){
            Nodo i = first;
            for(int j=0;j<index;j++){
                i = i.sig;
            }
            return (E)i.contenido;  
        }else{
            Nodo i = first;
            for(int j=0;j<(index*-1);j++){
                i = i.ant;
            }
            return (E)i.contenido; 
        }
    }
    
    public void clear(){
        first=last=null;
    }
    
    public void visualizar(){
        Nodo i = this.first;
        if(i!=null){
            i.ant.mostrarContenido();
            System.out.println("<- ");
            for(int j = 0;j<longitud-1;j++){
                i.mostrarContenido();
                i = i.sig;
            }
            i.mostrarContenido();
            System.out.println("-> ");
            i.sig.mostrarContenido();
        }else{
            System.out.println("Lista vacia");
        }
    }
    
    public boolean contains(E e){
        boolean ok = false;
        for(E a:this){
            if(a.equals(e)){
                ok = true;
            }
        }
        return ok;
    }
}
