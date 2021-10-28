import java.util.*;
import java.io.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
    static ArrayList<Integer> rondas = new ArrayList<Integer>();
    static ArrayList<Integer> turnos = new ArrayList<Integer>();
    static ArrayList<Boolean> finish = new ArrayList<Boolean>();
    static ArrayList<String> resultado = new ArrayList<String>();
    static int count = 0;
    
    public static void main(String[] args) throws IOException{
        //No se contempla la posibilidad de que uno llegue una vez se haya empezado a
        //ejecutar otro, por lo que el ordenado se hace desde el principio.

        //Cantidad de procesos que se van a realizar.
        int nProcesos = 5;

        //Número mínimo en la generación aleatória.
        int minNum = 5;

        //Número máximo en la generación aleatória, no puede ser igual que el minNum.
        int maxNum = 10;

        //1 = Aleatorio, 2 = Manual
        int elec = 1;

        if(elec != 2 && nProcesos < 1){

        }else{
            do{
            
            switch(elec){
                case 1:
                   datos(nProcesos, maxNum, minNum);
                break;
                case 2:
                    int f = 0;
                    while(f == 0){
                        System.out.println("Dime cuantos procesos quieres hacer: ");
                        String s = br.readLine();
                        try {
                            nProcesos = Integer.parseInt(s);
                            f = 1;
                        } catch (Exception e) {
                            System.out.println("Error");
                        }
                    }
                    manual(nProcesos);
                break;
                default:
                    datos(nProcesos, maxNum, minNum);
                break;
            }
            order(nProcesos);
            process();
            if(count == 1){

            }else{
               System.out.println("\033[H\033[2J");
               System.out.println("List: " + list);
               System.out.println("Turnos: " + turnos);
               System.out.println("Rondas: " + rondas);
               System.out.println("");
               see();
               System.out.println("- : preparado.\nx : en ejecución.\n· : terminado.");
               System.out.println("");
               contador(nProcesos);
            }
            }while(count == 1);
        }
    }

    static void datos(int nProcesos, int maxNum, int minNum){
        int tiempo = 0, llegada = 0;
        double  timepoD = 0;
        int num = maxNum - minNum;
        if(num <= 0){
            num = 5;
        }
        for (int i = 0; i < nProcesos; i++) {
            ArrayList<Integer> subLista2 = new ArrayList<Integer>();
            //Creo números aleatórios
            timepoD = (Math.random() * num + minNum);
            //Cambio de double a Int
            tiempo = (int)timepoD;
            llegada = i;
            //Los añado a la sbulista
            subLista2.add(tiempo);
            subLista2.add(llegada);
            //Añado la sublista a la Lista
            list.add(subLista2);
            rondas.add(0);
            finish.add(false);
            resultado.add("PID " + (i+1) + ": ");
        }
    }

    static void manual(int nProcesos){
        for (int i = 0; i < nProcesos; i++) {
            int e = 0, tiempo = 0;
            while(e == 0){
                try {
                    System.out.println("Dime el proceso Nº " + (i+1) + "(Tiempo): ");
                    tiempo = sc.nextInt();
                    ArrayList<Integer> subL = new ArrayList<Integer>();
                    subL.add(tiempo);
                    subL.add(i);
                    resultado.add("PID " + (i+1) + ": ");
                    rondas.add(0);
                    finish.add(false);
                    list.add(subL);
                    
                    e = 1;
                } catch (Exception l) {
                    System.out.println("Error.");
                }
            }
        }
    }

    static void order(int nProcesos){
        for (int i = 0; i < nProcesos; i++) {
            int c = 0;
            for (int j = 0; j < nProcesos; j++) {
                int tiem1 = list.get(i).get(0), tiem2 = list.get(j).get(0);
                int llegada1 = list.get(i).get(1), llegada2 = list.get(j).get(1);
                if(tiem1 > tiem2){
                    c += 1;
                }else if(tiem1 == tiem2){
                    if(llegada1 > llegada2){
                        c += 1;
                    }else{

                    }
                }
            }
            turnos.add(c);
        }
        for (int i = 0; i < turnos.size(); i++) {
            rondas.set(turnos.get(i), i);
        }
    }

    static void see(){
        for(int l = 0; l < resultado.size(); l++) {
            System.out.println(resultado.get(l)+"\n");
        }
    }

    static void process(){
        for(int i = 0; i < rondas.size(); i++) {
            int pos = rondas.get(i);
            int tiempo = list.get(pos).get(0);
            for (int j = 0; j < tiempo; j++){
                    boolean acabado = finish.get(pos);
                    if(acabado == true){

                        break;
                    }else{
                        for (int k = 0; k < resultado.size(); k++) {
                            if(k == pos){
                                resultado.set(k, resultado.get(k) + "X");
                            }else{
                                if(finish.get(k)){
                                    resultado.set(k, resultado.get(k) + "·");
                                }else{
                                    resultado.set(k, resultado.get(k) + "-");
                                }
                                
                            }
                        }
                        
                    }
            }
            finish.set(pos, true);
        }
    }

    static void contador(int nP){
        for(int i = 0; i < resultado.size(); i++) {
            String frase = resultado.get(i);
            int e = 0, p = 0;
            for(int j = 0; j < frase.length(); j++) {
                if(frase.charAt(j) == 'X'){
                    e += 1;
                }else if(frase.charAt(j) == '-'){
                    p += 1;
                }
            }
            System.out.println("PID " + (i+1) + ": ");
            System.out.println("  Tiempo total: " + (e+p));
            System.out.println("  Tiempo 'X'  : " + (e));
            System.out.println("  Tiempo '-'  : " + (p));
            System.out.println("");
        }
    }

}
