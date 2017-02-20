package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ua.pp.blastorq.planebattle.loader.data;

public class HP extends Sprite {
    private Plane assign;
    private int startWidth;
    private boolean fl_r;
    public HP(Texture texture, Plane a, boolean fl){
        super(texture);
        this.assign = a;
        this.fl_r = fl;
    }
    public void calculate(){
        if(this.fl_r){
            float r = data.vw - this.getX() - this.getWidth();
            this.setSize(this.startWidth*this.assign.getHp(), this.getHeight());
            this.setPosition(data.vw-r-this.getWidth(), this.getY());
        }else{
            this.setSize(this.startWidth*this.assign.getHp(), this.getHeight());
        }
    }
    public void render(){
        this.draw(data.batch);
    }
    public void setStartWidth(int t){this.startWidth=t;}
}