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
    //������
    public String calculate(String calStr) {
        List<Item> infixes = parse(calStr);//���ַ���ת��Ϊ������б�
        List<Item> postfixes = infix2postfix(infixes);//���õ����б�ת���ɺ�׺���ʽ�б�
        return calculateByPostfix(postfixes);//ͨ����׺���ʽ������������
    }
    //��������ʽƥ��ʵ�ֽ��ַ���ת���ɺ�����б�
    public List<Item> parse(String calStr) {
        Pattern pattern = Pattern.compile("\\d+\\/\\d+|\\+|\\-|\\��|\\��|\\(|\\)|\\d+\\'\\d+\\/\\d+|\\d+");
        Matcher m = pattern.matcher(calStr);
        List<Item> items = new ArrayList<Item>();
        while (m.find()) {
            items.add(new Item(m.group(0)));
        }
        return items;
    }
    
    //��׺���ʽת��Ϊ��׺���ʽ�㷨
    /* 1.����������������ŵ�����С�
     * 2.����������ǲ���������Ҫ�����ж��Ƿ����ջ��
     * 2.1 ������������Բ��������ջ��
    * 2.2 �������һ�������ţ���ô�ͽ�ջ��Ԫ�ص��������ֱ������������Ϊֹ���������������ֻ�����������������
    * 3.�ڶ���������ʱ������˲��������ȼ�С�ڻ���ڴ�ʱջ������������ջ��Ԫ�ص���ֱ���������
    * 3.1 ����������
    * 3.2 ջ��Ԫ��Ϊ�������ȼ�
    * 3.3 ջΪ��
       * ע���������У�'+''-'���ȼ���ͣ�'('')'���ȼ����
    * 4.������������ĩβ����ջԪ�ص���ֱ����ջ��ɿ�ջ��������д������С�*/           
 
    public List<Item> infix2postfix(List<Item> infixes) {
        List<Item> postfixes = new ArrayList<Item>();//�����б�洢
        Stack<Item> stack = new Stack<Item>();//�½�ջ�洢�м������
        for (Item item : infixes) {//������׺���ʽ�б�
        	if (item.isBlank()) {//��Ϊ�ո�����������
        		continue;
        	}
            if (item.isNumber()) {//��Ϊ������ֱ�����
                postfixes.add(item);
            } else if (item.isRightBracket()) {//��Ϊ��������Ԫ�ص���ֱ������������
                while (true) {
                    Item tmp = stack.pop();
                    if (tmp.isLeftBracket())
                        break;
                    postfixes.add(tmp);
                }
            } else if (item.isLeftBracket()) {//��Ϊ�����Ž�������ѹ��ջ
                stack.push(item);
            } else {
                if (stack.isEmpty()) {//��ջΪ���򽫵�ǰ�ַ�ѹ��ջ
                    stack.push(item);
                    continue;
                }
                Item top = stack.peek();
                if (top.isLeftBracket()) {//��Ϊ�����Ž���ֱ��ѹ��ջ
                    stack.push(item);
                    continue;
                }
                if (item.getPriority() <= top.getPriority()) {//�ж����ȼ�������ǰ���ȼ�С�ڵ���ջ������ջ��Ԫ�ص���ֱ��˼·���ᵽ��3.1��3.2��3.3�������
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
        while (!stack.isEmpty()) {//��������ջ��Ϊ����ֱ�����ʣ��Ԫ��
            postfixes.add(stack.pop());
        }
        return postfixes;
    }

    //���������ַ�����ʽת������int�ͷ��ӷ�ĸ��ɵ�Number��ʽ
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
    //ͨ����׺���ʽ������ֵ
     /* 1. �����ұ������ʽ��ÿ�����ֺͷ���
      * 1.1 �����������ջ
      * 1.2 �����������ջ������Ԫ�س�ջ,�������㲢����������ջ
     * 2. �������׺���ʽ,��ʱջ��ʣ������־���������
     */
    private String calculateByPostfix(List<Item> postfixes) {
        Stack<String> stack = new Stack<String>();
        for (Item item : postfixes) {//������׺���ʽ
            if (item.isNumber()) {//��Ϊ����ֱ����ջ
                stack.push(item.value);
            } else {//��Ϊ��������ջ������Ԫ��ȡ����������Ӧ����
            	//���õ��������ַ�������ת����Number��
            	String num_1,num_2;
            	Number num1 = new Number();
                Number num2 = new Number();
                Number result = new Number();
                num_1 = stack.pop();
                num_2 = stack.pop();
                num1 = returnNum(num_1);
                num2 = returnNum(num_2);
                if (item.isAdd()) {//����ӷ�
                    result.a = num2.a * num1.b + num1.a * num2.b;
                    result.b = num2.b * num1.b;
                } else if (item.isSub()) {//�������
                	result.a = num2.a * num1.b - num1.a * num2.b;
                    result.b = num2.b * num1.b;
                    if(result.a < 0) return null;//��Ϊ���������ɴ�
                } else if (item.isMul()) {//����˷�
                	result.a = num2.a * num1.a;
                	result.b = num2.b * num1.b;
                } else if (item.isDiv()) {//�������
                	result.a = num2.a * num1.b;
                	result.b = num2.b * num1.a;
                } else {
                    throw new IllegalArgumentException("Operator invalid : " + item.value);
                }
                stack.push(result.a + "/" + result.b);            
            }
        }
        //���ػ���ɷ�����������������Ľ��
        Number num = new Number();
        String ans = stack.pop();
        num = returnNum(ans);
        if(num.a < num.b) return proSimple(num.a,num.b);
        else return improSimple(num.a,num.b);
    }
    
    //���б�����ת��Ϊ�ַ�������
    public String listToString(List<Item> list) {
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0; i < list.size(); i++) {
    		sb.append(list.get(i).value);
    	}
    	return sb.toString().substring(0,sb.toString().length());
    }
		
}
//һ�������Ĵ洢
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
        return "��".equals(value);
    }

    public boolean isDiv() {
        return "��".equals(value);
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