
import java.util.*;

public class RelationParser {
    private String[] inputArr;
    private String input;
    private int check;
    private String name;
    private int attrNum;
    private LinkedList<Attribute> scheme;
    SurlyDatabase database = SurlyDatabase.getInstance();

    public RelationParser(String input){
        this.input = input;
        this.inputArr = input.split(" ");
        parseRelationName();
        check = parseAttributeCount();
        if(check == -1){
            System.out.println("INVALID RELATION");
        }
        else {
            parseRelation();
        }
        if(!this.name.equals("CATALOG")) {
            new InsertParser("INSERT CATALOG " + this.name + " " + this.attrNum, database);
        }
    }


    public Relation parseRelation(){


        if(database.getRelation(this.name)==null) {
            Relation relation = new Relation(this.name, this.scheme);
            database.createRelation(relation);
        }
        else{
            System.out.println("RELATION " + this.name + " ALREADY EXISTS");
        }


        return null;
    }



    public void parseRelationName(){

        this.name =  inputArr[1];
    }



    public int parseAttributeCount(){

        /////////////////////////////////////////////////////////////
        //Creating String Builder for checking attributes
        String[] attrArr = input.split("\\(");
        StringBuilder sb = new StringBuilder(attrArr[1]);
        String relAttr = sb.deleteCharAt(sb.length()-1).toString();
        /////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////
        //Creating string to check if relation attributes are inside
        //parenthesis
        String attr = "";
        for(int i=2; i<inputArr.length; i++){
            attr += inputArr[i];
        }
        /////////////////////////////////////////////////////////////

        if(attr.charAt(0)!='(' || attr.charAt(attr.length()-1) !=')'){
            return -1; //not inside parenthesis
        }
        else if(relationChecker(relAttr)==false){
            return -1; //attributes not formatted correctly
        }
        else{
            createScheme(relAttr);
        }
        return attrNum;
    }

    public boolean relationChecker(String relation){
        relation=relation.replace("", "");
        String[] relationArr = relation.split(", ");
        this.attrNum = relationArr.length;
        for(int i=0; i<relationArr.length; i++){
            String[] attribute = relationArr[i].split(" ");
            attribute = arrFix(attribute);
            if(attribute.length != 3){
                System.out.println("INCORRECT ATTRIBUTE 1");
                return false;
            }
            if(!attribute[1].equals("NUM") && !attribute[1].equals("CHAR")){
                System.out.println("INCORRECT ATTRIBUTE 2");
                return false;
            }
            if(isInteger(attribute[2])==false){
                System.out.println("INCORRECT ATTRIBUTE 3");
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try
        {
            Integer.parseInt(s);

            // s is a valid integer

            isValidInteger = true;
        }
        catch (NumberFormatException ex)
        {
            // s is not an integer
        }

        return isValidInteger;
    }

    private void createScheme(String attributes){

        String[] attributeArr = attributes.split(", ");
        LinkedList<Attribute> scheme = new LinkedList<>();

        for(int i=0; i<attributeArr.length; i++){
            String[] attribute = attributeArr[i].split(" ");
            attribute = arrFix(attribute);
            Attribute attr = new Attribute(attribute[0], attribute[1], Integer.parseInt(attribute[2]));
            scheme.add(attr);
        }
        this.scheme=scheme;
    }

    private void print(String attr, int attrNum){
        if(attrNum == -1){
            System.out.println("INVALID RELATION");
        }
        else{

            System.out.println("Creating " + attr + " with " + attrNum + " attributes.");
        }
    }

    private String[] arrFix(String[] arr){
        List<String> list = new ArrayList<String>(Arrays.asList(arr));
        list.removeAll(Arrays.asList(""));
        arr = list.toArray(new String[0]);

        return arr;
    }

}
