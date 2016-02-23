package mclama.com.util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import mclama.com.SimpleCircleRpg;

public class Artist {

	//public static final int WIDTH = 1280/2, HEIGHT = 960/2;
	//public static final int WIDTH = (int) (1920/1.5), HEIGHT = (int) (1080/1.5);
	public static final int WIDTH = 720, HEIGHT = 480;
	public static double camX=0, camY=0;
	
	
	public static Texture tex_circle;

	public static void createCircleLibrary(){
		tex_circle = LoadTexture("res/images/circles/circle.png", "PNG");
	}
	
	public static void BeginSession(){
		Display.setTitle("MY GAME");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		//Prepare game
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
	}
	
	public static void DrawQuad(float x, float y, float width, float height){
		glBegin(GL_QUADS);
			glVertex2f(x         ,y);
			glVertex2f(x + width ,y);
			glVertex2f(x + width ,y + height);
			glVertex2f(x         ,y + height);
		glEnd();
		glLoadIdentity();
	}

	public static void DrawQuadTex(Texture tex, double x, double y, float width,float height) {
		tex.bind();
		glTranslated(camX,camY,0);
		glTranslated(x,y,0);
		glTranslated(-(width/2), -(height/2), 0);
		//glTranslatef((tex.getImageWidth()/2),(tex.getImageHeight()/2), 0);
		glBegin(GL_QUADS);
			glTexCoord2d(0, 0);
			glVertex2d(0, 0);
			glTexCoord2d(1, 0);
			glVertex2d(width, 0);
			glTexCoord2d(1, 1);
			glVertex2d(width, height);
			glTexCoord2d(0, 1);
			glVertex2d(0, height);
		glEnd();
		glTranslated((width/2),(height/2),0);
		glLoadIdentity();
	}
	
	public static void DrawQuadTexNormal(Texture tex, double x, double y, float width,float height) {
		tex.bind();
		glTranslated(camX,camY,0);
		glTranslated(x,y,0);
		glBegin(GL_QUADS);
			glTexCoord2d(0, 0);
			glVertex2d(0, 0);
			glTexCoord2d(1, 0);
			glVertex2d(width, 0);
			glTexCoord2d(1, 1);
			glVertex2d(width, height);
			glTexCoord2d(0, 1);
			glVertex2d(0, height);
		glEnd();
		glLoadIdentity();
	}
	
	public static void DrawQuadTexRot(Texture tex, float x, float y, float width, float height, float angle) {
		tex.bind();
		glTranslatef(x + (tex.getImageWidth()/2), y + (tex.getImageHeight()/2), 0);
		glRotatef(angle, 0, 0, 1);
		glTranslatef(-(tex.getImageWidth()/2), -(tex.getImageHeight()/2), 0);
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			glTexCoord2f(1, 0);
			glVertex2f(tex.getImageWidth(), 0);
			glTexCoord2f(1, 1);
			glVertex2f(tex.getImageWidth(), tex.getImageHeight());
			glTexCoord2f(0, 1);
			glVertex2f(0, tex.getImageHeight());
		glEnd();
		glLoadIdentity();
	}
	
	public static void DrawPoint(double x, double y){
		//glPushMatrix();
		glPointSize(2.0f);
		glBegin(GL_POINTS);
	        glVertex2d(x, y);
        glEnd();
        //glPopMatrix();
	}

	public static Texture LoadTexture(String path, String fileType){
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tex;
	}
	
	public static boolean onScreen(float x, float y, float xOff, float yOff){
		if(x+1024>camX-xOff && -(x+1024)<camX+xOff && y>camY-yOff && (-y)<camY + yOff){
		//if(Math.abs(x)>Math.abs(camX)-xOff && Math.abs(x)<Math.abs(camX)+xOff ){
		//if (Math.abs(x+1024) > Math.abs(camX) - xOff && -Math.abs(x+1024) < Math.abs(camX) + xOff
		//		&& Math.abs(y) > Math.abs(camY) - yOff && -Math.abs(y) < Math.abs(camY) + yOff) {
		//		&& Math.abs(y)>Math.abs(camY)-yOff && Math.abs(y)<Math.abs(camY) + yOff){
			return true;
		}
		
		return false;
	}
	
	public static Texture QuickLoad(String name){
		Texture tex = null;
		tex = LoadTexture("res/images/" + name + ".png", "PNG");
		return tex;
	}
	
	public static Texture QuickLoadTile(String name, String theme){
		Texture tex = null;
		tex = LoadTexture("res/level/" + theme + "/" + name + ".png", "PNG");
		return tex;
	}
	
	
	//textureLoaders thx to Krythic
	private static final int BYTES_PER_PIXEL = 4;//3 for RGB, 4 for RGBA
    public static int loadTextureKry(BufferedImage image){

       int[] pixels = new int[image.getWidth() * image.getHeight()];
         image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

         ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

         for(int y = 0; y < image.getHeight(); y++){
             for(int x = 0; x < image.getWidth(); x++){
                 int pixel = pixels[y * image.getWidth() + x];
                 buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                 buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                 buffer.put((byte) (pixel & 0xFF));               // Blue component
                 buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
             }
         }

         buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

         // You now have a ByteBuffer filled with the color data of each pixel.
         // Now just create a texture ID and bind it. Then you can load it using 
         // whatever OpenGL method you want, for example:

       int textureID = glGenTextures(); //Generate texture ID
         glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

         //Setup wrap mode
         glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
         glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

         //Setup texture scaling filtering
         glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
         glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

         //Send texel data to OpenGL
         glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

         //Return the texture ID so we can bind it later again
       return textureID;
    }

    public static BufferedImage loadImage(String loc)
    {
         try {
            return ImageIO.read(SimpleCircleRpg.class.getResource(loc));
         } catch (IOException e) {
             //Error Handling Here
         }
        return null;
    }
	
}
