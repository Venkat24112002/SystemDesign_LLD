package LoggerChainResponsibiltyDesignPattern;

abstract class LogProcessor {

    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    LogProcessor nextLogProcessor;

    public LogProcessor(LogProcessor nextLogProcessor){
        this.nextLogProcessor = nextLogProcessor;
    }

    public void log(int logLevel, String messagge) {
        if(nextLogProcessor != null){
            nextLogProcessor.log(logLevel, messagge);
        }
    }
}

class InfoLogProceessor extends LogProcessor {

    public InfoLogProceessor(LogProcessor nextLogProcessor){
        super(nextLogProcessor);
    }

    @Override
    public void log(int logLevel, String message){
        if(logLevel == 1){
            System.out.println("INFO" + message);
        }
        else {
            super.log(logLevel,message);
        }
    }
}

class DebugLogProceessor extends LogProcessor {

    public DebugLogProceessor(LogProcessor nextLogProcessor){
        super(nextLogProcessor);
    }

    public void log(int logLevel, String message){
        if(logLevel == 2){
            System.out.println("DEBUG" + message);
        }
        else {
            super.log(logLevel,message);
        }
    }
}

class ErrorLogProceessor extends LogProcessor {
    public ErrorLogProceessor(LogProcessor nextLogProcessor){
        super(nextLogProcessor);
    }

    public void log(int logLevel, String message){
        if(logLevel == 3){
            System.out.println("ERROR" + message);
        }
        else {
            super.log(logLevel,message);
        }
    }
}




public class Logger {

    public static void main(String args[]) {
        LogProcessor logProcessor = new ErrorLogProceessor(new InfoLogProceessor(new DebugLogProceessor(null)));
        logProcessor.log(2,"Debug levell logg");
//    logProcessor.log(2,"this is Info");
    }
}
