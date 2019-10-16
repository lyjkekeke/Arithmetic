package arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//查重类
public class DupCheck {

	DupCheck(){
		
	}
	
	//将后缀表达式转化为可以查重的表达式的形式，即按照计算过程排列的表达式
	/*由于运算结果时会用到后缀表达式，在这里将后缀表达式转换成可以查重的表达式
	 * (过程运用堆栈且基本和计算后缀表达式一的做法).思路主要是以后缀表达式的
	 * 长度循环，如果遇到数字就压栈，遇到运算符就连续出栈两个数字字符，出栈后将“#”
	 * 压入数字栈，这个字符就类似计算后缀表达式时的结果，完成循环后可得到查重的表达式*/
	public List<String> getDupExpression(List<Item> postfixes) {
		List<String> dup = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();
        for (Item item : postfixes) {
            if (item.isNumber()) {
                stack.push(item.value);
            } else {
                String result = "#",num1,num2;
                dup.add(item.value);
                num1 = stack.pop();
                num2 = stack.pop();
                if(num1 != "#") {
                	dup.add(num1);
                }else {
                	dup.add("null");
                }
                if(num2 != "#") {
                	dup.add(num2);
                }else {
                	dup.add("null");
                }
                stack.push(result);            
            }
         }
		return dup;
	}
	//判断两个字符串是否重复
	public boolean dupCheck(String str1,String str2) {

		formJudge jd = new formJudge();
		List<String> s1 = getDupExpression(jd.infix2postfix(jd.parse(str1)));//将字符串s1转化成字符列表
		List<String> s2 = getDupExpression(jd.infix2postfix(jd.parse(str2)));//将字符串s2转化成字符列表
		if(s1.equals(s2)) return true;//若两列表内容相同返回true
		for(int j = 0; j < s2.size()-1; j++) {//若不同进行循环将+或×后的两个字符串交换看能否相同
			if(s2.get(j).equals("+")||s2.get(j).equals("×")) {
				String temp = s2.get(j+1);
				s2.set(j+1,s2.get(j+2));
				s2.set(j+2,temp);
			}
			j*=3;
			if(s1.equals(s2)) return true;//若交换后相同则返回true
		}
		return false;
	}
	//判断一个字符串是否在另一个字符串数组里
	public boolean muldupcheck(String str,String [] str1) {
		if(str1 == null) return false;
		try {
			for(int i = 0; i < str1.length; i++) {
				if(dupCheck(str,str1[i])) return true;
			}
		}catch(Exception ee) {
				ee.printStackTrace();
		}
		return false;
	}
}
