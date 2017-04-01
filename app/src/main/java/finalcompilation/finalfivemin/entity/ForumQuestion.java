package finalcompilation.finalfivemin.entity;

/**
 * Created by Denise on 23-Mar-17.
 */

public class ForumQuestion {
    private int qnsID;
    private int askerID;
    private int catID;
    private String qns;
    //private ArrayList<ForumAnswer> ansList = new ArrayList<ForumAnswer>();

    public ForumQuestion(int qnsID, int askerID, String qns, int catID){
        this.qnsID = qnsID;
        this.askerID = askerID;
        this.qns = qns;
        this.catID = catID;
    }

    public ForumQuestion(int askerID, String qns, int catID){
        this.askerID = askerID;
        this.qns = qns;
        this.catID = catID;
    }

    public int getCatID(){return catID;}

    public void setQnsID(int id){qnsID = id;}
    public int getQnsID(){return qnsID;}

    public void setAskerID(int id){askerID = id;}
    public int getAskerID(){return askerID;}

    public void setQns(String qns){this.qns = qns;}
    public String getQns(){return qns;}

}


