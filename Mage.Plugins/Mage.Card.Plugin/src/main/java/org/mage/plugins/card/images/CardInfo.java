package org.mage.plugins.card.images;

public class CardInfo {
	public String name;
	public String set;
	public Integer collectorId;
	public boolean isToken = false;
	public CardInfo(String name, String set, Integer collectorId) {
		this.name = name;
		this.set = set;
		this.collectorId = collectorId;
	}
}
