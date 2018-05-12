package kr.java.swing;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kr.java.swing.db.DBCon;
import kr.java.swing.ui.SettingUi;

public class SettingMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingUi frame = new SettingUi();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							DBCon.getInstance().connectionClose();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
