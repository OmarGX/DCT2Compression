package application;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jtransforms.*;
import org.jtransforms.dct.DoubleDCT_2D;
import org.apache.commons.math3.*;
import pl.edu.icm.jlargearrays.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ApplicationController {
	
	@FXML
	private javafx.scene.layout.AnchorPane AnchorPane;
	
	@FXML
	private Button UploadButton;
	
	@FXML
	private Button TransformButton;
	
	@FXML
	private TextField Ffield;
	
	@FXML
	private TextField Dfield;
	
	@FXML
	private ImageView ImgView1;
	
	@FXML
	private ImageView ImgView2;
	
	public boolean isImgUploaded = false;
	
	public File file;
	
	public Image img;
	
	private double ImgHeight;
	
	private double ImgWidth;
	
	public double getImgHeight() {
		return ImgHeight;
	}

	public void setImgHeight(double imgHeight) {
		ImgHeight = imgHeight;
	}

	public double getImgWidth() {
		return ImgWidth;
	}

	public void setImgWidth(double imgWidth) {
		ImgWidth = imgWidth;
	}
	
	@FXML
	private void UploadImg(final ActionEvent e) 
	{
		Stage stage = (Stage) AnchorPane.getScene().getWindow();

		final FileChooser fileChooser = new FileChooser();
		file = fileChooser.showOpenDialog(stage);
        if (file != null) { // only proceed, if file was chosen
            img = new Image(file.toURI().toString());
            ImgView1.setImage(img);
            ImgView2.setImage(null);
            isImgUploaded = true;
            setImgHeight(img.getHeight());
            setImgWidth(img.getWidth());
        }
		

	}
	
	@SuppressWarnings("null")
	@FXML
	public void ComputeDCT2(final ActionEvent e) throws IOException {
		BufferedImage image = ImageIO.read(file);
		int dimensions = Integer.parseInt(Ffield.getText());
		int factor = Integer.parseInt(Dfield.getText());
		double [] [] TempMatrix = new double[dimensions][dimensions];
		double [] [] finalMatrix = new double[(int) getImgHeight()][(int) getImgWidth()];
		DoubleDCT_2D dct2 = new DoubleDCT_2D(dimensions,dimensions);
		
		for(int i=0;i<(int)(getImgHeight()-(getImgHeight()%dimensions)); i+=dimensions) {
			for(int j = 0; j<(int)(getImgWidth()-(getImgWidth()%dimensions));  j+=dimensions) {
				BufferedImage tempImage = image.getSubimage(j, i, dimensions, dimensions);
				for(int c = 0; c< tempImage.getWidth();c++) {
					for(int h=0; h< tempImage.getHeight();h++) {
						TempMatrix[c][h] = tempImage.getRGB(h,c)& 0xFF;
					}
				}
				dct2.forward(TempMatrix, true);
				for(int c = 0; c < dimensions;c++) {
					for(int h = 0; h < dimensions ;h++) {
						if(c + h >= factor) {
							TempMatrix[c][h] = 0.0;
						}
					}
				}
				dct2.inverse(TempMatrix, true);
				for(int c = 0; c< dimensions;c++) {
					for(int h=0; h< dimensions;h++) {
							TempMatrix[c][h] = Math.round(TempMatrix[c][h]);
							TempMatrix[c][h] = Math.max(0, Math.min(255, TempMatrix[c][h]));
					}
				}
				for(int c = 0; c< dimensions ;c++) {
					for(int h=0; h< dimensions;h++) {
							finalMatrix[i+c][j+h] = TempMatrix[c][h];
					}
				}
			}
		}
		try {
		    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(),BufferedImage.TYPE_INT_RGB);
		    for(int i=0; i< image.getHeight(); i++) {
		        for(int j=0; j< image.getWidth(); j++) {
		            int a =  (int)finalMatrix[i][j]<<16 | (int)finalMatrix[i][j] << 8 | (int)finalMatrix[i][j];
		            newImage.setRGB(j,i,a);
		        }
		    }
		    File output = new File("NewImage.bmp");
		    ImageIO.write(newImage, "bmp", output);
		    Image newImg = new Image(output.toURI().toString());
		    ImgView2.setImage(newImg);
		}

		catch(Exception ex) {
			System.out.print(ex.getMessage());
		}
		
	}



}