package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import World.Camera;
import main.Game;
import main.Sons;

public class Esfera extends Entity{
	private static BufferedImage[] sprites, carregado;
	public int frames, maxFrames = 10, index = 0;
	public double dx, dy;
	private double spd;
	public boolean colidindo = false, som = false;
	public int life = 1, curlife = 0;
	
	public Esfera(int x, int y, int width, int height, BufferedImage sprite, double dx2, double dy2) {
		super(x, y, width, height, sprite);
		this.dx = dx2;
		this.dy = dy2;
		sprites = new BufferedImage[4];
		carregado = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			carregado[i] = Game.spritesheet.getSprite((3+i)*Game.TS, 2*Game.TS, Game.TS, Game.TS);
		}
		sprites[0] = Game.spritesheet.getSprite(4*Game.TS, 1*Game.TS, Game.TS, Game.TS);
		sprites[1] = Game.spritesheet.getSprite(0*Game.TS, 2*Game.TS, Game.TS, Game.TS);
		sprites[2] = Game.spritesheet.getSprite(1*Game.TS, 2*Game.TS, Game.TS, Game.TS);
		sprites[3] = Game.spritesheet.getSprite(2*Game.TS, 2*Game.TS, Game.TS, Game.TS);
	}
	public void tick() {
		if (!colidindo) {
			x+= dx*spd;
			y+= dy*spd;
		}
		frames++;
		if (frames >= maxFrames) {
			if (life >= 30) {
				index++;
			}
			frames = 0;
			curlife++;
			if (curlife >= life) {//aumentar a life quanto mais eu seguro o espa�o
				Sons.esferaDestruida.play();
				Game.disparos.remove(this);
			}
			if(index > 3) {
				index=0;
			}
		}
	}
	public void render(Graphics g) {
		if (life <= 10) {
			g.drawImage(sprites[0], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(Weapon.atirando)
			setMask(6, 6, 4, 4);
			spd = 1.7;
		}else if (life <= 20) {
			g.drawImage(sprites[1], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(Weapon.atirando)
			setMask(6, 5, 5, 5);
			spd = 1.4;
		}else if (life <= 30) {
			g.drawImage(sprites[2], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(Weapon.atirando)
			this.setMask(4, 3, 8, 8);
			spd = 0.8;
		}else {
			g.drawImage(sprites[3], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(Weapon.atirando && !som) {
				som = true;
				Sons.esferaMaxima.play();
				this.setMask(2, 2, 12, 12);
			}
			g.drawImage(carregado[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			spd = 0.5;
		}
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + this.maskx - Camera.x, this.getY() + this.masky - Camera.y, mwidth, mheight);
	}
}
