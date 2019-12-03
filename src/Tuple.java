import java.util.LinkedList;

public class Tuple {
    public LinkedList<AttributeValue> values = new LinkedList<AttributeValue>();


    public boolean equals(Tuple tuple2){
        if(values.equals(tuple2.values)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean equalValues(Tuple tuple2){

        boolean check = true;
        for(int i=0; i<values.size(); i++){
            if(values.get(i).value.equals(tuple2.values.get(i).value)){
                check = true;
            }
            else{
                check = false;
            }
            if(check == false){
                return false;
            }
        }

        return true;

    }
}
