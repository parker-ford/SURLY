import java.util.ArrayList;
import java.util.LinkedList;

public class RelationCondition {

    private Relation tempRel;
    private String attribute;
    private String operator;
    private String value;
    private LinkedList<Tuple> tuples;


    public RelationCondition(){}


    public RelationCondition(Relation tempRel, String attribute, String operator, String value){
        this.tempRel = tempRel;
        this.attribute = attribute;
        this.operator = operator;
        this.value = value;
        this.tuples = tempRel.getTuples();
    }

    public Relation reduceRelation(){
        if(operator.equals("=")){
            return conditionEquals();
        }
        else if(operator.equals("!=")){
            return conditionDoesNotEqual();
        }
        else if(operator.equals("<")){
            return conditionLessThan();
        }
        else if(operator.equals(">")){
            return conditionGreaterThan();
        }
        else if(operator.equals("<=")){
            return conditionLessThanOrEqual();
        }
        else if(operator.equals(">=")){
            return conditionGreaterThanOrEqual();
        }
        else{
            System.out.println("INVALID SYNTAX");
        }

        return null;
    }

    private Relation conditionEquals(){
        LinkedList<Tuple> tupleList = new LinkedList<>();
        for(int i=0; i<tuples.size(); i++){
            Tuple tuple = tuples.get(i);
            for(int j=0; j<tuple.values.size(); j++){
                AttributeValue attrVal = tuple.values.get(j);
                if(attrVal.name.equals(attribute) && attrVal.value.equals(value)){
                    tupleList.add(tuple);
                }
            }
        }
        return new Relation("", tupleList, null);
    }

    private Relation conditionDoesNotEqual(){
        LinkedList<Tuple> tupleList = new LinkedList<>();
        for(int i=0; i<tuples.size(); i++){
            Tuple tuple = tuples.get(i);
            for(int j=0; j<tuple.values.size(); j++){
                AttributeValue attrVal = tuple.values.get(j);
                if(attrVal.name.equals(attribute) && !attrVal.value.equals(value)){
                    tupleList.add(tuple);
                }
            }
        }
        return new Relation("", tupleList, null);
    }

    private Relation conditionLessThan(){
        LinkedList<Tuple> tupleList = new LinkedList<>();
        for(int i=0; i<tuples.size(); i++){
            Tuple tuple = tuples.get(i);
            for(int j=0; j<tuple.values.size(); j++){
                AttributeValue attrVal = tuple.values.get(j);
                if(!attrVal.type.equals("NUM")){
                    System.out.println(operator + " CAN NOT BE APPLIED TO " + attrVal.name);
                    return null;
                }
                if(attrVal.name.equals(attribute) && Integer.parseInt(attrVal.value) < Integer.parseInt(value)){
                    tupleList.add(tuple);
                }else{
                }
            }
        }
        return new Relation("", tupleList, null);
    }

    private Relation conditionGreaterThan(){
        LinkedList<Tuple> tupleList = new LinkedList<>();
        for(int i=0; i<tuples.size(); i++){
            Tuple tuple = tuples.get(i);
            for(int j=0; j<tuple.values.size(); j++){
                AttributeValue attrVal = tuple.values.get(j);
                if(!attrVal.type.equals("NUM")){
                    System.out.println(operator + " CAN NOT BE APPLIED TO " + attrVal.name);
                    return null;
                }
                if(attrVal.name.equals(attribute) && Integer.parseInt(attrVal.value) > Integer.parseInt(value)){
                    tupleList.add(tuple);
                }
            }
        }
        return new Relation("", tupleList, null);
    }

    private Relation conditionGreaterThanOrEqual(){
        LinkedList<Tuple> tupleList = new LinkedList<>();
        for(int i=0; i<tuples.size(); i++){
            Tuple tuple = tuples.get(i);
            for(int j=0; j<tuple.values.size(); j++){
                AttributeValue attrVal = tuple.values.get(j);
                if(!attrVal.type.equals("NUM")){
                    System.out.println(operator + " CAN NOT BE APPLIED TO " + attrVal.name);
                    return null;
                }
                if(attrVal.name.equals(attribute) && Integer.parseInt(attrVal.value) >= Integer.parseInt(value)){
                    tupleList.add(tuple);
                }
            }
        }
        return new Relation("", tupleList, null);
    }

    private Relation conditionLessThanOrEqual(){
        LinkedList<Tuple> tupleList = new LinkedList<>();
        for(int i=0; i<tuples.size(); i++){
            Tuple tuple = tuples.get(i);
            for(int j=0; j<tuple.values.size(); j++){
                AttributeValue attrVal = tuple.values.get(j);
                if(!attrVal.type.equals("NUM")){
                    System.out.println(operator + " CAN NOT BE APPLIED TO " + attrVal.name);
                    return null;
                }
                if(attrVal.name.equals(attribute) && Integer.parseInt(attrVal.value) <= Integer.parseInt(value)){
                    tupleList.add(tuple);
                }
            }
        }
        return new Relation("", tupleList, null);
    }

    public ArrayList<Integer> getRelationIndex(Relation relation, Relation tempRel){
        ArrayList<Integer> indexList = new ArrayList<>();

        for(Integer i=0; i<relation.getTuples().size(); i++){
            for(int j=0; j<tempRel.getTuples().size(); j++){
                if(relation.getTuples().get(i).equals(tempRel.getTuples().get(j))){
                    indexList.add(i);
                }
            }
        }

        return indexList;
    }

}
