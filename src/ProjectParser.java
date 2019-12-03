import java.util.LinkedList;

public class ProjectParser {

    private String input;
    private String[] inputArr;
    SurlyDatabase database = SurlyDatabase.getInstance();
    private LinkedList<Attribute> scheme = new LinkedList<>();
    private Relation relation;
    private String[] attrArr;
    private LinkedList<Tuple> tuples = new LinkedList<>();

    public ProjectParser(String input){
        this.input = input;
        this.inputArr = input.split(" ");

        String statement = input.split(" ", 2)[1];

        String attributes = statement.split(" FROM ")[0];
        this.relation = database.getRelation(statement.split(" FROM ")[1]);
        if(relation == null){
            System.out.println("RELATION DOES NOT EXIST");
        }

        this.attrArr = attributes.split(", ");


    }

    public LinkedList<Attribute> parseScheme(){
        for(int i=0; i<attrArr.length; i++){
            boolean attrCheck = false;
            for(int j=0; j<relation.getScheme().size(); j++){
                if(attrArr[i].equals(relation.getScheme().get(j).getName())){
                    scheme.add(relation.getScheme().get(j));
                    attrCheck = true;
                }
            }
            if(attrCheck == false){
                System.out.println("INVALID ATTRIBUTE");
                return null;

            }
        }
        return scheme;
    }

    public LinkedList<Tuple> parseTuples(){


        for(int i=0; i<relation.getTuples().size(); i++){
            Tuple tuple = new Tuple();
            for(int j=0; j<attrArr.length; j++){
                for(int k=0; k<relation.getTuples().get(i).values.size(); k++){
                    if(relation.getTuples().get(i).values.get(k).name.equals(attrArr[j])){
                        tuple.values.add(relation.getTuples().get(i).values.get(k));
                    }
                }
            }
            boolean skipAdd = false;
            for(int j=0; j<tuples.size(); j++){
                if(tuples.get(j).equalValues(tuple) == true){
                    skipAdd=true;
                }
            }
            if(skipAdd == false) {
                tuples.add(tuple);
            }
        }


        return tuples;
    }

    public void createTempRelation(String name, LinkedList<Tuple> tuples, LinkedList<Attribute> scheme){
        Relation tempRel = new Relation(name, tuples, scheme);
        database.createTempRelation(tempRel);
    }

    public Relation getRelation(){
        return this.relation;
    }


}
