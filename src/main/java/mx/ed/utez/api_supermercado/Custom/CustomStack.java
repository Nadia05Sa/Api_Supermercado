package mx.ed.utez.api_supermercado.Custom;

import java.util.ArrayList;
import java.util.List;

public class CustomStack<T> {


//Este sera parecido al examen estudiar todo esto

        int capacidad;
        int tope;
        T[] items;

        // constru
        @SuppressWarnings("unchecked")
        public CustomStack(int capacidad){
            this.capacidad=capacidad;
            tope=-1;
            this.items= (T[]) new Object[capacidad];
        }

        //aqui agrega
        public void push(T item){
            if(isFull()){
                System.out.println("El stack ya esta lleno, no puedes agregar mas");
                return;
            }
            items[++tope]=item;
        }

        //este muestra y elimina
        public T pop(){
            if(isEmpty()){
                System.out.println("El stack esta vacio");
                return null;
            }
            return items[tope--];
        }

        public T peek(){
            if(isEmpty()){
                System.out.println("El stack esta vacio");
                return null;
            }
            return items[tope];
        }

        //verifica si esta vacio
        public boolean isEmpty(){
            return (tope==-1);
        }

        //verifica si esta lleno
        public boolean isFull(){
            return tope==capacidad-1;
        }
    }

