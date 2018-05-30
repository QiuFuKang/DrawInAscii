import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageTools{
	//��PIXEL_SIZE*PIXEL_SIZE���ص�ͼ����һ���ַ�����
	//�޸Ĵ˱����Ըı�����ͼƬ�ľ�ϸ��(ԽСԽ��ϸ)
	public static int PIXEL_SIZE = 9;
	
	/**
	 * ��ȡͼƬÿ�����صĻҶ�ֵ����һ������
	 * @param file ͼƬ�ļ���
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
   	 	//������������
   	 	for (int i = minx;i < height;i++) {
	   		for (int j = miny;j < width;j++) {
	   			//��ȡRPGֵ
	   			int pixel = bi.getRGB(j, i);
	   			rgb[0] = (pixel >> 16) & 0xFF;
	   		 	rgb[1] = (pixel >> 8) & 0xFF;
	   		 	rgb[2] = (pixel) & 0xFF;
	   		 	//����Ҷ�ֵ
	   		 	list[i][j] = (int)(rgb[0]*0.3 + rgb[1]*0.59 + rgb[2]*0.11);
	   		}
   	 	}
   	 	return list;
	}
	
	/**
	 * ���ÿ������ĻҶ�ƽ��ֵ������8���ȼ�����
	 * @param list ����ÿ�����صĻҶ�����
	 */
	public int[][] getGrayLevel(int[][] list) {
		//�������ʴ�С������
		int[][] levelList = 
				new int[list.length/PIXEL_SIZE][list[0].length/PIXEL_SIZE];
		for (int i = 0;i < (int)(list.length/PIXEL_SIZE);i++) {
			for (int j = 0;j < (int)(list[0].length/PIXEL_SIZE);j++) {
				//��ȡһ������Ҷȵ�ƽ��ֵ
				int avg = getListAvg(i * PIXEL_SIZE, j * PIXEL_SIZE, list);
				//���ֵȼ�
				levelList[i][j] = (int)(avg/28.5f);
			}
		}
		return levelList;
	}
	
	/**
	 * ���һ����ά������ĳ�����ÿ�ʼ������ĻҶ�ƽ��ֵ
	 * @param x ����
	 * @param y ����
	 * @param list ʹ�õ�����
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
	 * �ö�Ӧ�ַ�����Ҷȵȼ���ӡ����
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
