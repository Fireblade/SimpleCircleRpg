package mclama.com.item;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glColor3f;
import org.newdawn.slick.opengl.Texture;

import mclama.com.entity.Player;

import static mclama.com.util.Artist.*;
import static mclama.com.util.DebugGlobals.D_PlayerShowMoveToLine;

public class ItemDrop {
	
	private Item item;
	private double x, y;
	private int width=8, height=8;
	private Texture texture;
	
	
	
	
	public ItemDrop(Item item, double x, double y){
		this.item = item;
		this.x = x;
		this.y = y;
		
		texture = tex_item_droplet_melee;
	}

	public void draw() {
		
		if (texture != null) {
			switch(item.getRarity()){
			case 1: //common
				glColor3f(1f, 1f, 1f);
				break;
			case 2: //magic
				glColor3f(0f, 0f, 1f);
				break;
			case 3: //rare
				glColor3f(0f, 1f, 0f);
				break;
			case 4: //legendary
				glColor3f(1f, 1f, 0f);
				break;
			case 5: //unique
				glColor3f(0.75f, 0.5f, 0.25f);
				break;
			case 6: //cursed
				glColor3f(0.5f, 0f, 0.5f);
				break;
			}
			// set rarity color
			DrawQuadTex(texture, x, y, width, height);
		} else
			texture = LoadTexture("res/images/items/droplet/base.png", "PNG");
	}
	
	public boolean inBounds(double mouseX, double mouseY) {
		if(mouseX < x - (width/2)) return false;
		if(mouseX > x + (width/2)) return false;
		
		if(mouseY < y - (height/2)) return false;
		if(mouseY > y + (height/2)) return false;
		return true;
	}

	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
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
	

}
