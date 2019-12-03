import java.util.LinkedList;

public class Relation {
    private String name;
    private LinkedList<Attribute> scheme = new LinkedList<Attribute>();
    private LinkedList<Tuple> tuples = new LinkedList<Tuple>();
    SurlyDatabase database = SurlyDatabase.getInstance();

    public Relation(String name, LinkedList<Attribute> scheme){
        this.name = name;
        this.scheme = scheme;
    }

    public Relation(String name, LinkedList<Tuple> tuples, LinkedList<Attribute> scheme){
        this.name = name;
        this.scheme = scheme;
        this.tuples = tuples;
        database.createTempRelation(this);
    }

    public void print(){
        System.out.println("\n  RELATION: " + name);
        int widths[] = new int[scheme.size()];
        int totalWidth = 0;

        for(int i = 0; i < tuples.size(); i++)
        {
            Tuple tup = tuples.get(i);
            for(int j = 0; j < tup.values.size(); j++)
            {
              AttributeValue attr = tup.values.get(j);
              if(widths[j] < attr.value.length())
                widths[j] = attr.value.length();
            }
        }
        for(int i = 0; i < scheme.size(); i++)
        {
            Attribute attr = scheme.get(i);
            if(widths[i] < attr.name.length())
              widths[i] = attr.name.length();
        }

        for(int i = 0; i < widths.length; i++)
        {
          totalWidth += widths[i] + 4;
        }
        System.out.print(" ░▒");
        for(int i = 0; i < totalWidth; i++)
        {
          System.out.print("▒");
        }
        System.out.print("\n ░▒");
        for(int i = 0; i < scheme.size(); i++)
        {
            Attribute attr = scheme.get(i);
            System.out.format(" %" + widths[i] + "s ░▒", attr.name);
        }
        System.out.println();
        System.out.print(" ░▒");
        for(int i = 0; i < totalWidth; i++)
        {
          System.out.print("▒");
        }
        for(int i = 0; i < tuples.size(); i++)
        {
            System.out.print("\n ░▒");
            Tuple tup = tuples.get(i);
            for(int j = 0; j < tup.values.size(); j++)
            {
                AttributeValue attr = tup.values.get(j);
                System.out.format(" %" +  widths[j] + "s ░▒", attr.value);
            }
        }
        System.out.println();
        System.out.print(" ░▒");
        for(int i = 0; i < totalWidth; i++)
        {
          System.out.print("▒");
        }

        System.out.println();
    }

    public void insert(Tuple tuple) {
        tuples.add(tuple);
        System.out.println("Inserted tuple into " + name + " new size: " + tuples.size());
    }

    public void delete(){
        this.tuples.clear();
    }

    public String getName(){
        return this.name;
    }

    public LinkedList<Attribute> getScheme() {
        return scheme;
    }
    public void printScheme(){
        System.out.print("PRINTING SCHEME :  ");
        for(int i=0; i<scheme.size(); i++){
            System.out.print(scheme.get(i).getName() + "  |  ");
        }
        System.out.println();
    }
    public LinkedList<Tuple> getTuples() {
        return tuples;
    }

    public void deleteTuple(int index){
        this.tuples.remove(index);
    }

    public boolean checkIfAttribute(String attr){
        for(int i=0; i<scheme.size(); i++){
            if(scheme.get(i).name.equals(attr)){
                return true;
            }

        }
        return false;
    }

}
