package gui;

import bananas.DAOUtils;
import bananas.FoodItem;
import bananas.ListItem;
import bananas.ListOfItems;
import bananas.ShoppingList;
import bananas.ShoppingListDAO;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * A GUI class to display Lists of groceries
 *
 * Created by Andy on 6/14/2014.
 */
public class GroceriesWindow extends JFrame implements WindowFocusListener {

    //Private Variables
    private JFrame mainWindow;
    private JPanel mainPanel, listPanel;
    private GridBagConstraints c;
    private JLabel title;
    private JList groceriesList;
    private DefaultListModel listModel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem closeMenuItem;
    private JButton goButton, deleteListButton, createListButton;
    private ShoppingList shoppingList = new ShoppingList("HomeList");
    private ArrayList<ListOfItems> hasLists;
    private ArrayList<ListOfItems> noLists;
    private DAOUtils daoUtil = new DAOUtils();
    private ShoppingListDAO dao = new ShoppingListDAO(daoUtil);
    private Map<String, ArrayList<ListOfItems>> allLists;

    public GroceriesWindow () {
        //Create the JFrame
        mainWindow = new JFrame("Groceries Home");
        mainWindow.setSize(350,500);
        mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create the main JPanel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(GUIColors.getBANANA_COLOR());
        mainWindow.getContentPane().add(mainPanel);
        c = new GridBagConstraints();

        //Create the top menu
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        //Exit option
        closeMenuItem = new JMenuItem("Close");
        menu.add(closeMenuItem);
        closeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainWindow.setVisible(false);
            }
        });

        mainWindow.setJMenuBar(menuBar);

        //Window title
        title = new JLabel("Your Grocery Lists");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        title.setFont(GUIColors.getHEADING_FONT());
        title.setForeground(GUIColors.getFONT_BROWN());
        mainPanel.add(title, c);

        //JPanel for groceries lists
        listPanel = new JPanel();
        listPanel.setPreferredSize(new Dimension(150,200));
        listPanel.setMinimumSize(new Dimension(150,200));
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(listPanel, c);

        //Groceries List
        listModel = new DefaultListModel();

        groceriesList = new JList(listModel);
        groceriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        groceriesList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(groceriesList);
        listScroller.setPreferredSize(new Dimension(150, 200));
        
        c.gridx = 0;
        c.gridy = 2;
        listPanel.add(groceriesList, c);
        
        allLists = shoppingList.getAllShoppingListsFromDB();
        hasLists = allLists.get("Pass");
        noLists = allLists.get("Fail");
        
        
        if(noLists != null){
            listModel.addElement("There was an error retrieving the lists from the database. Its probably because the"
            		+ "guy who coded it has had too many brain inuries");
        }
        if(hasLists == null || hasLists.size() == 0){
        	listModel.addElement("You have no lists");
        }
        else if (hasLists.size() > 0){
        	for(ListOfItems list : hasLists){
        		listModel.addElement(list.getName());
        }//end for-each
        }//end if

        //"Go" button (select highlighted list item)
        goButton = new JButton("Go");
        c.gridx = 0;
        c.gridy = 3;
        mainPanel.add(goButton, c);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //open selected ShoppingList
                //new DisplayListWindow(shoppingList);
            	//display window is where all the adding/deleting of items takes place
            	
            	String listName = (String) groceriesList.getSelectedValue();
            	if(hasLists != null){
            	for(ListOfItems sList : hasLists){
            		if(sList.getName() == listName){
            		
            			new DisplayListWindow(listName);
            		}
            	}
            	}else{
            		new DisplayListWindow("There are no lists");
            	}
            	
                mainWindow.setVisible(false);
            }
        });

        //"Delete list" button (select highlighted list item)
        deleteListButton = new JButton("Delete list");
        c.gridx = 0;
        c.gridy = 4;
        mainPanel.add(deleteListButton, c);
        deleteListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //delete selected ShoppingList
            	
            	String listName = (String) groceriesList.getSelectedValue();
            	if(listName != null && hasLists != null){
            		for(ListOfItems sList : hasLists){
            			if(sList.getName() == listName){
                    		
            				ShoppingList listToDelete = (ShoppingList)sList;
            				Boolean isDeleted = listToDelete.deleteShoppingListFromDB();
            				if(isDeleted){
            					//wrong place to use this conditional, declare outside and use for repaint maybe
                        		}
                		}//end if
            	}//end for

                    listModel.removeElement(listName);
                    mainWindow.repaint();
            	}//end action performed
            }
        });
        
        //create list button
        createListButton = new JButton("Create list");
        c.gridx = 0;
        c.gridy = 5;
        mainPanel.add(createListButton, c);
        createListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new CreateListWindow();
            }
        });

        mainWindow.addWindowFocusListener(this);

        //Show the window
        mainWindow.setVisible(true);
    }

    public void windowGainedFocus(WindowEvent e) {
        listModel.removeAllElements();
        allLists = shoppingList.getAllShoppingListsFromDB();
        hasLists = allLists.get("Pass");
        if(hasLists != null){
        	for(ListOfItems list : hasLists){
        		listModel.addElement(list.getName());
        	}//end for-each
        }
        mainPanel.repaint();
     }

    public void windowLostFocus(WindowEvent e) {}
}
