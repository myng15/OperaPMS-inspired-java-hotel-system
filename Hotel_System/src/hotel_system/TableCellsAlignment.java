package hotel_system;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellsAlignment {
	
	public static void setCellsAlignment(JTable table, int alignment) {
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(alignment);
		int numOfCols = table.getColumnCount();
		
		for(int i = 0; i < numOfCols; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}
	}
}
