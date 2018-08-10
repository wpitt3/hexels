package hexels

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class IRW {
	public static List convertImageToArray(BufferedImage bufferedImage) {
		Integer width = bufferedImage.getWidth();
		Integer height = bufferedImage.getHeight();
		List image = new int [width][height][3];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
			int colour = bufferedImage.getRGB(x, y);
			image[x][y][0] = (colour & 0x00ff0000) >> 16;
			image[x][y][1] = (colour & 0x0000ff00) >> 8;
			image[x][y][2] = colour & 0x000000ff;
			}
		}
		return image;
	}
	
	public static BufferedImage convertArrayToImage(List image) {
		Integer width = image.size() 
		Integer height = image[0].size()
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				bufferedImage.setRGB(x, y, new Color(image[x][y][0], image[x][y][1], image[x][y][2]).getRGB());;
			}
		}
		return bufferedImage;
	}
	
	public static void writeBufferedImage(BufferedImage image, String fileName) {
		File outputfile = new File(fileName);
	    try {
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
		}
	}
	
	public static BufferedImage readBufferedImage(String fileName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
		}
		return image;
	}
}
