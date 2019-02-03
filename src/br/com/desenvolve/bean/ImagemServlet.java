package br.com.desenvolve.bean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@WebServlet("/image")
public class ImagemServlet extends HttpServlet {
	
	 private static final long serialVersionUID = 1460571643688705941L;


	    private String imagePath;


	    public void init() throws ServletException {


	        //this.imagePath = "c:/qr/";
	        this.imagePath = this.getServletContext().getRealPath("/resources/qr/");


	    }

	    // Actions ------------------------------------------------------------------------------------

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	String requestedImage = null;
	    try{
	        requestedImage = request.getParameter("imagem");

	        File image = new File(imagePath, requestedImage);

	        String contentType = getServletContext().getMimeType(image.getName());

	        response.reset();
	        response.setContentType(contentType);
	        response.setHeader("Content-Length", String.valueOf(image.length()));

	        Files.copy(image.toPath(), response.getOutputStream());
	    }catch (Exception e) {
			
			int qrTanAncho = 200;
			int qrTanAlto = 200;
			String formato = "png";
			String ruta = this.imagePath+requestedImage;
			
			//String dato = bovino.getCodigo()+"";
			String dato = requestedImage.replaceAll(".png","");
			
			BitMatrix matriz = null;
			Writer write = new QRCodeWriter();
			
			try {
				matriz = write.encode(dato, BarcodeFormat.QR_CODE, qrTanAlto, qrTanAncho);
			} catch (WriterException ex) {
				ex.printStackTrace();
			}
			
			BufferedImage imagen = new BufferedImage(qrTanAlto, qrTanAncho, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < qrTanAlto; x++) {
				for (int y = 0; y < qrTanAncho; y++) {
					int valor2 = (matriz.get(x, y) ? 0 : 1) & 0xff;
					imagen.setRGB(x, y, (valor2 == 0 ? 0 : 0xFFFFFF));
				}
			}
			
			FileOutputStream codigo2 = new FileOutputStream(ruta);
			ImageIO.write(imagen, formato, codigo2);
			codigo2.close();
			
	    }

	    }

	}
