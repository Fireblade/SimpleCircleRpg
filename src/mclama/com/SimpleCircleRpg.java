package mclama.com;


import java.io.IOException;
import java.util.ArrayList;
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
import mclama.com.item.Item;
import mclama.com.item.ItemDrop;
import mclama.com.level.Level;
import mclama.com.util.Artist;
import mclama.com.util.Utility;

import static mclama.com.util.Artist.*;
import static mclama.com.util.Globals.*;
import static mclama.com.util.DebugGlobals.*;
import static mclama.com.util.Console.*;


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
	private int screenX;
	private int screenY;

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
		if(!gameIsRunning)
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
		
		screenX = Mouse.getX();
		screenY = game_height - Mouse.getY() - 1;
		
		mouseX = Mouse.getX() - camX;
		mouseY = game_height - Mouse.getY() - 1 - camY;
		
		//get active monsters and issue their tick method.
		if(currentLevel!= null){
			gameClient.activeMonsters = currentLevel.getActiveMonsters(delta);
		}
		
//		float mx = Mouse.getX() - camX;
//		float my = HEIGHT - Mouse.getY() - 1 - camY;

		if(D_FREEMOVEMENT && !gShowConsole){
			float speed = gNoclipSpeed;
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) speed *= 3;
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
				myPlayer.setX(myPlayer.getX() - speed * delta);
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				myPlayer.setX(myPlayer.getX() + speed * delta);

			if (Keyboard.isKeyDown(Keyboard.KEY_W))
				myPlayer.setY(myPlayer.getY() - speed * delta);
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
				myPlayer.setY(myPlayer.getY() + speed * delta);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_B)){
			if(!gameIsRunning)
				startHost("name " + gen.nextInt(2000));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_N)){
			if(!gameIsRunning)
				joinHost("name " + gen.nextInt(2000));
		}
		
		
		
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 0) { //left click
		        	Monster monClicked = getMonsterClickedOn();
		        	ItemDrop iDrop;
		        	if(monClicked!= null){
		        		//System.out.println("clicked on monster");
		        		myPlayer.setTarget(monClicked);
		        		myPlayer.setAttacking(true);
		        	}
		        	else if((iDrop = getItemDropletClickedOn()) != null)
		        	{
		        		System.out.println(iDrop.getItem().getName());
		        		gameClient.addItemToInventory(iDrop.getItem());
		        	}
		        	else
		        	{
			        	if(myPlayer==null) System.out.println("null player");
			        	else {
			        		//fix invalid player
			        		if(myPlayer.getId()==0 && gameIsRunning){
			        			if(myPlayerId != 0){
			        				Player plyr = gameClient.getCharacter(myPlayerId);
			    					if(plyr != null){
			    						myPlayer = gameClient.loadPlayerCharacter();
			    						myPlayer.setId(plyr.getId());
			    						myPlayer.setX(plyr.getX());
			    						myPlayer.setY(plyr.getY());
			    						myPlayer.name = plyr.name;
			    						plyr = myPlayer;
			    						gameClient.characters.add(myPlayer); 
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
				if (Keyboard.getEventKey() == Keyboard.KEY_GRAVE) {
					gShowConsole = !gShowConsole;
				}
				if (gShowConsole) {
					if (Keyboard.getEventKey() != Keyboard.KEY_GRAVE) {
						if (Keyboard.getEventKey() == Keyboard.KEY_BACK) {
							conLineBackspace();
						} else if (Keyboard.getEventKey() == 28) { // ENTER
							conCommandEntered();
						} else if ((Keyboard.getEventCharacter() + "").matches("[a-zA-Z0-9,.;:_']")) {
							conLineAddChar(Keyboard.getEventCharacter());
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
						conLineUpArrow();
					}
				} else { // Start of not showing console
					if (Keyboard.getEventKey() == Keyboard.KEY_L && gameIsHosting) {
						//System.out.println("call new host map");
						int newLevelId = gameServer.createNewLevel();
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_P && currentLevel != null){
						//Entered place.
						myPlayer.setX(currentLevel.getSpawnXLoc());
						myPlayer.setY(currentLevel.getSpawnYLoc());
						try {
							myPlayer.calculateInfo();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
						System.out.println("=========");
						double grantExp = 0;
						int level = 0;
						double reqxp = 15;
						for(int i=1; i<=125; i++){
//							level++;
//							grantExp = level * level;
//							grantExp *= 1 + (level/10);
							//System.out.println(lvl + ": " + grantExp);
							//System.out.println(level + ": " + grantExp);
							//double reqxp = 15 + (25*Math.sqrt((level-1)*(level-1)*25));
							
							// level ^ 1.05
							
							
//							1: 250
//							5: 2000
//							10: 8000
//							20: 45,000
//							40: 1,000,000
//							75: 45,000,000
//							100: 125,000,000
							
							//reqxp *= 1.05;
							//if(level%3==0) reqxp *= 1.4;
							//if(level%7==0) reqxp *= 1.4;
							reqxp = ((250 * i) * Math.pow(Math.max(1.15 - i/500, (1.05) ), i)) /* * Math.max(1,(i/5))*/;
							double xpreward = ((15 * (double) i) * Math.pow(1.05, (double) i + Math.floor((double) i/10) + Math.floor((double) i/21.5))) * Math.max((((double) i/100) * 12), 1);
							//xpreward *= (double)Math.max((double)((double)(i/100)*12),1);
							System.out.println(i + " .. reqXp: " + Utility.df.format(reqxp) + " .. xpReward: "
									+ Utility.df.format(xpreward) + " div: " + Utility.df.format(reqxp/xpreward));
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
						if (gameIsRunning) {
							int[] rarerolls = {0,0,0,0,0,0,0};
							//System.out.println("send");
							for(int count=0; count<=200; count++)
							{
								monst = new Monster(0, 1, mouseX+16,mouseY+16);
								monst.setWidth(128);
								monst.setHeight(128);
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
					if (Keyboard.getEventKey() == Keyboard.KEY_I) {
						gShowInventory = !gShowInventory;
					}
					
					// if (Keyboard.getEventKey() == Keyboard.KEY_F) {
					// setDisplayMode(game_width, game_height,
					// !Display.isFullscreen());
					// }
					// else if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					// vsync = !vsync;
					// Display.setVSyncEnabled(vsync);
					// }
				} //end of !gShowConsole
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
	
	private ItemDrop getItemDropletClickedOn() {
		for(int i=0; i<currentLevel.getItemDrops().size(); i++){
			ItemDrop iDrop = currentLevel.getItemDrops().get(i);

			if (iDrop.inBounds(mouseX, mouseY)) {
				return iDrop;

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
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
	}

	public void renderGL() {
		// Clear The Screen And The Depth Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		
		
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

		

		glEnable(GL_TEXTURE_2D);
		
		if(currentLevel!=null){
			currentLevel.renderLevel();
			currentLevel.renderItemDroplets(); //show under monsters
			currentLevel.renderMonsters();
		}
		
		glColor3f(1.0f, 1.0f, 1.0f);
		
		if(myPlayer.getTexture()==null) myPlayer.setTexture(tex_circle);
		
		
		
		try {
			if(gameIsRunning){
				for(int i=0; i<GameClient.characters.size(); i++){
					Player plyr = GameClient.characters.get(i);
					plyr.draw();
				}
				glColor4f(1f, 1f, 1f, 1f);
				if(gShowInventory){
					renderInventory();
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//draw some background to the dev info
		glColor4f(0.2f, 0.2f, 0.2f, 0.8f);
		glDisable(GL_TEXTURE_2D);
		DrawQuad(0, 0, 112, 196);
		
		glColor4f(1.0f, 1.0f, 1.0f, 1f);
		ttf.drawString(10, 10, "FPS: " + fps, Color.orange);
		if (gameIsHosting)
			ttf.drawString(10, 24, "Connections: " + server.connectedClients(), Color.orange);
		if (gameIsRunning && client.myCharacter != null)
			ttf.drawString(10, 38, "Name: " + client.myCharacter.name, Color.orange);
		
		ttf.drawString(10, 64, "mx: " + (int) mouseX, Color.orange);
		ttf.drawString(10, 74, "my: " + (int) mouseY, Color.orange);
		
		ttf.drawString(10, 84, "monsters: " + D_MonstersOnScreen, Color.orange);
		
		try {
			ttf.drawString(10, 112, "HP: " + (int) myPlayer.getHealth(), Color.orange);
			ttf.drawString(64, 112, " / " + (int) myPlayer.getMaxHealth(), Color.orange);
		} catch (Exception e1) {
		}
		
		if(gShowConsole){
			glDisable(GL_TEXTURE_2D);
			glColor4f(0f, 0f, 0f, 0.35f);
			DrawQuad(0, 0, game_width, game_height);
			ArrayList<String> logs = conGetConsole();
			for(int i = 0; i<logs.size(); i++){
				String line = logs.get(i);
				
				ttf.drawString(12, game_height - 32 - (i * 12), line, Color.orange);
			}
			ttf.drawString(12, game_height - 16, ">" + conGetConsoleLine(), Color.orange);
		}
		else
		if(gShowDeveloperConsole){
			ArrayList<String> logs = conGetConsole(5);
			for(int i = 0; i<logs.size(); i++){
				String line = logs.get(i);
				
				ttf.drawString(12, game_height - 288 - (i * 12), line, Color.orange);
			}
		}
		
		
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
	
	public void renderInventory(){
		try {
			Item[][] inventory = gameClient.getPlayerInventory();
			for(int y=0; y<gInvHeight; y++){
				for(int x=0; x<gInvWidth; x++){
					if(inventory[x][y] != null){
						DrawQuadTex(tex_item_droplet_melee, (game_width - 330) + (x * 32) - camX, (game_height - 330) + (y * 32) - camY, 32, 32);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//equipped gear 
		DrawQuadTex(tex_item_droplet_melee, (game_width - 170) - camX, (game_height - 560) - camY, 32, 32); //head
		DrawQuadTex(tex_item_droplet_melee, (game_width - 170) - camX, (game_height - 520) - camY, 32, 32); //chest
		DrawQuadTex(tex_item_droplet_melee, (game_width - 170) - camX, (game_height - 480) - camY, 32, 32); //belt
		DrawQuadTex(tex_item_droplet_melee, (game_width - 170) - camX, (game_height - 440) - camY, 32, 32); //pants
		
		
		DrawQuadTex(tex_item_droplet_melee, (game_width - 210) - camX, (game_height - 500) - camY, 32, 32); //MH
		DrawQuadTex(tex_item_droplet_melee, (game_width - 130) - camX, (game_height - 500) - camY, 32, 32); //OH
		
		DrawQuadTex(tex_item_droplet_melee, (game_width - 204) - camX, (game_height - 460) - camY, 24, 24); //ring 1
		DrawQuadTex(tex_item_droplet_melee, (game_width - 134) - camX, (game_height - 460) - camY, 24, 24); //ring 2
		DrawQuadTex(tex_item_droplet_melee, (game_width - 134) - camX, (game_height - 540) - camY, 24, 24); //amulet
		DrawQuadTex(tex_item_droplet_melee, (game_width - 204) - camX, (game_height - 540) - camY, 24, 24); //Symbol
		
		DrawQuadTex(tex_item_droplet_melee, (game_width - 210) - camX, (game_height - 420) - camY, 32, 32); //gloves
		DrawQuadTex(tex_item_droplet_melee, (game_width - 130) - camX, (game_height - 420) - camY, 32, 32); //boots
		
		DrawQuadTex(tex_item_droplet_melee, (game_width - 240) - camX, (game_height - 560) - camY, 32, 32); //relic 1
		DrawQuadTex(tex_item_droplet_melee, (game_width - 100) - camX, (game_height - 560) - camY, 32, 32); //relic 2
	}
}//