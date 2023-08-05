package mage.game.permanent.token;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;

import java.util.List;
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

    void removeAbility(Ability abilityToRemove);

    void removeAbilities(List<Ability> abilitiesToRemove);

    boolean putOntoBattlefield(int amount, Game game, Ability source);

    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId);

    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking);

    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking, UUID attackedPlayer);

    boolean putOntoBattlefield(int amount, Game game, Ability source, UUID controllerId, boolean tapped, boolean attacking, UUID attackedPlayer, boolean created);

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
