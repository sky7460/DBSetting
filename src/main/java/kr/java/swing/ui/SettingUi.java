package kr.java.swing.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import kr.java.swing.service.AbstractService;
import kr.java.swing.service.ExportService;
import kr.java.swing.service.ImportService;
import kr.java.swing.service.InitService;

@SuppressWarnings("serial")
public class SettingUi extends JFrame implements ActionListener {
	private JPanel contentPane;
	private List<AbstractService> services;
	private List<String> btnNames;
	
	public SettingUi() {
		services = Arrays.asList(new InitService(), new ExportService(), new ImportService());
		btnNames = Arrays.asList("초기화", "백업", "복원");
		
		setTitle("DBSetting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 10, 0));
		
		JPanel btnPannel = new JPanel();
		btnPannel.setBorder(new TitledBorder(null, "CoffeeProject Database 관리자 설정", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(btnPannel);
		btnPannel.setLayout(new GridLayout(1, 0, 0, 0));
		
		for(String btnTitle : btnNames) {
			JButton btn = new JButton(btnTitle);
			btn.addActionListener(this);
			btnPannel.add(btn);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "초기화"	:
				services.get(0).service();
				break;
			case "백업":
				services.get(1).service();
				break;
			case "복원":
				services.get(2).service();	
				break;
		}
		JOptionPane.showMessageDialog(null, e.getActionCommand() + " 완료");
	}

}
