# Ascii作图
ascii作图或者说骰子作图的原理十分简单，将图片平均分成多个小块，将每个小块的平均灰度求出，按照自己的需要将灰度平分为多个等级，将同一等级的图块用同一种字符替换就完成了
- 声明每个小块的边长，影响到图片的精细程度,这里将9*9的图块替换为一个字符
```
public static int PIXEL_SIZE = 9
```
##### 主要方法
- 获取图片灰度，这里使用位运算转换颜色，这里有详细介绍[关于位运算看这个就够了](https://juejin.im/post/5a5886bef265da3e38496fd5)
```
public static int[][] getGray(File file) {}
```
- 上面我们只是求出所有像素的灰度，我们还要分成多个块，首先是如何求出一个小块的平均灰度
```
//x、y为起始坐标，list为刚才获得的灰度二维数组
public static int getListAvg(int x, int y, int[][] list) {}
```
- 接下来获取整张图像的灰度分级
```
//传入保存所有像素灰度的二维数组
public static int[][] getGrayLevel(int[][] list) {}
```
- 按照灰度等级替获取对应字符
```
//按照等级获取字符
public static char getChar(int x) {}
//将灰度等级二维数组的值为字符，获得字符二维数组
public static char[][] levelToChar(int[][] levelList) {}
```
##### 主要流程
```
//获取全图灰度
int[][] list = getGray(file);
//分块获取灰度等级
int[][] levelList = getGrayLevel(list);
//计算新图像尺寸
int width=(int)(levelList[0].length * PIXEL_SIZE);
int height=(int)(levelList.length * PIXEL_SIZE);
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
- 效果展示<br>
源图片:<br>
![enter image description here](https://raw.githubusercontent.com/QiuFuKang/DrawInAscii/master/DrawInAscii/sourceImage.jpg)<br>
转换后:<br>
![enter image description here](https://raw.githubusercontent.com/QiuFuKang/DrawInAscii/master/DrawInAscii/asciiImage.jpg)
- 参考：[骰子作画的算法](http://www.ruanyifeng.com/blog/2011/11/dice_portrait.html)
