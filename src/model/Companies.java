package model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 * 
 */
public class Companies {
	
	private List<Company> companies;
	
	public Companies()
	{
		setCompanies(Collections.emptyList());
	}

	public List<Company> getCompanies() {
		return companies;
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

}
