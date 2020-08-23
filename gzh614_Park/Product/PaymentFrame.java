//THERE ARE MANY MORE CONDITIONS TO BE ADDED
//THESE ARE NOT FINAL CONDITIONS AND ARE ARBITRARY VALUES
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class PaymentFrame extends JFrame 
{
	
	private JPanel contentPane;
	private JPanel panel;
	public static GolfCourse Jaypee = new GolfCourse();
	public static GolfCourse Classic = new GolfCourse();
	public static GolfCourse DLF = new GolfCourse();
	public static ArrayList <String> promoPeople = 	promoPeople = new ArrayList<String>(); //HERE;
	public static boolean isWeek;
	public static int d = DateFrame.dd;
	
	public PaymentFrame(final int row, final int col) 
	{
		int [][] intOnly = DateFrame.objToInt(GolfCalendar.formattedCalendar);
		final int dateInt = intOnly[row][col] -1;
		final DateCal[] forPayment = GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate();
		final GolfSession gsForPayment = forPayment[dateInt].getGs();
		final JFrame pFrame = new JFrame();
		pFrame.getContentPane().setLayout(null);
		setDefaultCloseOperation(PaymentFrame.EXIT_ON_CLOSE);
		final JButton cal = new JButton("Calculate");
		if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==1)
		{
				pFrame.setSize(250, 360);
				contentPane = new JPanel();
				contentPane.setBounds(0,0,250,338);
				cal.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
				cal.setBounds(20, 280, 200, 40);
		}
		else if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==2)
		{
			pFrame.setSize(410, 360);
			contentPane = new JPanel();
			contentPane.setBounds(0,0,410,338);
			cal.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			cal.setBounds(100, 280, 200, 40);
		}
		else if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==3)
		{
			pFrame.setSize(600, 360);
			contentPane = new JPanel();
			contentPane.setBounds(0,0,600,338);
			cal.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			cal.setBounds(200, 280, 200, 40);
		}
		else if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==4)
		{
			pFrame.setSize(800, 360);
			contentPane = new JPanel();
			contentPane.setBounds(0,0,800,338);
			cal.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			cal.setBounds(300, 280, 200, 40);
		}
		else if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==5)
		{
			pFrame.setSize(800, 660);
			contentPane = new JPanel();
			contentPane.setBounds(0,0,800,638);
			cal.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			cal.setBounds(300, 580, 200, 40);
		}
		else if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==6)
		{
			pFrame.setSize(800, 660);
			contentPane = new JPanel();
			contentPane.setBounds(0,0,800,638);
			cal.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			cal.setBounds(300, 580, 200, 40);
		}
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setLayout(null);
		pFrame.setTitle("Payment for players in group");
		pFrame.setLocationRelativeTo(null);
		pFrame.getContentPane().add(contentPane);
		if (GolfCalendar.getTable().getColumnName(col).equals("Sunday")|| GolfCalendar.getTable().getColumnName(col).equals("Saturday"))
		{
			isWeek = true;
		}
		else
		{
			isWeek = false;
		}
		
		
		if (forPayment[dateInt].getGs().getGc().getGolfCourse().equals("Jaypee"))
			{
				Jaypee.setGolfCourse("Jaypee");
				Jaypee.setpCaddie(550.80);
				Jaypee.setpGolfCartNH(500.0);
				Jaypee.setpGolfCartEH(1186.44);
				Jaypee.setpNH(1650.0);
				Jaypee.setpEH(2550.0);
				Jaypee.setpWeekEh(7120.0);
				Jaypee.setpWeekNH(5550.0);
				forPayment[dateInt].getGs().setGc(Jaypee);
			}
		else if (forPayment[dateInt].getGs().getGc().getGolfCourse().equals("Classic"))
			{
				Classic.setGolfCourse("Classic");
				Classic.setpCaddie(850.0);
				Classic.setpGolfCartNH(725.11);
				Classic.setpGolfCartEH(1300.32);
				Classic.setpNH(2700.0);
				Classic.setpEH(4250.0);
				Classic.setpWeekEh(3400);
				Classic.setpWeekNH(2160);
				forPayment[dateInt].getGs().setGc(Classic);
			}
		else if (forPayment[dateInt].getGs().getGc().getGolfCourse().equals("DLF"))
			{
				DLF.setGolfCourse("DLF");
				DLF.setpCaddie(932.20);
				DLF.setpGolfCartNH(932.20);
				DLF.setpGolfCartEH(1355.93);
				DLF.setpNH(4350.0);
				DLF.setpEH(6250.0);
				DLF.setpWeekEh(7832.24);
				DLF.setpWeekNH(10320.58);
				forPayment[dateInt].getGs().setGc(DLF);
			}		
		GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().setCentralGST(0.09);
		GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().setStateGST(0.09);
			for(int i = 0; i<GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size(); i++)
			{
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getMember() == null)
				{
					GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(i).setMember(false);
				}
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getCaddie() == null)
				{
					GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(i).setGolfCart(false);
				}
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getGolfCart() == null)
				{
					GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(i).setGolfCart(false);
				}
				
			}
			final JLabel [] lblPrice = new JLabel[GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()];
			refreshPayment(row, col, lblPrice);
			cal.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent arg0) 
				{
					promoPeople.clear();
					for (int i = 0; i<GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getMonth()[GolfCalendar.cbMonth.getSelectedIndex()].getDate()[dateInt].getGs().getGroup().size(); i++)
					{
						double total = 0.0;
						int nCart = 0;
						ArrayList<Person> members = new ArrayList<Person> ();
						ArrayList<Person> nonMembers = new ArrayList<Person> ();
						int [][] intOnly = DateFrame.objToInt(GolfCalendar.formattedCalendar);
						final int dateInt = intOnly[row][col] -1;
						final GolfSession gsForPayment = forPayment[dateInt].getGs();
						int isPromo = new GregorianCalendar(GolfCalendar.cbModelYear.get(GolfCalendar.cbYear.getSelectedIndex()), GolfCalendar.cbModelMonth.get(GolfCalendar.cbMonth.getSelectedIndex())-1, intOnly[row][col]).get(Calendar.DAY_OF_WEEK) -1;
						if(forPayment[dateInt].getGs().getGroup().get(i).getGolfCart())
						{
							nCart+=1.0;
						}	
						if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getMember() != null)
						{
							if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getMember() == false)
							{
								nonMembers.add(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i));
								if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles() == 9)
								{
									if(isWeek)
									{
										total = total + forPayment[dateInt].getGs().getGc().getpWeekNH();
									}
									else if(isWeek == false)
									{
										total = total + forPayment[dateInt].getGs().getGc().getpNH();
									}
								}
								else if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles() == 18)
								{
									if(isWeek)
									{
										total = total + forPayment[dateInt].getGs().getGc().getpWeekEh();
									}
									else if(isWeek == false)
									{
										total = total + forPayment[dateInt].getGs().getGc().getpEH();
									}
								}
							}
							else if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getMember())
								members.add(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i));
						}
						if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getCaddie() != null)
						{
							if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getCaddie())
							{
									total = total + forPayment[dateInt].getGs().getGc().getpCaddie();
							}
						}
						if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getGolfCart() != null)
						{
							if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getGolfCart())
							{
								if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles() == 9)
								{
									total = total + forPayment[dateInt].getGs().getGc().getpGolfCartNH();
								}
								else if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles() == 18)
								{
									total = total + forPayment[dateInt].getGs().getGc().getpGolfCartEH();
								}
							}
						}
							nonMembers.clear();
							members.clear();
							for (int a = 0; a<GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getMonth()[GolfCalendar.cbMonth.getSelectedIndex()].getDate()[dateInt].getGs().getGroup().size(); a++)
							{
								if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getMonth()[GolfCalendar.cbMonth.getSelectedIndex()].getDate()[dateInt].getGs().getGroup().get(a).getMember() == false)
								{
									nonMembers.add(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getMonth()[GolfCalendar.cbMonth.getSelectedIndex()].getDate()[dateInt].getGs().getGroup().get(a));
								}
								else if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getMonth()[GolfCalendar.cbMonth.getSelectedIndex()].getDate()[dateInt].getGs().getGroup().get(a).getMember())
								{
									members.add(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getMonth()[GolfCalendar.cbMonth.getSelectedIndex()].getDate()[dateInt].getGs().getGroup().get(a));
								}
							}
							if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGc().getGolfCourse().equals("Classic") &&
									members.size() > 0 && nonMembers.size() >0 && isPromo == 3)
							{
								while(nonMembers.size() > 0 && members.size()> 0)
								{
									for(int t = 0; t< nonMembers.size(); t++)
									{
										if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getMonth()[GolfCalendar.cbMonth.getSelectedIndex()].getDate()[dateInt]
												.getGs().getGroup().get(i).getName().equals
												(nonMembers.get(t).getName()))
										{
											if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles() == 9)
											{
												if(isWeek)
												{
														total = total - forPayment[dateInt].getGs().getGc().getpWeekNH();
												}
												else if(isWeek == false)
												{
														total = total - forPayment[dateInt].getGs().getGc().getpNH();
												}
											}
											else if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getNumHoles() == 18)
											{
												if(isWeek)
												{
													total = total - forPayment[dateInt].getGs().getGc().getpWeekEh();
												}
												else if(isWeek == false)
												{
													total = total - forPayment[dateInt].getGs().getGc().getpEH();
												}
											}
											promoPeople.add(nonMembers.get(t).getName());
										}
										nonMembers.remove(t);
										members.remove(t);
										break;
									}
								}
							}
							if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGc().getGolfCourse().equals("DLF") && 
									isPromo == 4 && GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getMonth()[GolfCalendar.cbMonth.getSelectedIndex()]
											.getDate()[dateInt].getGs().getGroup().get(i).getMember() == false)
							{
								total = 3950.0;
								promoPeople.add(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).getMonth()[GolfCalendar.cbMonth.getSelectedIndex()].getDate()[dateInt].getGs().getGroup().get(i).getName());
							}
						lblPrice[i] = new JLabel();
						double cgst = GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getCentralGST();
						double sgst = GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getStateGST();
						total = total + (total*cgst) +(total*sgst);
						int t = (int) Math.rint(total);
						lblPrice[i].setText("Rs. "+ t);
						GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).setPrice(t);
						lblPrice[i].setFont(new Font("Lucida Grande", Font.PLAIN, 15));
						lblPrice[i].setBounds(11+180*i, 205, 146, 36);
					}
					contentPane.removeAll();
					contentPane.add(cal);
					JTextArea promo = new JTextArea();
					promo.setFont(new Font("Lucida Grande", Font.BOLD, 12));
					if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==2)
					{
						promo.setBounds(20,270, 400, 80);
					}
					else if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==3)
					{
						promo.setBounds(20, 280, 400, 80);
					}
					else if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==4)
					{
						promo.setBounds(20, 280, 400, 80);
					}
					else if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==5)
					{
						promo.setBounds(120, 580, 400, 80);
					}
					else if (GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size()==6)
					{
						promo.setBounds(120, 580, 400, 80);
					}
					promo.setBackground(new Color(236,236,236));
					promo.setForeground(Color.RED);
					String s = " ";
					if(promoPeople.size() >0)
					{
						for (int i = 0; i<promoPeople.size(); i++)
						{
							if (i == 0)
								s = s +promoPeople.get(i);
							else
								s = s + " ," +promoPeople.get(i);
						}
						promo.setText("Promotion sale applied to: "+ " \n" +s);
					}
					contentPane.add(promo);
					refreshPayment(row, col, lblPrice);
					pFrame.getContentPane().validate();
					pFrame.getContentPane().repaint();
				}
			});
		contentPane.add(cal);
		pFrame.getContentPane().setLayout(null);
		pFrame.setVisible(true);
	}
	public void refreshPayment(int row, int col, JLabel [] arr)
	{
		int [][] intOnly = DateFrame.objToInt(GolfCalendar.formattedCalendar);
		final int dateInt = intOnly[row][col] -1;
		final DateCal[] forPayment = GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate();
		final GolfSession gsForPayment = forPayment[dateInt].getGs();
		int isPromo = new GregorianCalendar(GolfCalendar.cbModelYear.get(GolfCalendar.cbYear.getSelectedIndex()), GolfCalendar.cbModelMonth.get(GolfCalendar.cbMonth.getSelectedIndex())-1, intOnly[row][col]).get(Calendar.DAY_OF_WEEK) -1;
		for(int i = 0; i<GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().size(); i++)
		{
			if (i >= 0 && i < 4)
			{
				panel = new JPanel();
				panel.setBounds(30+180*i, 6, 180, 255);
				panel.setBackground(Color.LIGHT_GRAY);
				panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), gsForPayment.getGroup().get(i).getName(), TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
				panel.setLayout(null);
				JLabel lblMember = new JLabel("Member: ");
				lblMember.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
				lblMember.setBounds(37+180*i, 29, 68, 36);
				contentPane.add(lblMember);
				final int count = i;
				final JCheckBox chckbxMember = new JCheckBox("");
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getMember() != null)
					{
					if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getMember())
					{
						//chckbxMember.setIgnoreRepaint(true);
						chckbxMember.setSelected(true);
					}
				}
				chckbxMember.addItemListener(new ItemListener() 
				{
					public void itemStateChanged(ItemEvent arg0) 
					{
						if(chckbxMember.isSelected())
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setMember(true);
						}
						else if(chckbxMember.isSelected() == false)
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setMember(false);
						}
					}
				});
				chckbxMember.setBounds(100+180*i, 37, 28, 23);
				contentPane.add(chckbxMember);
				
				JLabel lblCaddie = new JLabel("Caddie: ");
				lblCaddie.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
				lblCaddie.setBounds(35+180*i, 64, 68, 36);
				contentPane.add(lblCaddie);
				
				final JCheckBox chckbxCaddie = new JCheckBox(" ");
				chckbxCaddie.setBounds(89+180*i, 71, 28, 23);
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getCaddie() != null)
				{
					if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getCaddie())
					{
						chckbxCaddie.setSelected(true);
					}
				}
				chckbxCaddie.addItemListener(new ItemListener() 
				{
					public void itemStateChanged(ItemEvent arg0) 
					{
						if(chckbxCaddie.isSelected())
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setCaddie(true);
						}
						else if(chckbxCaddie.isSelected() == false)
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setCaddie(false);
						}
					}
				});
				contentPane.add(chckbxCaddie);
				JLabel lblGolfCart = new JLabel("Golf Cart: ");
				lblGolfCart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
				lblGolfCart.setBounds(40+180*i, 99, 75, 36);
				contentPane.add(lblGolfCart);
				 
				final JCheckBox chckbxGolfCart = new JCheckBox("");
				chckbxGolfCart.setBounds(105+180*i, 105, 28, 23);
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getGolfCart() != null)
				{
					if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getGolfCart())
					{
						chckbxGolfCart.setSelected(true);
					}
				}
				chckbxGolfCart.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						if(chckbxGolfCart.isSelected())
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setGolfCart(true);
						}
						else if(chckbxGolfCart.isSelected() == false)
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setGolfCart(false);
						}  
					}
				});
				contentPane.add(chckbxGolfCart);
				JTextArea lblPriceFor = new JTextArea();
				lblPriceFor.setBounds(40+180*i, 180, 146, 50);
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getPrice() != 0)
				{
					lblPriceFor.setLineWrap(true);
					lblPriceFor.setWrapStyleWord(true);
					lblPriceFor.setBackground(Color.LIGHT_GRAY);
					lblPriceFor.setText("Price for  " + GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getName()+ ": "
						+"\n" + GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getPrice());
				}
				else
				{
					if(arr[0] != null)
					{
						lblPriceFor.setLineWrap(true);
						lblPriceFor.setWrapStyleWord(true);
						lblPriceFor.setBackground(Color.LIGHT_GRAY);
						lblPriceFor.setText("Price for  " + GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getName()+ ": "
							+"\n" + arr[i].getText());
					}
					else
					{
						lblPriceFor.setBackground(Color.LIGHT_GRAY);
						lblPriceFor.setText("Price for "+ GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getName()+ " :");
					}
				}
				lblPriceFor.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
				contentPane.add(lblPriceFor);	
				contentPane.add(panel);
			}
			else if (i > 3 && i < 6)
			{
				panel = new JPanel();
				panel.setBounds(30+(180*(i-4)), 270, 180, 255);
				panel.setBackground(Color.LIGHT_GRAY);
				panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), gsForPayment.getGroup().get(i).getName(), TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
				panel.setLayout(null);
				JLabel lblMember = new JLabel("Member: ");
				lblMember.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
				lblMember.setBounds(37+(180*(i-4)), 283, 68, 36);
				contentPane.add(lblMember);
				final int count = i;
				final JCheckBox chckbxMember = new JCheckBox("");
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getMember() != null)
				{
					if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getMember())
					{
						chckbxMember.setSelected(true);
					}
				}
				chckbxMember.addItemListener(new ItemListener() 
				{
					public void itemStateChanged(ItemEvent arg0) 
					{
						if(chckbxMember.isSelected())
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setMember(true);
						}
						else if(chckbxMember.isSelected() == false)
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setMember(false);
						}
					}
				});
				chckbxMember.setBounds(100+(180*(i-4)), 287, 28, 23);
				contentPane.add(chckbxMember);
				
				JLabel lblCaddie = new JLabel("Caddie: ");
				lblCaddie.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
				lblCaddie.setBounds(35+180*(i-4), 314, 68, 36);
				contentPane.add(lblCaddie);
				
				final JCheckBox chckbxCaddie = new JCheckBox(" ");
				chckbxCaddie.setBounds(89+(180*(i-4)), 321, 28, 23);
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getCaddie() != null)
				{
					if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getCaddie())
					{
						//chckbxMember.setIgnoreRepaint(true);
						chckbxCaddie.setSelected(true);
					}
				}
				chckbxCaddie.addItemListener(new ItemListener() 
				{
					public void itemStateChanged(ItemEvent arg0) 
					{
						if(chckbxCaddie.isSelected())
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setCaddie(true);
						}
						else if(chckbxCaddie.isSelected() == false)
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setCaddie(false);
						}
					}
				});
				contentPane.add(chckbxCaddie);
				JLabel lblGolfCart = new JLabel("Golf Cart: ");
				lblGolfCart.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
				lblGolfCart.setBounds(35+(180*(i-4)), 349, 75, 36);
				contentPane.add(lblGolfCart);
				final JCheckBox chckbxGolfCart = new JCheckBox("");
				chckbxGolfCart.setBounds(105+(180*(i-4)), 355, 28, 23);
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getGolfCart() != null)
				{
					if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getGolfCart())
					{
						chckbxGolfCart.setSelected(true);
					}
				}
				chckbxGolfCart.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						if(chckbxGolfCart.isSelected())
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setGolfCart(true);
						}
						else if(chckbxGolfCart.isSelected() == false)
						{
							GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].date[dateInt].getGs().getGroup().get(count).setGolfCart(false);
						}
					}
				});
				contentPane.add(chckbxGolfCart);
				JTextArea lblPriceFor = new JTextArea();
				lblPriceFor.setBounds(40+(180*(i-4)), 430, 146, 50);
				if(GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getPrice() != 0)
				{
					lblPriceFor.setLineWrap(true);
					lblPriceFor.setWrapStyleWord(true);
					lblPriceFor.setBackground(Color.LIGHT_GRAY);
					lblPriceFor.setText("Price for  " + GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getName()+ ": "
						+"\n" + GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getPrice());
				}
				else
					{
					if(arr[0] != null)
					{
						lblPriceFor.setLineWrap(true);
						lblPriceFor.setWrapStyleWord(true);
						lblPriceFor.setBackground(Color.LIGHT_GRAY);
						lblPriceFor.setText("Price for  " + GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getName()+ ": "
							+"\n" + arr[i].getText());
					}
					else
					{
						lblPriceFor.setBackground(Color.LIGHT_GRAY);
						lblPriceFor.setText("Price for "+ GolfCalendar.yy.get(GolfCalendar.cbYear.getSelectedIndex()).month[(GolfCalendar.cbMonth.getSelectedIndex())].getDate()[dateInt].getGs().getGroup().get(i).getName()+ " :");
					}
				}
				lblPriceFor.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
				contentPane.add(lblPriceFor);	

				contentPane.add(panel);
			}
			}
	}
	
}
