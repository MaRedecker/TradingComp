package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import model.Buildings;
import model.Companies;
import model.Company;
import model.InventoryInformation;
import model.OfferInformation;
import model.StoredArticleInformation;
import model.PendingOffers;
import model.TruckInformation;

public class BoardDisplay extends JPanel {

	public static final Color BACKGROUND = Color.BLACK;
	private PendingOffers tendingOffers;
	private Companies companies;
	private Rectangle clipBound;
	private Rectangle warehouse;
	private Rectangle sales;
	private Rectangle store;
	private Rectangle storeFront;
	Rectangle warehouseFront;
	
	public BoardDisplay(PendingOffers tendOffers, Companies companies)
	{   	
		tendingOffers = tendOffers;
		this.companies = companies;
		this.setMinimumSize(new Dimension(100,100));
		this.setBackground(Color.BLACK);
		warehouse = new Rectangle(0, 0, 10, 10);
		sales = new Rectangle(0,0,10,10);
		store = new Rectangle(0,0,10,10);
	}
	
    @Override
    protected void paintComponent(final Graphics g) {
    	
    	this.clipBound = g.getClipBounds();
    	this.updateRectangles();
    	g.setColor(Color.GREEN);
    	g.fillRect(0,0, clipBound.width, clipBound.height);
    	g.setColor(Color.BLACK);
    	this.paintWarehouseFront(g);
    	this.paintStoreFront(g);
    	this.paintStreets(g);
    	this.paintTrucks(g);
    	this.paintEntryDec(g);
    	this.paintStore(g);
    	this.paintWarehouse(g);
    	this.paintSales(g);
    	this.paintOffers(g);
    	this.paintPlayerInfos(g, warehouse);
    	this.paintPlayerMoney(g, sales);
    	this.paintArticleValues(g, store);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }
    
    private void paintStore(Graphics g)
    {
    	g.setColor(Color.GRAY);
    	fillRect(g, store);
    }
    
    private void paintWarehouse(Graphics g)
    {
    	g.setColor(Color.GRAY);
    	fillRect(g, warehouse);
    	g.setColor(Color.BLACK);
    	drawRect(g, warehouse);
    }
    
    private void paintWarehouseFront(Graphics g)
    {
    	g.setColor(Color.GRAY);
    	warehouseFront = new Rectangle((int) ((int) warehouse.getX() + warehouse.getWidth()),0,clipBound.width / 16,clipBound.height);
    	fillRect(g, warehouseFront); 
    	g.setColor(Color.BLACK);
    	drawRect(g, warehouseFront);
    }
    
    private void paintStoreFront(Graphics g)
    {
    	g.setColor(Color.GRAY);
    	storeFront = new Rectangle((int) ((int) store.getX() + store.getWidth()),0,clipBound.width / 16,clipBound.height);
    	fillRect(g, storeFront); 
    	g.setColor(Color.BLACK);
    	drawRect(g, storeFront);   	
    }
    
    private void paintSales(Graphics g)
    {
    	g.setColor(Color.GRAY);
    	fillRect(g, sales);
    	g.setColor(Color.BLACK);
    	drawRect(g, sales);
    }
    
    private void paintEntryDec(Graphics g)
    {
    	List<Company> allComps = companies.getCompanies();
    	for (int i = 0; i < allComps.size(); i++)
    	{
    		g.setColor(Color.GRAY);
    		g.fillRect((int) this.warehouseFront.getX()+1, this.getHeightofPlayer(i) - 5, this.warehouseFront.width /2, clipBound.height / 20 + 10);
    		g.fillRect((int) this.storeFront.getX()+1, this.getHeightofPlayer(i) - 5, this.storeFront.width /2, clipBound.height / 20 + 10);
    		g.setColor(Color.DARK_GRAY);
    		g.fillRect((int) this.warehouseFront.getX()+1+this.warehouseFront.width /2, this.getHeightofPlayer(i) - 10, this.warehouseFront.width /2, 5);
    		g.fillRect((int) this.storeFront.getX()+1+this.storeFront.width /2, this.getHeightofPlayer(i) - 10, this.warehouseFront.width /2, 5);
    	}       	
    }
    
    private void paintOffers(Graphics g)
    {
    	g.setColor(Color.BLACK);
    	g.setFont(new Font("Arial", Font.PLAIN, clipBound.height / 25));
    	g.drawString("Offers: ", 10, clipBound.height / 25);
    	for (int i = 0; i < tendingOffers.getOffers().size(); i++)
    	{
    		OfferInformation offer = tendingOffers.getOffers().get(i);
    		String output = offer.getAmount() +" "+
    				offer.getArticle().getName() + " for " + 
    				String.format("%.2f", offer.getFullPrice());
    		Font font = this.getFontWhichFits(g, output, store, 75);
    		g.setFont(font);
    		g.drawString(output, 5 ,clipBound.height / 20 * (i+2));
    	}
    }
    
    private void paintTrucks(Graphics g)
    {
    	List<Company> allComps = companies.getCompanies();
    	for (int i = 0; i < allComps.size(); i++)
    	{
    		TruckInformation truck = allComps.get(i).getTruck();
    		g.setColor(allComps.get(i).getColor());
    		g.fillRect(truck.getPosition() * clipBound.width / Buildings.SALES,
    				(i+1) * clipBound.height / 10, clipBound.width / 20, 
    				clipBound.height / 20);
    	}
    }
    
    private void paintStreets(Graphics g)
    {
    	List<Company> allComps = companies.getCompanies();
    	for (int i = 0; i < allComps.size(); i++)
    	{
    		g.setColor(Color.BLACK);
    		g.fillRect(0, this.getHeightofPlayer(i) - 5, clipBound.width, clipBound.height / 20 + 10);
    	}   	
    }
    
    private void updateRectangles()
    {
    	this.store.setSize(clipBound.width / 6, clipBound.height);
    	this.warehouse.setBounds(clipBound.width / 2 - clipBound.width / 12, 0, 
    							2 * clipBound.width / 12, clipBound.height);
    	this.sales.setBounds(clipBound.width - store.width, 0, store.width, clipBound.height);
    	
    }
    
    private void fillRect(Graphics g, Rectangle2D rect)
    {
    	g.fillRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
    }
    
    private void drawRect(Graphics g, Rectangle2D rect)
    {
    	g.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
    }
    
    private void paintPlayerInfos(Graphics g, Rectangle dest)
    {
    	g.setColor(Color.BLACK);
    	String header = "Companies:";
    	Font font = this.getFontWhichFits(g, header, dest, 80);
    	g.setFont(font);
    	g.drawString(header, (int) (dest.x + (dest.width * 0.1)), clipBound.height / 25); 
    	List<Company> allComps = companies.getCompanies();
    	for (int i = 0; i < allComps.size(); i++)
    	{
    		Color playerColor = allComps.get(i).getColor();
    		g.setColor(playerColor);
    		String playerName = allComps.get(i).getName();
    		String output = playerName;
    		font = this.getFontWhichFits(g, output, dest, 50);
    		g.setFont(font);
    		g.drawString(output, (int) (dest.x + (dest.width * 0.25)), this.getHeightofPlayer(i));
    		output = "Amount Stored: " + allComps.get(i).getInventory().getAmountOfStoredArticles();
    		font = this.getFontWhichFits(g, output, dest, 50);
    		g.setFont(font);
    		g.drawString(output, (int) (dest.x + (dest.width * 0.25)), 
    				(this.getHeightofPlayer(i+1) - this.getHeightofPlayer(i)) / 2 + 
    				 this.getHeightofPlayer(i));
    	}
    	
    }
    
    private void paintPlayerMoney(Graphics g, Rectangle dest)
    {
    	g.setColor(Color.BLACK);
    	String header = "Total money:";
    	Font font = this.getFontWhichFits(g, header, dest, 80);
    	g.setFont(font);
    	g.drawString(header, (int) (dest.x + (dest.width * 0.1)), clipBound.height / 25);  
    	List<Company> allComps = companies.getCompanies();
    	for (int i = 0; i < allComps.size(); i++)
    	{
    		Color playerColor = allComps.get(i).getColor();
    		g.setColor(playerColor);
    		double money = allComps.get(i).getInventory().getMoney();
    		String output = "Money:  " + String.format("%.2f", money);
    		font = this.getFontWhichFits(g, output, dest, 50);
    		g.setFont(font);
    		g.drawString(output, (int) (dest.x + (dest.width * 0.25)) , 
    				this.getHeightofPlayer(i) + dest.height / 18);
    	}
    }
    
    private void paintArticleValues(Graphics g, Rectangle dest)
    {
    	g.setColor(Color.BLACK);
    	String header = "Selling value:";
    	Font font = this.getFontWhichFits(g, header, dest, 70);
    	g.setFont(font);
    	g.drawString(header, dest.x, clipBound.height / 2); 
    	if (companies.getCompanies().size() > 0)
    	{
    		InventoryInformation inv = companies.getCompanies().get(0).getInventory();
    		for (int i = 0; i < inv.getAllArticles().size(); i++)
    		{
    			StoredArticleInformation storedArticle = inv.getAllArticles().get(i);
    			String output = storedArticle.getArticleObject().getName() + ": "  
    					+ String.format("%.2f", storedArticle.getSellingPricePerUnit());
    			
    			font = this.getFontWhichFits(g, output, dest, 60);
    			g.setFont(font);
    			g.drawString(output, dest.x, clipBound.height / 2 + ((i+1) * clipBound.height / 15));
    		}
    	}
    }
    
    private int getHeightofPlayer(int index)
    {
    	return (index+1) * clipBound.height / 10;
    }
    
    private Font getFontWhichFits(Graphics g, String text, Rectangle dest, double coverPercentage)
    {
    	Font font = new Font("Arial", Font.PLAIN, 20);
    	Rectangle2D r2d = g.getFontMetrics(font).getStringBounds(text, g);
    	font = font.deriveFont((float)(font.getSize2D() * dest.getWidth() / 
    			(100 / coverPercentage) / r2d.getWidth()));
    	return font;
    }
    
}
