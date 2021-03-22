package view;

import model.Activity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportDialog extends JDialog {
    private final DefaultTableModel activityTableModel = new DefaultTableModel();
    private final JTable activityTable = new JTable(activityTableModel);
    private final JScrollPane scrollPane = new JScrollPane();
    public ReportDialog(List<Activity> activityList) throws HeadlessException {
        setModalityType(ModalityType.APPLICATION_MODAL);
        initializeTableCols();
        fillTables(activityList);
        scrollPane.setViewportView(activityTable);
        add(scrollPane);
        this.pack();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void fillTables(List<Activity> activities){
        for(Activity activity: activities){
           activityTableModel.addRow(new String[]{
                   activity.getId().toString(),
                   activity.getUser().getUsername(),
                   activity.getDescription(),
                   activity.getDate().format(DateTimeFormatter.ISO_DATE_TIME)
           });
        }
    }

    private void initializeTableCols(){
        activityTableModel.addColumn("Id");
        activityTableModel.addColumn("User");
        activityTableModel.addColumn("Description");
        activityTableModel.addColumn("Time");
    }
}
