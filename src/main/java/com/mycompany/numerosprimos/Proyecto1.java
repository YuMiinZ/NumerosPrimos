package com.mycompany.numerosprimos;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Proyecto1 {

    public static int obtenerNumeroAleatorio() {
        Random rand = new Random();
        int num = Math.abs(rand.nextInt()); // nextInt genera un número entero aleatorio de 32 bits positvo
        if (num % 2 == 0) { // Verifica si es par
            num++; // Agrega 1 para convertirlo en impar
        }
        return num;
    }

    // Funcion para calcular a^b mod m de manera eficiente
    public static long expModular(long a, long b, long m) {
        long result = 1;
        a = a % m;
        while (b > 0) {
            if (b % 2 == 1)
                result = (result * a) % m;
            b /= 2;
            a = (a * a) % m;
        }
        return result;
    }

    // Funcion para verificar si un numero es primo utilizando Miller-Rabin
    public static boolean esPrimo(long n, int s, int t) {
        long r = n - 1;

        // Encontrar s y r tal que n - 1 = (2^s) * r, donde r es impar
        while (r % 2 == 0) {
            r /= 2;
            s++;
        }

        // Realizar el test de Miller-Rabin t veces
        for (int i = 0; i < t; i++) {
            long a = 2 + (long) (Math.random() % (n - 4)); // Numero aleatorio "a" en el rango [2,n−2].
            long y = expModular(a, r, n); // Calculo de a^r mod n

            if (y != 1 && y != n - 1) { // Verificacion de test de miller rabin
                int j = 1;
                while (j <= s - 1 && y != n - 1) {
                    y = (y * y) % n;
                    j++;
                }
                if (y != n - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int generarPrimo() {
        boolean found = false;
        int s = 0, t = 5, numero_primo_encontrado = 0; // s representa la mayor potencia de 2 que divide a n-1

        do {
            int n = obtenerNumeroAleatorio(); // Genere al azar r impar
            if (esPrimo(n, s, t)) {
                found = true;
                numero_primo_encontrado = n;
            }
        } while (!found);
        return numero_primo_encontrado;
    }

    // Función recursiva para calcular los coeficientes "x", "y" de la ecuación de
    // Bezout
    public static int[] euclidesExtendidoCoeficientes(int a, int b) {
        if (b == 0) // Caso base si b == 0, "x = 1" "y = 0"
            return new int[] { 1, 0 };
        else { // Calculo recursivo para obtener los coeficientes
            int[] coeficientesAnteriores = euclidesExtendidoCoeficientes(b, a % b);
            int x = coeficientesAnteriores[1];
            int y = coeficientesAnteriores[0] - (a / b) * coeficientesAnteriores[1];
            return new int[] { x, y };
        }
    }

    // Funcion que hace al calculo de coeficientes "x", "y" para los numeros a y b.
    public static int euclidesExtendido(int a, int b) {
        if (b == 0) // Caso para finalizar la recursion
            return a; // a contiene el maximo comun divisor de los numeros a y b

        else // llamada recursiva a euclidesExtendido con b como el nuevo a y a % b como el
             // nuevo b.
            return euclidesExtendido(b, a % b);
    }

    public static int inversoModular(int a, int n) {
        int mcd = euclidesExtendido(a, n);
        if (mcd != 1) {
            System.out.println(a + " no tiene inverso multiplicativo módulo " + n);
            return -1; // No tiene inverso multiplicativo
        } else {
            int[] coeficientes = euclidesExtendidoCoeficientes(a, n);
            int x = coeficientes[0];
            while (x < 0) {
                x += n;
            }
            return x;
        }
    }

    public static int[] generadores(int primo) {
        // al menos 3 generados del grupo ciclico multiplicativo Z*n
        int[] generadores = new int[3];
        int count = 0;
        //int parts = primo / 100;
        for (int g = 2; g < primo; g++) {
           /*  if (g % parts == 0) {
                System.out.println("va por el  " + (g * 100) / primo + "% de la ejecución");
            } */
            if (esRaizPrimitiva(g, primo)) {
                generadores[count++] = g;
                if (count == 3) {
                    return generadores;
                }

            }
        }
        return generadores;
    }

    public static boolean esRaizPrimitiva(int g, int n) {
        ArrayList<Integer> potencias = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            int potencia = (int) Math.pow(g, i) % n; // Calcula g^i módulo n
            //System.out.println(potencia);
            if (potencias.contains(potencia) || potencia == 1) {
                return false;
            }
            potencias.add(potencia);
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Proyecto 1 - Números Primos");
        // Punto 1. Generación de primos con el algoritmo de Miller Rabin
        int n = generarPrimo();
        System.out.println("Número primo generado: " + n);

        // Punto 2. Encontrar inversos con n primo usando el algoritmo de euclides
        // extendido
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "\nIngrese el tamaño de la lista de enteros que desea ingresar para encontrar el inverso multiplicativo: ");
        int cantidad = scanner.nextInt();
        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nIngrese el entero para encontrar su inverso módulo " + n + ": ");
            int entero = scanner.nextInt();
            int inverso = inversoModular(entero, n);
            if (inverso != -1) {
                System.out.print("El inverso multiplicativo de " + entero + " módulo " + n + " es: " + inverso);
            }
        }
        scanner.close();

        // Punto 3. Generadores
        int[] generadores = generadores(n);
        System.out.println("\n\nGeneradores del grupo cíclico multiplicativo Z*n" + n + ": ");
        for (int i = 0; i < generadores.length; i++) {
            System.out.println("Generador " + (i + 1) + ": " + generadores[i]);
        }
    }
}
