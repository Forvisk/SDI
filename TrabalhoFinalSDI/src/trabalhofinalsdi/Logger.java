package trabalhofinalsdi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

class Logger {

    public enum LogType {
        INFO,
        WARNING,
        ERROR
    }

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
    private Path filePath;
    private Path filePathResults;

    public Logger(String file) {
        filePathResults = Paths.get("resultados.txt");
        filePath = Paths.get(file);
        File f = new File(file);
        if (!f.exists() && !f.isDirectory()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        f = new File("resultados.txt");
        if (!f.exists() && !f.isDirectory()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    
    void Log(String message, LogType type) {
        Log(message, type, false);
    }

    void Log(String message, LogType type, boolean newFile) {
        String tag;
        switch (type) {
            case INFO:
                tag = "[   INFO   ] -> ";
                break;
            case WARNING:
                tag = "[   WARN   ] -> ";
                break;
            case ERROR:
                tag = "[   ERRO   ] -> ";
                break;
            default:
                tag = "[   INFO   ] -> ";
                break;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(getTime());
        sb.append("]");
        sb.append(tag);
        sb.append(message);
        sb.append("\n");

        System.out.println(sb.toString());
        if (newFile) {
            try {
                Files.write(filePathResults, sb.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Files.write(filePath, sb.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
