import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class SettingFrameKOR extends JFrame {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public SettingFrameKOR() 
	{
		final JFrame secondFrame = new JFrame();
		secondFrame.setSize(600,100);
		secondFrame.setDefaultCloseOperation(secondFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		secondFrame.setLocationRelativeTo(null);
		secondFrame.setContentPane(contentPane);
		secondFrame.getContentPane().setLayout(null);
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"영어", "한국어"}));
		comboBox.setBounds(139, 26, 210, 27);
		secondFrame.getContentPane().add(comboBox);
		
		JLabel lblNewLabel = new JLabel("언어:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel.setBounds(54, 25, 86, 24);
		secondFrame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("완료");
		btnNewButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				if(comboBox.getSelectedItem().equals("영어"))
				{
					secondFrame.setVisible(false);
					GolfCalendarKOR.guiFrameKOR.setVisible(false);
					dispose();
					try 
					{
						GolfCalendar gc = new GolfCalendar();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(354, 25, 75, 29);
		secondFrame.getContentPane().add(btnNewButton);
		secondFrame.setVisible(true);
	}
}
