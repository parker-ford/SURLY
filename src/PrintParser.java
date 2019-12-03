import java.util.Arrays;

public class PrintParser {
    private String[] inputArr;

    public PrintParser(String input, SurlyDatabase db){
        this.inputArr = input.split(" ");
        String[] relations = parseRelationNames();
        for(int i = 0; i < relations.length; i++)
        {
            Relation rel = db.getRelation(relations[i]);

            if(rel == null)
            {
                System.out.println("Relation " + relations[i] + " not found in print command");
            }else
            {
                rel.print();
            }
        }
    }
    public String[] parseRelationNames(){
        String[] attr = Arrays.copyOfRange(inputArr, 1, inputArr.length);
        for(int i=0; i<attr.length-1; i++){
            StringBuilder sb = new StringBuilder(attr[i]);
            attr[i] = sb.deleteCharAt(sb.length()-1).toString();
        }
        return attr;
    }
}
