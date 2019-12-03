import java.util.ArrayList;
import java.util.LinkedList;

public class JoinRelationCondition {

  private Relation tempRel;
  private Relation tempRel2;
  private String attribute;
  private String operator;
  private String value;
  private LinkedList<Tuple> tuples;
  private LinkedList<Tuple> tuples2;
  private Relation relation;
  private int index1;
  private int index2;
  private String name;


  public JoinRelationCondition(Relation tempRel, Relation tempRel2, String attribute, String operator, String value, String name){
      this.tempRel = tempRel;
      this.tempRel2 = tempRel2;
      this.attribute = attribute;
      this.operator = operator;
      this.value = value;
      this.tuples = tempRel.getTuples();
      this.tuples2 = tempRel2.getTuples();
      this.name = name;
  }

  public Relation reduceRelation(){
    try{
      try{
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
      }catch(NumberFormatException e){
        System.out.println("ERROR: ATTRIBUTES NOT COMPARABLE WITH SPECIFIED OPERATOR\n");
      }
    }catch(IndexOutOfBoundsException e){
      return null;
    }

      return null;
  }

  private void assignIndices(){
    LinkedList<Attribute> scheme1 = (LinkedList) tempRel.getScheme().clone();
    LinkedList<Attribute> scheme2 = (LinkedList) tempRel2.getScheme().clone();

    if(attribute.indexOf('.') != -1){
      String[] attrArray = attribute.split("\\.");
      String relName = attrArray[0];
      attribute = attrArray[1];
      if(relName.equals(tempRel2.getName())){
        index1 = scheme1.size();
      }else if(relName.equals(tempRel.getName())){
        index1 = 0;
      }else{
        System.out.println("ERROR: NO SUCH RELATION " + relName + "\n");
        throw new IndexOutOfBoundsException("NO RELATION");
      }
    }
      while(index1 < scheme1.size()){
        if(attribute.equals(scheme1.get(index1).name)){
          break;
        }else{
          index1++;
        }
      }
      if(index1 == scheme1.size()){
        while(index1 - scheme1.size() < scheme2.size()){
          if(attribute.equals(scheme2.get(index1 - scheme1.size()).name)){
            break;
          }else{
            index1++;
          }
        }
        if(index1 == scheme1.size() + scheme2.size()){
          System.out.println("ERROR: NO SUCH ATTRIBUTE " + attribute + "\n");
          throw new IndexOutOfBoundsException("NO ATTRIBUTE");
        }
      }




      if(value.indexOf('.') != -1){
        String[] valArray = value.split("\\.");
        String relName = valArray[0];
        value = valArray[1];
        if(relName.equals(tempRel2.getName())){
          index2 = scheme1.size();
        }else if(relName.equals(tempRel.getName())){
          index2 = 0;
        }else{
          System.out.println("ERROR: NO SUCH RELATION " + relName + "\n");
          throw new IndexOutOfBoundsException("NO RELATION");
        }
      }
    while(index2 < scheme1.size()){
      if(value.equals(scheme1.get(index2).name)){
        break;
      }else{
        index2++;
      }
    }
    if(index2 == scheme1.size()){
      while(index2 - scheme1.size() < scheme2.size()){
        if(value.equals(scheme2.get(index2 - scheme1.size()).name)){
          break;
        }else{
          index2++;
        }
      }
      if(index2 == scheme1.size() + scheme2.size()){
        System.out.println("ERROR: NO SUCH ATTRIBUTE " + value + "\n");
        throw new IndexOutOfBoundsException("NO ATTRIBUTE");
      }
    }
  }

  private Relation conditionEquals(){
      LinkedList<Tuple> tupleList = new LinkedList<>();
      LinkedList<Attribute> scheme1 = (LinkedList) tempRel.getScheme().clone();
      LinkedList<Attribute> scheme2 = (LinkedList) tempRel2.getScheme().clone();
        assignIndices();

        LinkedList<Tuple> tupleList1 = tuples;
        LinkedList<Tuple> tupleList2 = tuples;
        if(index1 >= scheme1.size()){
          tupleList1 = tuples2;
          index1 -= scheme1.size();
        }
        if(index2 >= scheme1.size()){
          tupleList2 = tuples2;
          index2 -= scheme1.size();
        }

        for(int i = 0; i < tupleList1.size(); i++){
          Tuple tuple1 = tupleList1.get(i);
          AttributeValue attr1 = tuple1.values.get(index1);
          for(int j = 0; j < tupleList2.size(); j++){
            Tuple tuple2 = tupleList2.get(j);
            AttributeValue attr2 = tuple2.values.get(index2);
            if(attr1.value.equals(attr2.value)){
              Tuple toAdd = new Tuple();
              toAdd.values = (LinkedList<AttributeValue>) tuples.get(i).values.clone();
              toAdd.values.addAll(tuples2.get(j).values);
              tupleList.add(toAdd);
            }
          }
        }

        LinkedList<Attribute> newScheme = (LinkedList<Attribute>) scheme1.clone();
        newScheme.addAll(scheme2);
        return new Relation(name, tupleList, newScheme);
  }

  private Relation conditionDoesNotEqual(){
    LinkedList<Tuple> tupleList = new LinkedList<>();
    LinkedList<Attribute> scheme1 = (LinkedList) tempRel.getScheme().clone();
    LinkedList<Attribute> scheme2 = (LinkedList) tempRel2.getScheme().clone();
    assignIndices();

    LinkedList<Tuple> tupleList1 = tuples;
    LinkedList<Tuple> tupleList2 = tuples;
    if(index1 >= scheme1.size()){
      tupleList1 = tuples2;
      index1 -= scheme1.size();
    }
    if(index2 >= scheme1.size()){
      tupleList2 = tuples2;
      index2 -= scheme1.size();
    }

    for(int i = 0; i < tupleList1.size(); i++){
      Tuple tuple1 = tupleList1.get(i);
      AttributeValue attr1 = tuple1.values.get(index1);
      for(int j = 0; j < tupleList2.size(); j++){
        Tuple tuple2 = tupleList2.get(j);
        AttributeValue attr2 = tuple2.values.get(index2);
        if(!attr1.value.equals(attr2.value)){
          Tuple toAdd = new Tuple();
          toAdd.values = (LinkedList<AttributeValue>) tuple1.values.clone();
          toAdd.values.addAll(tuple2.values);
          tupleList.add(toAdd);
        }
      }
    }

    LinkedList<Attribute> newScheme = (LinkedList<Attribute>) scheme1.clone();
    newScheme.addAll(scheme2);
    return new Relation(name, tupleList, newScheme);
  }

  private Relation conditionLessThan(){
    LinkedList<Tuple> tupleList = new LinkedList<>();
    LinkedList<Attribute> scheme1 = (LinkedList) tempRel.getScheme().clone();
    LinkedList<Attribute> scheme2 = (LinkedList) tempRel2.getScheme().clone();
    assignIndices();

    LinkedList<Tuple> tupleList1 = tuples;
    LinkedList<Tuple> tupleList2 = tuples;
    if(index1 >= scheme1.size()){
      tupleList1 = tuples2;
      index1 -= scheme1.size();
    }
    if(index2 >= scheme1.size()){
      tupleList2 = tuples2;
      index2 -= scheme1.size();
    }

    for(int i = 0; i < tupleList1.size(); i++){
      Tuple tuple1 = tupleList1.get(i);
      AttributeValue attr1 = tuple1.values.get(index1);
      for(int j = 0; j < tupleList2.size(); j++){
        Tuple tuple2 = tupleList2.get(j);
        AttributeValue attr2 = tuple2.values.get(index2);
        if(Integer.parseInt(attr1.value) < Integer.parseInt(attr2.value)){
          Tuple toAdd = new Tuple();
          toAdd.values = (LinkedList<AttributeValue>) tuple1.values.clone();
          toAdd.values.addAll(tuple2.values);
          tupleList.add(toAdd);
        }
      }
    }

    LinkedList<Attribute> newScheme = (LinkedList<Attribute>) scheme1.clone();
    newScheme.addAll(scheme2);
    return new Relation(name, tupleList, newScheme);
  }

  private Relation conditionGreaterThan(){
    LinkedList<Tuple> tupleList = new LinkedList<>();
    LinkedList<Attribute> scheme1 = (LinkedList) tempRel.getScheme().clone();
    LinkedList<Attribute> scheme2 = (LinkedList) tempRel2.getScheme().clone();
    assignIndices();

    LinkedList<Tuple> tupleList1 = tuples;
    LinkedList<Tuple> tupleList2 = tuples;
    if(index1 >= scheme1.size()){
      tupleList1 = tuples2;
      index1 -= scheme1.size();
    }
    if(index2 >= scheme1.size()){
      tupleList2 = tuples2;
      index2 -= scheme1.size();
    }

    for(int i = 0; i < tupleList1.size(); i++){
      Tuple tuple1 = tupleList1.get(i);
      AttributeValue attr1 = tuple1.values.get(index1);
      for(int j = 0; j < tupleList2.size(); j++){
        Tuple tuple2 = tupleList2.get(j);
        AttributeValue attr2 = tuple2.values.get(index2);
        if(Integer.parseInt(attr1.value) > Integer.parseInt(attr2.value)){
          Tuple toAdd = new Tuple();
          toAdd.values = (LinkedList<AttributeValue>) tuple1.values.clone();
          toAdd.values.addAll(tuple2.values);
          tupleList.add(toAdd);
        }
      }
    }

    LinkedList<Attribute> newScheme = (LinkedList<Attribute>) scheme1.clone();
    newScheme.addAll(scheme2);
    return new Relation(name, tupleList, newScheme);
  }

  private Relation conditionGreaterThanOrEqual(){
    LinkedList<Tuple> tupleList = new LinkedList<>();
    LinkedList<Attribute> scheme1 = (LinkedList) tempRel.getScheme().clone();
    LinkedList<Attribute> scheme2 = (LinkedList) tempRel2.getScheme().clone();
    assignIndices();

    LinkedList<Tuple> tupleList1 = tuples;
    LinkedList<Tuple> tupleList2 = tuples;
    if(index1 >= scheme1.size()){
      tupleList1 = tuples2;
      index1 -= scheme1.size();
    }
    if(index2 >= scheme1.size()){
      tupleList2 = tuples2;
      index2 -= scheme1.size();
    }

    for(int i = 0; i < tupleList1.size(); i++){
      Tuple tuple1 = tupleList1.get(i);
      AttributeValue attr1 = tuple1.values.get(index1);
      for(int j = 0; j < tupleList2.size(); j++){
        Tuple tuple2 = tupleList2.get(j);
        AttributeValue attr2 = tuple2.values.get(index2);
        if(Integer.parseInt(attr1.value) >= Integer.parseInt(attr2.value)){
          Tuple toAdd = new Tuple();
          toAdd.values = (LinkedList<AttributeValue>) tuple1.values.clone();
          toAdd.values.addAll(tuple2.values);
          tupleList.add(toAdd);
        }
      }
    }

    LinkedList<Attribute> newScheme = (LinkedList<Attribute>) scheme1.clone();
    newScheme.addAll(scheme2);
    return new Relation(name, tupleList, newScheme);
  }

  private Relation conditionLessThanOrEqual(){
    LinkedList<Tuple> tupleList = new LinkedList<>();
    LinkedList<Attribute> scheme1 = (LinkedList) tempRel.getScheme().clone();
    LinkedList<Attribute> scheme2 = (LinkedList) tempRel2.getScheme().clone();
    assignIndices();

    LinkedList<Tuple> tupleList1 = tuples;
    LinkedList<Tuple> tupleList2 = tuples;
    if(index1 >= scheme1.size()){
      tupleList1 = tuples2;
      index1 -= scheme1.size();
    }
    if(index2 >= scheme1.size()){
      tupleList2 = tuples2;
      index2 -= scheme1.size();
    }

    for(int i = 0; i < tupleList1.size(); i++){
      Tuple tuple1 = tupleList1.get(i);
      AttributeValue attr1 = tuple1.values.get(index1);
      for(int j = 0; j < tupleList2.size(); j++){
        Tuple tuple2 = tupleList2.get(j);
        AttributeValue attr2 = tuple2.values.get(index2);
        if(Integer.parseInt(attr1.value) <= Integer.parseInt(attr2.value)){
          Tuple toAdd = new Tuple();
          toAdd.values = (LinkedList<AttributeValue>) tuple1.values.clone();
          toAdd.values.addAll(tuple2.values);
          tupleList.add(toAdd);
        }
      }
    }

    LinkedList<Attribute> newScheme = (LinkedList<Attribute>) scheme1.clone();
    newScheme.addAll(scheme2);
    return new Relation(name, tupleList, newScheme);
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
