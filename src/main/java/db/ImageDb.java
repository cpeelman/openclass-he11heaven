package db;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;

public class ImageDb {
	private Map<String, BufferedImage> images;
	private int count;

	public ImageDb() {
		this.count = 0;
		//map nog initialiseren
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
	
	public RenderedImage getImage(String fileName){
		return images.get(fileName);
	}
	
	public List<BufferedImage> getImages(){
		List<BufferedImage> allImages = new ArrayList<>();
		for (BufferedImage image : images.values()) {
			allImages.add(image);
		}
		return allImages;
	}
}
