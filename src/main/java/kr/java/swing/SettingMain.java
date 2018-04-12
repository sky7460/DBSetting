package kr.java.swing;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import kr.java.swing.service.AbstractService;
import kr.java.swing.service.ExportService;
import kr.java.swing.service.ImportService;
import kr.java.swing.service.InitService;

@SuppressWarnings("serial")
public class SettingMain extends JFrame{
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				AbstractService initService = new InitService();
				initService.service();
				AbstractService importService = new ImportService();
				importService.service();
				AbstractService exportService = new ExportService();
				exportService.service();
				/*try {
					SettingMain frame = new SettingMain();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							DBCon.getInstance().connectionClose();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
		});
	}

	public SettingMain() {
		setTitle("DBSetting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 269);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 10, 0));
		
		JPanel p1 = new JPanel();
		p1.setBorder(new TitledBorder(null, "3개 테이블(product, sale, sale_detail)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(p1);
		p1.setLayout(new GridLayout(1, 0, 0, 0));
		
/*		JButton btnInit1 = new JButton(new BtnAction("초기화", TableType.FIRST));
		p1.add(btnInit1);
		
		JButton btnBackup1 = new JButton(new BtnAction("백업", TableType.FIRST));
		p1.add(btnBackup1);
		
		JButton btnRestore1 = new JButton(new BtnAction("복원", TableType.FIRST));
		p1.add(btnRestore1);*/
	}

}
