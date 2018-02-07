package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import util.DynamicCompiler;

/*
 * 
 */
public class Companies {
	
	private List<Company> companies;
	private List<String> companies_src;
	
	public Companies()
	{
		setCompanies(Collections.emptyList());
	}

	public List<Company> getCompanies() {
		return companies;
	}
	
	public boolean hasUserChoice()
	{
		if (companies_src == null || companies_src.isEmpty())
		{
			return false;
		}
		return true;
	}
	
	public Company getCompany(int index)
	{
		return companies.get(index);
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	
	public List<Company> getCompaniesWhichBuy()
	{
		return this.companies.stream().filter(Company::isBuying).collect(Collectors.toList());		
	}
	
	public List<Company> getCompaniesWhichTrucksReachDest()
	{
		return this.companies.stream().filter(c -> c.getTruck().getDistanceToTarget() == 0 && c.getTruck().isIdling())
				.collect(Collectors.toList());
	}
	
	public void Tick()
	{
		for (Company company : companies)
		{
			company.update();
		}
	}
	
	public void setSource(List<String> src)
	{
		this.companies_src = src;
	}

	public int getNumberOfParticipants() {
		
		return this.companies.size();
	}

	public void compile() {
		this.companies = new ArrayList<Company>();
		for (int i = 0; i < this.companies_src.size(); i++)
		{
			File dic = new File(companies_src.get(i));
			String name = dic.getName();
			dic = new File(dic.getParent() + "\\");
			this.companies.add(DynamicCompiler.compileAndLoad(dic, name).get(0));
		}
		
	}

}
