package gui;

import javax.swing.*;

import bananas.FoodItem;
import bananas.ListItem;
import bananas.ListOfItems;
import bananas.ShoppingList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * Main GUI window of the application, used to access the ShoppingLists and Inventory
 */
public class BananaHome extends JFrame {

    //Private Variables
    private JFrame mainWindow;
    private JPanel mainPanel;
    private GridBagConstraints c;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem exitMenuItem;
    private JLabel title;
    private JButton groceriesButton, fridgeButton;

    public BananaHome() {

        //Create JFrame
        mainWindow = new JFrame("...Have No Bananas");

        mainWindow.setSize(450,400);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the main JPanel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(GUIColors.getBANANA_COLOR()); //banana yellow
        mainWindow.getContentPane().add(mainPanel);
        c = new GridBagConstraints();

        //Create the top menu
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        //Exit option
        exitMenuItem = new JMenuItem("Exit");
        menu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        mainWindow.setJMenuBar(menuBar);

        //Window title
        title = new JLabel("...Have no Bananas");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        title.setFont(GUIColors.getTITLE_FONT());
        title.setForeground(GUIColors.getFONT_BROWN());
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

        mainWindow.setVisible(true);
    }


}
