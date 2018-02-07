package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import util.DynamicCompiler;
import model.Companies;
import model.Company;

public class SelectCompaniesDisplay extends JFrame {
	
	JList<String> selectedCompanies;
	JList<String> availableCompanies;
	List<String> companies_src;
	JLabel content;
	DefaultListModel<String> selectedCompaniesList;
	DefaultListModel<String> availableCompaniesList;
	JButton delete_company;
	JButton add_company;
	JButton folder_button;
	JButton ok_button;
	JButton cancel_button;
	File chosenDirectory;
	List<Company> comps;
	Companies companies;
	
	public SelectCompaniesDisplay(Companies comps)
	{
		this.companies = comps;
	}
	
	public void init()
	{
		this.setTitle("Select Companies");
		this.setMinimumSize(new Dimension(600,500));
		this.setMaximumSize(new Dimension(800,600));
		this.setResizable(false);
		comps = new ArrayList<Company>();
		selectedCompanies = new JList<String>();
		availableCompanies = new JList<String>();
		companies_src = new ArrayList<String>();
		delete_company = new JButton("<");
		add_company = new JButton (">");
		ok_button = new JButton("Apply");
		cancel_button = new JButton ("Cancel");
		folder_button = new JButton ("Choose folder");
		selectedCompaniesList = new DefaultListModel<String>();
		availableCompaniesList = new DefaultListModel<String>();
		selectedCompanies = new JList<String>(selectedCompaniesList);
		selectedCompanies.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		selectedCompanies.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		selectedCompanies.setVisibleRowCount(-1);
		selectedCompanies.setBackground(Color.WHITE);
		availableCompanies = new JList<String>(availableCompaniesList);
		availableCompanies.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		availableCompanies.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		availableCompanies.setVisibleRowCount(-1);
		availableCompanies.setBackground(Color.WHITE);
		selectedCompanies.setPreferredSize(new Dimension(300,400));
		JScrollPane listScroller = new JScrollPane(selectedCompanies);
		listScroller.setPreferredSize(new Dimension(200,400));
		JScrollPane listScroller2 = new JScrollPane(availableCompanies);
		listScroller2.setPreferredSize(new Dimension(200,400));
		content = new JLabel();
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.45;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		
		content.add(new JLabel("Choose Companies"), c);
		c.gridx = 2;
		content.add(new JLabel("Selected Companies"), c);
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.8;
		c.gridheight = 2;
		content.add(listScroller2, c);
		c.gridheight = 1;
		c.gridx = 1;
		c.weightx = 0.1;
		c.anchor = GridBagConstraints.CENTER;
		content.add(add_company, c);
		c.anchor = GridBagConstraints.NORTH;
		c.gridy = 1;
		c.gridx = 2;
		c.gridheight = 2;
		c.weightx = 0.45;
		content.add(listScroller, c);
		c.gridy = 2;
		c.gridx = 1;
		c.gridheight = 1;
		c.weightx = 0.1;
		content.add(delete_company, c);
		c.gridy = 3;
		c.gridx = 0;
		c.weightx = 0.3;
		content.add(cancel_button, c);
		c.gridx = 1;
		c.gridheight = 1;
		c.weightx = 0.3;
		content.add(folder_button, c);
		c.gridx = 2;
		content.add(ok_button, c);
		this.add(content);
		
		folder_button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//JFrame fileChooser = new JFrame();
			    JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("choosertitle");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    add(chooser);
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			        System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			        System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			      } else {
			        System.out.println("No Selection ");
			      }
			    handleChosenDirectory(chooser.getSelectedFile());
			}
			
		});
		
		add_company.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (availableCompanies.getSelectedIndex() > -1)
					handleAddButton(availableCompanies.getSelectedIndex());		
			}
		});
		
		delete_company.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedCompanies.getSelectedIndex() > -1)
					handleRemoveButton(selectedCompanies.getSelectedIndex());
			}
		});
		
		ok_button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				handleOKButton();
			}
		});
		
		cancel_button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCancelButton();
			}
		});
	}
	
	public void start()
	{
		this.pack();
		this.setVisible(true);	
		this.repaint();
	}
	
	private void handleChosenDirectory(File dic)
	{
		this.chosenDirectory = dic;
        final List<File> sourceFiles =
                Arrays
                .asList(dic.listFiles())
                .stream()
                .filter(f -> f.getName().endsWith(".java"))
                .collect(Collectors.toList());
        for (File f : sourceFiles)
        {
        	this.availableCompaniesList.addElement(f.getName());
        }
        this.repaint();
	}
	
	private Company getCompanyFromFile(String name)
	{
		return DynamicCompiler.compileAndLoad(chosenDirectory, name).get(0);	
	}
	
	private void handleAddButton(int index)
	{
		String name = availableCompaniesList.getElementAt(index);
		this.selectedCompaniesList.addElement(name);
		this.companies_src.add(chosenDirectory.getPath() +"\\" + name);
	}
	
	private void handleRemoveButton(int index)
	{
		this.selectedCompaniesList.remove(index);
		this.companies_src.remove(index);
	}
	
	private void handleOKButton()
	{
		this.companies.setSource(this.companies_src);
		this.dispose();
	}
	
	private void handleCancelButton()
	{
		this.dispose();
	}
	

}
