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
    String getLogName();
    String getImageName();
    void setName(String name);

    List<CardType> getCardType();
    List<String> getSubtype();
    boolean hasSubtype(String subtype);
    List<String> getSupertype();

    Abilities<Ability> getAbilities();
    Abilities<Ability> getAbilities(Game game);
    void removeAbility(Ability ability, Game game);
    void clearAbilities(Game game);
    boolean hasAbility(UUID abilityId, Game game);
    boolean hasAbility(Ability ability, Game game);

    ObjectColor getColor();
    ManaCosts<ManaCost> getManaCost();

    MageInt getPower();
    MageInt getToughness();

    void adjustChoices(Ability ability, Game game);
    void adjustCosts(Ability ability, Game game);
    void adjustTargets(Ability ability, Game game);

    MageObject copy();

    /**
     * Defines that MageObject is a copy of another object
     * @param isCopy
     */
    void setCopy(boolean isCopy);

    /**
     * Checks if current MageObject is a copy of another object
     * @return
     */
    boolean isCopy();
}
