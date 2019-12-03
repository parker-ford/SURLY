public class DestroyParser {
    private String input;
    private String[] inputArr;
    private String name;
    SurlyDatabase database = SurlyDatabase.getInstance();

    public DestroyParser(String input){
        this.input = input;
        this.inputArr = input.split(" ");

        if(inputArr.length > 2){
            System.out.println("INVALID DESTROY");
        }
        else{
            parseRelationName();
            if(database.checkTemp(this.name) == true){
                System.out.println("CAN NOT DESTROY TEMP RELATIONS");
                return;
            }
            if(database.getRelation(this.name)!=null) {
                database.destroyRelation(this.name);
            }
            else{
                System.out.println("RELATION " + this.name + " DOES NOT EXIST");
            }
        }

    }
    public void parseRelationName(){
        this.name = inputArr[1];
    }
}
