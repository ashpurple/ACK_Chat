
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.Arrays;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.LayerUI;

public class client_2 extends JFrame { // 채팅프로램의 대화창에 관한 클래스
	private int choiceRadio = 0; // 라디오 버튼을 어떤것을 눌렀는지 알기위함.
	private int roomNumber = 0; // 각 자 생성되는 방번호. 방마다 방번호가 부여되고 다름.
	String[] chattingList = new String[10]; // 해당방의 대화목록 나열
	public static String str = ""; // 공통되는 메시지
	// =================================================강문성 수정
	JButton send_bt, exit_bt; // 보내기 버튼 // 나가기 버튼
	public JTextArea out_ar; // 채팅 메시지 결과창
	JTextField in_tf; // 사용자가 보내고자 하는 문자열
	JPanel west_p, center_p, south_p, east_p;
	public JList list; // 친구 리스트 및 대화목록 리스트
	JRadioButton firstButton, secondButton;
	JPopupMenu pm; // 팝업메뉴 변수
	JPopupMenu pm2; // 팝업메뉴 변수 대화목록 눌렀을 시
	LayerUI<JPanel> layuerUI;// 프로그램명 뜨게 하기
	JScrollPane qScroller; // out_ar의 스크롤
	int scrollValue = 0;

	public client_2() {

		/////////////////////////////////////////////////////////// 왼쪽 패널 작업
		west_p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		west_p.setBackground(new Color(235, 235, 235));

		// ************************************************************TextArea 수정
		west_p.add(out_ar = new JTextArea(15, 15));
		// **************************************************************
		qScroller = new JScrollPane(out_ar); // 스크롤 생성
		out_ar.setFocusable(false);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		west_p.add(qScroller);
		this.add(west_p, BorderLayout.WEST);

		/////////////////////////////////////////////////////////// 오른쪽 패널 작업
		east_p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		east_p.setBackground(new Color(235, 235, 235));
		east_p.add(list = new JList(), "Center");
		list.setFixedCellWidth(130);
		// ******************************************************list 수정
		list.setFixedCellHeight(20);
		// ******************************************************
		JScrollPane qScroller1 = new JScrollPane(list); // 스크롤 생성
		qScroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pm = new JPopupMenu(); // 팝업메뉴 생성
		pm2 = new JPopupMenu(); // 대화목록 눌렀을 시
		JMenuItem Info = new JMenuItem("Info"); // 팝업메뉴 아이템 정의
		JMenuItem Info2 = new JMenuItem("Info"); // 한곳의 팝업에만 속하기 때문에 2개를 생성
		JMenuItem FrInvite = new JMenuItem("Invite");
		JMenuItem addFriend = new JMenuItem("Add");
		pm.add(Info); // 팝업메뉴에 아이템 추가
		pm.add(FrInvite);
		pm2.add(Info2);
		pm2.add(addFriend);
		list.addMouseListener(new ListAction());
		FrInvite.addActionListener(new PopAction());
		Info.addActionListener(new PopAction());
		Info2.addActionListener(new PopAction());
		addFriend.addActionListener(new PopAction());
		east_p.add(qScroller1);
		east_p.add(secondButton = new JRadioButton("Room"), true);
		east_p.add(firstButton = new JRadioButton("Friends"));
		secondButton.setBackground(new Color(220, 220, 220));
		firstButton.setBackground(new Color(220, 220, 220));
		// *********************************************************라디오 버튼 오른쪽패널로 이동
		ButtonGroup bg = new ButtonGroup();
		bg.add(firstButton);
		bg.add(secondButton);

		firstButton.addActionListener(new MyAction());
		secondButton.addActionListener(new MyAction());
		this.add(east_p, BorderLayout.CENTER);
		// ******************************************************************************

		//////////////////////////////////////////////////////////// 아래쪽 패널 작업
		south_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		south_p.setBackground(new Color(220, 220, 220));

		south_p.add(in_tf = new JTextField(16));
		south_p.add(send_bt = new JButton("Send"));
		south_p.add(exit_bt = new JButton("Quit"));
		send_bt.setForeground(new Color(255, 255, 255));
		exit_bt.setForeground(new Color(255, 255, 255));
		send_bt.setBackground(new Color(051, 102, 153));
		exit_bt.setBackground(new Color(051, 102, 153));
		send_bt.addActionListener(new SendButtonListener());
		exit_bt.addActionListener(new ExitButtonListener());
		in_tf.addKeyListener(new textFieldAction());

		this.add(south_p, BorderLayout.SOUTH);

		// ****************************************************** 전체크기 수정
		setSize(380, 350);
		setResizable(false); // false 일때 크기 고정
		// ***************************************************************
		setVisible(true);
		FrameLocation.setLocation(this);
		in_tf.requestFocus();
		this.addWindowListener(new FrameListener()); // 윈도우의 X를 눌렀을 시 이벤트처리

	}

	public class FrameListener implements WindowListener { // 윈도우 창의 이벤트 처리 나가기 기능 대신.

		public void windowActivated(WindowEvent arg0) {
		}

		public void windowClosed(WindowEvent arg0) {
		}

		public void windowClosing(WindowEvent arg0) {

			exit_bt.doClick();
		}// 윈도우 상에서 x표를 눌렀을 시 나가기 버튼을 클릭하게 한다.

		public void windowDeactivated(WindowEvent arg0) {
		}

		public void windowDeiconified(WindowEvent arg0) {
		}

		public void windowIconified(WindowEvent arg0) {
		}

		public void windowOpened(WindowEvent arg0) {
		}

	}

	public void setRoomNumber(int num) // 방번호 설정
	{

		this.roomNumber = num;

	}

	public int getRoomNumber() // 방번호 반환
	{
		return this.roomNumber;
	}

	public class textFieldAction implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == 10) { // 엔터를 누를 때 보내기 버튼을 강제 클릭

				send_bt.doClick();

			}

		}

		public void keyReleased(KeyEvent arg0) {
		}

		public void keyTyped(KeyEvent arg0) {
		}

	}

	public class PopAction implements ActionListener // 리스트에서 오른쪽 클릭 후 팝업 창에대한 이벤트 관리
														// 친구초대 기능과 친구 상세정보 관리함
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Invite") && choiceRadio == 1) // 친구목록을 눌렀고 대화초대를 눌렀을 경우에 이벤트 발생
			{
				// String[] fr=null;
				// fr=getFriendList.getSearch(New_Client.getClientName()); //친구 목록을 불러옴

				// int selectIndex=list.getSelectedIndex();//리스트에서 선택한 인덱스를 불러옴

				// String selectFr=fr[selectIndex]; //친구리스트중 선택한 사람의 ID를 불러옴

				// String[] tt= (String[]) list.getSelectedValues();

				// Arrays.toString(tt);

				String selectFr = (String) list.getSelectedValue(); // 대화방에 초대할 친구 ID
				if (!(selectFr == null)) { // 내가 선택한 친구가 Null일경우와 내 ID와 일치 하지 않을경우
					String inviteFr = "52275#" + selectFr + "#" + getRoomNumber(); // 친구초대 52275#친구ID#방번호
					New_Client.pw.println(inviteFr); // 초대할 친구에게 메시지를 보냄
					System.out.println(selectFr + "님을 초대함");
					System.out.println("친구초대클릭");
				}

			} else if (e.getActionCommand().equals("Info")) // 상세정보를 클릭 //친구목록에서나 대화목록에서나
			{

				String selectString = (String) list.getSelectedValue(); // 리스트에서 선택한 값 전달
				if (!(selectString == null))
					try {
						Friend_Information.friend_Info(selectString);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			} else if (e.getActionCommand().equals("Add")) {
				String selectString = (String) list.getSelectedValue(); // 리스트에서 선택한 값 전달
				if (!(selectString == null) && !(selectString.equals(New_Client.getClientName())))
					AddFriend.addFriend(New_Client.getClientName(), selectString); // 내 ID와 친구 추가할 ID를 입력합니다.
			}

		}
	}

	public class ListAction implements MouseListener { // 리스트에 대한 이벤트

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

			if (arg0.getButton() == arg0.BUTTON3) // 마우스 오른쪽 버튼에 대한이벤트
			{
				JList c = (JList) arg0.getComponent();
				int x = arg0.getX();
				int y = arg0.getY();
				if (!list.isSelectionEmpty() && list.locationToIndex(arg0.getPoint()) == list.getSelectedIndex()) {
					int count = c.getModel().getSize();
					int cal = count * 18;
					if (y <= cal) {
						if (choiceRadio == 1)// 친구 목록을 눌렀을 시
						{
							pm.show(list, x, y); // 팝업을 선택한 위치에서 보여줌
						} // choice end
						else if (choiceRadio == 0) {
							pm2.show(list, x, y);
						}
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}
	}

	public class MyAction implements ActionListener { // =====================//리스트에 대한 이벤트 관리 클래스

		// 라디오 버튼 클릭시 리스트에 친구목록
		// 띄우기 미구현
		public void actionPerformed(ActionEvent e) {
			// list.add(FriendListMain.getFriendListMain());
			if (e.getActionCommand().equals("Friends")) // 친구목록의 라디오 버튼을 눌렀을 시
			{
				String[] fr = null;
				fr = getFriendList.getSearch(New_Client.getClientName());
				choiceRadio = 1;// 친구목록을 누름
				try {
					list.setListData(fr);
				} catch (NullPointerException e1) {
					System.out.println("친구가 없다..");
				}

			} else // 대화목록을 눌렀을 시
			{
				// String[] tempChatList=New_Client.getChatList();
				list.setListData(getChatList());
				choiceRadio = 0;// 대화목록을 누름
				System.out.println("대화목록 선택");

			}
		}

	}

	public void setChatList(String[] chatList) {
		System.arraycopy(chatList, 0, this.chattingList, 0, chatList.length); // 대화목록을 복사함.
		secondButton.doClick(); // 대화목록이 업데이트 되었기 때문에 자동으로 대화목록으로 이동시킴
		System.out.println(list);
		list.setListData(chatList); // 리스트를 업데이트함
	}

	public String[] getChatList() {
		return this.chattingList;
	}

	public class SendButtonListener implements ActionListener { // ===================보내기
																// 버튼
		public void actionPerformed(ActionEvent event) {
			try {
				if (!in_tf.getText().isEmpty()) {
					// ==========================================자동 스크롤 작업

					JScrollBar scrollBar = qScroller.getVerticalScrollBar();

					scrollBar.setValue(scrollBar.getMaximum());
					scrollValue = scrollValue + 10;
					// ==========================================
					// pw.println( out_ar.getText());
					String sendMsg = getRoomNumber() + "#" + New_Client.getClientName() + ": " + in_tf.getText(); // 방번호#ID:보낼메시지
					New_Client.pw.println(sendMsg); // 직접 아웃풋스트림을 불러와서 씀.
					in_tf.setText("");
				}
				in_tf.hasFocus(); // 포커스 이동.
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public class ExitButtonListener implements ActionListener { // ======================나가기
																// 버튼
		public void actionPerformed(ActionEvent event) {
			try {
				System.out.println("현재 방 나가기");
				String exitStr = getRoomNumber() + "#!exit";
				Map<Integer, client_2> tempMap = New_Client.getMap();
				tempMap.remove(getRoomNumber());
				New_Client.pw.println(exitStr);
				dispose(); // 해당 프레임만 끄기

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/*
	 * public static void main(String[] args) throws IOException { //client_1 Chat =
	 * new client_1();
	 * 
	 * }
	 */

}
