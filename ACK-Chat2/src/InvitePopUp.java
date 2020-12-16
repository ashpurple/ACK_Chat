import javax.swing.JOptionPane;

public class InvitePopUp {

	public boolean isInvite() {
		boolean ok;
		int result = JOptionPane.showConfirmDialog(null, "Do you want to join the chat?", "Invite Message",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			ok = true;
		} else {
			ok = false;
		}

		return ok;
	}

}