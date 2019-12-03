import java.util.Arrays;
import java.util.LinkedList;

public class SelectParser {

    private String input;
    private String name;
    private String[] inputArr;
    private Relation relation;
    private LinkedList<Tuple> tuples = new LinkedList<>();
    SurlyDatabase database = SurlyDatabase.getInstance();


    public SelectParser(String input){
        this.input = input;
        inputArr = input.split(" ");
        this.name = inputArr[1];
    }


    public LinkedList<Tuple> selectTuples(){
        if(inputArr.length == 2){
            Relation rel = database.getRelation(inputArr[1]);
            return rel.getTuples();
        }
        else if(inputArr.length > 2 && inputArr[2].equals("WHERE")){
            String[] conditionSplit = input.split(" WHERE ");

            String[] statementsArr = conditionSplit[1].split(" OR ");


            relation = database.getRelation(this.name);

            for(int i=0; i<statementsArr.length; i++) {
                String[] conditionsArr = statementsArr[i].split(" AND ");
                Relation tempRel = new Relation("", relation.getTuples(), null);
                for (int j = 0; j < conditionsArr.length; j++) {
                    String[] condition = conditionsArr[j].split(" (?=([^\']*\'[^\']*\')*[^\']*$)");
                    String attribute = condition[0];
                    String operator = condition[1];
                    String value = condition[2];
                    value = value.replace("\'", "");

                    if(checkIfAttrInRel(attribute) == false){
                        return null;
                    }

                    RelationCondition rc = new RelationCondition(tempRel, attribute, operator, value);

                    tempRel = rc.reduceRelation();
                    if(tempRel == null){
                        return null;
                    }
                }



                for(int j=0; j<tempRel.getTuples().size(); j++){
                   tuples.add(tempRel.getTuples().get(j));
                }

            }
            return tuples;

        }
        else{
            System.out.println("INVALID SYNTAX");

            return null;
        }
    }

    public void createTempRel(String name, LinkedList<Tuple> tuples, LinkedList<Attribute> scheme){
        Relation tempRel = new Relation(name, tuples, scheme);
        database.createTempRelation(tempRel);
    }

    public boolean checkIfAttrInRel(String attr){
        for(int i=0; i<relation.getScheme().size(); i++){
            if(relation.getScheme().get(i).name.equals(attr)){
                return true;
            }
        }
        System.out.println("ATTRIBUTE " + attr + " NOT IN RELATION " + relation.getName());
        return false;
    }
}
