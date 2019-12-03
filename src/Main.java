import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{
        LexicalAnalyzer surly = new LexicalAnalyzer();
        surly.run(args[0]);
    }
}
