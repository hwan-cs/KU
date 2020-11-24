package pa5.jhpark.konkuk;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class myEnglishVocab 
{

	private JFrame frame;
	private String fileName;
	private String shareFile;
	private String sendTo;
	private String path;
	public static ArrayList <Word> vocab = new ArrayList<Word>();
	private Boolean isOpened;
	private ArrayList <Word> rVocab;
	private int numResults;
	private JTextField searchVoc;
	private JTextField searchVoc2;
	private JTextField engWord;
	private JTextField korWord;
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					myEnglishVocab window = new myEnglishVocab();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public myEnglishVocab() 
	{
		initialize();
	}


	private void initialize() 
	{
		frame = new JFrame();
		frame.setTitle("202011298 박정환");
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		isOpened = false;
		JMenu fileMenu = new JMenu("File");
		JMenuItem newFile = new JMenuItem("New");
		newFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				fileName = "defaultVocabList" + (int)(Math.random() * (500)) + ".txt";
				try
				{
					File file = new File("/Users/19juhpark/Desktop/PA5/" + fileName); // 새로운 단어장이 저장되는 디렉토리. 사용자가 바꿔야함.
					PrintWriter fw= new PrintWriter(file);
					Scanner scan = new Scanner(new File("quiz.txt"));
					while(scan.hasNextLine())
					{
						String str = scan.nextLine();
			            fw.println(str);
					}
					fw.flush();
					fw.close();
				}
				catch(Exception exc)
				{
				    exc.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, fileName + "가 생성되었습니다!");
			}
		});
		fileMenu.add(newFile);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 130, 600, 620);
		frame.getContentPane().add(panel);
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(0, 130, 600, 620);
		frame.getContentPane().add(scrollPane);
		
		JMenuItem openFile = new JMenuItem("Open");
		openFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt");
				fileChooser.setFileFilter(filter);
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) 
				{
					panel.removeAll();
					path = null;
					isOpened = true;
					vocab = new ArrayList<Word>();
					panel.setLayout(new GridLayout(0, 2, 0, 0));
				    path = fileChooser.getSelectedFile().getAbsolutePath();
				    fileName = fileChooser.getSelectedFile().getName();
				    Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
				    try(Scanner scan = new Scanner(new File(path)))
				    {
				    	while(scan.hasNextLine())
						{
							String str = scan.nextLine();
							String [] s = str.split("\t");
							JLabel foo = new JLabel(" " + str);
							foo.setBorder(border);
							foo.setFont(new Font("Arial", Font.PLAIN, 15));
							vocab.add(new Word(s[0].trim(), s[1].trim(), 0));
							panel.add(foo);
						}
				    }
					catch(FileNotFoundException fnfe)
				    {
						JOptionPane.showMessageDialog(null, "파일을 찾지 못하였습니다.");
				    }
				    frame.validate();
				    frame.repaint();
				}
			}
		});
		fileMenu.add(openFile);
		JMenuItem share = new JMenuItem("Share");
		share.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt");
				fileChooser.setFileFilter(filter);
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(null);
				sendTo = null;
				if (result == JFileChooser.APPROVE_OPTION) 
				{
				    File selectedFile = fileChooser.getSelectedFile();
				    shareFile = selectedFile.getAbsolutePath();
				    sendTo = fileChooser.getSelectedFile().getName();
				    JDialog dialog = new JDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				    dialog.setModal(true);
				    dialog.setResizable(false);
				    dialog.setBounds(200, 200, 420,120);
				    dialog.setTitle("친구에게 단어장 공유하기");
				    dialog.getContentPane().setLayout(null);
					final String username = "사용자의 이메일";
			        final String password = "이메일 비밀번호";

			        JLabel sender = new JLabel("보내는 사람: "+username);
			        sender.setBounds(10, 10, 250, 20);
			        dialog.getContentPane().add(sender);
			        JLabel to = new JLabel("받는 사람: ");
			        to.setBounds(10, 40, 100, 20);
			        dialog.getContentPane().add(to);
			        JTextField recepient = new JTextField(20);
			        recepient.setBounds(70,40, 150, 20);
			        dialog.getContentPane().add(recepient);
			        JButton send = new JButton("전송");
			        send.setBounds(320, 40, 50,20);
			        dialog.getContentPane().add(send);
			        JLabel gm = new JLabel("@gmail.com");
			        gm.setBounds(220, 40, 80,20);
			        dialog.getContentPane().add(gm);
			        send.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							for(char s: recepient.getText().toCharArray())
							{
								if(!Character.toString(s).matches("^[a-zA-Z]*$") && !Character.toString(s).matches(".*\\d.*"))
								{
									JOptionPane.showMessageDialog(null, "올바르지 않은 이메일주소 입니다");
									return;
								}
							}
							Properties prop = new Properties();
					        prop.put("mail.smtp.host", "smtp.gmail.com");
					        prop.put("mail.smtp.port", "587");
					        prop.put("mail.smtp.auth", "true");
					        prop.put("mail.smtp.starttls.enable", "true"); //TLS
					        
					        Session session = Session.getInstance(prop, new javax.mail.Authenticator() 
					        {
			                    protected PasswordAuthentication getPasswordAuthentication() 
				                {
			                    	return new PasswordAuthentication(username, password);
				                }
				                });
						        try 
						        {
						            Message message = new MimeMessage(session);
						            String rec = recepient.getText().trim() + "@gmail.com";
						            message.setFrom(new InternetAddress("hwann1005@gmail.com"));
						            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
						            message.setSubject("같이 영단어 공부해요!");
						            BodyPart messageBodyPart1 = new MimeBodyPart();  
						            messageBodyPart1.setText("제 영어 단어장 입니다" + "\n\n같이 공부해요, " + rec+"님!"); 
						            BodyPart messageBodyPart2 = new MimeBodyPart();  
						            DataSource source = new FileDataSource(shareFile);   
						            messageBodyPart2.setDataHandler(new DataHandler(source));   
						            messageBodyPart2.setFileName(sendTo);   
						            Multipart multipartObject = new MimeMultipart();    
						            multipartObject.addBodyPart(messageBodyPart1);
						            multipartObject.addBodyPart(messageBodyPart2); 
						            message.setContent(multipartObject); 
						            Transport.send(message);
						            JOptionPane.showMessageDialog(null, rec + "에게 "+sendTo + "을 보냈습니다!");
						        } catch (MessagingException e1) {
						        	throw new RuntimeException(e1);
						        }
						}
					});
			        dialog.setVisible(true);
				}
			}
		});
		fileMenu.add(share);
		menuBar.add(fileMenu);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("단어 뜻 검색: ");
		lblNewLabel.setBounds(6, 63, 71, 26);
		frame.getContentPane().add(lblNewLabel);
		
		searchVoc = new JTextField();
		searchVoc.addActionListener(new ActionListener()  
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!(searchVoc.getText().trim().isEmpty()))
				{
					if(!searchVoc.getText().replaceAll("\\s+","").matches(".*\\d.*") 
							&& searchVoc.getText().replaceAll("\\s+","").matches("^[a-zA-Z]*$"))
					{
						panel.removeAll();
						for(int i = 0; i<vocab.size();i++)
						{
							if(searchVoc.getText().trim().equals(vocab.get(i).eng))
							{
								BevelBorder border = new BevelBorder(BevelBorder.RAISED);
								panel.setLayout(new BorderLayout(0, 0));
								JLabel foo = new JLabel(searchVoc.getText() +" 의 뜻은 : " +vocab.get(i).kor);
								foo.setFont(new Font("Arial", Font.PLAIN, 15));
								foo.setHorizontalAlignment(SwingConstants.CENTER);
								foo.setBorder(border);
								foo.setBounds(6, 95, 10, 26);
								panel.add(foo, BorderLayout.NORTH);
							}
						}
						if(panel.getComponentCount() == 0)
						{
							JLabel foo = new JLabel("일치하는 단어를 찾을 수 없습니다");
							BevelBorder border = new BevelBorder(BevelBorder.RAISED);
							foo.setHorizontalAlignment(SwingConstants.CENTER);
							foo.setFont(new Font("Arial", Font.BOLD, 25));
							foo.setBorder(border);
							panel.setLayout(new GridLayout(0,1,0, 0));
							panel.add(foo);
						}
						frame.validate();
						frame.repaint();
					}
					else
					{						
						JOptionPane.showMessageDialog(null, "영어 단어를 입력해주세요");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "단어를 입력해주세요");
				}
			}
		});
		searchVoc.setBounds(76, 63, 160, 26);
		searchVoc.setColumns(20);
		frame.getContentPane().add(searchVoc);
		
		JLabel lblNewLabel_1 = new JLabel("단어 검색: ");
		lblNewLabel_1.setBounds(249, 63, 71, 26);
		frame.getContentPane().add(lblNewLabel_1);
		
		searchVoc2 = new JTextField();
		searchVoc2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!(searchVoc2.getText().trim().isEmpty()))
				{
					if(!searchVoc2.getText().replaceAll("\\s+","").matches(".*\\d.*") 
							&& searchVoc2.getText().replaceAll("\\s+","").matches("^[a-zA-Z]*$"))
					{
						panel.removeAll();
						numResults = 0;
						String sWord = searchVoc2.getText();
						sWord = sWord.trim();
						sWord = sWord.toLowerCase();
						for(int i = 0; i<vocab.size();i++)
						{
							if(vocab.get(i).eng.contains(sWord))
							{
								numResults++;
							}
						}
						if(numResults >=30)
							panel.setLayout(new GridLayout(0, 2, 0, 0));
						else
							panel.setLayout(new GridLayout(0, 1, 0, 0));
						for(int j = 0; j<vocab.size();j++)
						{
							if(vocab.get(j).eng.contains(sWord))
							{
								JLabel foo = new JLabel(vocab.get(j).eng +" 의 뜻은 : " +vocab.get(j).kor);
								BevelBorder border = new BevelBorder(BevelBorder.RAISED);
								if((numResults > 0) && (numResults <=15))
									foo.setFont(new Font("Arial", Font.PLAIN, 20));
								else if(numResults > 15)
									foo.setFont(new Font("Arial", Font.PLAIN, 15));
								foo.setHorizontalAlignment(SwingConstants.CENTER);
								foo.setBorder(border);
								panel.add(foo);
							}
							else if(numResults == 0)
							{
								JLabel foo = new JLabel("일치하는 단어를 찾을 수 없습니다");
								BevelBorder border = new BevelBorder(BevelBorder.RAISED);
								foo.setHorizontalAlignment(SwingConstants.CENTER);
								foo.setFont(new Font("Arial", Font.BOLD, 25));
								foo.setBorder(border);
								panel.add(foo);
								break;
							}
						}
						frame.validate();
						frame.repaint();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "영어 단어를 입력해주세요");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "단어를 입력해주세요");
				}
			}
		});
		searchVoc2.setBounds(308, 63, 174, 26);
		searchVoc2.setColumns(20);
		frame.getContentPane().add(searchVoc2);
		
		
		JButton refresh = new JButton();
		refresh.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				panel.removeAll();
				panel.setLayout(new GridLayout(0, 2, 0, 0));
			    Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
			    try(Scanner scan = new Scanner(new File(path))) //단어장이 저장된 디렉토리. 사용자가 바꿀것.
			    {
			    	while(scan.hasNextLine())
					{
						String str = scan.nextLine();
						JLabel foo = new JLabel(" " + str);
						foo.setBorder(border);
						foo.setFont(new Font("Arial", Font.PLAIN, 15));
						panel.add(foo);
					}
			    }
				catch(FileNotFoundException fnfe)
			    {
					JOptionPane.showMessageDialog(null, "파일을 찾지 못하였습니다.");
			    }
			    catch(NullPointerException npe)
			    {
			    	JOptionPane.showMessageDialog(null, "파일이 열려있지 않습니다.");
			    }
			    frame.validate();
			    frame.repaint();
			}
		});
		refresh.setIcon(new ImageIcon("/Users/19juhpark/Downloads/refresh.png")); //사진을 불러내기위한 디렉토리. 사용자가 바꿀것.
		refresh.setBounds(89, 6, 45, 45);
		frame.getContentPane().add(refresh);
		
		JLabel lblNewLabel_3 = new JLabel("단어 추가: ");
		lblNewLabel_3.setBounds(6, 92, 77, 26);
		frame.getContentPane().add(lblNewLabel_3);
		
		engWord = new JTextField();
		engWord.setText("영어 단어 입력...");
		engWord.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusGained(FocusEvent e) 
			{
				JTextField source = (JTextField)e.getComponent();
		        source.setText("");
		        source.removeFocusListener(this);
			}
		});
		engWord.setBounds(68, 92, 140, 25);
		engWord.setColumns(20);
		frame.getContentPane().add(engWord);
		
		korWord = new JTextField();
		korWord.setText("단어 뜻 입력...");
		korWord.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusGained(FocusEvent e) 
			{
				JTextField source = (JTextField)e.getComponent();
		        source.setText("");
		        source.removeFocusListener(this);
			}
		});
		korWord.setBounds(220, 92, 140, 25);
		korWord.setColumns(20);
		frame.getContentPane().add(korWord);
		
		JButton addWord = new JButton("추가하기");
		addWord.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(isOpened)
				{
					if(!engWord.getText().isEmpty() && !korWord.getText().isEmpty() 
							&& !engWord.getText().equals("영어 단어 입력...") && !korWord.getText().equals("단어 뜻 입력..."))
					{
						if(!engWord.getText().replaceAll("\\s+","").matches(".*\\d.*") 
								&& engWord.getText().replaceAll("\\s+","").matches("^[a-zA-Z]*$"))
						{
							if(!korWord.getText().replaceAll("\\s+","").matches(".*\\d.*") 
									&& korWord.getText().replaceAll("\\s+","").matches("^[ㄱ-ㅎ가-힣]*$"))
							{
								try(Scanner scan = new Scanner(new File(path)))
								{
									FileWriter fileWritter = new FileWriter(path,true);        
						            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						            bufferWritter.write(engWord.getText().trim() + "\t" + korWord.getText().trim()+"\n");
						            bufferWritter.close();
						            fileWritter.close();
								} catch (FileNotFoundException e1) 
								{
									e1.printStackTrace();
								}
								catch (Exception e2)
								{
									e2.printStackTrace();
								}
								vocab.add(new Word(engWord.getText().trim(), korWord.getText().trim(), 0));
								Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
								JLabel foo = new JLabel(" " + engWord.getText().trim() + "\t" + korWord.getText().trim());
								foo.setBorder(border);
								foo.setFont(new Font("Arial", Font.PLAIN, 15));
								panel.add(foo);
								frame.validate();
								frame.repaint();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "한글로 뜻을 입력해 주세요");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "영어 단어를 입력해 주세요");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "영어 단어와 단어의 뜻을 입력해주요");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "열려있는 파일이 없습니다");
				}
					
			}
		});
		addWord.setBounds(372, 92, 110, 26);
		frame.getContentPane().add(addWord);
		
		JButton quizButton = new JButton("단어 퀴즈");
		quizButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				vocabQuiz quiz;
				if(isOpened)
					quiz = new vocabQuiz();
				else
					JOptionPane.showMessageDialog(null, "열려있는 파일이 없습니다");
			}
		});
		quizButton.setBounds(140, 6, 200, 45);
		frame.getContentPane().add(quizButton);
		
		JButton randVocab = new JButton("랜덤 영단어장 생성");
		randVocab.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				rVocab = new ArrayList<>();
				fileName = "randomVocabList" + (int)(Math.random() * (500)) + ".txt";
				try
				{
					File file = new File("/Users/19juhpark/Desktop/PA5/" + fileName);
					PrintWriter fw= new PrintWriter(file);
					Scanner scan = new Scanner(new File("enko_dict.txt"));
					while(scan.hasNextLine())
					{
						String str = scan.nextLine();
						String [] s = str.split("\t");
						String [] t = s[1].split(",");
						if(t.length > 1)
							rVocab.add(new Word(s[0].trim(), (t[0]+"/"+t[1]).trim(), 0));
						else if (t.length >0)
							rVocab.add(new Word(s[0].trim(), t[0].trim(), 0));
					}
					for(int i = 0;i<60;i++)
					{
						int rIndex = (int)(Math.random() * (rVocab.size()));
						fw.println(rVocab.get(rIndex).eng + "\t" + rVocab.get(rIndex).kor);
					}
					fw.flush();
					fw.close();
				}
				catch(Exception exc)
				{
				    exc.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, fileName + "가 생성되었습니다!");
			}
		});
		randVocab.setBounds(340, 6, 140, 45);
		frame.getContentPane().add(randVocab);
		
		JPanel imagePanel = new JPanel();
		imagePanel.setBounds(486, 0, 110, 130);
		frame.getContentPane().add(imagePanel);
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("/Users/19juhpark/Desktop/PA5/titleImage.jpg")); // 메인 화면의 사진의 디렉토리. 사용자가 조정할것.
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			imagePanel.add(picLabel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
