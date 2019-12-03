import java.util.LinkedList;

public class SurlyDatabase {
    public LinkedList<Relation> relations = new LinkedList<Relation>();
    public LinkedList<Relation> tempRelations = new LinkedList<Relation>();

    private static SurlyDatabase instance = null;

    private SurlyDatabase(){};

    public static SurlyDatabase getInstance() {
        if (instance == null){
            instance = new SurlyDatabase();
        }
        return instance;
    }

    public Relation getRelation (String name){
        for(int i=0; i<relations.size(); i++){
            Relation relation = relations.get(i);
            if(relation.getName().equals(name)){
                return relation;
            }
        }
        for(int i=0; i<tempRelations.size(); i++){
            Relation relation = tempRelations.get(i);
            if(relation.getName().equals(name)){
                return relation;
            }
        }
        return null;
    }

    public void destroyRelation(String name){
        System.out.println("destroyed " + name);
        relations.remove(getRelation(name));
    }
    private void destroyTempRelation(String name){
        tempRelations.remove(getRelation(name));
    }

    public void createRelation (Relation relation){
        relations.add(relation);

        for(int i=0; i<tempRelations.size(); i++){
            if(relation.getName().equals(tempRelations.get(i).getName())){
                tempRelations.remove(i);
            }
        }
    }

    public void createTempRelation(Relation tempRel){

        boolean check = false;

        for(int i=0; i<relations.size(); i++){
            if(relations.get(i).getName().equals(tempRel.getName())){
                check = true;
            }
        }

        if(check == false) {
            if(checkTemp(tempRel.getName())==true){
                destroyTempRelation(tempRel.getName());
            }
            tempRelations.add(tempRel);
        }
        else{
            System.out.println("CAN NOT CREATE TEMP RELATION WITH SAME NAME AS RELATION");
        }
    }

    public boolean checkTemp(String name){
        for(int i=0; i<tempRelations.size(); i++){
            if(tempRelations.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
