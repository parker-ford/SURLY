import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    SurlyDatabase database = SurlyDatabase.getInstance();

    public void run(String fileName) throws IOException {

        //Creating Catalog
        new RelationParser("RELATION CATALOG (RELATION CHAR 15, ATTRIBUTES NUM 15)");
        //


        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        String str;
        String str2 = "";
        while (sc.hasNextLine()){
            str = sc.nextLine();
            if(str.startsWith("#")){
                continue;
            }
            else{
                str2 += str;
            }

        }
        sc = new Scanner(str2);
        sc.useDelimiter(Pattern.compile(";"));

        while(sc.hasNext()){
            str = sc.next();
            handleLine(str);
        }

    }

    private void handleLine(String line){
        String[] strArr = line.split(" ", 3);
        String str = strArr[0];

        switch(str) {
            case "RELATION":
                new RelationParser(line);
                break;
            case "INSERT":
                new InsertParser(line, database);
                break;
            case "PRINT":
                new PrintParser(line, database);
                break;
            case "DESTROY":
                new DestroyParser(line);
                break;
            case "DELETE":
                new DeleteParser(line);
                break;
            default:
                if(strArr[1].equals("=")){
                    new StatementParser(line);
                }else{
                  System.out.println("INVALID COMMAND");
                }
        }

    }
}
