package a;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class b {
	static List<doctor> docLst = new ArrayList<>();
	static List<hospital> hosLst = new ArrayList<>();
	static List<binren> binLst = new ArrayList<>();

	public void addDoctor(String name, String age, String mainwork) {
		doctor d = new doctor();
		d.setName(name);
		d.setAge(age);
		d.setMainWork(mainwork);
		docLst.add(d);
	}

	public void addHos(String name, String doctor, String binren) {
		hospital h = new hospital();
		h.setBinren(binren);
		h.setDoctor(doctor);
		h.setName(name);
		hosLst.add(h);
	}

	public void binren(String age, String name, String problem, String room_id) {
		binren b = new binren();
		b.setAge(age);
		b.setName(name);
		b.setProblem(problem);
		b.setRoom_id(room_id);
		binLst.add(b);
	}

	public static String getSelect(String s) {
		s = s.substring(0, 1);
		switch (s) {
		case "1":
			System.out.println("�����벡������");
			Scanner sn = new Scanner(System.in);
			String na = sn.next();
			if (na.equals("4")) {
				System.out.println("�ص���ҳ");
				go();
				break;
			}
			for (int i = 0; i < binLst.size(); i++) {
				if (na.equals(binLst.get(i).getName())) {
					System.out.println(binLst.get(i).toString());
				}
			}
			break;
		case "2":
			System.out.println("������ҽ������");
			sn = new Scanner(System.in);
			na = sn.next();
			if (na.equals("4")) {
				System.out.println("�ص���ҳ");
				go();
				break;
			}
			for (int i = 0; i < docLst.size(); i++) {
				if (na.equals(docLst.get(i).getName())) {
					System.out.println(docLst.get(i).toString());
				}
			}
			break;
		case "3":
			for (int i = 0; i < binLst.size(); i++) {
				binren b = binLst.get(i);
				System.out.println(b.toString());
			}
			for (int i = 0; i < docLst.size(); i++) {
				doctor d = docLst.get(i);
				System.out.println(d.toString());
			}
			break;
		case "4":
			System.out.println("�ص���ҳ");
			go();
			break;
		default:
			s = "������";
		}
		return s;
	}

	public static void test() {
		// һҽԺ
		hospital h = new hospital();
		h.setBinren("������:" + 3);
		h.setDoctor("ҽ����:" + 2);
		h.setName("��ɽҽԺ");
		hosLst.add(h);
		// ������
		for (int i = 0; i < 3; i++) {
			binren b = new binren();
			b.setAge(3 + "" + i);
			b.setName("��" + i + "��");
			switch (i) {
			case 0:
				b.setProblem("ͷ��");
				break;
			case 1:
				b.setProblem("����");
				break;
			case 2:
				b.setProblem("����");
			}
			b.setRoom_id(i + "�Ų���");
			binLst.add(b);
		}
		// ��ҽ��
		for (int i = 0; i < 2; i++) {
			doctor d = new doctor();
			d.setAge(4 + "" + i);
			if (i == 0) {
				d.setName("����");
				d.setMainWork("Ƥ����");
			} else {
				d.setName("����");
				d.setMainWork("�ۿ�");
			}
			docLst.add(d);
		}
	}

	public static void go() {
		// ���ǵ���Ŀ
		Scanner sc = new Scanner(System.in);
		for (;;) {
			System.out.println("/*******************��ӭ����ҽԺ����ϵͳ*******************/");
			System.out.println("����(����1)\tҽ��(����2)\tҽԺ(����3)\t�ص���ҳ(����4)");
			System.out.println("������:");
			String s = sc.next();
			getSelect(s);
		}
	}

	public static void main(String[] args) {
		// �������ݵĲ�����
		test();
		// go
		go();
	}
}

class doctor {
	String name;
	String age;
	String mainWork;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMainWork() {
		return mainWork;
	}

	public void setMainWork(String mainWork) {
		this.mainWork = mainWork;
	}

	@Override
	public String toString() {
		return "doctor [����=" + name + ", ����=" + age + ", ���ο�Ŀ=" + mainWork + "]";
	}
}

class hospital {
	String name;
	String binren;
	String doctor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBinren() {
		return binren;
	}

	public void setBinren(String binren) {
		this.binren = binren;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	@Override
	public String toString() {
		return "hospital [����=" + name + ", ������=" + binren + ", ҽ����=" + doctor + "]";
	}
}

class binren {
	String name;
	String age;
	String problem;
	String room_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	@Override
	public String toString() {
		return "binren [����=" + name + ",����=" + age + ", ��֢=" + problem + ", ����=" + room_id + "]";
	}
}