import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.TableColumn;
import javax.swing.event.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;


public class GolfCalendarKOR extends JFrame {

	private JPanel contentPane;
	
	public static String [] day = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
	public static Object [][] date = { {1,2,3,4,5,6,7},
									  {8,9,10,11,12,13,14},
									  {15,16,17,18,19,20,21},
									  {22,23,24,25,26,27,28},
									  {29,30,31,null,null,null,null}
									};
	public static Calendar c = new GregorianCalendar();
	public static JTable tableKOR;
	public int rowIndex;
	public int colIndex;
	public static int month = c.get(Calendar.MONTH);
	public static int year = c.get(Calendar.YEAR);
	public static JComboBox cbYear = new JComboBox();
	public static JComboBox cbMonth = new JComboBox();
	public static ArrayList<Integer> cbModelMonth = new ArrayList<Integer>();
	public static ArrayList<Integer> cbModelYear = new ArrayList<Integer>();
	Font fontDays = new Font("Lucida Grande", Font.PLAIN, 16);
	Font fontDate = new Font("Lucida Grande", Font.PLAIN, 14);
	private static JLabel monthlyReport = new JLabel();
	public static JFrame guiFrameKOR = new JFrame();
	
	public class MultipleLines extends JTextArea implements TableCellRenderer 
	{
		public MultipleLines()
		{
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
		}
		
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	    {
	    	 if (isSelected) 
	    	 	{
	    	      setForeground(table.getSelectionForeground());
	    	      setBackground(table.getSelectionBackground());
	    	    } else 
	    	    {
	    	      setForeground(table.getForeground());
	    	      setBackground(table.getBackground());
	    	    }
	    	    setFont(table.getFont());
	    	    if (hasFocus) 
	    	    {
	    	      setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
	    	      if (table.isCellEditable(row, column)) 
	    	      {
	    	        setForeground(UIManager.getColor("Table.focusCellForeground"));
	    	        setBackground(UIManager.getColor("Table.focusCellBackground"));
	    	      }
	    	      else if(formattedCalendarKOR[row][column] == null)
	    	      {
	    	    	  setForeground(UIManager.getColor("Table.focusCellForeground"));
		    	      setBackground(UIManager.getColor("Table.focusCellBackground"));
	    	      }
	    	    } 
	    	    else 
	    	    {
	    	      setBorder(new EmptyBorder(1, 2, 1, 2));
	    	    }
	    	    setText((value == null) ? "" : value.toString()); 
	    	 if (!(table.isCellEditable(row, column)))
	    		 setBackground(Color.LIGHT_GRAY);
	    	 return this;
	    }
	}
	public class AlignCells extends DefaultTableCellRenderer 
	{
		public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) 
		{
		Component temp = super.getTableCellRendererComponent(table, value,isSelected, hasFocus,row, column);
		setHorizontalAlignment(JLabel.LEFT);
		setVerticalAlignment(JLabel.TOP);
		return temp;
		}
	}
	@SuppressWarnings("unchecked")
	public GolfCalendarKOR() throws Exception 
	{
		guiFrameKOR = new JFrame();
		guiFrameKOR.setTitle("골프 스코어 트래커 달력");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrameKOR.setSize(800,825);
		guiFrameKOR.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBounds(0, 0, 800, 50);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		guiFrameKOR.getContentPane().setLayout(null);
		c = Calendar.getInstance();
		c.setTime(new Date());
		month++;
		year = c.get(Calendar.YEAR);
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setText("설정");
		btnNewButton.setBounds(500, 750, 150, 50);
		JButton btnNewButton_1 = new JButton (" ");
		btnNewButton_1.setText("종료");
		btnNewButton_1.setBounds(650,750,150,50);
		btnNewButton_1.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				try 
				{
					outputStream();
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				SettingFrameKOR second = new SettingFrameKOR();
			}
		});
		guiFrameKOR.getContentPane().add(btnNewButton);
		guiFrameKOR.getContentPane().add(btnNewButton_1);
		guiFrameKOR.getContentPane().add(contentPane);
		contentPane.setLayout(null);
		
		monthlyReport = new JLabel();
		monthlyReport.setBounds(0, 750, 800, 50);
		monthlyReport.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		guiFrameKOR.getContentPane().add(GolfCalendarKOR.monthlyReport);
		tableKOR = new JTable(new ExampleTableModel());
		guiFrameKOR.getContentPane().add(tableKOR);
		tableScrollPane = new JScrollPane(tableKOR);
		tableKOR.setCellSelectionEnabled(true);
		tableScrollPane.setBounds(0, 50, 800, 700);
		tableKOR.setFont(fontDate);
		tableKOR.setForeground(Color.DARK_GRAY);
		tableKOR.getTableHeader().setFont(fontDays);
		
		JLabel lblMonth = new JLabel("New label");
		lblMonth.setBounds(5, 5, 91, 40);
		contentPane.add(lblMonth);
		lblMonth.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblMonth.setText("월: ");
		
		JLabel lblYear = new JLabel("년: ");
		lblYear.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblYear.setBounds(210, 5, 72, 40);
		contentPane.add(lblYear);
		
		cbModelMonth = new ArrayList<Integer>();
		for (int i =1; i<13; i++)
		{
			cbModelMonth.add(i);
		}
		
		cbModelYear = new ArrayList<Integer>();
		for (int i = 0; i<52; i++)
		{
			cbModelYear.add(1968+i);
		}
		cbMonth = new JComboBox(cbModelMonth.toArray());
		cbMonth.setSelectedItem(c.get(Calendar.MONTH)+1);
		cbMonth.setBounds(88, 13, 103, 27);
		contentPane.add(cbMonth);
		
		cbYear = new JComboBox(cbModelYear.toArray());
		cbYear.setSelectedItem(c.get(Calendar.YEAR));
		cbYear.setBounds(269, 13, 116, 27);
		contentPane.add(cbYear);
		cbMonth.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				refreshCal();
				return;
			}
		});
		cbYear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				refreshCal();
				return;
			}
		});
		
		AlignCells changeColor = new AlignCells();
		tableKOR.setDefaultRenderer(Object.class, changeColor);
		tableKOR.setGridColor(Color.GRAY);
		tableKOR.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				int rowIndex = tableKOR.getSelectedRow();
				int colIndex = tableKOR.getSelectedColumn();
				if(!(tableKOR.isCellEditable(rowIndex, colIndex)))
				{
					JOptionPane.showMessageDialog(null, "CANNOT EDIT A BLANK DATE", "ERROR", JOptionPane.WARNING_MESSAGE);
					return;
				}
				tableKOR.getColumnModel().getColumn(colIndex).setCellRenderer(new MultipleLines());
				tableKOR.setFont(fontDate);
				DateFrameKOR dFrameKOR = new DateFrameKOR(rowIndex,colIndex);
			}
		});
		for (int c = 0; c<7; c++)
		{
			tableKOR.getColumnModel().getColumn(c).setCellRenderer(new MultipleLines());
		}
		guiFrameKOR.getContentPane().add(tableScrollPane);
		tableKOR.setBounds(0,50,800,700);
		tableKOR.setRowHeight(113);
		if(GolfCalendar.f.length() != 0)
			inputStream();
		refreshCal();
		guiFrameKOR.setVisible(true);
	}
	public static Object [][] howManyDate(int m) 
	{
		if(m%2 == 0 && m!=2 && m!=8)
		{
			Object [][] date = { {1,2,3,4,5,6,7},
							  {8,9,10,11,12,13,14},
							  {15,16,17,18,19,20,21},
							  {22,23,24,25,26,27,28},
							  {29,30,null,null,null,null,null},
							  {null,null,null,null,null,null,null}
							};
			return date;
		}
		else if (m%2 ==1)
		{
			Object [][] date = { {1,2,3,4,5,6,7},
					  {8,9,10,11,12,13,14},
					  {15,16,17,18,19,20,21},
					  {22,23,24,25,26,27,28},
					  {29,30,31,null,null,null,null},
					  {null,null,null,null,null,null,null}
					};			
			return date;
		}
		else if (m==2)
		{
			Object [][] date = { {1,2,3,4,5,6,7},
					  {8,9,10,11,12,13,14},
					  {15,16,17,18,19,20,21},
					  {22,23,24,25,26,27,28},
					  {null,null,null,null,null,null,null},
					  {null,null,null,null,null,null,null}
					};		
			return date;
		}
		else if (m==8)
		{
			Object [][] date = { {1,2,3,4,5,6,7},
					  {8,9,10,11,12,13,14},
					  {15,16,17,18,19,20,21},
					  {22,23,24,25,26,27,28},
					  {29,30,31,null,null,null,null},
					  {null,null,null,null,null,null,null}
					};			
			return date;
		}
		return date;
	}
	public static Object[][] formatCalendar(Object [][] arr, int leadGap)
	{
		Object [][] arrtemp = arr;
		for (int i = 0; i<leadGap; i++)
		{
			arrtemp[0][i] = " ";
		}
		int a= 1;	
		for (int r = 0; r<arrtemp.length; r++)
		{
			for(int c = 0; c< arrtemp[0].length; c++)
			{
				if(arrtemp[r][c] != null)
					a++;
			}
		}
		int temp = 1;
		for (int r = 0; r<arrtemp.length;r++)
		{
			if (r == 0)
			{
				for (int c = leadGap; c<arrtemp[0].length; c++)
				{
					arrtemp[0][c] = temp;
					temp++;
				}
			}
			else
			{
				for (int c = 0; c<arrtemp[0].length; c++)
				{
					arr[r][c] = temp;
					temp++;
					if (temp > a)
					{
						arrtemp[r][c] = " ";
					}
				}
			}
		}
		return arrtemp;
	}
	public static void makeReport(JLabel lbl)
	{
		double sTotal = 0.0;
		double n = 0.0;
		double f = 0.0;
		for (int i = 0; i<31; i++)
		{
			if(GolfCalendar.yy.get(cbYear.getSelectedIndex()).month[(cbMonth.getSelectedIndex())].getDate()[i].getScore() != 0)
			{
				sTotal = sTotal +GolfCalendar.yy.get(cbYear.getSelectedIndex()).month[(cbMonth.getSelectedIndex())].getDate()[i].getScore();
				n++;
			}
		}

		if (n != 0)
		{
			f = sTotal/n;
			monthlyReport.setText("  이번달 평균 점수: "+ Math.round(f*100.0)/100.0);
		}
		else
			monthlyReport.setText("이번달 평균 점수: ");
	}
	public static void outputStream() throws Exception
	{
		FileOutputStream fos = new FileOutputStream(GolfCalendar.f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(GolfCalendar.yy);
		oos.close();
	}
	@SuppressWarnings("unchecked")
	public static void inputStream() throws Exception
	{
		FileInputStream fis = new FileInputStream(GolfCalendar.f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		GolfCalendar.yy = (ArrayList<Year>) ois.readObject();
		initCal();
		ois.close();
	}
	
	public void refreshCal()
	{
		Object[][] arr = howManyDate(cbModelMonth.get(cbMonth.getSelectedIndex()));
		int leadGap = new GregorianCalendar(cbModelYear.get(cbYear.getSelectedIndex()), cbModelMonth.get(cbMonth.getSelectedIndex())-1, 1).get(Calendar.DAY_OF_WEEK)-1;
		Object [][] arr1 = formatCalendar(arr, leadGap); 
		formattedCalendarKOR = arr1;
		tempCalendar = formattedCalendarKOR;
		for(int r = 0;r<arr1.length;r++)
		{
			for(int c=0; c<arr1[0].length;c++)
			{ 
				if(arr1[r][c] != " ")
				{
					int dInt = (Integer)arr1[r][c]-1;
					int sTemp = GolfCalendar.yy.get(cbYear.getSelectedIndex()).month[(cbMonth.getSelectedIndex())].date[dInt].getScore();
					String gTemp = GolfCalendar.yy.get(cbYear.getSelectedIndex()).month[(cbMonth.getSelectedIndex())].date[dInt].getGs().getGc().getGolfCourse();
					int nTemp = GolfCalendar.yy.get(cbYear.getSelectedIndex()).month[(cbMonth.getSelectedIndex())].date[dInt].getGs().getNumHoles();
					if (sTemp != 0 || !gTemp.equals("") || nTemp !=0)
					{
						tableKOR.getColumnModel().getColumn(c).setCellRenderer(new MultipleLines());
						formattedCalendarKOR[r][c] = arr1[r][c] + "\n"+"\n"+"점수:" 
						+ GolfCalendar.yy.get(cbYear.getSelectedIndex()).month[(cbMonth.getSelectedIndex())].date[dInt].getScore() 
						+ "\n" + "홀:" 
						+ GolfCalendar.yy.get(cbYear.getSelectedIndex()).month[(cbMonth.getSelectedIndex())].date[dInt].getGs().getNumHoles() 
						+ "\n" + "골프 코스: " 
						+ toKor(GolfCalendar.yy.get(cbYear.getSelectedIndex()).month[(cbMonth.getSelectedIndex())].date[dInt].getGs().getGc().getGolfCourse());
						}
					}
				}
			}
		makeReport(monthlyReport);
		guiFrameKOR.validate();
		guiFrameKOR.repaint();
	}
	public static void initCal()
	{
		Object[][] arr = howManyDate(Calendar.MONTH);
		int leadGap = new GregorianCalendar(cbModelYear.get(cbYear.getSelectedIndex()), cbModelMonth.get(cbMonth.getSelectedIndex())-1, 1).get(Calendar.DAY_OF_WEEK)-1;
		Object [][] arr1 = formatCalendar(arr, leadGap); 
		formattedCalendarKOR = arr1;
		tempCalendar = formattedCalendarKOR;
		if (GolfCalendar.yy.size() != 0)
		{
			for(int r = 0;r<arr1.length;r++)
			{
				for(int c=0; c<arr1[0].length;c++)
				{ 
					if(arr1[r][c] != " ")
					{
						int dInt = (Integer)arr1[r][c]-1;
						int sTemp = GolfCalendar.yy.get(year-1968).month[month-1].date[dInt].getScore();
						String gTemp = GolfCalendar.yy.get(year-1968).month[month-1].date[dInt].getGs().getGc().getGolfCourse();
						int nTemp = GolfCalendar.yy.get(year-1968).month[month-1].date[dInt].getGs().getNumHoles();
						if (sTemp !=0 || !gTemp.equals("") || nTemp != 0)
						{
								formattedCalendarKOR[r][c] = arr1[r][c] + "\n"+"\n"+"점수:" 
								+ GolfCalendar.yy.get(year-1968).month[month-1].date[dInt].getScore() 
								+ "\n" + "홀:" 
								+ GolfCalendar.yy.get(year-1968).month[month-1].date[dInt].getGs().getNumHoles() 
								+ "\n" + "골프 코스: " 
								+ toKor(GolfCalendar.yy.get(year-1968).month[month-1].date[dInt].getGs().getGc().getGolfCourse());
						}
					}
				}
			}
		}
		makeReport(monthlyReport);
		guiFrameKOR.validate();
		guiFrameKOR.repaint();
	}
	public static String toKor(String s)
	{
		String temp = null;
		if(s.equals("Jaypee") || s.equals("제이피"))
		{
			temp = "제이피";
		}
		else if (s.equals("Classic") || s.equals("클래식"))
		{
			temp = "클래식";
		}
		else if (s.equals("DLF"))
		{
			temp = "DLF";
		}
		return temp;
	}
	public static Object[][] myCalendarKOR = howManyDate(Calendar.MONTH-1);
	public static Object[][] myCalendarKOR1 = howManyDate(Calendar.MONTH-1);
	public static Object[][] formattedCalendarKOR = formatCalendar(myCalendarKOR, new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1).get(Calendar.DAY_OF_WEEK) - 1);
	public static Object[][] tempCalendar = formatCalendar(myCalendarKOR1, new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1).get(Calendar.DAY_OF_WEEK) - 1);
	private JScrollPane tableScrollPane;
	
	class ExampleTableModel extends AbstractTableModel
	{
		String [] day = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
		
		@Override
		public int getColumnCount() 
		{
			return day.length;
		}

		@Override
		public int getRowCount() 
		{
			return formattedCalendarKOR.length;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex)
		{
			return formattedCalendarKOR[rowIndex][columnIndex];
		}
		@Override
        public boolean isCellEditable(int rowIndex, int columnIndex) 
		{
			if (formattedCalendarKOR[rowIndex][columnIndex].equals(" "))
				return false;
			else
				return true;
        }
		
		@Override
		public String getColumnName(int column) 
		{
		return day[column];
		}
		
	}
	public static int getmyCalendarKORMonth()
	{
		return month;
	}
	public static int getmyCalendarKORYear()
	{
		return year;
	}
	public static JTable getTable() 
	{
		return tableKOR;
	}
}