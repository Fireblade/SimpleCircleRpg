package mclama.com.entity;

import org.newdawn.slick.opengl.Texture;
import static mclama.com.util.DebugGlobals.*;
import static mclama.com.util.Artist.*;
import static org.lwjgl.opengl.GL11.*;

public class Entity {
	
	protected int id, level;
	protected double x, y;
	protected float size=1;
	
	protected double health = 10;
	
	protected int width, height;
	protected Texture texture;
	
	protected boolean alive=false;
	protected boolean hasDied=true;
	protected boolean moveToLoc=false;
	
	protected double moveX, moveY;
	
	
	
	public void draw(){
		
		if(texture!=null){
			DrawQuadTex(texture, x, y, width, height);
		}// else texture = QuickLoad(texName);
		
		if(moveToLoc && D_PlayerShowMoveToLine){
			glTranslatef(camX,camY,0);
			glDisable(GL_TEXTURE_2D);
			glColor4f(1f,0f,0f,1f);
			glBegin(GL_LINES);
				glVertex2d(x,y);
				glVertex2d(moveX, moveY);
			glEnd();
			glLoadIdentity();
			glColor4f(1f,1f,1f,1f);
			glEnable(GL_TEXTURE_2D);
		}
		
//		if(O_showMonsterHealthBars){
//			
//		}
//		
//		if(O_showMonsterHealthBarNumbers){
//			TrueTypeFont ttf = getMonsterTtf();
//			int widthOffset = getMonsterFontOffset((int) health+" ", getMonsterFont());
//			ttf.drawString( camX + x - widthOffset/2, camY + y - height/2 - 10, (int) health + " ");
//		}
	}



	public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public double getX() {
		return x;
	}



	public void setX(double x) {
		this.x = x;
	}



	public double getY() {
		return y;
	}



	public void setY(double y) {
		this.y = y;
	}



	public double getHealth() {
		return health;
	}



	public void setHealth(double health) {
		this.health = health;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public Texture getTexture() {
		return texture;
	}



	public void setTexture(Texture texture) {
		this.texture = texture;
	}



	public boolean isAlive() {
		return alive;
	}



	public void setAlive(boolean alive) {
		this.alive = alive;
	}



	public boolean isHasDied() {
		return hasDied;
	}



	public void setHasDied(boolean hasDied) {
		this.hasDied = hasDied;
	}

}
