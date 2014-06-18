package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Andy on 6/14/2014.
 */
public class BananaHome extends JFrame {

    //Private Variables
    private JFrame mainWindow;
    private JPanel mainPanel;
    private GridBagConstraints c;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    private JLabel title;
    private JButton groceriesButton, fridgeButton;

    public BananaHome() {

        //Create JFrame
        mainWindow = new JFrame("Have No Bananas");
        mainWindow.setVisible(true);
        mainWindow.setSize(500,500);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the main JPanel
        mainPanel = new JPanel(new GridBagLayout());
        mainWindow.getContentPane().add(mainPanel);
        c = new GridBagConstraints();

        //Create the top menu
        menuBar = new JMenuBar();
        menu = new JMenu("A Menu");
        menuBar.add(menu);
        menuItem = new JMenuItem("Text Only");
        menu.add(menuItem);
        mainWindow.setJMenuBar(menuBar);

        //Window title
        title = new JLabel("Welcome to Have No Bananas!");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        mainPanel.add(title, c);

        //Grocery List button
        groceriesButton = new JButton("Groceries");
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(groceriesButton, c);
        groceriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new GroceriesWindow();
            }
        });

        //Fridge (Inventory) button
        fridgeButton = new JButton("Your Fridge");
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(fridgeButton, c);
        fridgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new FridgeWindow();
            }
        });

    }


    public static void main (String[] args) {
        new BananaHome();
    }

}
