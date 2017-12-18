package db;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

public class ImageDb {
	private Map<String, BufferedImage> images;
	private int count;

	public ImageDb() {
		this.count = 0;
	}

	public void addNewImage(Part file) {
		try {
			String fileName = "image" + (count++)
					+ file.getSubmittedFileName().substring(file.getSubmittedFileName().lastIndexOf("."));
			BufferedImage image = ImageIO.read(file.getInputStream());
			this.images.put(fileName, image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}