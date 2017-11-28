import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DownloadPhoto extends JFrame {
	public DownloadPhoto(InputStream is) throws IOException {
		this.setTitle("download");
		this.setSize(400, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel f = new JPanel();
		this.add(f);
//		Image img = Toolkit.getDefaultToolkit().createImage(buffer);
//		BufferedImage img=ImageIO.read(new File(fileName));
		 Image img = ImageIO.read(is);
		ImageIcon icon = new ImageIcon(img);
		JLabel l = new JLabel();

		l.setIcon(icon);
		f.add(l);
		
	}

	public  void doMain(String[] args) throws Exception {
		try {
			String fileName = "10-dithering-opt.jpg";
			String website = "https://www.smashingmagazine.com/wp-content/uploads/2015/06/" + fileName;
//			String website = "D:\\aobongda\\123456.jpg" ;
			System.out.println("Downloading File From: " + website);

			URL url = new URL(website);
			InputStream inputStream = url.openStream();
			InputStream inputStreamTemp = url.openStream();
			OutputStream outputStream = new FileOutputStream(fileName);
			byte[] buffer = new byte[2048];
			int length = 0;

			while ((length = inputStream.read(buffer)) != -1) {
				System.out.println("Buffer Read of length: " + length);
				outputStream.write(buffer, 0, length);
			}
			
			DownloadPhoto d = new DownloadPhoto(inputStreamTemp);
			d.setVisible(true);
			
			inputStream.close();
			outputStream.close();
			
		      
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}