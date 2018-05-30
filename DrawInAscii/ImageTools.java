import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageTools{
	//将PIXEL_SIZE*PIXEL_SIZE像素的图像用一个字符覆盖
	//修改此变量以改变生成图片的精细度(越小越精细)
	public static int PIXEL_SIZE = 9;
	
	/**
	 * 获取图片每个像素的灰度值存于一个数组
	 * @param file 图片文件流
	 */
	public int[][] getGray(File file) {
		BufferedImage bi = null;
   	 	try {
			bi = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
   	 	int width = bi.getWidth();
   	 	int height = bi.getHeight();
   	 	int minx = bi.getMinX();
   	 	int miny = bi.getMinY();
   	 	int[] rgb = new int[3];
   	 	int[][] list = new int[height][width];
   	 	//遍历所有像素
   	 	for (int i = minx;i < height;i++) {
	   		for (int j = miny;j < width;j++) {
	   			//获取RPG值
	   			int pixel = bi.getRGB(j, i);
	   			rgb[0] = (pixel >> 16) & 0xFF;
	   		 	rgb[1] = (pixel >> 8) & 0xFF;
	   		 	rgb[2] = (pixel) & 0xFF;
	   		 	//计算灰度值
	   		 	list[i][j] = (int)(rgb[0]*0.3 + rgb[1]*0.59 + rgb[2]*0.11);
	   		}
   	 	}
   	 	return list;
	}
	
	/**
	 * 求出每个区域的灰度平均值，按照8个等级划分
	 * @param list 保存每个像素的灰度数组
	 */
	public int[][] getGrayLevel(int[][] list) {
		//创建合适大小的数组
		int[][] levelList = 
				new int[list.length/PIXEL_SIZE][list[0].length/PIXEL_SIZE];
		for (int i = 0;i < (int)(list.length/PIXEL_SIZE);i++) {
			for (int j = 0;j < (int)(list[0].length/PIXEL_SIZE);j++) {
				//获取一个区域灰度的平均值
				int avg = getListAvg(i * PIXEL_SIZE, j * PIXEL_SIZE, list);
				//划分等级
				levelList[i][j] = (int)(avg/28.5f);
			}
		}
		return levelList;
	}
	
	/**
	 * 求出一个二维数组以某个引用开始的区域的灰度平均值
	 * @param x 引用
	 * @param y 引用
	 * @param list 使用的数组
	 */
	public int getListAvg(int x, int y, int[][] list) {
		int sum = 0, avg = 0;
		for (int i = x;i < PIXEL_SIZE + x;i++) {
			for (int j = y;j < PIXEL_SIZE + y;j++) {
				sum += list[i][j];
				avg = (int)(sum/(PIXEL_SIZE*PIXEL_SIZE));
			}
		}
		return avg;
	}
	
	/**
	 * 用对应字符代替灰度等级打印数组
	 */
	public void PrintImage(int[][] levelList) {
		CharacterTools cTools = new CharacterTools();
		for (int i = 0;i < levelList.length;i++) {
			for (int j = 0;j < levelList[0].length;j++) {
				char c = cTools.getChar(levelList[i][j]);
				System.out.print(c + " ");
			}
			System.out.println();
		}
	}
}
