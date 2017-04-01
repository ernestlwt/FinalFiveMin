package finalcompilation.finalfivemin.entity;

/**
 * Created by Denise on 23-Mar-17.
 */
public class ForumAnswer {
    private int ansID;
    private int answererID;
    private String ans;
    private int qnsID;

    public ForumAnswer(int ansID, int answererID, String ans){
        this.ansID = ansID;
        this.answererID = answererID;
        this.ans = ans;
    }

    public ForumAnswer(int answererID, String ans, int qnsID){
        this.answererID = answererID;
        this.ans = ans;
        this.qnsID = qnsID;
    }


    public int getQnsID(){return qnsID;}
    public int getAnswererID(){return answererID;}

    public String getAns(){ return ans;}

}

