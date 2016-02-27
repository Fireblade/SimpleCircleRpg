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
import org.newdawn.slick.opengl.Texture;

import mclama.com.entity.Monster;
import mclama.com.entity.Player;
import mclama.com.level.Level;
import mclama.com.util.Artist;

import static mclama.com.util.Artist.*;
import static mclama.com.util.Globals.*;
import static mclama.com.util.DebugGlobals.*;


public class SimpleCircleRpg {



	public static GameClient client;
	public static GameServer server;


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
	
	private Texture background2;
	
	private boolean leftclickHeld=false;
	private boolean leftclickMoving;

	public static void main(String[] args) throws IOException {
		
		System.out.println("Starting");
		
//		Scanner reader = new Scanner(System.in);
//		System.out.println("Host or name: ");
//		String text = reader.next();

		// Log.DEBUG();
		
//		if (text.equals("host"))
//		{
//			System.out.println("What is your name: ");
//			text = reader.next();
//			gameIsHosting = true;
//			try {
//				server = new GameServer();
//				client = new GameClient(text);
//				myPlayer.name = text;
//				gameIsRunning = true;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else
//		{
//			client = new GameClient(text);
//			myPlayer.name = text;
//			gameIsRunning = true;
//		}
		
		SimpleCircleRpg game = new SimpleCircleRpg();
		game.start();

	}
	
	public void startHost(String name){
		gameIsHosting = true;
		try {
			server = new GameServer();
			client = new GameClient(name, "localhost");
			myPlayer.name = name;
			gameIsRunning = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void joinHost(String name){
		if(!gameIsRunning){
			client = new GameClient(name, "localhost");
			myPlayer.name = name;
			gameIsRunning = true;
		}
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
			Display.sync(targetFPS); // cap fps to 60fps
		}

		Display.destroy();
		System.out.println("Terminating...");
		System.exit(0);
	}

	public void initialize() {
		Artist.createCircleLibrary();
		background2 = LoadTexture("res/images/background2.png", "PNG");
		
		
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
		if(D_FREELOOKCAM){
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				camX+=5;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				camX-=5;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
				camY+=5;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				camY-=5;
			}
		} else {
			camX = myPlayer.getX() - game_width/2;
			camX = -camX;
			camY = myPlayer.getY() - game_height/2;
			camY = -camY;
		}
		
		mouseX = Mouse.getX() - camX;
		mouseY = game_height - Mouse.getY() - 1 - camY;
		
		//get active monsters and issue their tick method.
		if(currentLevel!= null){
			gameClient.activeMonsters = currentLevel.getActiveMonsters(delta);
		}
		
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

		
		if (Keyboard.isKeyDown(Keyboard.KEY_B)){
			startHost("name " + gen.nextInt(2000));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_N)){
			joinHost("name " + gen.nextInt(2000));
		}
		
		
		
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 0) { //left click
		        	Monster monClicked = getMonsterClickedOn();
		        	if(monClicked!= null){
		        		//System.out.println("clicked on monster");
		        		myPlayer.setTarget(monClicked);
		        		myPlayer.setAttacking(true);
		        	}
		        	else //if check for items
		        	//{
		        		//Pick up item.
		        	//}
		        	//else
		        	{
			        	if(myPlayer==null) System.out.println("null player");
			        	else {
			        		//fix invalid player
			        		if(myPlayer.getId()==0 && gameIsRunning){
			        			if(myPlayerId != 0){
			        				Player plyr = gameClient.getCharacter(myPlayerId);
			    					if(plyr != null){
			    						myPlayer = plyr;
			    					}
			        			}
			        		} //move player
			        		leftclickHeld=true;
			        		leftclickMoving=true;
			        		
			        		myPlayer.setTarget(null);
			        		myPlayer.setAttacking(false);
			        		//myPlayer.playerClicked(mouseX,mouseY);
			        	}
		        	}
		        }
		    }else {
		        if (Mouse.getEventButton() == 0) {
		            //System.out.println("Left button released");
		        	leftclickHeld=false;
		        	leftclickMoving=false;
		        }
		    }
		}
		
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_L && gameIsHosting) {
					//System.out.println("call new host map");
					int newLevelId = gameServer.createNewLevel();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_P && currentLevel != null){
					//Entered place.
					myPlayer.setX(currentLevel.getSpawnXLoc());
					myPlayer.setY(currentLevel.getSpawnYLoc());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					if (gameIsRunning) {
						int[] rarerolls = {0,0,0,0,0,0,0};
						//System.out.println("send");
						for(int count=0; count<=100; count++)
						{
							monst = new Monster(0, 1, mouseX+16,mouseY+16);
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
		
		if(gameIsRunning){
			if(leftclickHeld && leftclickMoving){
				myPlayer.playerClicked(mouseX,mouseY);
			}
			
			if(gameClient.queGenerateNewLevelId!=0){
				System.out.println("gen new currlevel");
				currentLevel = gameClient.generateNewLevel(gameClient.queGenerateNewLevelId, gameClient.queGenerateNewLevelSeed);
				gameClient.queGenerateNewLevelId=0;
				
//				myPlayer.setX(currentLevel.getSpawnXLoc());
//				myPlayer.setY(currentLevel.getSpawnYLoc());
				
				for(int i=0; i<GameClient.characters.size(); i++){
					Player plyr = GameClient.characters.get(i);
					plyr.setX(currentLevel.getSpawnXLoc());
					plyr.setY(currentLevel.getSpawnYLoc());
				}
			}
			
			//myPlayer.tick(delta);
	
			for(int i=0; i<GameClient.characters.size(); i++){
				Player plyr = GameClient.characters.get(i);
				plyr.tick(delta);
			}
	}
		

		updateFPS(); // update FPS Counter
	}
	/**
	 * Returns the monster that was clicked under the cursor.
	 * 
	 * @return Monster
	 */

	private Monster getMonsterClickedOn() {
		for(int i=0; i<gameClient.activeMonsters.size(); i++){
			Monster mons = gameClient.activeMonsters.get(i);
			if(mons.isAlive())
			{
				if(mons.inBounds(mouseX, mouseY)){
					return mons;
				}
			}
		}
		return null;
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
			Display.setTitle("SimpleCircleRPG FPS: " + fps);
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
		
		glColor4f(1f, 1f, 1f, 1f);

		
		float xOff = (float) Math.floor(camX/1024) * 1024;
		xOff = -xOff;
		float yOff = (float) Math.floor(camY/1024) * 1024;
		yOff = -yOff;
		
		//top 3
		DrawQuadTex(background2, -512 + xOff, -512  + yOff, 1024, 1024);
		DrawQuadTex(background2, 1536 + xOff, -512 + yOff, 1024, 1024);
		DrawQuadTex(background2, 512 + xOff, -512 + yOff, 1024, 1024);
		//middle 3
		DrawQuadTex(background2, -512 + xOff, 512 + yOff, 1024, 1024);
		DrawQuadTex(background2, 1536 + xOff, 512 + yOff, 1024, 1024);
		DrawQuadTex(background2, 512 + xOff, 512 + yOff, 1024, 1024);
		//bottom 3
		DrawQuadTex(background2, -512 + xOff, 1536 + yOff, 1024, 1024);
		DrawQuadTex(background2, 1536 + xOff, 1536 + yOff, 1024, 1024);
		DrawQuadTex(background2, 512 + xOff, 1536 + yOff, 1024, 1024);

		
		if(currentLevel!=null){
			currentLevel.renderLevel();
		}
		
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
		//glColor3f(1.0f, 0f, 0f);
		//draw enemies
		if(currentLevel!=null){
			currentLevel.renderItemDroplets(); //show under monsters
			currentLevel.renderMonsters();
		}
		
		glColor3f(1.0f, 1.0f, 1.0f);
		
		if(myPlayer.getTexture()==null) myPlayer.setTexture(tex_circle);
		
		if(gameIsRunning){
			for(int i=0; i<GameClient.characters.size(); i++){
				Player plyr = GameClient.characters.get(i);
				plyr.draw();
			}
		}
		
		glColor4f(0.2f, 0.2f, 0.2f, 0.8f);
		
		
		glDisable(GL_TEXTURE_2D);
		DrawQuad(0, 0, 112, 128);
		
		glColor4f(1.0f, 1.0f, 1.0f, 1f);
		ttf.drawString(10, 10, "FPS: " + fps, Color.orange);
		if (gameIsHosting)
			ttf.drawString(10, 24, "Connections: " + server.connectedClients(), Color.orange);
		if (gameIsRunning && client.myCharacter != null)
			ttf.drawString(10, 38, "Name: " + client.myCharacter.name, Color.orange);
		
		ttf.drawString(10, 64, "mx: " + (int) mouseX, Color.orange);
		ttf.drawString(10, 74, "my: " + (int) mouseY, Color.orange);
		
		ttf.drawString(10, 84, "monsters: " + D_MonstersOnScreen, Color.orange);
		
		
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
							DrawPoint(120+(x*gap), 4+(y*gap), 2f);
						}
					}
				}
				glColor3f(1f, 1f, 0f);
				DrawPoint(120 + ((myPlayer.getX() / currentLevel.getTileWidth()) * gap),
						4 + ((myPlayer.getY() / currentLevel.getTileHeight()) * gap));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
