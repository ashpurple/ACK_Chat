import javax.swing.event.*;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.swing.*;

public class FriendListMain extends JFrame {

	static String[] Friend_ID = new String[100]; // 최대 등록 가능 수
	static String Login_ID = New_Client.getClientName();
	static JList FriendList;

	static JPanel getFriendListMain() throws ClassNotFoundException {

		// super("친구목록");
		Login_ID = New_Client.getClientName();
		final JPopupMenu FFindpopUp = new JPopupMenu();
		FriendList = new JList();

		// 공공데이터를 이용하여 화면 아래에 날씨를 알려주는 label 추가
		ApiExplorer weather = new ApiExplorer();
		String weatherInfo = "";

		try {
			weatherInfo += weather.getWeather();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		JTextPane weatherText = new JTextPane();
		weatherText.setBackground(new Color(135, 206, 250));
		weatherText.setText(weatherInfo);

		JButton myInfo = new JButton("My Info");
		JButton Logout = new JButton("LogOut");
		myInfo.setSize(100, 100);
		Logout.setSize(100, 100);
		myInfo.setForeground(new Color(255, 255, 255));
		Logout.setForeground(new Color(255, 255, 255));
		myInfo.setBackground(new Color(051, 102, 153));
		Logout.setBackground(new Color(051, 102, 153));
		JPanel Buttom = new JPanel();
		Buttom.setLayout(new BorderLayout());
		Buttom.add("North", weatherText);
		JPanel btnPane = new JPanel(new FlowLayout());
		btnPane.add(myInfo);
		btnPane.add(Logout);
		Buttom.add(btnPane);
		// Logout.add(new JLabel(new ImageIcon(getClass().getResource("1.jpg")))); // 로고
		// 삽입

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout()); //
		panel1.add("South", Buttom); //
		panel1.add("Center", FriendList); //

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("친구 목록", null, panel1, "second Panel");

		int f_count = 0;
		Connection con = null;
		Class.forName("com.mysql.cj.jdbc.Driver");

		ActionListener LogoutAction = new ActionListener() { // Logout 버튼 이벤트

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Map d = New_Client.getMap(); // 현재 회원이 들어가있는 방에대한 정보를 얻어옴
				Collection<client_2> client_2_values = d.values(); // 방에 대한 처리를 하기위해 값을 전달
				for (Iterator<client_2> it = client_2_values.iterator(); it.hasNext();) {
					it.next().exit_bt.doClick(); // 해당 방의 나가기 버튼을눌러줌.
				}

				System.exit(1);

			}

		};
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

				Connection con1 = null;

				if (actionEvent.getActionCommand() == "Invite") {

					String inviteFriendID = (String) FriendList.getSelectedValue();
					if (!(inviteFriendID == null)) { // 초대할 사람이 공백이이 않을 경우 초대
						New_Client.pw.println("52274#" + inviteFriendID); // 대화요청
						System.out.println("대화하기");
					}
				}

				else if (actionEvent.getActionCommand() == "Information") {

					int num = FriendList.getSelectedIndex();

					String SelFrdID = (String) FriendList.getSelectedValue();
					if (!(SelFrdID == null)) {
						System.out.println("친구 정보 보기");
						String Friend_Id = null;
						String Friend_Name = null;
						String Friend_Email = null;
						String Friend_Phone = null;
						String Friend_Nick = null;
						String Friend_Birth = null;
						String Friend_Comment = null;
						String Friend_logout = null;

///                Connection con1 = null;

						try {

							con1 = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/mydb", "root",
									"12345");

							PreparedStatement ps1 = null;
							ResultSet rs1 = null;
							String sql1 = "select * from client_list where client_id=?";

							ps1 = con1.prepareStatement(sql1);
							ps1.setString(1, SelFrdID); // 리스트에 선택된 친구의 ID(또는 이름)
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
						}

						catch (SQLException sqex) {
							System.out.println("SQLException: " + sqex.getMessage());
							System.out.println("SQLState: " + sqex.getSQLState());
						}

						JFrame a = new JFrame();
						// BorderLayout f = new BorderLayout();

						Label ShowFrdName = new Label(" Name : " + Friend_Name);
						Label ShowFrdEmail = new Label(" E-mail : " + Friend_Email);
						Label ShowFrdPhone = new Label(" Phone-num : " + Friend_Phone);
						Label ShowFrdID = new Label(" ID : " + Friend_Id);
						Label ShowFrdInfo = new Label("< Freind Info  >");
						Label ShowFrdNick = new Label(" Nick Name : " + Friend_Nick);
						Label ShowFrdBirth = new Label(" Birth Date : " + Friend_Birth);
						Label ShowFrdComment = new Label(" Comment: " + Friend_Comment);
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

						a.setVisible(true);
						a.setSize(300, 300);
						FrameLocation.setLocation(a);
						a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					} // if end
				}

				else if (actionEvent.getActionCommand() == "Delete") {

					int num = FriendList.getSelectedIndex();
					String SelFrdID = (String) FriendList.getSelectedValue();
					if (!(SelFrdID == null)) {
						try {
							System.out.println(SelFrdID + "친구 삭제");
							con1 = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/mydb", "root",
									"12345");

							PreparedStatement ps2 = null;
							int rs2;
							String sql2 = "delete from client_friend_list where client_id=? and friend_id=?";

							ps2 = con1.prepareStatement(sql2);
							String MyId = New_Client.getClientName();
							ps2.setString(1, MyId);
							ps2.setString(2, SelFrdID);
							rs2 = ps2.executeUpdate();

							System.out.println("삭제된 친구 수" + rs2);

//                    String SetFriend_ID[] = new String[100];

							int f_count = 0;

							PreparedStatement ps3 = null;
							ResultSet rs3 = null;
							String sql = "select * from client_friend_list where client_id=?";
							ps3 = con1.prepareStatement(sql);
							ps3.setString(1, Login_ID);
							rs3 = ps3.executeQuery();

							while (rs3.next()) {
								String str = rs3.getString("friend_id");
								System.out.println(str);

								Friend_ID[f_count] = str;
								f_count++;

							}
							Friend_ID[f_count] = null;
							FriendList.setListData(Friend_ID);

						}

						catch (SQLException sqex) {
							System.out.println("SQLException: " + sqex.getMessage());
							System.out.println("SQLState: " + sqex.getSQLState());
						}
					} // if end
				} else if (actionEvent.getActionCommand() == "My Info") {

		               System.out.println("Hi");
		               String My_Id = null;
		               String My_Name = null;
		               String My_Email = null;
		               String My_Phone = null;
		               String My_Nickname = null;
		               String My_Birth = null;
		               String My_Comment = null;

		///                Connection con1 = null;

		               try {
		                  con1 = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/mydb", "root",
		                        "12345");

		                  PreparedStatement ps1 = null;
		                  ResultSet rs1 = null;
		                  String sql1 = "select * from client_list where client_id=?";

		                  ps1 = con1.prepareStatement(sql1);
		                  String MyId = New_Client.getClientName();
		                  ps1.setString(1, MyId); // 리스트에 선택된 친구의 ID(또는 이름)
		                  rs1 = ps1.executeQuery();

		                  while (rs1.next()) {

		                     My_Id = rs1.getString("client_ID");
		                     My_Name = rs1.getString("client_name");
		                     My_Email = rs1.getString("client_email");
		                     My_Phone = rs1.getString("client_phone");
		                     My_Nickname = rs1.getString("client_nickName");
		                     My_Birth = rs1.getString("client_birthDay");
		                     My_Comment= rs1.getString("client_comment");
		                     
		                  }
		               }

		               catch (SQLException sqex) {
		                  System.out.println("SQLException: " + sqex.getMessage());
		                  System.out.println("SQLState: " + sqex.getSQLState());
		               }

		               final JFrame a = new JFrame("Info");
		               final JFrame b = new JFrame("Notice");

		               Label ShowFrdName = new Label(" Name : ");
		               Label ShowFrdEmail = new Label(" E-mail : ");
		               Label ShowFrdPhone = new Label(" Phone-num : ");
		               Label ShowFrdID = new Label(" ID : " + My_Id);
		               Label ShowFrdInfo = new Label("< My Information >");
		               Label ShowFrdNick = new Label(" Nick Name : ");
		               Label ShowFrdBirth = new Label(" Birth Date : ");
		               Label ShowFrdComment = new Label(" Comment  : ");
		               

		               final JTextField Myname = new JTextField(My_Name);
		               final JTextField Myemail = new JTextField(My_Email);
		               final JTextField Myphone = new JTextField(My_Phone);
		               final JTextField MynickName = new JTextField(My_Nickname);
		               final JTextField Mybirth = new JTextField(My_Birth);
		               final JTextField Mycomment = new JTextField(My_Comment);

		               JButton Edit = new JButton("Edit");
		               JButton Enter = new JButton("Confirm");
		               Edit.setForeground(new Color(255, 255, 255));
		               Enter.setForeground(new Color(255, 255, 255));
		               Edit.setBackground(new Color(051, 102, 153));
		               Enter.setBackground(new Color(051, 102, 153));
		               final JButton EditOk2 = new JButton("완료");
		               EditOk2.setBackground(new Color(230, 110, 100));
		               a.setLayout(null);

		               ShowFrdInfo.setBounds(30, 10, 100, 30);

		               ShowFrdID.setBounds(30, 50, 100, 30);

		               ShowFrdName.setBounds(30, 90, 65, 30);
		               Myname.setBounds(120, 90, 100, 30);

		               ShowFrdNick.setBounds(30, 130, 80, 30);
		               MynickName.setBounds(120, 130, 200, 30);
		               
		               ShowFrdEmail.setBounds(30, 170, 65, 30);
		               Myemail.setBounds(120, 170, 200, 30);

		               ShowFrdPhone.setBounds(30, 210, 65, 30);
		               Myphone.setBounds(120, 210, 200, 30);

		               ShowFrdBirth.setBounds(30, 250, 65, 30);
		               Mybirth.setBounds(120, 250, 200, 30);
		               
		               ShowFrdComment.setBounds(30, 290, 65, 30);
		               Mycomment.setBounds(120, 290, 200, 30);
		               
		               Edit.setBounds(30, 350, 90, 30);
		               Enter.setBounds(250, 350, 90, 30);

		               Myname.disable();
		               Myphone.disable();
		               Myemail.disable();
		               MynickName.disable();
		               Mybirth.disable();
		               Mycomment.disable();
		               

		               a.add(ShowFrdID);

		               a.add(ShowFrdName);
		               a.add(Myname);

		               a.add(ShowFrdEmail);
		               a.add(Myemail);

		               a.add(ShowFrdPhone);
		               a.add(Myphone);
		               /////////
		               a.add(ShowFrdNick);
		               a.add(MynickName);
		               
		               a.add(ShowFrdBirth);
		               a.add(Mybirth);
		               
		               a.add(ShowFrdComment);
		               a.add(Mycomment);
		               ///////
		               

		               a.add(ShowFrdInfo);
		               a.add(Edit);
		               a.add(Enter);

		               a.setVisible(true);
		               a.setSize(400, 450);
		               FrameLocation.setLocation(a);
		               FrameLocation.setLocation(b);

		               ActionListener actionListener = new ActionListener() {
		                  public void actionPerformed(ActionEvent actionEvent) {

		                     Connection con1 = null;

		                     if (actionEvent.getActionCommand() == "Edit") {
		                        System.out.println("Check!");
		                        Myname.enable();
		                        Myphone.enable();
		                        Myemail.enable();
		                        MynickName.enable();
		                        Mybirth.enable();
		                        Mycomment.enable();
		                     }

		                     else if (actionEvent.getActionCommand() == "Confirm") {

		                        Myname.disable();
		                        Myphone.disable();
		                        Myemail.disable();
		                        MynickName.disable();
		                        Mybirth.disable();
		                        Mycomment.disable();

		                        String My_Name = Myname.getText();
		                        String My_Email = Myemail.getText();
		                        String My_Phone = Myphone.getText();
		                        String My_Nick = MynickName.getText();
		                        String My_Birth = Mybirth.getText();
		                        String My_Comment = Mycomment.getText();

		                        int i;

		                        try {
		                           con1 = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/mydb",
		                                 "root", "12345");

		                           PreparedStatement ps1 = null;
		                           ResultSet rs1 = null;
		                           String sql1 = "Update client_list set client_name=?, client_phone=?, client_email=?, client_nickName=?, client_birthDay=?, client_comment=? where client_id=?";

		                           ps1 = con1.prepareStatement(sql1);
		                           ps1.setString(1, My_Name);
		                           ps1.setString(2, My_Phone);
		                           ps1.setString(3, My_Email);
		                           ps1.setString(4, My_Nick);
		                           ps1.setString(5, My_Birth);
		                           ps1.setString(6, My_Comment);
		                           
		                           String MyId = New_Client.getClientName();
		                           ps1.setString(7, MyId); // 리스트에 선택된 친구의 ID(또는 이름)

		                           i = ps1.executeUpdate();
		                        }

		                        catch (SQLException sqex) {
		                           System.out.println("SQLException: " + sqex.getMessage());
		                           System.out.println("SQLState: " + sqex.getSQLState());
		                        }

								Label EditOk1 = new Label("Edit Complete");

								b.setLayout(null);

								EditOk1.setBounds(40, 10, 200, 30);
								EditOk2.setBounds(75, 70, 70, 30);

								b.add(EditOk1);
								b.add(EditOk2);

								b.setVisible(true);
								b.setSize(230, 150);

							} else if (actionEvent.getActionCommand() == "완료") {
								a.setVisible(false);
								b.setVisible(false);
							}

						}
					};

					Edit.addActionListener(actionListener);
					Enter.addActionListener(actionListener);
					EditOk2.addActionListener(actionListener);

				}
			}
		};

		JMenuItem CreateTalk = new JMenuItem("Invite");
		CreateTalk.addActionListener(actionListener);
		FFindpopUp.add(CreateTalk);
		Logout.addActionListener(LogoutAction);
		JMenuItem InfoFriend = new JMenuItem("Information");
		InfoFriend.addActionListener(actionListener);
		FFindpopUp.add(InfoFriend);

		JMenuItem DeleteFriend = new JMenuItem("Delete");
		DeleteFriend.addActionListener(actionListener);
		FFindpopUp.add(DeleteFriend);

		myInfo.addActionListener(actionListener);

		// getContentPane().add(tabbedPane);
		// setSize(500, 500);
		// setVisible(true);

		FriendList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON3) {
					JList c = (JList) e.getComponent();
					int x = e.getX();
					int y = e.getY();
					if (!FriendList.isSelectionEmpty()
							&& FriendList.locationToIndex(e.getPoint()) == FriendList.getSelectedIndex()) {
						int count = c.getModel().getSize();
						int cal = count * 18;
						if (y <= cal) {
							FFindpopUp.show(FriendList, x, y);
						}
					}
				}
			}
		});

		return panel1;
	}

}