package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import main.Game;
import world.Camera;
import world.Mundo;
import world.Node;
import world.Vector2i;

public class Entity {
	

	protected double x, x1;
	protected double y, y1;
	protected int width;
	protected int height;
	protected double speed, gravity;
	
	protected int mx, my, mheight, mwidth;
	
	public int depth, queda;

	protected List<Node> path;
	
	public boolean debug = false;
	
	protected BufferedImage sprite;
	
	public static Random rand = new Random();
	
	public Entity(double x,double y,int width,int height,double speed,BufferedImage sprite){
		this.x = x;
		this.x1 = x;
		this.y = y;
		this.y1 = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public void setMask(int mx, int my, int mwidth, int mheight) {
		this.mx = mx;
		this.my = my;
		this.mheight = mheight;
		this.mwidth = mwidth;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - Game.WIDTH/2,0,Mundo.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - Game.HEIGHT/2,0,Mundo.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void setX(int newX) {
		this.x = newX;
		this.x1 = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
		this.y1 = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick(){}
	
	public double distancia(int x1,int y1,int x2,int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16) {
					x+=speed;
				}else if(x > target.x * 16) {
					x-=speed;
				}
				
				if(y < target.y * 16) {
					y+=speed;
				}else if(y > target.y * 16) {
					y-=speed;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
				
			}
		}
	}
	
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.mx,e1.getY()+e1.my,e1.getWidth()-e1.mwidth,e1.getHeight()-e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.mx,e2.getY()+e2.my,e2.getWidth()-e2.mwidth,e1.getHeight()-e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		//g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
		g.setColor(Color.red);
		g.fillRect(this.getX() + mx - Camera.x,this.getY() + my - Camera.y,mwidth,mheight);
	}

	public void restart() {
		setX((int)x1);
		setY((int) y1);
	}
	
}
