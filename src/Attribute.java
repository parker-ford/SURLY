public class Attribute {
    public String name;
    private String dataType;
    private int length;

    public Attribute(String name, String dataType, int length){
        this.name = name;
        this.dataType = dataType;
        this.length = length;
    }
    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public int getLength() {
        return length;
    }
}
