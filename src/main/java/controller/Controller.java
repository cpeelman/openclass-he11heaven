package controller;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db.ImageDb;

@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ImageDb imageDb;

	public Controller() {
		super();
		imageDb = new ImageDb();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleAction(request, response);
	}

	private void handleAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String destination = "";
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		switch (action) {
		case "uploadimage":
			uploadImage(request, response);
			break;
		case "downloadimage":
			downloadImage(request, response);
			break;
		case "imageoverview":
			destination = imageOverview(request, response);
		default:
			break;
		}
		if (destination != "") {
			RequestDispatcher rd = request.getRequestDispatcher(destination);
			rd.forward(request, response);
		}
	}

	private void uploadImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Part file = request.getPart("image");
		//String fileName = request.getParameter("image");
		//File file = new File(this.getImageDirectory(),fileName);
		//BufferedImage image = ImageIO.read(file);
		this.imageDb.addNewImage(file);
		response.sendRedirect("Controller?action=imageOverview");
	}
	
	public String getImageDirectory(){
		return "UCLL/project2coaching/images";
	}

	private void downloadImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String fileName = request.getParameter("fileName");
		String fileFormat = fileName.substring(fileName.indexOf(".") + 1);

		response.setHeader("Content-Disposition", "attachment; filename=\"download." + fileFormat + "\"");
		ImageIO.write(this.imageDb.getImage(fileName), fileFormat, response.getOutputStream());
		response.sendRedirect("Controller?action=imageOverview");
	}

	private String imageOverview(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("images", imageDb.getImages());
		return "imageOverview.jsp";
	}
}
