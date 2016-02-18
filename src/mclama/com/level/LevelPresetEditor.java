package mclama.com.level;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelPresetEditor extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtStartx;
	private JTextField txtStarty;
	private JTextField txtEndX;
	private JTextField txtEndY;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LevelPresetEditor frame = new LevelPresetEditor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LevelPresetEditor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setShowGrid(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
				{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
			},
			new String[] {
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"
			}
		) {
			Class[] columnTypes = new Class[] {
				Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setBounds(28, 35, 280, 256);
		contentPane.add(table);
		
		txtStartx = new JTextField();
		txtStartx.setText("1");
		txtStartx.setBounds(327, 34, 86, 20);
		contentPane.add(txtStartx);
		txtStartx.setColumns(10);
		
		txtStarty = new JTextField();
		txtStarty.setText("8");
		txtStarty.setBounds(327, 62, 86, 20);
		contentPane.add(txtStarty);
		txtStarty.setColumns(10);
		
		txtEndX = new JTextField();
		txtEndX.setColumns(10);
		txtEndX.setBounds(327, 191, 86, 20);
		contentPane.add(txtEndX);
		
		txtEndY = new JTextField();
		txtEndY.setColumns(10);
		txtEndY.setBounds(327, 219, 86, 20);
		contentPane.add(txtEndY);
		
		JList list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 11));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {" 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10", "11", "12", "13", "14", "15", "16"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(10, 35, 20, 256);
		contentPane.add(list);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
		textArea.setText(" 1  2 3  4  5  6  7  8  9 10 11 12 13 14 15 16");
		textArea.setBounds(28, 19, 280, 20);
		contentPane.add(textArea);
		
		JButton btnProcess = new JButton("Process");
		btnProcess.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				processTable();
			}
		});
		btnProcess.setBounds(367, 268, 89, 23);
		contentPane.add(btnProcess);
	}

	protected void processTable() {
		boolean data[][] = getTableData(table);
		int sx = Integer.parseInt(txtStarty.getText());
		int sy = Integer.parseInt(txtStartx.getText());
		
		int ex = 0;
		int ey = 0;
		
		
		int width=0, height=0;
		String text = "";
		int lowX=99,highX=0;
		int lowY=99,highY=0;
		
		
		//X AND Y reversed here? idk why? doesn't work with flip
		for(int x=1; x<data.length+1; x++){
			for(int y=1; y<data[0].length+1; y++){
				if(data[x-1][y-1]==true)
				{
					if (y > highX) {
						highX = y;
						ex = x; ey = y;
					}
					if (y < lowX) {
						lowX = y;
					}
					if (x > highY) {
						highY = x;
					}
					if (x < lowY) {
						lowY = x;
					}
					if(Math.abs(sx - x)>width){
						width = Math.abs(sx-x);
					}
					if(Math.abs(sy - y)>height){
						height = Math.abs(sy-y);
					}
					//System.out.println(sx - x + " x " + sx + "," + x);
					//System.out.println(sy - y + " y " + sy + "," + y);
					text += "tiles[x+" + (sx - x) + "][y+" + (sy - y) + "]=true;\n";
				}
			}
		}
		
		try {
			ex = Integer.parseInt(txtEndY.getText());
			ey = Integer.parseInt(txtEndX.getText());
		} catch (NumberFormatException e) {
		}
		
		//if(x+3 > 0 && x+3 <width
		//&& y-2 > 0 && y+4<height){
		//tiles[x][y]=true;
		//que.add(new Point(x+3, y+3));
		
		lowX = Math.abs(sy - lowX);
		lowY = Math.abs(sx - lowY);
		
		highX = Math.abs(sy - highX);
		highY = Math.abs(sx - highY);
		
		text = text.replace("+-", "-");
		
		System.out.println("if(x+" + highX + " < width");
		System.out.println("&& y-"+ lowY + " > 0 && y+" + highY + " < height){");
		System.out.println(text);
		//System.out.println(ex + "," + ey);
		System.out.println(("que.add(new Point(x+" + 
				(ex-sx) + ", y+" + (ey-sy) + "));\n}\nbreak;").replace("+-", "-"));
		
	}
	
	public boolean[][] getTableData (JTable table) {
	    DefaultTableModel dtm = (DefaultTableModel) table.getModel();
	    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
	    boolean[][] tableData = new boolean[nRow][nCol];
	    for (int i = 0 ; i < nRow ; i++)
	        for (int j = 0 ; j < nCol ; j++)
	            tableData[i][j] = (boolean) dtm.getValueAt(i,j);
	    return tableData;
	}
}
