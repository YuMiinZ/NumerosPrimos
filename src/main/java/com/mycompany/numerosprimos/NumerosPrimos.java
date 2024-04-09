/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.numerosprimos;

import java.util.Random;

/**
 *
 * @author yumii
 */
public class NumerosPrimos {
    
    public static int obtenerNumeroAleatorio(){
        Random rand = new Random();
        int num = Math.abs(rand.nextInt()); //nextInt genera un número entero aleatorio de 32 bits positvo
        if (num % 2 == 0) { // Verifica si es par
            num++; // Agrega 1 para convertirlo en impar
        }
        return num;
    }
    
    public static long generarPrimos(int t){
        Random rand = new Random();
        boolean found = false;
        long s = 32; // Longitud en bits del número primo a generar
        long primoEncontrado = 0;
        
        do {
            int r = obtenerNumeroAleatorio(); // Genere al azar r impar
            
            long n = (long) (((Math.pow(2, s)) * r) - 1); // Calcule n = (2^s)r – 1
            
            for (long i = 1; i <= t; i++) {
                //long a = rand.nextInt(n - 2) + 2; // Escoja un entero aleatorio a, 2 ≤ a ≤ n – 2
                long a = rand.nextLong(n - 2) + 2; // Escoja un entero aleatorio a, 2 ≤ a ≤ n – 2

                long y = expModular(a, r, n); // y = a^r mod n
                //long y = (long) (Math.pow(a, r) % n); // y = a^r mod n
                
                if (y != 1 && y != n - 1) {
                    int j = 1;
                    while (j <= s - 1 && y != n - 1) {
                        y = (int) ((y * y) % n);
                        j++;
                    }
                    
                }
                found = (y == n - 1);
            }
            /*System.out.println("Valor de n: " + n);
            System.out.println("Valor de f: " + found);
            System.out.println("Valor de r: " + r);*/
            if (found) {
                primoEncontrado = n;
            }
        } while (!found); // Repetir hasta que se encuentre un número que pase la prueba de Miller-Rabin
        
        return primoEncontrado;
    }
    
    // Función de exponenciación modular para calcular a^r mod n
    public static long expModular(long base, long exponente, long modulo) {
        long resultado = 1;
        base = base % modulo;
        
        while (exponente > 0) {
            if ((exponente & 1) == 1) {
                resultado = (resultado * base) % modulo;
            }
            exponente >>= 1; // y = y/2
            base = (base * base) % modulo;
        }
        return resultado;
    }
    
    public static boolean miller_rabin(long r, long n, long t){
        int a = 2 + (int)(Math.random() % (n - 4));
        
        long y = expModular(a, r, n); // y = a^r mod n
        
        if (y == n - 1 || y == 1){
                return true;
        }
        
        /* Continue squaring x until one of the following occurs. */
        while (r != n - 1) {
            y = (y * y) % n;
            r *= 2;

            if (y == n - 1){
                    return true;
            }
            if (y == 1){
                    return false;
            }
        }

        /* returning composite */ 
        return false;
    }
    
    public static long generarPrimo(long t){
    long s = 32; // Longitud en bits del número primo a generar
    boolean found = false;
    long numero_primo = 0;
    do {
        int r = obtenerNumeroAleatorio(); // Genere al azar r impar
        long n = (long) (((Math.pow(2, s)) * r)); // Calcule n = (2^s)r – 1
        
        

        if (miller_rabin(r,n,t)){
            numero_primo = n;
            found = true;
        }
    }while (!found);
    return numero_primo;
    }
   
    public static void main(String[] args) {
        System.out.println(generarPrimo(5));

    }
}