
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.Spring;
//import com.mysql.jdbc.PreparedStatement;

public class FindPanel extends JFrame {

	static String[] MaxList = new String[100]; // 최대 친구 리스트 배열
	static String Login_ID = New_Client.getClientName();
	static String Fdata[] = new String[7]; // 친구의 상세 정보 저장

	static JPopupMenu pm; // 팝업메뉴
	static JTextField txt1; // 텍스트필드
	static JButton FButton; // 검색버튼

	static Choice FChoice; // 콤보박스 변수
	static int findindex; // 콤보박스 인덱스
	static int num; // 검색목록 인덱스
	int pmindex;
	static JList FriendList; // 리스트 변수
	static String FriendInfo; // 텍스트필드에 입력된 값을저장하는 변수

	static JPanel getFindFriend() {
		// super("TingTalk");
		Login_ID = New_Client.getClientName();
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel FFindMain = new JPanel(new BorderLayout()); // 메인 패널, BorderLayout으로 정렬
		Panel Logout = new Panel(new BorderLayout()); // Logout패널 생성, BorderLayout으로 정렬
		Panel FFind = new Panel(); // FFind(Friend Find)패널 생성
		Panel Logoutset = new Panel(); // Logout패널 생성
		JButton LogoutBtn = new JButton("LogOut");
		LogoutBtn.setBackground(new Color(051, 102, 153));
		LogoutBtn.setForeground(new Color(255, 255, 255));

		FButton = new JButton("Search"); // 버튼생성
		FButton.setForeground(new Color(255, 255, 255));
		FButton.setBackground(new Color(051, 102, 153));
		txt1 = new JTextField(10); // 텍스트필드생성
		FriendList = new JList(); // 리스트생성

		FChoice = new Choice(); // 콤보박스생성
		FChoice.add("ID");
		FChoice.add("Name");
		FChoice.add("NickName");
		FChoice.add("Tel");

		FFind.add(FChoice); // North부분 패널
		FFind.add(txt1);
		FFind.add(FButton);

		// JLabel logo = new JLabel(new ImageIcon(getClass().getResource("1.jpg")));
		// //팅톡 아이콘생성
		Logoutset.add(LogoutBtn);// 로그아웃 UI세팅

		Logout.add("Center", Logoutset); // South 부분 패널
		// Logout.add("West",logo);

		FFindMain.add("North", FFind);
		FFindMain.add("South", Logout);
		FFindMain.add("Center", FriendList);

		tabbedPane.addTab("친구 찾기", null, FFindMain, "Firet Panel"); // 탭명
		// getContentPane().add(tabbedPane);
		ActionListener LogoutAction = new ActionListener() { // 로그아웃 버튼 이벤트

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
/////////////////////////////////////////////////////////////////////////////////////
		ActionListener actionListener = new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {

				String TempFriendList = (String) FriendList.getSelectedValue();

				findindex = FChoice.getSelectedIndex(); // 콤보박스JChoic)의 선택된 인덱스 번호 셋팅
				num = FriendList.getSelectedIndex();

				if (actionEvent.getSource() == txt1) {
					FriendInfo = actionEvent.getActionCommand();
				} // 텍스트필드에서 엔터를 누를시...이벤트
				if (actionEvent.getSource() == FButton) {
					FriendInfo = txt1.getText();
				} // 버튼클릭시 이벤트...

				////////////////// 친구 검색하는 부분///////////////////
				Connection conn;
				try {
					String sql = null;
					conn = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/mydb", "root",
							"12345");

					if (findindex == 0) {
						sql = "select * from client_list where client_id like ?";
					} // 물음표에는 동적으로 변화하는 값을 넣기 위함
					else if (findindex == 1) {
						sql = "select * from client_list where client_name like ?";
					} // 물음표에는 동적으로 변화하는 값을 넣기 위함
					else if (findindex == 2) {
						sql = "select * from client_list where client_phone like ?";
					} // 물음표에는 동적으로 변화하는 값을 넣기 위함
					else if (findindex == 3) {
						sql = "select * from client_list where client_nickName like ?";
					} // 물음표에는 동적으로 변화하는 값을 넣기 위함

					java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%" + FriendInfo + "%"); // 텍스트에 있는 입력한 값~ SQL로 보낼값~
					java.sql.ResultSet result = pstmt.executeQuery();

					String data[] = new String[4]; // SQL로부터 이름 전화번호 닉네임을 받는 배열
					String line = ""; // SQL로부터받은 각각의 정보를 하나로 묶는 문자열
					int i = 0;

					for (int j = 0; j < 100; j++) {
						MaxList[j] = ""; // MaxList을 검색시마다 초기화

					}

					while (result.next()) {
						data[0] = result.getString("client_id"); // SQL로 부터 이름을 받는다
						data[1] = result.getString("client_name"); // SQL로 부터 전화번호를받는다
						data[2] = result.getString("client_phone"); // SQL로 부터 이메일을 받는다
						data[3] = result.getString("client_nickName"); // SQL로 부터 이메일을 받는다

						line = ""; // line초기화
						for (int j = 0; j < data.length; j++) // data[]를 하나의 문자열에 저장
						{

							line = line + data[j] + " ";

						}
						MaxList[i] = line;// 합친 정보(line)을 배열 MaxList에 하나하나 저장
						i++;

						FriendList.setListData(MaxList); // 검색된친구가 모인 MaxList배열에있는값을 각각 리스트에 세팅
					}
				}

				catch (Exception e) {
					System.out.println("DB접속 오류 " + e);
				}

				// 상세정보 액션 이벤트

				if (actionEvent.getActionCommand() == "Info") {

					String SelFrdID = MaxList[num];
					String[] InfoCut = SelFrdID.split(" "); // 친구아이디

					// System.out.println(InfoCut[num+1]);
					if (!InfoCut[0].equals("")) {

						try {
							conn = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/mydb", "root",
									"12345");

							PreparedStatement ps1 = null;
							ResultSet rs1 = null;
							String sql1 = null;

							if (findindex == 0) {
								sql1 = "select * from client_list where client_id like ?";
							} // 물음표에는 동적으로 변화하는 값을 넣기 위함
							else if (findindex == 1) {
								sql1 = "select * from client_list where client_name like ?";
							} // 물음표에는 동적으로 변화하는 값을 넣기 위함
							else if (findindex == 2) {
								sql1 = "select * from client_list where client_phone like ?";
							} // 물음표에는 동적으로 변화하는 값을 넣기 위함
							else if (findindex == 3) {
								sql1 = "select * from client_list where client_nickName like ?";
							} // 물음표에는 동적으로 변화하는 값을 넣기 위함

							ps1 = (PreparedStatement) conn.prepareStatement(sql1);
							ps1.setString(1, InfoCut[0]); // 리스트에 선택된 친구의 ID(또는 이름)
							rs1 = ps1.executeQuery();

							while (rs1.next()) {
								Fdata[0] = rs1.getString("client_name");
								Fdata[1] = rs1.getString("client_email");
								Fdata[2] = rs1.getString("client_phone");
								Fdata[3] = rs1.getString("client_nickName");
								Fdata[4] = rs1.getString("client_birthDay");
								Fdata[5] = rs1.getString("client_comment");
								Fdata[6] = rs1.getString("client_logout_time");
							}
						} catch (SQLException sqex) {
							System.out.println("SQLException: " + sqex.getMessage());
							System.out.println("SQLState: " + sqex.getSQLState());
						}

						JFrame a = new JFrame();
						// BorderLayout f = new BorderLayout();

						Label ShowFrdID = new Label(" ID : " + InfoCut[0]);
						Label ShowFrdInfo = new Label(" < Friend Information >");
						Label ShowFrdName = new Label(" Name : " + Fdata[0]);
						Label ShowFrdEmail = new Label(" E-mail : " + Fdata[1]);
						Label ShowFrdPhone = new Label(" Phone-num : " + Fdata[2]);
						Label ShowFrdNick = new Label(" NickName : " + Fdata[3]);
						Label ShowFrdBirth = new Label(" Birth Date : " + Fdata[4]);
						Label ShowFrdComment = new Label(" Comment : " + Fdata[5]);
						Label ShowFrdLogout = new Label(" LogoutTime : " + Fdata[6]);

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
						///// 추가
						a.add(ShowFrdNick);
						a.add(ShowFrdBirth);
						a.add(ShowFrdComment);
						a.add(ShowFrdLogout);

						a.setVisible(true);
						a.setSize(300, 300);
						FrameLocation.setLocation(a);
					} // if end
				}

				if (actionEvent.getActionCommand() == "Add") {

					int num = FriendList.getSelectedIndex(); // 리스트에서 선택된 인덱스번호

					// String SelFrdID = MaxList[num+1]; //리스트 인덱스의 문자열(추가하려는 친구 변수)
					String[] addCut = TempFriendList.split(" ");

					if ((!addCut[0].equals("")) && !addCut[0].equals(New_Client.getClientName())) { // 빈칸을 클릭하거나 자신의 ID를
						// 추가 할 경우 막음
						// 즉 내가 클릭한 항목이 빈칸이 아니거나 내 ID와 같지 않다면...
						Connection con = null;

						try {
							con = DriverManager.getConnection("jdbc:mysql://" + New_Client.DBIP + ":3306/mydb", "root",
									"12345");
							// System.out.println("MySql 접속 성공");
						} catch (SQLException e) {
							// System.out.println("접속 실패");
						}

						PreparedStatement ps = null;
						String sql = "INSERT INTO client_friend_list (client_id,friend_id) SELECT ?,? FROM DUAL WHERE NOT EXISTS (SELECT * FROM client_friend_list WHERE client_id=? and friend_id=?)";

						try {
							ps = (PreparedStatement) con.prepareStatement(sql);
							String MyId = New_Client.getClientName();
							ps.setString(1, MyId); // 내 이름
							ps.setString(2, addCut[0]); // 선택된 친구이름
							ps.setString(3, MyId);
							ps.setString(4, addCut[0]);
							int n = ps.executeUpdate();

							if (n > 0) {
								System.out.println("친추 성공");
							} else {
								System.out.println("실패");
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}

			}

		};
		// 텍스트부분 이벤트생성

		txt1.addActionListener(actionListener);
		FButton.addActionListener(actionListener);
		LogoutBtn.addActionListener(LogoutAction);

		pm = new JPopupMenu(); // 팝업메뉴 생성
		JMenuItem Info = new JMenuItem("Info"); // 팝업메뉴 아이템 정의
		JMenuItem Fadd = new JMenuItem("Add");

		pm.add(Info); // 팝업메뉴에 아이템 추가
		pm.add(Fadd);

		Info.addActionListener(actionListener);
		Fadd.addActionListener(actionListener);

		//////////////////////////// 리스트에서 우클릭시 팝업메뉴//////////////////////////////
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
							pm.show(FriendList, x, y);
						}
					}
				}
			}
		});
		return FFindMain;

	}

	/*
	 * public static void main(String args[]) { FindPanel Search = new FindPanel();
	 * Search.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * Search.setVisible(true); Search.setSize(500, 500); }
	 */

}