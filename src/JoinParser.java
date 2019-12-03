import java.util.Arrays;
import java.util.LinkedList;
public class JoinParser {

  private String input;
  private String name;
  private String[] inputArr;
  private LinkedList<Attribute> newScheme = new LinkedList<Attribute>();
  private Relation rel1;
  private Relation rel2;
  private String syntaxError = "ERROR: Incorrect JOIN syntax";
  private String relationError = "ERROR: Relation not found: ";
  SurlyDatabase database = SurlyDatabase.getInstance();

  public JoinParser(String input, String name){
      this.name = name;
      this.input = input;
      this.inputArr = input.split(" ");
      parseRelations();
  }

  private void parseRelations(){
    String r1 = inputArr[1];
    String r2 = inputArr[2];
    if(r1.indexOf(',') == -1){
      System.out.println(syntaxError);
      return;
    }else{
      r1 = r1.substring(0, r1.length() - 1);
    }
    rel1 = database.getRelation(r1);
    rel2 = database.getRelation(r2);
    if(rel1 == null){
      System.out.println(relationError + r1);
      return;
    }
    if(rel2 == null){
      System.out.println(relationError + r2);
      return;
    }

    JoinRelationCondition jr = new JoinRelationCondition(rel1, rel2, inputArr[4],inputArr[5],inputArr[6], name);
    jr.reduceRelation();
  }
}
