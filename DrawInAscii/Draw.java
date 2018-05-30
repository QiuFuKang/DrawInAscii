import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


public class Draw {

	public static void main(String[] args) {
		//源图片
		File file = new File(args[0]);
		ImageTools iTools = new ImageTools();
		//获取灰度
		int[][] list = iTools.getGray(file);
		//给灰度分级
		int[][] levelList = iTools.getGrayLevel(list);
		
		//生成的图片宽=数组长度*一个字符被打印出来所占的长度
		int width=(int)(levelList[0].length * 10);
		int height=(int)(levelList.length * 9.9f);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		CharacterTools cTools = new CharacterTools();
		int x = 0,y = 0;
		//绘图
		for (int i = 0;i < levelList.length;i++) {
			for (int j = 0;j < levelList[0].length;j++) {
				char c = cTools.getChar(levelList[i][j]);
				g.drawString(c+"", x, y);
				x += 10;
			}
			x = 0;
			y += 10;
		}
		g.dispose();
		
		//输出图片
		Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("jpg");
   	 	ImageWriter writer = it.next();
   	 	try {
			ImageOutputStream ios = ImageIO.createImageOutputStream(new File("asciiImage.jpg"));
			writer.setOutput(ios);
			writer.write(image);
			image.flush();
			ios.flush();
			ios.close();
			System.out.println("转换完成");
   	 	} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
