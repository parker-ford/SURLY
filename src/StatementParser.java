import java.util.LinkedList;

public class StatementParser {

    private String input;
    private String[] inputArr;
    private String name;
    private LinkedList<Tuple> tuples;
    private LinkedList<Attribute> scheme;
    SurlyDatabase database = SurlyDatabase.getInstance();


    public StatementParser(String input){
        this.input=input;
        this.inputArr = input.split(" ");
        parseTempName();
        parseStatement();
    }

    public void parseTempName(){
        this.name = inputArr[0];
    }

    public void parseStatement(){
        String[] statementSplit = input.split(" = ", 2);
        String statement = statementSplit[1];
        String[] statementArr = statement.split(" ");
        String str = statementArr[0];


        switch(str){
            case "SELECT":
               SelectParser sp = new SelectParser(statement);
               tuples = sp.selectTuples();
               if(tuples == null){
                   return;
               }
               sp.createTempRel(name, tuples, database.getRelation(inputArr[3]).getScheme());
               break;
            case "PROJECT":
                ProjectParser pp = new ProjectParser(statement);
                if(pp.getRelation() != null) {
                    scheme = pp.parseScheme();
                    if(scheme != null) {
                        tuples = pp.parseTuples();
                        pp.createTempRelation(name, tuples, scheme);
                    }
                }
                break;
            case "JOIN":
                JoinParser jp = new JoinParser(statement, statementSplit[0]);
                break;
        }



    }
}
