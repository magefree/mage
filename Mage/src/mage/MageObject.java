package mage;

import java.io.Serializable;
import java.util.List;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.game.Game;

public interface MageObject extends MageItem, Serializable {

	public String getName();
	public void setName(String name);

	public List<CardType> getCardType();
	public List<String> getSubtype();
	public List<String> getSupertype();

	public Abilities<Ability> getAbilities();
	public ObjectColor getColor();
	public ManaCosts<ManaCost> getManaCost();
	
	public MageInt getPower();
	public MageInt getToughness();
	public MageInt getLoyalty();

//	public Zone getZone();
//	public void setZone(Zone zone);

	public void adjustCosts(Ability ability, Game game);

	public MageObject copy();
}
