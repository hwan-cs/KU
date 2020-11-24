package pa5.jhpark.konkuk;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.border.BevelBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class vocabQuiz extends JFrame
{

	private JFrame quizFrame;
	private JPanel panel;
	private JButton completed;
	private JButton again;
	private ArrayList <Word> foo = myEnglishVocab.vocab;
	private JRadioButton [] a = new JRadioButton [40];
	private Word [] q = new Word [10];
	private JLabel [] qLabel = new JLabel [10];
	private int correct;
	private long startTime;
	

	public vocabQuiz() 
	{
		
		quizFrame = new JFrame();
		quizFrame.setTitle("202011298 박정환");
		quizFrame.getContentPane().setForeground(Color.BLACK);
		quizFrame.setBounds(100, 100, 600, 800);
		quizFrame.setResizable(false);
		quizFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		quizFrame.getContentPane().setLayout(null);
		
		startTime = System.currentTimeMillis();
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED));
		panel.setBounds(0, 90, 600, 620);
		quizFrame.getContentPane().add(panel);
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(0, 90, 600, 620);
		quizFrame.getContentPane().add(scrollPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		panel.setLayout(new GridLayout(0,1,0,0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 250, 90);
		panel_1.setLayout(null);
		JTextArea info = new JTextArea("영어 단어 퀴즈");
		info.setFont(new Font("Arial", Font.BOLD, 18));
		info.setEditable(false);  
		info.setCursor(null);  
		info.setOpaque(false);  
		info.setFocusable(false);
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setBounds(10, 10, 240, 80);
		panel_1.add(info);
		quizFrame.getContentPane().add(panel_1);
		initQuiz();
		completed = new JButton("정답 맞추기");
		completed.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				correct = 0;
				for(int i =0;i<q.length; i++)
				{
					for(int j = i*4;j<((i+1)*4);j++)
					{
						if(a[j].getText().equals(q[i].kor))
						{
							if(a[j].isSelected())
							{
								qLabel[i].setForeground(new Color(0,153,0));
								correct++;
							}
							else
								qLabel[i].setForeground(new Color(204,0,0));
						}
					}
				}
				long endTime = System.currentTimeMillis();
				float sec = (endTime - startTime) / 1000F; 
				String str = null;
				if(sec <= 60)
				{
					str = "영어 단어 퀴즈\n10문제 중" + correct + "문제를 맞췄습니다. \n" + "소요된 시간: " 
							+String.format("%.2f", sec) + "초";
				}
				else
				{
					str = "영어 단어 퀴즈\n10문제 중" + correct + "문제를 맞췄습니다. \n" + "소요된 시간: " + (int)(Math.floor(sec/60))
							+ "분 " + String.format("%.2f", (sec-(60*(int)(Math.floor(sec/60))))) + "초";
				}
				info.setText(str);
				completed.setEnabled(false);
				again.setEnabled(true);
				quizFrame.validate();
				quizFrame.repaint();
			}
		});
		completed.setBounds(0, 710, 600, 60);
		quizFrame.getContentPane().add(completed);
		JButton numSearch = new JButton("빈출 단어 리스트");
		numSearch.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				JDialog dialog = new JDialog();
				JPanel numSearchPanel = new JPanel();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			    dialog.setResizable(false);
			    dialog.setBounds(200, 200, 305,330);
			    numSearchPanel.setBounds(0, 0, 300,300);
			    dialog.setTitle("빈출 단어 리스트");
			    dialog.getContentPane().setLayout(null);
			    dialog.getContentPane().add(numSearchPanel);
			    JScrollPane dialogScroll = new JScrollPane(numSearchPanel);
			    dialogScroll.setBounds(0, 0, 300, 300);
			    dialog.getContentPane().add(dialogScroll);
				dialogScroll.getVerticalScrollBar().setUnitIncrement(16);
				numSearchPanel.setLayout(new GridLayout(0,1,0,0));
				Collections.sort(foo, new stdComparator());
				for(Word word: foo)
				{
					if(word.search >0)
					{
						BevelBorder border = new BevelBorder(BevelBorder.RAISED);
						JLabel searchLabel = new JLabel("검색빈도("+word.search+") "+word.eng);
						searchLabel.setBorder(border);
						searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));
						numSearchPanel.add(searchLabel);
					}
				}
				dialog.setVisible(true);
			}
		});
		numSearch.setBounds(250, 0, 130, 90);
		quizFrame.getContentPane().add(numSearch);
		
		again = new JButton("다시하기");
		again.setEnabled(false);
		again.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				startTime = System.currentTimeMillis();
				completed.setEnabled(true);
				panel.removeAll();
				initQuiz();
				quizFrame.validate();
				quizFrame.repaint();
				again.setEnabled(false);
			}
		});
		again.setBounds(380, 0, 216, 90);
		quizFrame.getContentPane().add(again);
		quizFrame.setVisible(true);
	}
	class stdComparator implements Comparator <Word>
	{
		@Override
		public int compare(Word i, Word j)
		{
			if(i.search > 0 || j.search >0)
				return (i.search - j.search)*-1;  //내림차순으로 하기 위해 -1 곱하기
			else
				return 0;
		}
	}
	public void initQuiz()
	{
		Boolean [] question = new Boolean[foo.size()];
		Arrays.fill(question, false);
		int index = 0;
		for(int i = 0;i<10;i++)
		{
			Boolean[] answer = new Boolean[foo.size()];
			Arrays.fill(answer, false);
			int qWord = (int)(Math.random() * (foo.size()));
			while(question[qWord])
			{
				qWord = (int)(Math.random() * (foo.size()));
			}
			question[qWord] = true;
			panel.add(new JLabel(""));
			int randInt = (int)(Math.random() * (4));
			qLabel[i]  = new JLabel(" " + (i+1) +". " + foo.get(qWord).eng + "의 뜻은? ");
			foo.get(qWord).search++;
			q[i] = foo.get(qWord);
			qLabel[i].setFont(new Font("Arial", Font.BOLD, 15));
			panel.add(qLabel[i]);
			ButtonGroup bg = new ButtonGroup();
			for(int j =0;j<4;j++)
			{
				int ans = (int)(Math.random() * (foo.size()));
				while(answer[ans] || qWord == ans)
				{
					ans =  (int)(Math.random() * (foo.size()));
				}
				answer[ans] = true;
				if(j == randInt)
				{
					a[index] = new JRadioButton(foo.get(qWord).kor);
					a[index].setFont(new Font("Arial", Font.PLAIN, 15));
					panel.add(a[index]);
				}
				else
				{
					a[index] = new JRadioButton(foo.get(ans).kor);
					a[index].setFont(new Font("Arial", Font.PLAIN, 15));
					panel.add(a[index]);
				}
				bg.add(a[index]);
				index++;
			}
		}
	}
}
