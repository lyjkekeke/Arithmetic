package arithmetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class formJudge {

	public formJudge() {
		
	}
	
	public String proSimple(int a, int b){
		String str = null;
		int value = 0;
		int a1 = a,b1 = b;
		while(b1 != 0) {
			value = a1 % b1;
			a1 = b1;
			b1 = value;
		}
		a = a/a1;
		b = b/a1;
		if(a % b == 0) {
			str = Integer.toString(a/b);
		}
		else {
			str = a + "/" + b;
		}
		return str;
	}
	
	public String improSimple(int a, int b){
		String str = null;
		int value = 0;
		int a1 = a,b1 = b,c1 = 0;
		value = a1/b1;
		if(a1 % b1 == 0) {
			str = Integer.toString(value);
		}
		else {
			c1 = a1 % b1;
			str = proSimple(c1,b);
			str = value + "'" + str;
		}
		return str;
	}
    //计算结果
    public String calculate(String calStr) {
        List<Item> infixes = parse(calStr);//将字符串转化为合理的列表
        List<Item> postfixes = infix2postfix(infixes);//将得到的列表转化成后缀表达式列表
        return calculateByPostfix(postfixes);//通过后缀表达式计算结果并返回
    }
    //用四则表达式匹配实现将字符串转化成合理的列表
    public List<Item> parse(String calStr) {
        Pattern pattern = Pattern.compile("\\d+\\/\\d+|\\+|\\-|\\×|\\÷|\\(|\\)|\\d+\\'\\d+\\/\\d+|\\d+");
        Matcher m = pattern.matcher(calStr);
        List<Item> items = new ArrayList<Item>();
        while (m.find()) {
            items.add(new Item(m.group(0)));
        }
        return items;
    }
    
    //中缀表达式转换为后缀表达式算法
    /* 1.如果读到数，将它放到输出中。
     * 2.如果读到的是操作符则需要接着判断是否该入栈。
     * 2.1 若读到的是左圆括号则入栈。
    * 2.2 如果遇到一个右括号，那么就将栈中元素弹出并输出直至遇到左括号为止。但是这个左括号只被弹出，并不输出。
    * 3.在读到操作符时，如果此操作符优先级小于或等于此时栈顶操作符，则将栈中元素弹出直到以下情况
    * 3.1 遇到左括号
    * 3.2 栈顶元素为更低优先级
    * 3.3 栈为空
       * 注：操作符中，'+''-'优先级最低，'('')'优先级最高
    * 4.如果读到输入的末尾，将栈元素弹出直到该栈变成空栈，将符号写到输出中。*/           
 
    public List<Item> infix2postfix(List<Item> infixes) {
        List<Item> postfixes = new ArrayList<Item>();//创建列表存储
        Stack<Item> stack = new Stack<Item>();//新建栈存储中间操作符
        for (Item item : infixes) {//遍历中缀表达式列表
        	if (item.isBlank()) {//若为空格，则继续便遍历
        		continue;
        	}
            if (item.isNumber()) {//若为数字则直接输出
                postfixes.add(item);
            } else if (item.isRightBracket()) {//若为右括号则将元素弹出直到遇到左括号
                while (true) {
                    Item tmp = stack.pop();
                    if (tmp.isLeftBracket())
                        break;
                    postfixes.add(tmp);
                }
            } else if (item.isLeftBracket()) {//若为左括号将左括号压入栈
                stack.push(item);
            } else {
                if (stack.isEmpty()) {//若栈为空则将当前字符压入栈
                    stack.push(item);
                    continue;
                }
                Item top = stack.peek();
                if (top.isLeftBracket()) {//若为左括号将其直接压入栈
                    stack.push(item);
                    continue;
                }
                if (item.getPriority() <= top.getPriority()) {//判断优先级，若当前优先级小于等于栈顶，则将栈中元素弹出直到思路中提到的3.1，3.2，3.3三种情况
                    while (true) {
                        Item tmp = stack.peek();
                        if (tmp.isLeftBracket() || tmp.getPriority() < item.getPriority()) {
                            break;
                        }
                        postfixes.add(tmp);
                        stack.pop();
                        if (stack.isEmpty())
                            break;
                    }
                    stack.push(item);
                } else {
                    stack.push(item);
                }
            }
        }
        while (!stack.isEmpty()) {//若结束后栈不为空则直接输出剩余元素
            postfixes.add(stack.pop());
        }
        return postfixes;
    }

    //将分数的字符串形式转化成由int型分子分母组成的Number形式
    public Number returnNum(String value) {
    	Number num = new Number();
    	try {
        	int index = value.indexOf('/');
       	    num.a = Integer.parseInt(value.substring(0, index));
       	    num.b = Integer.parseInt(value.substring(index+1));
        } catch (Exception ignore) {
        }
    	return num;
    }
    //通过后缀表达式计算数值
     /* 1. 从左到右遍历表达式的每个数字和符号
      * 1.1 若是数字则进栈
      * 1.2 若是运算符则将栈顶两个元素出栈,进行运算并将运算结果进栈
     * 2. 遍历完后缀表达式,此时栈中剩余的数字就是运算结果
     */
    private String calculateByPostfix(List<Item> postfixes) {
        Stack<String> stack = new Stack<String>();
        for (Item item : postfixes) {//遍历后缀表达式
            if (item.isNumber()) {//若为数则直接入栈
                stack.push(item.value);
            } else {//若为操作符则将栈顶两个元素取出并进行相应运算
            	//将得到的两个字符串分数转化成Number型
            	String num_1,num_2;
            	Number num1 = new Number();
                Number num2 = new Number();
                Number result = new Number();
                num_1 = stack.pop();
                num_2 = stack.pop();
                num1 = returnNum(num_1);
                num2 = returnNum(num_2);
                if (item.isAdd()) {//计算加法
                    result.a = num2.a * num1.b + num1.a * num2.b;
                    result.b = num2.b * num1.b;
                } else if (item.isSub()) {//计算减法
                	result.a = num2.a * num1.b - num1.a * num2.b;
                    result.b = num2.b * num1.b;
                    if(result.a < 0) return null;//若为负数则不生成答案
                } else if (item.isMul()) {//计算乘法
                	result.a = num2.a * num1.a;
                	result.b = num2.b * num1.b;
                } else if (item.isDiv()) {//计算除法
                	result.a = num2.a * num1.b;
                	result.b = num2.b * num1.a;
                } else {
                    throw new IllegalArgumentException("Operator invalid : " + item.value);
                }
                stack.push(result.a + "/" + result.b);            
            }
        }
        //返回化简成分数或整数或带分数的结果
        Number num = new Number();
        String ans = stack.pop();
        num = returnNum(ans);
        if(num.a < num.b) return proSimple(num.a,num.b);
        else return improSimple(num.a,num.b);
    }
    
    //将列表类型转化为字符串类型
    public String listToString(List<Item> list) {
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0; i < list.size(); i++) {
    		sb.append(list.get(i).value);
    	}
    	return sb.toString().substring(0,sb.toString().length());
    }
		
}
//一个分数的存储
class Number{
	int a;
	int b;
}

class Item {

    public String value;

    public Item(String value) {
        this.value = value;
    }

    
    public boolean isBlank() {
        return " ".equals(value);
    }
    
    public boolean isNumber() {
    	Pattern pattern = Pattern.compile("\\d+\\/\\d+|\\d+\\'\\d+\\/\\d+|\\d+");
        Matcher m = pattern.matcher(value);
        return m.find();
    }

    public boolean isAdd() {
        return "+".equals(value);
    }

    public boolean isSub() {
        return "-".equals(value);
    }

    public boolean isMul() {
        return "×".equals(value);
    }

    public boolean isDiv() {
        return "÷".equals(value);
    }

    public boolean isLeftBracket() {
        return "(".equals(value);
    }

    public boolean isRightBracket() {
        return ")".equals(value);
    }

    public int getPriority() {
        if (isAdd() || isSub())
            return 0;
        if (isMul() || isDiv())
            return 1;
        throw new RuntimeException("This is not +, -, *, /");
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : null;
    }
}