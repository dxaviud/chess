/*
 * Copyright 2020 David Xu All rights reserved. Use for commercial purposes is prohibited.
 */

package chess;

import javax.swing.JOptionPane;

class MessageBox {
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
}
