package logica.producto.validaciones;

import java.util.Locale;

public class Validaciones {
    //TODAS LAS VALIDACIONES DEBEN SER TRUE PARA PROCEDER :P

    public boolean validar(String texto, int opcion){
        int ni; float nf;
        if (texto.equals("")) return false;
        else{
        switch (opcion){
            case 1: // nombre
                // nombre debe tener máximo 50 caracteres y al menos 5
                System.out.println("nombre:"+(!(texto.length()>50))+texto.length()+texto);
                return ((!((texto.length()>50)||(texto.length()<5)))); //false no cumple
            case 2: //descripcion
                //desc debe tener máximo 280 caracteres y al menos 20
                System.out.println("desc:"+(!((texto.length()>280)||(texto.length()<20))));
                return (!((texto.length()>280)||(texto.length()<20))); //false si no cumple

            //case 3 y 4 debe convertir a int/float
            //antes de convertir a int/float debe:
            //a) verificar que solo contenga numeros + punto
            //b) si consigue un caracter invalido, false
            case 3: //precio, float
                if ((texto.contains(" "))||(texto.contains(","))||(texto.contains("-"))
                        ||(texto.toLowerCase(Locale.ROOT).contains("p"))||(texto.toLowerCase(Locale.ROOT).contains("r"))
                        ||(texto.toLowerCase(Locale.ROOT).contains("e"))||(texto.toLowerCase(Locale.ROOT).contains("c"))
                        ||(texto.toLowerCase(Locale.ROOT).contains("i"))||(texto.toLowerCase(Locale.ROOT).contains("o"))){ //si no estan los caracteres correctos
                    System.out.println("precio: false regex");
                    return false;
                }
                // precio entre 0 y 1000$
                else{nf=Float.parseFloat(texto);
                System.out.println("precio:length "+(!((nf>1000)||(nf<=0))));
                return (!((nf>1000)||(nf<=0))); }//false si no cumple
            case 4: //cantidad, int
                if ((texto.contains(" "))||(texto.contains(","))||(texto.contains("-"))||(texto.contains("."))
                        ||(texto.toLowerCase(Locale.ROOT).contains("c"))||(texto.toLowerCase(Locale.ROOT).contains("a"))
                        ||(texto.toLowerCase(Locale.ROOT).contains("n"))){ //si no estan los caracteres correctos
                    System.out.println("cant: false regex");
                    return false;
                }
                //cantidad entre 0 y 1000
                else{ni=Integer.parseInt(texto);
                System.out.println("cant: length "+(!((ni>1000)||(ni<=0))));
                return (!((ni>1000)||(ni<=0))); }//false si no cumple
        }}
        return false;
    }

    //LAS SIGUIENTES SON PARA EVITAR QUE EL USUARIO ESCRIBA CARACTERES INNECESARIOS EN CASILLAS INCORRECTAS
    //EN PRECIO: SOLO NUMEROS Y COMA
    //EN CANTIDAD: SOLO NUMEROS

    public String deleteFromString(String test, boolean patron){
        String temp, temp2;
        //borra los caracteres que no cuadren del string
        //true=precio, false=cantidad
        //precio:
        if (patron){//caso precio: debe eliminar todos lo que no sea numero o punto y reemplazar . con ,
            temp2=test.replaceAll("[,]",".");
            temp=temp2.replaceAll("[^\\d.]", "");
        }
    //cantidad:
        else {//caso cantidad: debe eliminar todos lo que no sea numero
            temp=test.replaceAll("[^\\d]", "");
        }
        System.out.println("VALOR DE TEMP:"+temp);

        System.out.println("test:"+temp);
        return temp;
    }

}
