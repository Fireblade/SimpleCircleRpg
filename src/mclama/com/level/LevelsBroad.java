package mclama.com.level;

import java.awt.Point;
import java.util.Queue;
import java.util.Random;

public class LevelsBroad {

	public static boolean pullNewPreset(boolean[][] tiles, int width, int height, Point pnt, Queue<Point> que,
			Random levelGen) {
		int cycles = 0;
		int cycleLimit = 100;
		int mazePresets = 8;
		int x = pnt.x;
		int y = pnt.y;
		while (cycles < cycleLimit) {
			cycles++;

			int presetRoll = levelGen.nextInt(mazePresets);

			switch (presetRoll) {

			case 0:
				if (x + 6 < width - 1 && y - 4 > 0 && y + 6 < height - 1) {
					tiles[x + 0][y - 4] = true;
					tiles[x + 1][y - 4] = true;
					tiles[x + 2][y - 4] = true;
					tiles[x + 3][y - 4] = true;
					tiles[x + 4][y - 4] = true;
					tiles[x + 5][y - 4] = true;
					tiles[x + 6][y - 4] = true;
					tiles[x + 0][y - 3] = true;
					tiles[x + 1][y - 3] = true;
					tiles[x + 3][y - 3] = true;
					tiles[x + 4][y - 3] = true;
					tiles[x + 5][y - 3] = true;
					tiles[x + 0][y - 2] = true;
					tiles[x + 1][y - 2] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 3][y - 2] = true;
					tiles[x + 4][y - 2] = true;
					tiles[x + 5][y - 2] = true;
					tiles[x + 6][y - 2] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 4][y - 1] = true;
					tiles[x + 5][y - 1] = true;
					tiles[x + 6][y - 1] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 2][y + 0] = true;
					tiles[x + 3][y + 0] = true;
					tiles[x + 4][y + 0] = true;
					tiles[x + 5][y + 0] = true;
					tiles[x + 1][y + 1] = true;
					tiles[x + 2][y + 1] = true;
					tiles[x + 4][y + 1] = true;
					tiles[x + 5][y + 1] = true;
					tiles[x + 6][y + 1] = true;
					tiles[x + 1][y + 2] = true;
					tiles[x + 3][y + 2] = true;
					tiles[x + 4][y + 2] = true;
					tiles[x + 6][y + 2] = true;
					tiles[x + 0][y + 3] = true;
					tiles[x + 1][y + 3] = true;
					tiles[x + 2][y + 3] = true;
					tiles[x + 3][y + 3] = true;
					tiles[x + 6][y + 3] = true;
					tiles[x + 0][y + 4] = true;
					tiles[x + 1][y + 4] = true;
					tiles[x + 3][y + 4] = true;
					tiles[x + 4][y + 4] = true;
					tiles[x + 5][y + 4] = true;
					tiles[x + 6][y + 4] = true;
					tiles[x + 0][y + 5] = true;
					tiles[x + 1][y + 5] = true;
					tiles[x + 2][y + 5] = true;
					tiles[x + 4][y + 5] = true;
					tiles[x + 5][y + 5] = true;
					tiles[x + 2][y + 6] = true;

					que.add(new Point(x + 7, y + 1));
					return false;
				}
			case 1:
				if (x + 5 < width - 1 && y - 3 > 0 && y + 5 < height - 1) {
					tiles[x + 1][y - 3] = true;
					tiles[x + 3][y - 3] = true;
					tiles[x + 4][y - 3] = true;
					tiles[x + 0][y - 2] = true;
					tiles[x + 1][y - 2] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 3][y - 2] = true;
					tiles[x + 4][y - 2] = true;
					tiles[x + 5][y - 2] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 4][y - 1] = true;
					tiles[x + 5][y - 1] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 2][y + 0] = true;
					tiles[x + 4][y + 0] = true;
					tiles[x + 0][y + 1] = true;
					tiles[x + 2][y + 1] = true;
					tiles[x + 4][y + 1] = true;
					tiles[x + 5][y + 1] = true;
					tiles[x + 1][y + 2] = true;
					tiles[x + 3][y + 2] = true;
					tiles[x + 4][y + 2] = true;
					tiles[x + 5][y + 2] = true;
					tiles[x + 0][y + 3] = true;
					tiles[x + 1][y + 3] = true;
					tiles[x + 2][y + 3] = true;
					tiles[x + 3][y + 3] = true;
					tiles[x + 1][y + 4] = true;
					tiles[x + 3][y + 4] = true;
					tiles[x + 4][y + 4] = true;
					tiles[x + 5][y + 4] = true;
					tiles[x + 0][y + 5] = true;
					tiles[x + 1][y + 5] = true;
					tiles[x + 2][y + 5] = true;
					tiles[x + 4][y + 5] = true;

					que.add(new Point(x + 6, y + 1));
					return false;
				}

			case 2:
				if (x + 6 < width - 1 && y - 5 > 0 && y + 0 < height - 1) {
					tiles[x + 1][y - 5] = true;
					tiles[x + 2][y - 5] = true;
					tiles[x + 3][y - 5] = true;
					tiles[x + 4][y - 5] = true;
					tiles[x + 5][y - 5] = true;
					tiles[x + 0][y - 4] = true;
					tiles[x + 3][y - 4] = true;
					tiles[x + 4][y - 4] = true;
					tiles[x + 5][y - 4] = true;
					tiles[x + 6][y - 4] = true;
					tiles[x + 0][y - 3] = true;
					tiles[x + 2][y - 3] = true;
					tiles[x + 3][y - 3] = true;
					tiles[x + 6][y - 3] = true;
					tiles[x + 0][y - 2] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 4][y - 2] = true;
					tiles[x + 5][y - 2] = true;
					tiles[x + 6][y - 2] = true;
					tiles[x + 0][y - 1] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 3][y - 1] = true;
					tiles[x + 4][y - 1] = true;
					tiles[x + 5][y - 1] = true;
					tiles[x + 6][y - 1] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 4][y + 0] = true;
					tiles[x + 5][y + 0] = true;

					que.add(new Point(x + 7, y - 4));
					return false;
				}

			case 3:
				if (x + 8 < width - 1 && y - 5 > 0 && y + 7 < height - 1) {
					tiles[x + 1][y - 5] = true;
					tiles[x + 2][y - 5] = true;
					tiles[x + 3][y - 5] = true;
					tiles[x + 4][y - 5] = true;
					tiles[x + 0][y - 4] = true;
					tiles[x + 1][y - 4] = true;
					tiles[x + 3][y - 4] = true;
					tiles[x + 4][y - 4] = true;
					tiles[x + 5][y - 4] = true;
					tiles[x + 6][y - 4] = true;
					tiles[x + 7][y - 4] = true;
					tiles[x + 8][y - 4] = true;
					tiles[x + 1][y - 3] = true;
					tiles[x + 2][y - 3] = true;
					tiles[x + 3][y - 3] = true;
					tiles[x + 5][y - 3] = true;
					tiles[x + 8][y - 3] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 3][y - 2] = true;
					tiles[x + 4][y - 2] = true;
					tiles[x + 5][y - 2] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 3][y - 1] = true;
					tiles[x + 4][y - 1] = true;
					tiles[x + 5][y - 1] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 2][y + 0] = true;
					tiles[x + 4][y + 0] = true;
					tiles[x + 5][y + 0] = true;
					tiles[x + 6][y + 0] = true;
					tiles[x + 1][y + 1] = true;
					tiles[x + 4][y + 1] = true;
					tiles[x + 5][y + 1] = true;
					tiles[x + 6][y + 1] = true;
					tiles[x + 7][y + 1] = true;
					tiles[x + 8][y + 1] = true;
					tiles[x + 1][y + 2] = true;
					tiles[x + 2][y + 2] = true;
					tiles[x + 3][y + 2] = true;
					tiles[x + 5][y + 2] = true;
					tiles[x + 6][y + 2] = true;
					tiles[x + 7][y + 2] = true;
					tiles[x + 1][y + 3] = true;
					tiles[x + 2][y + 3] = true;
					tiles[x + 3][y + 3] = true;
					tiles[x + 6][y + 3] = true;
					tiles[x + 7][y + 3] = true;
					tiles[x + 0][y + 4] = true;
					tiles[x + 1][y + 4] = true;
					tiles[x + 2][y + 4] = true;
					tiles[x + 4][y + 4] = true;
					tiles[x + 5][y + 4] = true;
					tiles[x + 6][y + 4] = true;
					tiles[x + 7][y + 4] = true;
					tiles[x + 1][y + 5] = true;
					tiles[x + 2][y + 5] = true;
					tiles[x + 3][y + 5] = true;
					tiles[x + 4][y + 5] = true;
					tiles[x + 5][y + 5] = true;
					tiles[x + 6][y + 5] = true;
					tiles[x + 7][y + 5] = true;
					tiles[x + 8][y + 5] = true;
					tiles[x + 2][y + 6] = true;
					tiles[x + 3][y + 6] = true;
					tiles[x + 5][y + 6] = true;
					tiles[x + 6][y + 6] = true;
					tiles[x + 7][y + 6] = true;
					tiles[x + 8][y + 6] = true;
					tiles[x + 3][y + 7] = true;
					tiles[x + 4][y + 7] = true;
					tiles[x + 5][y + 7] = true;

					que.add(new Point(x + 9, y + 6));
					return false;
				}

			case 4:
				if (x + 6 < width - 1 && y - 3 > 0 && y + 4 < height - 1) {
					tiles[x + 2][y - 3] = true;
					tiles[x + 3][y - 3] = true;
					tiles[x + 4][y - 3] = true;
					tiles[x + 5][y - 3] = true;
					tiles[x + 6][y - 3] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 3][y - 2] = true;
					tiles[x + 4][y - 2] = true;
					tiles[x + 5][y - 2] = true;
					tiles[x + 6][y - 2] = true;
					tiles[x + 0][y - 1] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 4][y - 1] = true;
					tiles[x + 5][y - 1] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 4][y + 0] = true;
					tiles[x + 5][y + 0] = true;
					tiles[x + 1][y + 1] = true;
					tiles[x + 5][y + 1] = true;
					tiles[x + 6][y + 1] = true;
					tiles[x + 1][y + 2] = true;
					tiles[x + 2][y + 2] = true;
					tiles[x + 6][y + 2] = true;
					tiles[x + 0][y + 3] = true;
					tiles[x + 1][y + 3] = true;
					tiles[x + 2][y + 3] = true;
					tiles[x + 3][y + 3] = true;
					tiles[x + 4][y + 3] = true;
					tiles[x + 5][y + 3] = true;
					tiles[x + 6][y + 3] = true;
					tiles[x + 0][y + 4] = true;

					que.add(new Point(x + 7, y + 1));
					return false;
				}

			case 5:
				if (x + 6 < width - 1 && y - 4 > 0 && y + 4 < height - 1) {
					tiles[x + 0][y - 4] = true;
					tiles[x + 1][y - 4] = true;
					tiles[x + 0][y - 3] = true;
					tiles[x + 1][y - 3] = true;
					tiles[x + 2][y - 3] = true;
					tiles[x + 5][y - 3] = true;
					tiles[x + 6][y - 3] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 3][y - 2] = true;
					tiles[x + 4][y - 2] = true;
					tiles[x + 5][y - 2] = true;
					tiles[x + 6][y - 2] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 3][y - 1] = true;
					tiles[x + 5][y - 1] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 4][y + 0] = true;
					tiles[x + 5][y + 0] = true;
					tiles[x + 1][y + 1] = true;
					tiles[x + 2][y + 1] = true;
					tiles[x + 4][y + 1] = true;
					tiles[x + 5][y + 1] = true;
					tiles[x + 6][y + 1] = true;
					tiles[x + 1][y + 2] = true;
					tiles[x + 2][y + 2] = true;
					tiles[x + 3][y + 2] = true;
					tiles[x + 4][y + 2] = true;
					tiles[x + 5][y + 2] = true;
					tiles[x + 6][y + 2] = true;
					tiles[x + 0][y + 3] = true;
					tiles[x + 1][y + 3] = true;
					tiles[x + 2][y + 3] = true;
					tiles[x + 0][y + 4] = true;

					que.add(new Point(x + 7, y - 2));
					return false;
				}

			case 6:
				if (x + 5 < width - 1 && y - 4 > 0 && y + 6 < height - 1) {
					tiles[x + 0][y - 4] = true;
					tiles[x + 1][y - 4] = true;
					tiles[x + 2][y - 4] = true;
					tiles[x + 3][y - 4] = true;
					tiles[x + 4][y - 4] = true;
					tiles[x + 5][y - 4] = true;
					tiles[x + 0][y - 3] = true;
					tiles[x + 1][y - 3] = true;
					tiles[x + 3][y - 3] = true;
					tiles[x + 4][y - 3] = true;
					tiles[x + 5][y - 3] = true;
					tiles[x + 0][y - 2] = true;
					tiles[x + 1][y - 2] = true;
					tiles[x + 2][y - 2] = true;
					tiles[x + 3][y - 2] = true;
					tiles[x + 4][y - 2] = true;
					tiles[x + 0][y - 1] = true;
					tiles[x + 2][y - 1] = true;
					tiles[x + 4][y - 1] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 2][y + 0] = true;
					tiles[x + 3][y + 0] = true;
					tiles[x + 4][y + 0] = true;
					tiles[x + 5][y + 0] = true;
					tiles[x + 0][y + 1] = true;
					tiles[x + 1][y + 1] = true;
					tiles[x + 3][y + 1] = true;
					tiles[x + 4][y + 1] = true;
					tiles[x + 5][y + 1] = true;
					tiles[x + 0][y + 2] = true;
					tiles[x + 1][y + 2] = true;
					tiles[x + 2][y + 2] = true;
					tiles[x + 3][y + 2] = true;
					tiles[x + 5][y + 2] = true;
					tiles[x + 0][y + 3] = true;
					tiles[x + 1][y + 3] = true;
					tiles[x + 2][y + 3] = true;
					tiles[x + 3][y + 3] = true;
					tiles[x + 4][y + 3] = true;
					tiles[x + 1][y + 4] = true;
					tiles[x + 2][y + 4] = true;
					tiles[x + 3][y + 4] = true;
					tiles[x + 4][y + 4] = true;
					tiles[x + 5][y + 4] = true;
					tiles[x + 0][y + 5] = true;
					tiles[x + 1][y + 5] = true;
					tiles[x + 2][y + 5] = true;
					tiles[x + 4][y + 5] = true;
					tiles[x + 5][y + 5] = true;
					tiles[x + 1][y + 6] = true;
					tiles[x + 2][y + 6] = true;

					que.add(new Point(x + 6, y + 0));
					return false;
				}

			case 7:
				if (x + 5 < width - 1 && y - 2 > 0 && y + 3 < height - 1) {
					tiles[x + 0][y - 2] = true;
					tiles[x + 1][y - 2] = true;
					tiles[x + 1][y - 1] = true;
					tiles[x + 3][y - 1] = true;
					tiles[x + 0][y + 0] = true;
					tiles[x + 1][y + 0] = true;
					tiles[x + 2][y + 0] = true;
					tiles[x + 3][y + 0] = true;
					tiles[x + 4][y + 0] = true;
					tiles[x + 5][y + 0] = true;
					tiles[x + 0][y + 1] = true;
					tiles[x + 1][y + 1] = true;
					tiles[x + 2][y + 1] = true;
					tiles[x + 3][y + 1] = true;
					tiles[x + 5][y + 1] = true;
					tiles[x + 1][y + 2] = true;
					tiles[x + 3][y + 2] = true;
					tiles[x + 4][y + 2] = true;
					tiles[x + 0][y + 3] = true;
					tiles[x + 1][y + 3] = true;
					tiles[x + 2][y + 3] = true;
					tiles[x + 3][y + 3] = true;
					tiles[x + 4][y + 3] = true;
					tiles[x + 5][y + 3] = true;

					que.add(new Point(x + 6, y + 0));
					return false;
				}

			}// end of cases, add more above

			if (cycles >= cycleLimit) {
				System.out.println("reached end of pullNewPreset cycle");
				return true;
			}

		} // end of while
		return false;

	}

}
