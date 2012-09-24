package mage;

import mage.Constants.CardType;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.game.Game;

import java.io.Serializable;
import java.util.List;

public interface MageObject extends MageItem, Serializable {

    public String getName();
    public void setName(String name);

    public List<CardType> getCardType();
    public List<String> getSubtype();
    public boolean hasSubtype(String subtype);
    public List<String> getSupertype();

    public Abilities<Ability> getAbilities();
    public ObjectColor getColor();
    public ManaCosts<ManaCost> getManaCost();

    public MageInt getPower();
    public MageInt getToughness();

    public void adjustChoices(Ability ability, Game game);
    public void adjustCosts(Ability ability, Game game);
    public void adjustTargets(Ability ability, Game game);

    public MageObject copy();

    /**
     * Defines that MageObject is a copy of another object
     * @param isCopy
     */
    public void setCopy(boolean isCopy);

    /**
     * Checks if current MageObject is a copy of another object
     * @return
     */
    public boolean isCopy();
}
