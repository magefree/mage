package mage;

import java.io.Serializable;
import java.util.List;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.abilities.Abilities;
import mage.abilities.costs.mana.ManaCosts;

public interface MageObject extends MageItem, Serializable {

	public String getName();

	public List<CardType> getCardType();
	public List<String> getSubtype();
	public List<String> getSupertype();

	public Abilities getAbilities();
	public ObjectColor getColor();
	public ManaCosts getManaCost();
	
	public MageInt getPower();
	public MageInt getToughness();
	public MageInt getLoyalty();

	public Zone getZone();
	public void setZone(Zone zone);
	
}
