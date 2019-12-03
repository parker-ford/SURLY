import java.util.ArrayList;
import java.util.Collections;

public class DeleteParser {
    private String input;
    private String[] inputArr;
    private String name;
    private Relation relation;
    SurlyDatabase database = SurlyDatabase.getInstance();
    RelationCondition compare = new RelationCondition();

    public DeleteParser(String input){

        this.input = input;
        this.inputArr = input.split(" ");
        parseRelationName();

        if(database.checkTemp(this.name) == true){
            System.out.println("CAN NOT DELETE TEMP RELATIONS");
            return;
        }
        if(inputArr.length>2){
            boolean check = checkIfWhere();
            if(check == true){
                if(database.getRelation(this.name) != null) {
                    parseWhere();
                }
                else{
                    System.out.println("RELATION " + this.name +  " DOES NOT EXIST");
                }
            }
            else{
                System.out.println("INVALID DELETE");
            }
        }

        else{
            if(database.getRelation(this.name) != null) {
                this.relation = database.getRelation(this.name);
                relation.delete();
            }
            else {
                System.out.println("RELATION " + this.name + " DOES NOT EXIST");
            }
        }
    }
    public void parseRelationName(){
        this.name = inputArr[1];
    }

    public boolean checkIfWhere(){
        if(inputArr[2].equals("WHERE")){
            return true;
        }
        else{
            return false;
        }
    }

    public void parseWhere(){
         String[] splitArr = input.split(" WHERE ");
         //System.out.println(Arrays.toString(splitArr));

        /////////////////////////////////////////////////////////////
        //Getting relation to delete from and checks if relation exists
        parseRelationName();
        if(database.getRelation(this.name) != null) {
            this.relation = database.getRelation(this.name);

            String[] statementsArr = splitArr[1].split(" OR ");

            for(int i=0; i<statementsArr.length; i++){
                String[] conditionsArr = statementsArr[i].split(" AND ");
                Relation tempRel = new Relation("", relation.getTuples(), relation.getScheme());
                for( int j=0; j< conditionsArr.length; j++){
                    String[] condition = conditionsArr[j].split(" (?=([^\']*\'[^\']*\')*[^\']*$)");

                    String attribute = condition[0];
                    String operator = condition[1];
                    String value = condition[2];
                    value = value.replace("\'", "");

                    if(relation.checkIfAttribute(attribute)==false){
                        System.out.println("ATTRIBUTE " + attribute + " IS NOT VALID");
                        return;
                    }

                    RelationCondition rc = new RelationCondition(tempRel, attribute, operator, value);

                    tempRel = rc.reduceRelation();
                    if(tempRel==null){
                        return;
                    }
                }

                ArrayList<Integer> indexList = compare.getRelationIndex(relation,tempRel);
                Collections.sort(indexList, Collections.reverseOrder()); //This ensures that the relations will
                //be deleted from the end towards the beginning, preserving the indices of any future deletions

                for(int j = 0; j<indexList.size(); j++){
                    relation.deleteTuple(indexList.get(j));
                }

            }

        }
        /////////////////////////////////////////////////////////////


    }

}
