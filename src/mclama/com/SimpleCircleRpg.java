package mclama.com;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import mclama.com.entity.Monster;
import mclama.com.entity.Player;
import mclama.com.level.Level;
import mclama.com.util.Artist;

import static mclama.com.util.Artist.*;
import static mclama.com.util.globals.*;

public class SimpleCircleRpg {



	public static GameClient client;
	public static GameServer server;

	public static int game_width = 720;
	public static int game_height = 480;

	private UnicodeFont ttf;
	private UnicodeFont ttfMon;

	/** time at last frame */
	long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	/** is VSync Enabled */
	boolean vsync;
	private Monster monst=null;
	private double mouseX, mouseY;
	private Level currentLevel=null;

	public static void main(String[] args) throws IOException {
		
		System.out.println("Starting");
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Host or name: ");
		String text = reader.next();

		// Log.DEBUG();
		
		if (text.equals("host"))
		{
			System.out.println("What is your name: ");
			text = reader.next();
			gameIsHosting = true;
			try {
				server = new GameServer();
				client = new GameClient(text);
				myPlayer.name = text;
				gameIsRunning = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
		{
			client = new GameClient(text);
			myPlayer.name = text;
			gameIsRunning = true;
		}
		
		SimpleCircleRpg game = new SimpleCircleRpg();
		game.start();

	}

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(game_width, game_height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		
		initGL(); // init OpenGL
		initialize();
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested()) {
			int delta = getDelta();

			update(delta);
			renderGL();

			Display.update();
			Display.sync(60); // cap fps to 60fps
		}

		Display.destroy();
		System.out.println("Terminating...");
		System.exit(0);
	}

	public void initialize() {
		Artist.createCircleLibrary();
		
		
		ttf = new UnicodeFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 12));
		ttf.getEffects().add(new ColorEffect(java.awt.Color.white));
		ttf.addAsciiGlyphs();
		try {
			ttf.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}

		ttfMon = new UnicodeFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 8));
		ttfMon.getEffects().add(new ColorEffect(java.awt.Color.white));
		ttfMon.addAsciiGlyphs();
		try {
			ttfMon.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		//myPlayer = new Player(); //created in globals
		//myPlayer.setTexture(tex_circle);
		//myPlayer.setId(playersConnectedID);

	}

	public void update(int delta) {
		// rotate quad
		mouseX = Mouse.getX() - camX;
		mouseY = HEIGHT - Mouse.getY() - 1 - camY;
		
//		float mx = Mouse.getX() - camX;
//		float my = HEIGHT - Mouse.getY() - 1 - camY;

		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			myPlayer.setX(myPlayer.getX() - 0.35f * delta);
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			myPlayer.setX(myPlayer.getX() + 0.35f * delta);

		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			myPlayer.setY(myPlayer.getY() - 0.35f * delta);
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			myPlayer.setY(myPlayer.getY() + 0.35f * delta);

		
		
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 0) {
		        	if(myPlayer==null) System.out.println("null player");
		        	else myPlayer.playerClicked(mouseX,mouseY);
		        }
		    }else {
		        if (Mouse.getEventButton() == 0) {
		            //System.out.println("Left button released");
		        }
		    }
		}
		
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_L) {
					//new level
					currentLevel = new Level(128,32,1,gen.nextLong());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					if (gameIsRunning) {
						int[] rarerolls = {0,0,0,0,0,0,0};
						//System.out.println("send");
						for(int count=0; count<=10000; count++)
						{
							monst = new Monster(0,  1, 1, 64,64);
							monst.die(myPlayer);
							
							for(int i=0; i<monst.getItem_drops().size(); i++){
								rarerolls[monst.getItem_drops().get(i).getRarity()]++;
							}
						}
						
						System.out.println("0: " + rarerolls[0]);
						System.out.println("1: " + rarerolls[1]);
						System.out.println("2: " + rarerolls[2]);
						System.out.println("3: " + rarerolls[3]);
						System.out.println("4: " + rarerolls[4]);
						System.out.println("5: " + rarerolls[5]);
						System.out.println("6: " + rarerolls[6]);
						
						System.out.println("items: " + (rarerolls[1] + rarerolls[2] + rarerolls[3] +
								rarerolls[4] + rarerolls[5] + rarerolls[6]));
					}
				}
				// if (Keyboard.getEventKey() == Keyboard.KEY_F) {
				// setDisplayMode(game_width, game_height,
				// !Display.isFullscreen());
				// }
				// else if (Keyboard.getEventKey() == Keyboard.KEY_V) {
				// vsync = !vsync;
				// Display.setVSyncEnabled(vsync);
				// }
			}
		}//end of keyboard
		
		//myPlayer.tick(delta);

		for(int i=0; i<GameClient.characters.size(); i++){
			Player plyr = GameClient.characters.get(i);
			plyr.tick(delta);
		}
		

		updateFPS(); // update FPS Counter
	}

	/**
	 * Set the display mode to be used
	 * 
	 * @param width
	 *            The width of the display required
	 * @param height
	 *            The height of the display required
	 * @param fullscreen
	 *            True if we want fullscreen mode
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen) {

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null)
									|| (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against
						// the
						// original display mode then it's probably best to go
						// for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
		}
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, game_width, game_height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	public void renderGL() {
		// Clear The Screen And The Depth Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		// R,G,B,A Set The Color To Blue One Time Only
		glColor3f(0.5f, 0.5f, 1.0f);

		// draw quad
//		glPushMatrix();
//		glTranslatef(x, y, 0);
//		glRotatef(rotation, 0f, 0f, 1f);
//		glTranslatef(-x, -y, 0);
//
//		glBegin(GL_QUADS);
//		glVertex2f(x - 50, y - 50);
//		glVertex2f(x + 50, y - 50);
//		glVertex2f(x + 50, y + 50);
//		glVertex2f(x - 50, y + 50);
//		glEnd();
//		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glColor3f(1.0f, 1.0f, 1.0f);
		if(monst!=null){
			monst.draw();
			glColor3f(1.0f, 0.0f, 0.0f);
			DrawPoint(monst.getX(), monst.getY());
			DrawPoint(32, 32);
		}
		glColor3f(1.0f, 1.0f, 1.0f);
		
		if(myPlayer.getTexture()==null) myPlayer.setTexture(tex_circle);
		
		
		for(int i=0; i<GameClient.characters.size(); i++){
			Player plyr = GameClient.characters.get(i);
			plyr.draw();
		}

		ttf.drawString(10, 10, "FPS: " + fps, Color.orange);
		if (gameIsHosting)
			ttf.drawString(10, 24, "Connections: " + server.connectedClients(), Color.orange);
		if (gameIsRunning && client.myCharacter != null)
			ttf.drawString(10, 38, "Name: " + client.myCharacter.name, Color.orange);
		
		ttf.drawString(10, 64, "mx: " + mouseX, Color.orange);
		ttf.drawString(10, 74, "my: " + mouseY, Color.orange);
		
		

		glDisable(GL_TEXTURE_2D);
		glColor3f(1.0f, 0.5f, 0.5f);
		try {
			
			
			if(currentLevel != null){
				int gap=3;
				boolean[][] tiles = currentLevel.getTiles();
				for(int x=0; x<tiles.length; x++){
					for(int y=0; y<tiles[0].length; y++){
						//System.out.println(tiles[0].length);
						//System.out.println("x:" + x + " y:" + y);
						if(tiles[x][y]==true){
							DrawPoint(150+(x*gap), 150+(y*gap));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
