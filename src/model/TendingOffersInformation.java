package model;

import java.util.List;

/**
 * Allows access to all offers which are currently available.
 * @author Max
 *
 */
public interface TendingOffersInformation {
	
	/**
	 * 
	 * @return a list of all currently available offers.
	 */
	public List<OfferInformation> getOffers();
}
