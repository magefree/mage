package mage;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.constants.CardType;
import mage.game.Game;

public interface MageObject extends MageItem, Serializable {

    String getName();

    String getIdName();

    String getLogName();

    String getImageName();

    void setName(String name);

    List<CardType> getCardType();

    List<String> getSubtype(Game game);

    boolean hasSubtype(String subtype, Game game);

    List<String> getSupertype();

    Abilities<Ability> getAbilities();

    boolean hasAbility(UUID abilityId, Game game);

    ObjectColor getColor(Game game);
    
    ObjectColor getFrameColor(Game game);

    ManaCosts<ManaCost> getManaCost();

    int getConvertedManaCost();

    MageInt getPower();

    MageInt getToughness();
    
    int getStartingLoyalty();
    
    

    void adjustCosts(Ability ability, Game game);

    void adjustTargets(Ability ability, Game game);

    MageObject copy();

    /**
     * Defines that MageObject is a copy of another object
     *
     * @param isCopy
     */
    void setCopy(boolean isCopy);

    /**
     * Checks if current MageObject is a copy of another object
     *
     * @return
     */
    boolean isCopy();

    int getZoneChangeCounter(Game game);

    void updateZoneChangeCounter(Game game);

    void setZoneChangeCounter(int value, Game game);

}
