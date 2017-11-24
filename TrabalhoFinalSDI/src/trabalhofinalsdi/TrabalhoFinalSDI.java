/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinalsdi;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */
public class TrabalhoFinalSDI {

    static final Logger logger = Logger.getLogger(TrabalhoFinalSDI.class.getName());
    static private FileHandler fh;

    static final byte NUMBER_OF_PROCESSING_THREADS = 2;

    final LinkedHashMap<Integer, Thread> workers = new LinkedHashMap<>();

    final List<Integer> numerosEntrada = Arrays.asList(1, 1, 2, 3, 7, 8, 9, 10, 1, 9);

    final LinkedHashMap<Integer, Integer> resultados = new LinkedHashMap<>();
    
    boolean acabou = false;

    TrabalhoFinalSDI() {
        this.Initialize();
    }

    private void Initialize() {

        // Thread do cliente
        Thread client = new Thread(() -> {
            while (!acabou) {
                
            }
            
            System.out.println("Cliente acabou");
        });

        client.start();

        // Thread do gerenciador
        Thread manager = new Thread(() -> {

            int totalRes = 0;

            boolean continua = true;

            createWorkersBruto();
            List<Integer> aDeletar = new LinkedList<>();
            while (continua) {
                // Manage things

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TrabalhoFinalSDI.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (Map.Entry<Integer, Thread> enty : workers.entrySet()) {

                    int id = enty.getKey();
                    Thread t = enty.getValue();
                    int res = resultados.get(id);

                    System.out.println("**************************");
                    System.out.println("Ainda tem " + workers.size());
                    System.out.println("Is alive: " + t.isAlive());
                    System.out.println("Is Inter: " + t.isInterrupted());
                    System.out.println("ID: " + id);
                    System.out.println("Res: " + res);
                    System.out.println("Total: " + totalRes);
                    if (!t.isAlive() && res == -1) {
                        // Temos que refazer a thread t
                        System.out.println("Morreu!");
                        System.out.println("Interrompeu: " + t.isInterrupted());
                        aDeletar.add(id);
                    } else {
                        System.out.println("Thread viva!");

                        System.out.println("Valor: " + res);
                        if (res != -1) {
                            totalRes++;
                        }
                        if (totalRes >= 2) {

                            System.out.println("Chegamos no final!");
                            continua = false;
                            break;
                        }
                    }

                }
                if (!aDeletar.isEmpty()) {

                    for (int kill : aDeletar) {
                        workers.remove(kill);
                    }
                    aDeletar.clear();
                    createWorkersBruto();
                }
            }
            for (int kill : aDeletar) {
                workers.remove(kill);
            }
            aDeletar.clear();
            acabou = true;
        });

        manager.start();
    }

    private void createWorkersBruto() {
        IBinPacking algo = new BinPackingForcaBruta(numerosEntrada, 10);
        System.out.println("\n\n*********************** createWorkersBruto:");
        System.out.println("Criando " + (NUMBER_OF_PROCESSING_THREADS - workers.size()) + " trabalhadores");
        while (workers.size() < NUMBER_OF_PROCESSING_THREADS) {
            int id = workers.size();
            final Thread t = new Thread() {

                public void run() {
                    long startTime;
                    long estimatedTime;

                    startTime = System.currentTimeMillis();
                    System.out.println("needed bins (" + algo.getClass().getName() + "): " + algo.getResult());
                    estimatedTime = System.currentTimeMillis() - startTime;
                    System.out.println("in " + estimatedTime + " ms");

                    int result = algo.getResult();

                    resultados.replace(id, result);


                    System.out.println("\n\n");
                }

            };
            resultados.put(id, -1);
            workers.put(id, t);
            t.start();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            TrabalhoFinalSDI.fh = new FileHandler("/Users/gustavo/Documents/TrabalhoFinalSDI/logs.txt");
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(TrabalhoFinalSDI.class.getName()).log(Level.SEVERE, null, ex);
        }

        TrabalhoFinalSDI.logger.addHandler(fh);

        // ***************
        // Inicia Bins
        // ***************
        TrabalhoFinalSDI trabalho = new TrabalhoFinalSDI();

    }

}
