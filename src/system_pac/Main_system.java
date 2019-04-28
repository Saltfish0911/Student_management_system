package system_pac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.omg.CORBA.PRIVATE_MEMBER;

public class Main_system {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner in = new Scanner(System.in);	
		while(true) {
			System.out.println("欢迎来到学生管理系统，请问您需要以下什么服务：");
			System.out.println("--------------------");
			System.out.println("1.查看所有学生");
			System.out.println("2.添加学生");
			System.out.println("3.删除学生");
			System.out.println("4.修改学生信息");
			System.out.println("5.查看某个学生的信息");
			System.out.println("0.退出系统");
			System.out.println("--------------------");
			
			
					
			int choosenum=in.nextInt();
			
			
			switch(choosenum){
			
				case 1:
					//查看所有学生
					showallstu();
					
					break;
					
					
					
				case 2:
					//添加学生
					addstu();
					
					break;
					
					
					
				case 3:
					//删除学生
					deletestu();
					
					break;
					
					
					
					
				case 4:
					//修改学生信息
					changestu();
					
					
					break;
					
				case 5:
					//查询某个学生信息
					showonestu();
					
					break;
					
					
					
				case 0:
					System.out.println("感谢您的使用，再见！");
						in.close();
						System.exit(0);
					break;	
					
			}
			in.nextLine();
		}//while结束
		
	}		
		
	
	//添加学生信息，com
	public static void addstu() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Jdbc_utils.myconn();
		Scanner tempin=new Scanner(System.in);
		
		try {
			
			int tempnum;
			String tempname;
			String tempsex;
			int tempage;
			double tempscore;
			System.out.print("请输入需要添加的学生的学号:");
			tempnum=tempin.nextInt();
			tempin.nextLine();//！！！很重要，nextint不会读取 \n 所以要加一个tempin.nextlin()吸收这个 \n
			System.out.print("请输入需要添加的学生名字:");
			tempname=tempin.nextLine();
			System.out.print("请输入需要添加的学生性别:");
			tempsex=tempin.nextLine();
			System.out.print("请输入需要添加的学生年龄:");
			tempage=tempin.nextInt();
			System.out.print("请输入需要添加的学生绩点:");
			tempscore=tempin.nextDouble();
			
			
			
			
			ps=conn.prepareStatement("insert into testclass (number,name,sex,age,score) values(?,?,?,?,?)");
			ps.setObject(1, tempnum);
			ps.setObject(2, tempname);
			ps.setObject(3, tempsex);
			ps.setObject(4, tempage);
			ps.setObject(5, tempscore);
			ps.executeUpdate();
			System.out.println("添加完成");
			
			
			
		} catch (SQLException e) {
			System.out.println("添加学生失败，请按照要求重试");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally {
			
			Jdbc_utils.close(conn, ps, rs);
		}
		
	}
	
	//删除学生信息com
	public static void deletestu() {
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			conn=Jdbc_utils.myconn();
			Scanner tempin=new Scanner(System.in);
			
			int delnum;
			System.out.println("请输入需要删除的学生的学号");
			delnum=tempin.nextInt();
		try {
			ps=conn.prepareStatement("delete from testclass where number=?");
			ps.setObject(1, delnum);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("删除学生失败，请重试");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			Jdbc_utils.close(conn, ps, rs);
		}
		
	}
	
	//列出所有学生信息
	public static void showallstu() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Jdbc_utils.myconn();
		
		try {
			ps=conn.prepareStatement("select * from testclass");
			rs=ps.executeQuery();
			System.out.println("--------------------");
			System.out.println("以下为所有学生的信息：");
			while(rs.next()) {
					System.out.print("学号："+rs.getInt(6)+"\t");
					System.out.print("名字："+rs.getString(2)+"\t");
					System.out.print("性别："+rs.getString(3)+"\t");
					System.out.print("年龄："+rs.getInt(4)+"\t");
					System.out.println("绩点："+rs.getDouble(5)+"\t");
			}
			
			
		} catch (SQLException e) {
			System.out.println("发生未知错误，无法查看");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Jdbc_utils.close(conn, ps, rs);
			System.out.println("--------------------");
		}
		
		
		

		
	}
	
	//查询学生信息
	public static void showonestu() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Jdbc_utils.myconn();
		Scanner tempin=new Scanner(System.in);
		
		int asknum;
		System.out.println("请输入要查询的学生的学号");
		asknum=tempin.nextInt();
		tempin.nextLine();
		try {
			ps=conn.prepareStatement("select * from testclass where number=?");
			ps.setObject(1, asknum);
			rs=ps.executeQuery();
			rs.next();//important
			System.out.println("所查询的学生信息为：");
			System.out.print("学号："+rs.getInt(6)+"\t");
			System.out.print("名字："+rs.getString(2)+"\t");
			System.out.print("性别："+rs.getString(3)+"\t");
			System.out.print("年龄："+rs.getInt(4)+"\t");
			System.out.println("绩点："+rs.getDouble(5)+"\t");
			
			
		} catch (SQLException e) {
			System.out.println("找不到该学生");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			Jdbc_utils.close(conn, ps, rs);
		}
		
		
		
	}
	
	
	 //修改学生信息com
	public static void changestu() {
					//建立和数据库的链接
					Connection conn=null;
					PreparedStatement ps=null;
					ResultSet rs=null;
					conn=Jdbc_utils.myconn();
					Scanner tempin=new Scanner(System.in);
					
					int changenum;
					int tempnum;
					String tempname;
					String tempsex;
					int tempage;
					double tempscore;
					
					System.out.println("请输入要修改的学生的学号");
					changenum=tempin.nextInt();
					
					System.out.println("请输入修改后的学生的学号");
					tempnum=tempin.nextInt();
					tempin.nextLine();
					System.out.println("请输入修改后的学生名字");
					tempname=tempin.nextLine();
					
					
					System.out.println("请输入修改后的学生性别");
					tempsex=tempin.nextLine();
					System.out.println("请输入修改后的学生年龄");
					tempage=tempin.nextInt();
					System.out.println("请输入修改后的学生绩点");
					tempscore=tempin.nextDouble();
					
				try {
					ps=conn.prepareStatement("update testclass set number=?,name=?,sex=?,age=?,score=? where number=?");
					ps.setObject(1, tempnum);
					ps.setObject(2, tempname);
					ps.setObject(3, tempsex);
					ps.setObject(4, tempage);
					ps.setObject(5, tempscore);
					ps.setObject(6, changenum);
					ps.executeUpdate();
					
				} catch (SQLException e) {
					System.out.println("修改失败，请按照要求重试");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					
					Jdbc_utils.close(conn, ps, rs);
				}
				
	}
		
}
