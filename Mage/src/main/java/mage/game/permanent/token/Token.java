package mage.game.permanent.token;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author ArcadeMode
 */
public interface Token extends MageObject {

    @Override
    Token copy();

    String getDescription();

    List<UUID> getLastAddedTokenIds();

    void addAbility(Ability ability);
    void addAbility(Ability ability, boolean fromExistingObject);

    void removeAbility(Ability abilityToRemove);

    void removeAbilities(List<Ability> abilitiesToRemove);

    boolean putOntoBattlefield(int amount, Game game, Ability source);

    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId);

    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking);

    /**
     * @param attacking          tokens enter the battlefield attacking (rule 508.4)
     * @param attackedDefenders  defenders (player, planeswalker, battle) the attacking tokens can attack,
     *                           its controller chooses one of them, null for any defender (see Combat.getPossibleDefenders)
     */
    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking, Set<UUID> attackedDefenders);

    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking, Set<UUID> attackedDefenders, UUID attachedTo);

    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking, Set<UUID> attackedDefenders, UUID attachedTo, boolean created);

    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking, Set<UUID> attackedDefenders, UUID attachedTo, boolean created, List<Token> additionalTokens);

    void setPower(int power);

    void setToughness(int toughness);

    Card getCopySourceCard();

    void setCopySourceCard(Card copySourceCard);

    Token getBackFace();

    void setColor(ObjectColor color);

    void clearManaCost();

    void setEntersTransformed(boolean entersTransformed);

    boolean isEntersTransformed();
}
