package bm.fit_zone.gui;

import bm.fit_zone.Service.ClientService;
import bm.fit_zone.Service.IClientService;
import bm.fit_zone.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

@Component
public class ZoneFitForm extends JFrame {

    private JPanel mainPanel;
    private JLabel label1;
    private JTable clientTable;
    private JTextField textName;
    private JTextField surNameText;
    private JTextField memberText;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton clearButton;
    private DefaultTableModel clintTableModel;
    private Integer idClient;
    IClientService clientService;

    @Autowired
    public ZoneFitForm(ClientService clientService) {
        this.clientService = clientService;
        startForm();
        saveButton.addActionListener(e -> saveClient());

        clientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadSelectedClient();
            }
        });
        deleteButton.addActionListener(e -> deletedClient());

        clearButton.addActionListener(e -> clearForm());
    }

    private void startForm(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        // no edit
        this.clintTableModel = new DefaultTableModel(0, 4){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        String[] headLine = {"Id" , "Name" , "Surname" , "Membership"};
        this.clintTableModel.setColumnIdentifiers(headLine);
        this.clientTable = new JTable(clintTableModel);
        // one Register
        this.clientTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        // Charge Client List
        clientList();
    }

    private void clientList(){
        this.clintTableModel.setRowCount(0);
        List<Client> clients = this.clientService.clientList();
        clients.forEach(client -> {
            Object[] clientRong = {
                    client.getIdclient(),
                    client.getName(),
                    client.getSurname(),
                    client.getMember()
            };
            this.clintTableModel.addRow(clientRong);
        });
    }

    private void saveClient(){
        if(textName.getText().equals("")){
            wacthMassage("Please provide a name.");
            textName.requestFocusInWindow();
            return;
        } else if(surNameText.getText().equals("")){
            wacthMassage("Please provide a Surname.");
        } else if(memberText.getText().equals("")) {
            wacthMassage("Please provide a Membership.");
        }

        String name = textName.getText();
        String surName = surNameText.getText();
        var membership = Integer.parseInt(memberText.getText());
        Client client = new Client(this.idClient, name, surName, membership);
//        client.setIdclient(idClient);
//        client.setName(name);
//        client.setSurname(surName);
//        client.setMember(membership);
        this.clientService.saveClient(client); // insert new object in DB or modify
        if(this.idClient == null)
            wacthMassage("Save New Client");
        else
            wacthMassage("Modify Success");
        clearForm();
        clientList();
    }

    private void loadSelectedClient(){
        int rows = clientTable.getSelectedRow();
            if(rows != -1){ // -1 no select
                String id =  clientTable.getModel().getValueAt(rows, 0).toString();
                this.idClient = Integer.parseInt(id);
                String name = clientTable.getModel().getValueAt(rows, 1).toString();
                this.textName.setText(name);
                String surName = clientTable.getModel().getValueAt(rows, 2).toString();
                this.surNameText.setText(surName);
                String membership = clientTable.getModel().getValueAt(rows , 3).toString();
                this.memberText.setText(membership);
            }

    }

    private void deletedClient(){
        int rows = clientTable.getSelectedRow();

        if(rows != -1) {
            String idClientSTR = clientTable.getModel().getValueAt(rows, 0).toString();
            this.idClient = Integer.parseInt(idClientSTR);
            Client client = new Client();
            client.setIdclient(this.idClient);
            clientService.deleteClient(client);
            wacthMassage(" Client id " + this.idClient + " Deleted ");
            clearForm();
            clientList();

        }
        else
            wacthMassage("Select a Client for Delete This");
    }

    private void clearForm(){
        textName.setText("");
        surNameText.setText("");
        memberText.setText("");
        // clear id selected
        this.idClient = null;
        // clear selection
        this.clientTable.getSelectionModel().clearSelection();
    }

    private void wacthMassage(String message){
        JOptionPane.showMessageDialog(this, message);
    }
}
