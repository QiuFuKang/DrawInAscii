public class CharacterTools {
	/**
	 * 获取灰度等级对应的字符
	 * @param x 灰度等级
	 * @return 字符
	 */
	public char getChar(int x) {
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
	
	/**
	 * 将灰度等级数组转换成字符数组
	 */
	public char[][] levelToChar(int[][] levelList) {
		char[][] cList= new char[levelList.length][levelList[0].length];
		for (int i = 0;i < levelList.length;i++) {
			for (int j = 0;j < levelList.length;j++) {
				cList[i][j] = getChar(levelList[i][j]);
			}
		}
		return cList;
	}
}

