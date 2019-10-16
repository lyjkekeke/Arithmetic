package arithmetic;


public class Creater {

	formJudge fj = new formJudge();
	//创建构造函数
	public Creater() {
		
	}
	//创建一个新题目
	/*创建思路：首先通过随机数来确定生成的题目中有几个数字，然后通过for循环
	 * 循环生成2*n个随机数，这时每两个随机数可以组成一个分数，并且根据要求的
	 * 形式化简，接着通过随机数来判断生成的运算符从而随机生成n-1个运算符，然后将
	 * 这些按顺序连接成字符串返回*/
	public numItem pro_creater(int range) {
		//创建rc，rn，ro来存储随机数从而随即得到
		int rc,rn,ro;
		numItem str = new numItem();
		String [] ch = new String[] {"+","-","×","÷"};
			rc = (int)(Math.random()*3+2);//随机生成2~4的数来判断生成几个数
			int [] a = new int[2*rc]; 
			for(int i=0; i < 2*rc; i++) {
				a[i] = 1;
			}
			String [] num_str = new String[rc];
			String [] num_str1 = new String[rc];
			String [] b = new String[rc-1];
			for(int j = 0; j < rc; j++) {
				rn = (int)(Math.random()*(2-0));//随机生成0和1判断生成真分数或整数和带分数
				if(rn == 1) {
					a[2*j+1] = (int)(Math.random()*range+1);
					a[2*j] = (int)(Math.random()*(a[2*j+1]-1)+1);
					num_str[j] = fj.proSimple(a[2*j],a[2*j+1]);
				}
				else{
					a[2*j] = (int)(Math.random()*range+1);
					a[2*j+1] = (int)(Math.random()*(a[2*j]-1)+1);
					num_str[j] = fj.improSimple(a[2*j],a[2*j+1]);
				}
				
				num_str1[j] = a[2*j] + "/" + a[2*j+1];
			}
				
			for(int k =0; k < rc-1; k++) {
				ro = (int)(Math.random()*(4-0));//生成0到3的随机数来判断生成什么运算符
					b[k] = ch[ro];
			}
			
			//加括号
			str = parent_creater(rc,num_str,num_str1,b);	
			
		return str;
	}
	//添加括号
	/*思路：生成一个随机数来判断生成多少个括号，左括号和右括号必须成对出现，构建一个
	 * 最终字符串数组，一个左括号位置数组和一个右括号位置数组，如果是两个数字则不用生
	 * 成括号，如果是两个以上则随机生成最多n/2+1个括号，生成该范围内的随机数来判断生
	 * 成多少括号，先生成随机左括号的位置，然后根据左括号位置随机生成右括号位置，并将
	 * 两个同时放入数组，若右括号位置找出范围则停止，最后将他们和已有字符连接生成最终式子*/
	public numItem parent_creater(int rc, String [] num_str, String [] num_str1, String [] b) {
		numItem str = new numItem();
		String [] spl = new String[rc];
		String [] spr = new String[rc];
		for(int i = 0; i < rc; i++) {
			spl[i] = "";
			spr[i] = "";
		}
		int rp = (int)(Math.random()*(rc/2+1-0));
			if(rc == 2) {
				str.newstr = num_str[0] + " " + b[0] + " " + num_str[1];
				str.oldstr = num_str1[0] + " " + b[0] + " " + num_str1[1];
			}
			else {
				int lmin = 0;
				for(int i = 0; i < rp; i++) {
					int rpl = (int)(Math.random()*(rc-1-lmin)+lmin);//生成左括号位置
      				int rpr = (int)(Math.random()*(rc-rpl-1)+rpl+1);//根据左括号位置生成右括号位置
					if(rpl == 0 && rpr ==rc-1) {
						i--;
						continue;
					}
					if(rpr > rc-1) break;
					spl[rpl] = "(";
					spr[rpr] = ")";
					lmin = rpr+1;
					
				}
				if(spl[0] == "") {
					str.newstr = num_str[0] + " " + spr[0];
					str.oldstr = num_str1[0] + " " + spr[0];
				}
				else if(spr[0] == "") {
					str.newstr = spl[0] + " " + num_str[0];
					str.oldstr = spl[0] + " " + num_str1[0];
				}
				else{
					str.newstr = spl[0] + " " + num_str[0] + " " + spr[0];
					str.oldstr = spl[0] + " " + num_str1[0] + " " + spr[0];
				}
				for( int i = 1; i < rc; i++) {					
					str.newstr = str.newstr + " " + b[i-1] + " " + spl[i] + " " + num_str[i] + " " + spr[i]; 
					str.oldstr = str.oldstr + " " + b[i-1] + " " + spl[i] + " " + num_str1[i] + " " + spr[i];
				}
			}
		return str;
	}
	
}

//一个式子的存储形式
class numItem {
	String oldstr;
	String newstr;
	String ans;
	
}