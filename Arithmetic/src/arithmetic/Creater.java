package arithmetic;


public class Creater {

	formJudge fj = new formJudge();
	//�������캯��
	public Creater() {
		
	}
	//����һ������Ŀ
	/*����˼·������ͨ���������ȷ�����ɵ���Ŀ���м������֣�Ȼ��ͨ��forѭ��
	 * ѭ������2*n�����������ʱÿ����������������һ�����������Ҹ���Ҫ���
	 * ��ʽ���򣬽���ͨ����������ж����ɵ�������Ӷ��������n-1���������Ȼ��
	 * ��Щ��˳�����ӳ��ַ�������*/
	public numItem pro_creater(int range) {
		//����rc��rn��ro���洢������Ӷ��漴�õ�
		int rc,rn,ro;
		numItem str = new numItem();
		String [] ch = new String[] {"+","-","��","��"};
			rc = (int)(Math.random()*3+2);//�������2~4�������ж����ɼ�����
			int [] a = new int[2*rc]; 
			for(int i=0; i < 2*rc; i++) {
				a[i] = 1;
			}
			String [] num_str = new String[rc];
			String [] num_str1 = new String[rc];
			String [] b = new String[rc-1];
			for(int j = 0; j < rc; j++) {
				rn = (int)(Math.random()*(2-0));//�������0��1�ж�����������������ʹ�����
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
				ro = (int)(Math.random()*(4-0));//����0��3����������ж�����ʲô�����
					b[k] = ch[ro];
			}
			
			//������
			str = parent_creater(rc,num_str,num_str1,b);	
			
		return str;
	}
	//�������
	/*˼·������һ����������ж����ɶ��ٸ����ţ������ź������ű���ɶԳ��֣�����һ��
	 * �����ַ������飬һ��������λ�������һ��������λ�����飬�������������������
	 * �����ţ��������������������������n/2+1�����ţ����ɸ÷�Χ�ڵ���������ж���
	 * �ɶ������ţ���������������ŵ�λ�ã�Ȼ�����������λ���������������λ�ã�����
	 * ����ͬʱ�������飬��������λ���ҳ���Χ��ֹͣ��������Ǻ������ַ�������������ʽ��*/
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
					int rpl = (int)(Math.random()*(rc-1-lmin)+lmin);//����������λ��
      				int rpr = (int)(Math.random()*(rc-rpl-1)+rpl+1);//����������λ������������λ��
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

//һ��ʽ�ӵĴ洢��ʽ
class numItem {
	String oldstr;
	String newstr;
	String ans;
	
}