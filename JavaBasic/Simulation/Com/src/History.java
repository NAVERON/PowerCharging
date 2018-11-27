/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.geometry.Point2D;


public class History {  //轨迹点
    
    private double x;
    private double y;
    private double rotation;
    private Point2D velocity;
    private double rudder;
    
    public History(){
    }
    
    public History(double rotation, Point2D velocity, double x, double y, double rudder){  //change for data simulation
        this.x = x;
        this.y = 600 - y;
        this.rotation = (rotation + 90)%360;  //这里超过了，需要重新归一
        this.velocity = velocity;
        this.rudder = rudder;
    }
    
    @Override
    public String toString(){  //course speed x y rudder speed_x speed_y
        return  rotation  + ","+ Math.sqrt( velocity.getX()*velocity.getX() + velocity.getY()*velocity.getY() ) + ","
                + x + "," + y + ","+ rudder + ","+ velocity.getX() + "," + velocity.getY();
    }
}
