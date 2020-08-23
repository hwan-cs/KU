import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.beans.PropertyChangeEvent;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;

public class DateFrame extends JFrame 
{
	
	private JPanel contentPane;
	/**
	 * Create the frame.
	 */

	public static String enterScore = "\n"+"\n"+"Score:";
	public static String enterHoles = "\n" + "Holes:";
	public static String enterGC = "\n" + "Golf Course: ";
	public static int sTotal;
	public static int n;
	public static int dd;
	public static Object[][] detailCalendar = GolfCalendar.formattedCalendar;
	
	public DateFrame(final int r,final int c) 
	{
		final JFrame dFrame = new JFrame();
		int [][] intOnly = objToInt(GolfCalendar.formattedCalendar);
		final int dateInt = intOnly[r][c]-1; 
		dd = dateInt;
		dFrame.setTitle(intOnly[r][c] + "/" + GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].Month
						+ "/" + GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getYear()+"");
		setDefaultCloseOperation(DateFrame.EXIT_ON_CLOSE);
		dFrame.setSize(300,600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		dFrame.setLocationRelativeTo(null);
		dFrame.getContentPane().add(contentPane);
		dFrame.getContentPane().setLayout(null);
		
		
		final GolfSession gs = new GolfSession();
		if (GolfCalendar.getTable().getColumnName(c).equals("Sunday")|| GolfCalendar.getTable().getColumnName(c).equals("Saturday"))
		{
			gs.setWeek(true);
		}
		else
			gs.setWeek(false);
		
		JLabel lblScore = new JLabel("Score:");
		lblScore.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblScore.setBounds(6, 6, 67, 37);
		dFrame.getContentPane().add(lblScore);
		
		final JTextField tfScore = new JTextField();
		final JLabel lblScoreEntered = new JLabel();
		
		if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getScore() == 0)
		{
			tfScore.setBounds(56, 10, 130, 31);
			tfScore.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			dFrame.getContentPane().add(tfScore);
		}
		else if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getScore() != 0)
		{
			lblScoreEntered.setText(Integer.toString(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getScore()));
			lblScoreEntered.setBounds(56,10,130,31);
			lblScoreEntered.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			dFrame.getContentPane().add(lblScoreEntered);
		}
		Object [] arr = dFrame.getContentPane().getComponents();
		String s = "";
		for (Object o:arr)
		{
			if(o == tfScore)
			{
				s = "OK";
			}
			else if(o == lblScoreEntered)
			{
				s = "Edit";
			}
		}
		final JButton btnOk = new JButton(s);
		btnOk.setBounds(189, 12, 39, 29);
		dFrame.getContentPane().add(btnOk);
		btnOk.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{	
				Object [] arr1 = dFrame.getContentPane().getComponents();
				for (Object o:arr1)
				{
					if(o == tfScore)
					{
						int date = dateInt+1;
						btnOk.setText("Edit");
						String score = tfScore.getText();
						AbstractTableModel model = (AbstractTableModel)GolfCalendar.getTable().getModel();
						GolfCalendar.formattedCalendar[r][c] = date+enterScore+score+enterHoles 
								+ GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles() 
								+ enterGC +GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGc().getGolfCourse();
						GolfCalendar.getTable().setModel(model);
						dFrame.getContentPane().remove(tfScore);
						lblScoreEntered.setText(score);
						GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].setYy(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getYear());
						GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].setScore(Integer.valueOf(score));
						lblScoreEntered.setBounds(56,10,130,31);
						lblScoreEntered.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
						dFrame.getContentPane().add(lblScoreEntered);
						dFrame.validate();
						dFrame.repaint();
					}
				else if (o == lblScoreEntered)
					{
						btnOk.setText("OK");
						dFrame.getContentPane().remove(lblScoreEntered);
						tfScore.setBounds(56, 10, 130, 31);
						tfScore.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
						dFrame.getContentPane().add(tfScore);
						dFrame.validate();
						dFrame.repaint();
					}
				}
			}
		});
		JLabel lblHoles = new JLabel("Holes:");
		lblHoles.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblHoles.setBounds(6, 55, 67, 37);
		dFrame.getContentPane().add(lblHoles);
		
		JLabel lblGC = new JLabel("Golf Course: ");
		lblGC.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblGC.setBounds(6, 100, 103, 37);
		dFrame.getContentPane().add(lblGC);
		
		JLabel lblPlayer = new JLabel("Players in group:");
		lblPlayer.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblPlayer.setBounds(6, 145,137,37);
		dFrame.getContentPane().add(lblPlayer);
		final ArrayList <String> cbModel = new ArrayList <String>();
		cbModel.add(null);
		cbModel.add("Jaypee");
		cbModel.add("Classic");
		cbModel.add("DLF");
		final JComboBox comboBox = new JComboBox(cbModel.toArray());
		comboBox.setBounds(111, 107, 137, 27);
		dFrame.getContentPane().add(comboBox);
		if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGc().getGolfCourse() != "")
		{
			ArrayList<String> arr1 = new ArrayList<String>();
			arr1.add("Jaypee");
			arr1.add("Classic");
			arr1.add("DLF");
			comboBox.setModel(new DefaultComboBoxModel(arr1.toArray()));
			comboBox.getModel().setSelectedItem(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGc().getGolfCourse());
		}
		final JTextField playerNameTF = new JTextField();
		if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size() == 0)
		{
			playerNameTF.setBounds(136, 146, 114, 37);
			playerNameTF.setColumns(10);
			dFrame.getContentPane().add(playerNameTF);
		}
		else if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size() != 0)
		{
			playerNameTF.setBounds(136, 146, 114, 37);
			dFrame.getContentPane().add(playerNameTF);
			playerNameTF.setColumns(10);
			for (int i =0; i< GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size(); i++)
				{
					JLabel playerName = new JLabel();
					playerName.setBounds(139,191+i*45,114,37);
					playerName.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
					playerName.setText(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getName());
					dFrame.getContentPane().add(playerName);
				}
		}
		
		final JButton btnNewButton = new JButton("Add");
		final ArrayList<Person> temp = new ArrayList <Person>();
		btnNewButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()>5)
				{
					JOptionPane.showMessageDialog(null, "PLAYER LIMIT EXCEEDED", "ERROR", JOptionPane.WARNING_MESSAGE);
					return;
				}
				JLabel playerTemp = new JLabel();
				if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size() >0)
				{
				for (int i = 0; i<GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size();i++)
				{
					if (playerNameTF.getText().equals(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getName()))
					{
						JOptionPane.showMessageDialog(null, "DUPLICATE NAME", "ERROR", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				}
				String n = playerNameTF.getText();
				Person p = new Person();
				p.setName(n);
				temp.add(p);
				GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().add(p);
				playerTemp.setBounds(139,146+GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()*45,114,37);
				playerTemp.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				playerTemp.setText(n);
				dFrame.getContentPane().add(playerTemp);
				playerNameTF.setText(null);
				dFrame.validate();
				dFrame.repaint();
				gs.setGroup(temp);
			}
		});
		btnNewButton.setBounds(255, 151, 39, 29);
		dFrame.getContentPane().add(btnNewButton);
		JButton calButton = new JButton("Payment");
		calButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				PaymentFrame pFrame = new PaymentFrame(r,c);
			}
		});
		calButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		calButton.setBounds(6, 465, 288, 107);
		dFrame.getContentPane().add(calButton);
		
		final ArrayList<Integer> cbHoles = new ArrayList<Integer>();
		cbHoles.add(null);
		cbHoles.add(9);
		cbHoles.add(18);
		final JComboBox holeComboBox = new JComboBox(cbHoles.toArray());
		holeComboBox.setBounds(62,42,95,67);
		dFrame.getContentPane().add(holeComboBox);
		if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles() != 0)
		{
			ArrayList<Integer> arr1 = new ArrayList<Integer>();
			arr1.add(9);
			arr1.add(18);
			holeComboBox.setModel(new DefaultComboBoxModel(arr1.toArray()));
			holeComboBox.getModel().setSelectedItem(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles());
		}
		JButton btnHoles = new JButton("OK");
		btnHoles.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int date = dateInt+1;
				String hole = holeComboBox.getSelectedItem().toString();
				AbstractTableModel model = (AbstractTableModel)GolfCalendar.getTable().getModel();
				GolfCalendar.formattedCalendar[r][c] =  date+enterScore+GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getScore()
						+enterHoles + hole + enterGC 
						+ GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGc().getGolfCourse();
				GolfCalendar.getTable().setModel(model);
				GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().setNumHoles(Integer.valueOf(hole));
				holeComboBox.getModel().setSelectedItem(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles());
			}
		});
		btnHoles.setBounds(161, 61, 39, 29);
		dFrame.getContentPane().add(btnHoles);
		
		if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGc().getGolfCourse() != "")
		{
			ArrayList<String> arr1 = new ArrayList<String>();
			arr1.add("Jaypee");
			arr1.add("Classic");
			arr1.add("DLF");
			comboBox.setModel(new DefaultComboBoxModel(arr1.toArray()));
			comboBox.getModel().setSelectedItem(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGc().getGolfCourse());
		}
		JButton btnGc = new JButton("OK");
		btnGc.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int date = dateInt+1;
				String gctemp = comboBox.getSelectedItem().toString();
				AbstractTableModel model = (AbstractTableModel)GolfCalendar.getTable().getModel();
				GolfCalendar.formattedCalendar[r][c] =  date+enterScore+GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getScore()+enterHoles 
						+ GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles() + enterGC 
						+ gctemp;
				GolfCalendar.getTable().setModel(model);
				GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGc().setGolfCourse(gctemp);
			}
		});
		btnGc.setBounds(254, 106, 39, 29);
		dFrame.getContentPane().add(btnGc);
		GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[GolfCalendar.cbMonth.getSelectedIndex()].date[dateInt].setGs(gs);
		dFrame.setVisible(true);
	}
	
	public static int[][] objToInt(Object [][] arr)
	{
		int [][] temp = { 
						{0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0},
						};
		int i = 1;
		for (int r = 0; r<arr.length; r++)
		{
			for (int c = 0; c<arr[0].length; c++)
			{
				if(arr[r][c] != " ")
				{
					temp[r][c] = i;
					i++;
				}
			}
		}
		return temp;
	}
}
