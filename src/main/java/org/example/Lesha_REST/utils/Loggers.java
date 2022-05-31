package org.example.Lesha_REST.utils;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class Loggers {
    private static FileHandler fileHandler = null;
    private static SimpleFormatter simpleFormatter = new SimpleFormatter(){
        private static final String format = "[%1$tF %1$tT] [%2$-7s] [%3$s] %4$s %n";

        @Override
        public synchronized String format(LogRecord lr) {
            return String.format(format,
                    new Date(lr.getMillis()),
                    lr.getLevel().getLocalizedName(),
                    lr.getLoggerName(),
                    lr.getMessage()
            );
        }
    };

    public static Logger getLogger(String name){
        Logger logger = Logger.getLogger(name);
        logger.setLevel(Level.ALL);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);

        if(fileHandler == null){
            return logger;
        }

        logger.addHandler(fileHandler);

        return logger;
    }
    public static void createFileHandler(String path){
        try {
            fileHandler = new FileHandler(path, true);
            fileHandler.setFormatter(simpleFormatter);
        } catch (IOException e) {
            getLogger("LOGGERS").log(Level.WARNING, "Error create logfile "+path);
        }
        catch (IllegalArgumentException e){
            getLogger("LOGGERS").log(Level.WARNING, "Bad logfile path " +path);
        }
    }

}
