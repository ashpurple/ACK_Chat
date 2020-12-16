import java.awt.Label;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

public class Friend_Information { // 우 클릭한 상대에 대한 정보 얻기
	public static void friend_Info(String friendID) throws ClassNotFoundException {

		String Friend_Id = null;
		String Friend_Name = null;
		String Friend_Email = null;
		String Friend_Phone = null;
		String Friend_Nick = null;
		String Friend_Birth = null;
		String Friend_Comment = null;
		String Friend_logout = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/mydb", "root",
					"12345");

			PreparedStatement ps1 = null;
			ResultSet rs1 = null;
			String sql1 = "select * from client_list where client_id=?";

			ps1 = con.prepareStatement(sql1);
			ps1.setString(1, friendID); // 리스트에 선택된 친구의 ID(또는 이름)
			rs1 = ps1.executeQuery();

			while (rs1.next()) {

				Friend_Id = rs1.getString("client_ID");
				Friend_Name = rs1.getString("client_name");
				Friend_Email = rs1.getString("client_email");
				Friend_Phone = rs1.getString("client_phone");

				Friend_Nick = rs1.getString("client_nickName");
				Friend_Birth = rs1.getString("client_birthDay");
				Friend_Comment = rs1.getString("client_comment");
				Friend_logout = rs1.getString("client_logout_time");

			}
			con.close();
		}

		catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}

		JFrame a = new JFrame();
//     BorderLayout f = new BorderLayout();

		Label ShowFrdName = new Label(" Name : " + Friend_Name);
		Label ShowFrdEmail = new Label(" E-mail : " + Friend_Email);
		Label ShowFrdPhone = new Label(" Phone-num : " + Friend_Phone);
		Label ShowFrdID = new Label(" ID : " + Friend_Id);
		Label ShowFrdInfo = new Label("< Friend Info >");
		Label ShowFrdNick = new Label(" NickName : " + Friend_Nick);
		Label ShowFrdBirth = new Label(" Birth Date : " + Friend_Birth);
		Label ShowFrdComment = new Label(" Comment : " + Friend_Comment);
		Label ShowFrdLogout = new Label(" LogOutTime : " + Friend_logout);

		a.setLayout(null);

		ShowFrdInfo.setBounds(30, 5, 200, 30);
		ShowFrdName.setBounds(30, 30, 200, 30);
		ShowFrdID.setBounds(30, 60, 200, 30);
		ShowFrdNick.setBounds(30, 90, 200, 30);
		ShowFrdEmail.setBounds(30, 120, 200, 30);
		ShowFrdPhone.setBounds(30, 150, 200, 30);
		ShowFrdBirth.setBounds(30, 180, 200, 30);
		ShowFrdComment.setBounds(30, 210, 200, 30);
		ShowFrdLogout.setBounds(30, 235, 250, 30);

		a.add(ShowFrdName);
		a.add(ShowFrdID);
		a.add(ShowFrdEmail);
		a.add(ShowFrdPhone);
		a.add(ShowFrdInfo);
		a.add(ShowFrdNick);
		a.add(ShowFrdBirth);
		a.add(ShowFrdComment);
		a.add(ShowFrdLogout);

		a.setResizable(false); // false 일때 크기 고정
		a.setVisible(true);
		a.setSize(300, 300);
		FrameLocation.setLocation(a);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
}