/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinalsdi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
    final LinkedHashMap<Integer, Integer> resultados = new LinkedHashMap<>();

    boolean acabou = false;
    boolean comecou = false;

    TrabalhoFinalSDI() {
        this.Initialize();
    }

    private void Initialize() {

        // Thread do cliente
        Thread client = new Thread(() -> {
            System.out.println("Bem vindo cliente!");
            System.out.println("Iremos começar agora o nosso BinPacking!");
            System.out.println("Primerio, nos informe o nome do arquivo:");

            Scanner reader = new Scanner(System.in);
            String file = reader.next();
            reader.close();

            try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
                String[] numerosString = br.readLine().split(" ");
                
                List<Integer> numero = new ArrayList<>();
                
                for (String s : numerosString){
                    numero.add(Integer.valueOf(s));
                }
                
                int buckets = Integer.parseInt(br.readLine());

            } catch (Exception e){
                
            }

            System.out.println("Primeiro, com força bruta:");
            int res = executaForcaBruta();
            System.out.println("Resultado: " + res);
            System.out.println("Agora o outro:");
            int res2 = executarOutro();
            System.out.println("Resultado: " + res2);
            comecou = true;

            while (!acabou) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TrabalhoFinalSDI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            System.out.println("Cliente acabou");
        });

        client.start();
    }
    // Thread do gerenciador

    boolean brutoAcabou = false;
    int brutoResultado = 0;
    boolean ffdAcabou = false;
    int ffdResultado = 0;

    int executaForcaBruta() {
        brutoResultado = 0;
        Thread manager = new Thread(() -> {

            int totalRes = 0;

            boolean continua = true;

            createWorkersBruto();
            List<Integer> aDeletar = new LinkedList<>();
            List<Integer> resultadosInternos = new LinkedList<>();
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
                    if (!t.isAlive() && res == -1) {
                        aDeletar.add(id);
                    } else {
                        if (res != -1) {
                            totalRes++;
                            resultadosInternos.add(res);
                        }
                        if (totalRes >= 2) {

                            continua = false;
                            brutoAcabou = true;
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

            workers.clear();

            acabou = true;

            int part = 0;

            for (Integer resultado : resultadosInternos) {
                System.out.println("SOma " + part + " com " + resultado);
                part += resultado;
            }
            System.out.println("Soma: " + part);
            System.out.println("Divide por " + resultadosInternos.size());
            part /= resultadosInternos.size();
            System.out.println("DEU: " + part);

            brutoResultado = part;
        });

        manager.start();

        while (brutoResultado == 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TrabalhoFinalSDI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Finaliza programa");

        return brutoResultado;
    }

    int executarOutro() {
        ffdResultado = 0;
        Thread manager = new Thread(() -> {

            int totalRes = 0;

            boolean continua = true;

            createWorkersFFD();
            List<Integer> aDeletar = new LinkedList<>();
            List<Integer> resultadosInternos = new LinkedList<>();
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
                    if (!t.isAlive() && res == -1) {
                        aDeletar.add(id);
                    } else {
                        if (res != -1) {
                            totalRes++;
                            resultadosInternos.add(res);
                        }
                        if (totalRes >= 2) {
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
                    createWorkersFFD();
                }
            }
            workers.clear();

            acabou = true;

            int part = 0;

            for (Integer resultado : resultadosInternos) {
                System.out.println("SOma " + part + " com " + resultado);
                part += resultado;
            }
            System.out.println("Soma: " + part);
            System.out.println("Divide por " + resultadosInternos.size());
            part /= resultadosInternos.size();
            System.out.println("DEU: " + part);

            ffdResultado = part;
        });

        manager.start();
        return 0;

    }

    private void createWorkersFFD() {
        IBinPacking algo = new BinPackingFFD(numerosEntrada, 10);
        System.out.println("\n\n*********************** createWorkersFFD:");
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
