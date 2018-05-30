public class CharacterTools {
	/**
	 * ��ȡ�Ҷȵȼ���Ӧ���ַ�
	 * @param x �Ҷȵȼ�
	 * @return �ַ�
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
	 * ���Ҷȵȼ�����ת�����ַ�����
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

