package io.github.some_example_name.Model;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class User {
    private String name;
    private String password;
    private int questionNumber;
    private String answer;
    private int avatarNumber;
    private int score = 0;
    private int kills = 0;
    private int survive = 0;
    private Image costumeAvatar;

    public User(String name, String password, int questionNumber, String answer,  int avatarNumber) {

        this.name = name;
        this.password = password;
        this.questionNumber = questionNumber;
        this.answer = answer;
        this.avatarNumber = avatarNumber;
    }

    public void addDB(){
        DatabaseManager db = DatabaseManager.getInstance();
        if(!name.equals("guest")){
            db.connect();
            db.insertUser(name, password, questionNumber, answer, avatarNumber, score, kills, survive);
            db.close();
        }
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getAnswer() {
        return answer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAvatarNumber() {
        return avatarNumber;
    }

    public void setAvatarNumber(int avatarNumber) {
        this.avatarNumber = avatarNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getSurvive() {
        return survive;
    }

    public void setSurvive(int survive) {
        this.survive = survive;
    }

    public Image getCostumeAvatar() {
        return costumeAvatar;
    }

    public void setCostumeAvatar(Image costumeAvatar) {
        this.costumeAvatar = costumeAvatar;
    }
}
