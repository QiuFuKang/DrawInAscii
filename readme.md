# Ascii作图
ascii作图或者说骰子作图的原理十分简单，将图片平均分成多个小块，将每个小块的平均灰度求出，按照自己的需要将灰度平分为多个等级，将同一等级的图块用同一种字符替换就完成了
- 声明每个小块的边长，影响到图片的精细程度,这里将9*9的图块替换为一个字符
`public static int PIXEL_SIZE = 9`
- 获取图片灰度，这里使用位运算转换颜色，这里有详细介绍[关于位运算看这个就够了](https://juejin.im/post/5a5886bef265da3e38496fd5)
```
public static int[][] getGray(File file) {
	BufferedImage bi = null;
	//读取本地图片
	try {
		bi = ImageIO.read(file);
	} catch (IOException e) {
		e.printStackTrace();
	}
	//获取图片尺寸
	int width = bi.getWidth();
	int height = bi.getHeight();
	int minx = bi.getMinX();
	int miny = bi.getMinY();
	//用于存储单个颜色的值
	int[] rgb = new int[3];
	//用一个二维数组存储整个图像的灰度
	int[][] list = new int[height][width];
	//遍历每个像素
	for (int i = minx;i < height;i++) {
		for (int j = miny;j < width;j++) {
			//位运算获取r、g、b值
			int pixel = bi.getRGB(j, i);
			rgb[0] = (pixel >> 16) & 0xFF;
			rgb[1] = (pixel >> 8) & 0xFF;
			rgb[2] = (pixel) & 0xFF;
			//公式计算出灰度并保存
			list[i][j] = (int)(rgb[0]*0.3 + rgb[1]*0.59 + rgb[2]*0.11);
		}
	 }
	return list;
}
```
- 上面我们只是求出所有像素的灰度，我们还要分成多个块，首先是如何求出一个小块的平均灰度
```
//x、y为起始坐标，list为刚才获得的灰度二维数组
public static int getListAvg(int x, int y, int[][] list) {
	int sum = 0, avg = 0;
	//获取从（x, y）坐标开始一个小块的平均灰度
	for (int i = x;i < PIXEL_SIZE + x;i++) {
		for (int j = y;j < PIXEL_SIZE + y;j++) {
			sum += list[i][j];
			avg = (int)(sum/(PIXEL_SIZE*PIXEL_SIZE));
		}
	}
	return avg;
}
```
- 接下来获取整张图像的灰度分级
```
//传入保存所有像素灰度的二维数组
public static int[][] getGrayLevel(int[][] list) {
	//声明一个保存每个小块灰度等级的二维数组
	int[][] levelList = 
			new int[list.length/PIXEL_SIZE][list[0].length/PIXEL_SIZE];
	for (int i = 0;i < (int)(list.length/PIXEL_SIZE);i++) {
		for (int j = 0;j < (int)(list[0].length/PIXEL_SIZE);j++) {
			//调用上面获取一个小块平均灰度方法
			int avg = getListAvg(i * PIXEL_SIZE, j * PIXEL_SIZE, list);
			//这里将灰度分成9个等级，那么每个等级区间大小为28.5（255/9）
			levelList[i][j] = (int)(avg/28.5f);
		}
	}
	return levelList;
}
```
- 到这里就差不多完成了，剩下的就是把灰度等级替换成对应字符，重新绘制出来
按照灰度等级替换对应字符
```
public static char getChar(int x) {
	char c = ' ';
	switch (x) {
	case 0:
		c = ' ';
		break;
	case 1:
		c = '-';
		break;
	case 2:
		c = '_';
		break;
	case 3:
		c = 'c';
		break;
	case 4:
		c = '=';
		break;
	case 5:
		c = 'o';
		break;
	case 6:
		c = 'e';
		break;
	case 7:
		c = 'p';
		break;
	case 8:
		c = 'W';
		break;
	default:
		c = ' ';
		break;
	}
	return c;
}
//将灰度等级替换为字符
public static char[][] levelToChar(int[][] levelList) {
	char[][] cList= new char[levelList.length][levelList[0].length];
	for (int i = 0;i < levelList.length;i++) {
		for (int j = 0;j < levelList.length;j++) {
			cList[i][j] = getChar(levelList[i][j]);
		}
	}
	return cList;
}
```
- 到这里就可以打印数组或者绘制图像看效果了
重新绘制
```
//获取全图灰度
int[][] list = getGray(file);
//分块获取灰度等级
int[][] levelList = getGrayLevel(list);
//计算新图像尺寸
int width=(int)(levelList[0].length * 9);
int height=(int)(levelList.length * 9);
//创建新图像
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//画笔
Graphics g = image.getGraphics();
//绘制坐标
int x = 0,y = 0;
for (int i = 0;i < levelList.length;i++) {
	for (int j = 0;j < levelList[0].length;j++) {
		//获取对应等级字符
		char c = getChar(levelList[i][j]);
		//绘制
		g.drawString(c+"", x, y);
		//横坐标移动
		x += PIXEL_SIZE;
	}
	//换行，横坐标归零
	x = 0;
	y += PIXEL_SIZE;
}
g.dispose();
//保存图像
try {
   ImageIO.write(screen,"jpg",new File("asciiImage.jpg"));
} catch (IOException e) {
	e.printStackTrace();
 }
```
- 效果展示
源图片:
![enter image description here](https://raw.githubusercontent.com/QiuFuKang/DrawInAscii/master/DrawInAscii/sourceImage.jpg)
转换后:
![enter image description here](https://raw.githubusercontent.com/QiuFuKang/DrawInAscii/master/DrawInAscii/asciiImage.jpg)
- 完整源码github:[https://github.com/fanxquer/DrawInAscii](https://github.com/fanxquer/DrawInAscii)
- 参考：[骰子作画的算法](http://www.ruanyifeng.com/blog/2011/11/dice_portrait.html)
