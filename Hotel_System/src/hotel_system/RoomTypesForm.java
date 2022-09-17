package hotel_system;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.EventQueue;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import java.awt.Color;

public class RoomTypesForm extends JFrame {

	private JPanel contentPane;
	private JTable roomTypesTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomTypesForm frame = new RoomTypesForm();
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
	@SuppressWarnings("serial")
	public RoomTypesForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100, 800, 450);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 800, 422);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(scrollPane);
		
		roomTypesTable = new JTable();
		scrollPane.setViewportView(roomTypesTable);
		roomTypesTable.setModel(new DefaultTableModel(
				new Object[][] { }, 
				new String[] {"Type ID","Label", "Description", "Max Occupancy", "No. of Rooms", "Rack Rate"} //column names
			) {
				/**
				 * 
				 */
				//make the jTable cells not editable - 1. Alternative
		
				public boolean isCellEditable(int row, int column){
					return false;
				};
			});
		
		RoomManager roomManager = new RoomManager();
		roomManager.fillRoomTypesTable(roomTypesTable);
		roomTypesTable.setRowHeight(50);
		roomTypesTable.getColumnModel().getColumn(0).setPreferredWidth(7);
		roomTypesTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		roomTypesTable.getColumnModel().getColumn(2).setPreferredWidth(250);
		roomTypesTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		roomTypesTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		roomTypesTable.getColumnModel().getColumn(5).setPreferredWidth(40);		
	
		roomTypesTable.setGridColor(Color.gray);
		
//		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
//		for(int i=0; i<roomTypesTable.getColumnCount(); i++){
//			roomTypesTable.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
//	        }
		TableCellsAlignment.setCellsAlignment(roomTypesTable, SwingConstants.CENTER);
	}		
		
		
}
 


		
//import java.awt.Component;
//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;
//import java.awt.event.ComponentListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.util.EventObject;
//import javax.swing.event.CellEditorListener;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.TableCellEditor;
//import javax.swing.AbstractCellEditor;
//import javax.swing.DefaultCellEditor;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;

//		class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
//			WordWrapCellRenderer(){
//				setLineWrap(true);
//				setWrapStyleWord(true);
//			}
//			int rowHeight = 0;
//			@Override
//			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//					boolean hasFocus, int row, int column) {
//				setSize(table.getColumnModel().getColumn(1).getWidth(), table.getRowHeight(row));
//				int height = getPreferredSize().height + table.getRowMargin();
//				if (column == 0 || height > rowHeight) {
//			        table.setRowHeight(row, height);
//			        rowHeight = height;
//			    }
////				roomTypesTable.setRowHeight(50);
////				roomTypesTable.getColumnModel().getColumn(0).setPreferredWidth(7);
////				roomTypesTable.getColumnModel().getColumn(1).setPreferredWidth(40);
////				roomTypesTable.getColumnModel().getColumn(2).setPreferredWidth(50);
////				roomTypesTable.getColumnModel().getColumn(3).setPreferredWidth(20);
////				roomTypesTable.getColumnModel().getColumn(4).setPreferredWidth(20);
////				roomTypesTable.getColumnModel().getColumn(5).setPreferredWidth(15);
//				
//				return this;
//			}
//		}
//		

//	tableModel = new DefaultTableModel(
//			new Object[][] { }, 
//			new String[] {"Type ID","Label", "Description", "Max Occupancy", "No. of Rooms", "Rack Rate"});
//	
//	roomTypesTable = new JTable(tableModel) {
//		public TableCellRenderer getCellRenderer(int row, int col){
//            //Wrap text in a specific cell
//            if (row == 0 && col == 1){
//                return new MyRenderer();
//            } else {
//                return new DefaultTableCellRenderer();
//            }
//        }
//
//        public TableCellEditor getCellEditor(int row, int col){
//            //Wrap text in a specific cell
//            if (row == 0 && col == 1){
//                return new MyEditor();
//            
//            } else {
//                return new DefaultCellEditor(new JTextField());
//            }
//        }
//    };
//    roomTypesTable.setRowHeight(0, roomTypesTable.getFont().getSize()*3);
//    Room room = new Room();
//	room.fillRoomTypesTable(roomTypesTable);
//
////    JScrollPane sp = new JScrollPane(roomTypesTable);
////    getContentPane().add(sp);
////    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////    this.setSize(600, 400);
////    this.setVisible(true);
//}
//
//private class MyRenderer extends JTextArea implements TableCellRenderer{
//    public MyRenderer() {
//        setOpaque(true);
//        setLineWrap(true);
//        setWrapStyleWord(true);
//    }
// 
//    public Component getTableCellRendererComponent(JTable table,Object value,
//        boolean isSelected, boolean hasFocus, int row,int column) {
//     
//        this.setText(value == null ? "" : value.toString());
//        return this;
//    }
//
//}
//
//private class MyEditor extends AbstractCellEditor implements TableCellEditor {
//    JTextArea comp = new JTextArea();
//    JTable table;
//    int row;
//    
//    public MyEditor() {
//        comp.setLineWrap(true);
//        comp.setWrapStyleWord(true);
//        comp.addComponentListener((ComponentListener) new ComponentAdapter() {
//            public void componentResized(ComponentEvent e) {
//                super.componentResized(e);
//                table.setRowHeight(row, (int) (comp.getPreferredSize().getHeight()));
//            }
//        });
//        comp.addKeyListener((KeyListener) new KeyAdapter() {
//            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
//                table.setRowHeight(row, (int) (comp.getPreferredSize().getHeight()));
//            }
//        });
//    }
//    
//
//    public Component getTableCellEditorComponent(JTable table, Object value,
//            boolean isSelected, int row, int column) {
//        this.table = table;
//        this.row = row;
//
//        comp.setText((String) value);
//        comp.setFont(table.getFont());
//
//        return comp;
//    }
//
//    public Object getCellEditorValue() {
//        return comp.getText();
//    }
//
//
//	@Override
//	public boolean isCellEditable(EventObject anEvent) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//
//	@Override
//	public boolean shouldSelectCell(EventObject anEvent) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//
//	@Override
//	public boolean stopCellEditing() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//
//	@Override
//	public void cancelCellEditing() {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void addCellEditorListener(CellEditorListener l) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void removeCellEditorListener(CellEditorListener l) {
//		// TODO Auto-generated method stub
//		
//	}
//}



