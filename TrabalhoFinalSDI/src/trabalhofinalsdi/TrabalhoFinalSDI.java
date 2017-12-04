/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinalsdi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.FileHandler;

/**
 *
 * @author gustavo
 */
public class TrabalhoFinalSDI {

    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Entry<T, Integer> max = null;

        for (Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue()) {
                max = e;
            }
        }

        return max.getKey();
    }

    static private FileHandler fh;

    static final byte NUMBER_OF_PROCESSING_THREADS = 5;
    static final byte NUMBER_OF_WANTED_RESULTS = 10;
    static final byte NUM_MORTES = 0;

    final Random RANDOMIZER = new Random();

    final LinkedHashMap<Integer, Thread> workers = new LinkedHashMap<>();
    final LinkedHashMap<Integer, Integer> resultadosBruto = new LinkedHashMap<>();
    final LinkedHashMap<Integer, Integer> resultadosFFD = new LinkedHashMap<>();

    boolean teste = true;

    boolean acabou = false;
    boolean comecou = false;

    static final Logger LOG = new Logger("logger.txt");

    double tempoMedioBruto = 0;
    double tempoMedioFFD = 0;
    double tempoTotalBruto;
    double tempoTotalFFD;

    TrabalhoFinalSDI() {
        this.Initialize();
    }

    boolean clientHasGivenFile = false;

    private void Initialize() {

        Thread client = generateCliente();
        client.start();

        while (!acabou) {
            if (!client.isAlive()) {
                client = generateCliente();
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                LOG.Log(ex.getMessage(), Logger.LogType.ERROR);
            }
        }
    }

    int res = 0;
    int res2 = 0;

    Thread generateCliente() {
        // Thread do cliente
        return new Thread(() -> {

            if (!clientHasGivenFile) {
                String file;
                if (!teste) {
                    System.out.println("Bem vindo cliente!");
                    System.out.println("Iremos começar agora o nosso BinPacking!");
                    System.out.println("Primerio, nos informe o nome do arquivo:");

                    Scanner reader = new Scanner(System.in);
                    file = reader.next();
                    reader.close();
                } else {
                    file = "input.txt";
                }

                final List<Integer> numerosFFD = new ArrayList<>();
                final List<Integer> numerosBruto = new ArrayList<>();
                int buckets = 0;
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                    String[] numerosString = br.readLine().split(" ");

                    for (String s : numerosString) {
                        numerosFFD.add(Integer.valueOf(s));
                        numerosBruto.add(Integer.valueOf(s));
                    }

                    buckets = Integer.parseInt(br.readLine());

                } catch (Exception e) {
                    System.out.println("Falha ao ler o arquivo!!!!!!@1!@#12&!#!!");
                    LOG.Log("Falha ao abrir arquivo: " + file, Logger.LogType.ERROR);
                    System.exit(1);
                }

                System.out.println("Primeiro, com força bruta:");
                LOG.Log("Forca Bruta iniciada", Logger.LogType.INFO);
                res = executaForcaBruta(numerosBruto, buckets);
                System.out.println("Agora o FFD:");
                LOG.Log("FFD iniciado", Logger.LogType.INFO);
                res2 = executarOutro(numerosFFD, buckets);
                comecou = true;
                clientHasGivenFile = true;
            }

            while (!acabou) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    LOG.Log(ex.getMessage(), Logger.LogType.ERROR);
                }
            }

            System.out.println("Resultado Forca Bruta: " + res2);
            System.out.println("Resultado FFD: " + res);

            LOG.Log("Programa finalizado resultados: Forca Bruta: " + res + " e levou " + tempoTotalBruto + "ms total e em media " + tempoMedioBruto
                    + "ms, FFD: " + res2 + " e levou " + tempoTotalFFD + "ms total e em media " + tempoMedioFFD + "ms.", Logger.LogType.INFO, true);

            System.out.println("Cliente acabou");
        });
    }
    // Thread do gerenciador

    boolean brutoAcabou = false;
    int brutoResultado = 0;

    boolean ffdAcabou = false;
    int ffdResultado = 0;

    int executaForcaBruta(final List<Integer> numeros, int buckets) {
        workers.clear();
        brutoResultado = 0;
        Thread manager = new Thread(() -> {

            int totalRes = 0;

            boolean continua = true;

            createWorkersBruto(numeros, buckets);
            List<Integer> aDeletar = new LinkedList<>();
            List<Integer> resultadosInternos = new LinkedList<>();
            while (continua) {
                // Manage things

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    LOG.Log(ex.getMessage(), Logger.LogType.ERROR);
                }

                for (Map.Entry<Integer, Thread> enty : workers.entrySet()) {

                    int id = enty.getKey();
                    Thread t = enty.getValue();
                    int res = resultadosBruto.get(id);
                    if (!t.isAlive() && res == -1) {
                        LOG.Log("Trabalhador Bruto morreu! Começa outro", Logger.LogType.ERROR);
                        aDeletar.add(id);
                    } else {
                        if (res != -1) {
                            totalRes++;
                            resultadosInternos.add(res);
                            aDeletar.add(id);
                        }
                        if (totalRes >= NUMBER_OF_WANTED_RESULTS) {

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
                    createWorkersBruto(numeros, buckets);
                }
            }

            workers.clear();

            acabou = true;

            int part = mostCommon(resultadosInternos);

            tempoTotalBruto = tempoMedioBruto;
            tempoMedioBruto /= resultadosInternos.size();

            brutoResultado = part;
        });

        manager.start();

        while (brutoResultado == 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                LOG.Log(ex.getMessage(), Logger.LogType.ERROR);
            }
        }

        System.out.println("Finaliza programa");

        return brutoResultado;
    }

    int executarOutro(List<Integer> numeros, int buckets) {
        workers.clear();
        ffdResultado = 0;
        Thread manager = new Thread(() -> {

            int totalRes = 0;

            boolean continua = true;

            createWorkersFFD(numeros, buckets);
            List<Integer> aDeletar = new LinkedList<>();
            List<Integer> resultadosInternos = new LinkedList<>();
            while (continua) {
                // Manage things

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    LOG.Log(ex.getMessage(), Logger.LogType.ERROR);
                }

                for (Map.Entry<Integer, Thread> enty : workers.entrySet()) {

                    int id = enty.getKey();
                    Thread t = enty.getValue();
                    int res = resultadosFFD.get(id);
                    if (res != -1) {
                        totalRes++;
                        resultadosInternos.add(res);
                        aDeletar.add(id);

                        if (totalRes >= NUMBER_OF_WANTED_RESULTS) {
                            continua = false;
                            ffdAcabou = true;
                            break;
                        }
                    } else {
                        if (!t.isAlive() && res == -1) {
                            LOG.Log("Trabalhador FFD morreu! Começa outro", Logger.LogType.ERROR);
                            aDeletar.add(id);
                        } else {
                            if (res != -1) {
                                totalRes++;
                                resultadosInternos.add(res);
                                aDeletar.add(id);
                            }
                            if (totalRes >= NUMBER_OF_WANTED_RESULTS) {
                                continua = false;
                                ffdAcabou = true;
                                break;
                            }
                        }
                    }

                }
                if (!aDeletar.isEmpty()) {

                    for (int kill : aDeletar) {
                        workers.remove(kill);
                    }
                    aDeletar.clear();
                    createWorkersFFD(numeros, buckets);
                }
            }
            workers.clear();

            acabou = true;

            int part = mostCommon(resultadosInternos);

            tempoTotalFFD = tempoMedioFFD;
            tempoMedioFFD /= resultadosInternos.size();

            ffdResultado = part;
        });

        manager.start();

        while (ffdResultado == 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                LOG.Log(ex.getMessage(), Logger.LogType.ERROR);
            }
        }

        System.out.println("Finaliza programa");

        return ffdResultado;

    }

    int tentativasFFD = 0;

    private void createWorkersFFD(List<Integer> numeros, int buckets) {
        Random randomGenerator = new Random();
        System.out.println("\n\n*********************** createWorkersFFD:");
        System.out.println("Criando " + (NUMBER_OF_PROCESSING_THREADS - workers.size()) + " trabalhadores");
        while (workers.size() < NUMBER_OF_PROCESSING_THREADS && !ffdAcabou) {
            int id = workers.size();
            final Thread t = new Thread() {

                public void run() {
                    List<Integer> numerosEnviar = new ArrayList<>();
                    for (int n : numeros) {
                        numerosEnviar.add(n);
                    }
                    boolean mata = false;
                    if (randomGenerator.nextInt(100) > 50 && tentativasFFD < NUM_MORTES) {
                        mata = true;
                        tentativasFFD++;
                    }

                    IBinPacking algo = new BinPackingFFD(numerosEnviar, buckets, mata);

                    long startTime;
                    long estimatedTime;

                    startTime = System.currentTimeMillis();

                    int result = algo.getResult();
                    if (RANDOMIZER.nextInt(100) > 75) {
                        result += 2;
                    }
                    estimatedTime = System.currentTimeMillis() - startTime;
                    tempoMedioFFD += estimatedTime;
                    System.out.println("Esse FFD levou " + estimatedTime + " ms");

                    resultadosFFD.replace(id, result);
                }

            };
            resultadosFFD.put(id, -1);
            workers.put(id, t);
            t.start();
        }
    }

    int tentativasBruto = 0;

    private void createWorkersBruto(List<Integer> numeros, int buckets) {

        Random randomGenerator = new Random();
        System.out.println("\n\n*********************** createWorkersBruto:");
        System.out.println("Criando " + (NUMBER_OF_PROCESSING_THREADS - workers.size()) + " trabalhadores");
        while (workers.size() < NUMBER_OF_PROCESSING_THREADS && !brutoAcabou) {
            int id = workers.size();
            final Thread t = new Thread() {

                public void run() {
                    List<Integer> numerosEnviar = new ArrayList<>();
                    for (int n : numeros) {
                        numerosEnviar.add(n);
                    }
                    boolean mata = false;
                    if (randomGenerator.nextInt(100) > 25 && tentativasBruto < NUM_MORTES) {
                        tentativasBruto++;
                        mata = true;
                    }
                    IBinPacking algo = new BinPackingForcaBruta(numerosEnviar, buckets, mata);

                    long startTime;
                    long estimatedTime;

                    startTime = System.currentTimeMillis();

                    int result = algo.getResult();
                    if (RANDOMIZER.nextInt(100) > 75) {
                        result += 2;
                    }
                    estimatedTime = System.currentTimeMillis() - startTime;
                    tempoMedioBruto += estimatedTime;
                    System.out.println("Esse Bruto levou " + estimatedTime + " ms");

                    resultadosBruto.replace(id, result);

                }

            };
            resultadosBruto.put(id, -1);
            workers.put(id, t);
            t.start();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // ***************
        // Inicia Bins
        // ***************
        for (int i = 0; i < 10; i++) {
            new TrabalhoFinalSDI();
        }

    }

}
