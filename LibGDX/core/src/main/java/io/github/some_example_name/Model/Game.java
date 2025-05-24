package io.github.some_example_name.Model;

public class Game {
    private int time;
    private String hero;
    private String gun;
    private boolean autoReload;
    private boolean autoAim;


    public Game(String hero,String gun,String time) {
        if(time.contains("20")){
            this.time = 20;
        } else if(time.contains("10")){
            this.time = 10;
        } else if(time.contains("5")){
            this.time = 5;
        } else{
            this.time = 2;
        }
        this.hero = hero;
        this.gun = gun;
        this.autoReload = App.isAutoReload();
        this.autoAim = true;
    }


    public int getTime() {
        return time;
    }

    public String getHero() {
        return hero;
    }

    public String getGun() {
        return gun;
    }
}
