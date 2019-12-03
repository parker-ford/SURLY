
public class InsertParser {
    private int dex;
    private String relationName;
    private String[] attributes;
    private SurlyDatabase db;

    public InsertParser(String input, SurlyDatabase db){
        this.db = db;
        dex = 0;
        nextToken(input);
        relationName = nextToken(input);
        if(db.checkTemp(relationName) == true){
            System.out.println("CAN NOT INSERT INTO TEMP RELATIONS");
            return;
        }
        int tempDex = dex;
        int numAttribs = 0;
        while(nextToken(input) != null)
            numAttribs++;
        dex = tempDex;
        attributes = new String[numAttribs];
        for(int i = 0; i < numAttribs; i++)
        {
            attributes[i] = nextToken(input);
        }
        insertAttribs();
    }

    private void insertAttribs()
    {
        Relation rel = db.getRelation(relationName);
        if(rel != null)
        {
            Tuple tup = new Tuple();
            for(int i = 0; i < attributes.length; i++)
            {
                tup.values.add(new AttributeValue(rel.getScheme().get(i).getName(), attributes[i], rel.getScheme().get(i).getDataType()));
            }
            rel.insert(tup);
        }else
        {
            System.out.println("Error: relation " + relationName + " not found");
        }
    }

    private String nextToken(String input)
    {
        if(dex > input.length() - 1)
            return null;

        String toReturn = "";
        while(input.charAt(dex) == ' ' || input.charAt(dex) == '\n' && dex != input.length() - 1)
            dex++;

        boolean isQuotes = (input.charAt(dex) == '\'');

        if(isQuotes)
        {
            dex++;
            while(input.charAt(dex) != '\'' && dex < input.length() - 1)
            {
                toReturn += input.charAt(dex);
                dex++;
            }
        }else
        {
            while(input.charAt(dex) != ' ' && dex < input.length() - 1)
            {
                toReturn += input.charAt(dex);
                dex++;
            }
            if(dex == input.length() - 1)
                toReturn += input.charAt(dex);
        }
        dex++;
        return toReturn;
    }
}
