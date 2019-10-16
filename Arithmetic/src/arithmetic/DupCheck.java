package arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//������
public class DupCheck {

	DupCheck(){
		
	}
	
	//����׺���ʽת��Ϊ���Բ��صı��ʽ����ʽ�������ռ���������еı��ʽ
	/*����������ʱ���õ���׺���ʽ�������ｫ��׺���ʽת���ɿ��Բ��صı��ʽ
	 * (�������ö�ջ�һ����ͼ����׺���ʽһ������).˼·��Ҫ���Ժ�׺���ʽ��
	 * ����ѭ��������������־�ѹջ�������������������ջ���������ַ�����ջ�󽫡�#��
	 * ѹ������ջ������ַ������Ƽ����׺���ʽʱ�Ľ�������ѭ����ɵõ����صı��ʽ*/
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
	//�ж������ַ����Ƿ��ظ�
	public boolean dupCheck(String str1,String str2) {

		formJudge jd = new formJudge();
		List<String> s1 = getDupExpression(jd.infix2postfix(jd.parse(str1)));//���ַ���s1ת�����ַ��б�
		List<String> s2 = getDupExpression(jd.infix2postfix(jd.parse(str2)));//���ַ���s2ת�����ַ��б�
		if(s1.equals(s2)) return true;//�����б�������ͬ����true
		for(int j = 0; j < s2.size()-1; j++) {//����ͬ����ѭ����+�����������ַ����������ܷ���ͬ
			if(s2.get(j).equals("+")||s2.get(j).equals("��")) {
				String temp = s2.get(j+1);
				s2.set(j+1,s2.get(j+2));
				s2.set(j+2,temp);
			}
			j*=3;
			if(s1.equals(s2)) return true;//����������ͬ�򷵻�true
		}
		return false;
	}
	//�ж�һ���ַ����Ƿ�����һ���ַ���������
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
