/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.keyword.FuseAbility;
import mage.game.Game;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */

public abstract class SplitCard<T extends SplitCard<T>> extends CardImpl<T> {

    public enum ActiveCardHalf {
        NONE, LEFT, RIGHT, BOTH
    }
    private Card leftHalfCard;
    private Card rightHalfCard;

    private ActiveCardHalf activeCardHalf = ActiveCardHalf.NONE;

    public SplitCard(UUID ownerId, int cardNumber, String nameLeft, String nameRight, Rarity rarity, CardType[] cardTypes, String costsLeft, String costsRight) {
        super(ownerId, cardNumber, new StringBuilder(nameLeft).append(" - ").append(nameRight).toString(), rarity, cardTypes, costsLeft + costsRight);
        this.createLeftHalfCard(nameLeft, costsLeft);
        this.createRightHalfCard(nameRight, costsRight);
        this.splitCard = true;
    }

    public SplitCard(SplitCard card) {
        super(card);
        this.leftHalfCard = card.leftHalfCard.copy();
        this.rightHalfCard = card.rightHalfCard.copy();
        this.activeCardHalf = card.activeCardHalf;
    }

    private Card createLeftHalfCard (String nameLeft, String costsLeft) {
        CardType[] cardTypes = new CardType[getCardType().size()];
        this.getCardType().toArray(cardTypes);
        leftHalfCard = new LeftHalfCard(this.getOwnerId(), this.getCardNumber(), nameLeft, this.rarity, cardTypes, costsLeft);
        leftHalfCard.getAbilities().setSourceId(objectId);
        return leftHalfCard;
    }

    private Card createRightHalfCard (String nameRight, String costsRight) {
        CardType[] cardTypes = new CardType[getCardType().size()];
        this.getCardType().toArray(cardTypes);
        rightHalfCard = new LeftHalfCard(this.getOwnerId(), this.getCardNumber(), nameRight, this.rarity, cardTypes, costsRight);
        rightHalfCard.getAbilities().setSourceId(objectId);
        return rightHalfCard;
    }



    public Card getLeftHalfCard () {
        return leftHalfCard;
    }

    public Card getRightHalfCard () {
        return rightHalfCard;
    }

    public ActiveCardHalf getActiveCardHalf() {
        return activeCardHalf;
    }

    @Override
    public boolean cast(Game game, Constants.Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (this.getAbilities().contains(ability)) {
            activeCardHalf = ActiveCardHalf.BOTH;
        } else if (leftHalfCard.getAbilities().contains(ability)) {
            activeCardHalf = ActiveCardHalf.LEFT;
        } else if (rightHalfCard.getAbilities().contains(ability)) {
            activeCardHalf = ActiveCardHalf.RIGHT;
        } else {
            activeCardHalf = ActiveCardHalf.NONE;
        }
        if (super.cast(game, fromZone, ability, controllerId)) {
            return true;
        }
        activeCardHalf = ActiveCardHalf.NONE;
        return false;
    }

    @Override
    public boolean moveToZone(Constants.Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        if (super.moveToZone(toZone, sourceId, game, flag, appliedEffects)) {
            if (!toZone.equals(Zone.STACK)) {
                activeCardHalf = ActiveCardHalf.NONE;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        if(super.moveToExile(exileId, name, sourceId, game, appliedEffects)) {
            activeCardHalf = ActiveCardHalf.NONE;
            return true;
        }
        return false;
    }

    @Override
    public Abilities<Ability> getAbilities(){
        Abilities<Ability> allAbilites = new AbilitiesImpl<Ability>();
        if (activeCardHalf.equals(ActiveCardHalf.NONE) || activeCardHalf.equals(ActiveCardHalf.LEFT)) {
            allAbilites.addAll(leftHalfCard.getAbilities());
        }
        if (activeCardHalf.equals(ActiveCardHalf.NONE) || activeCardHalf.equals(ActiveCardHalf.RIGHT)) {
            allAbilites.addAll(rightHalfCard.getAbilities());
        }
        for (Ability ability: super.getAbilities()) {
            if (ability instanceof FuseAbility) {
                allAbilites.add(ability);
            }
        }
        return allAbilites;
    }

    @Override
    public SpellAbility getSpellAbility() {
        switch (activeCardHalf) {
            case LEFT:
                return leftHalfCard.getSpellAbility();
            case RIGHT:
                return rightHalfCard.getSpellAbility();
        }
        return null;
    }

    @Override
    public List<String> getRules() {
        List<String> rules = new ArrayList<String>();
        if (activeCardHalf.equals(ActiveCardHalf.NONE) || activeCardHalf.equals(ActiveCardHalf.LEFT)) {
            rules.add(new StringBuilder("<b>").append(leftHalfCard.getName()).append("<b/>").toString());
            rules.addAll(leftHalfCard.getRules());
        }
        if (activeCardHalf.equals(ActiveCardHalf.NONE)) {
            rules.add("<br/>");
        }
        if (activeCardHalf.equals(ActiveCardHalf.NONE) || activeCardHalf.equals(ActiveCardHalf.RIGHT)) {
            rules.add(new StringBuilder("<b>").append(rightHalfCard.getName()).append("<b/>").toString());
            rules.addAll(rightHalfCard.getRules());
        }

        for (Ability ability: super.getAbilities()) {
            if (ability instanceof FuseAbility) {
                rules.add("<br/>------------------------------------------------------------");
                rules.add(ability.getRule());
            }
        }
        return rules;
    }

    @Override
    public void setControllerId(UUID controllerId) {
        abilities.setControllerId(controllerId);
        leftHalfCard.getAbilities().setControllerId(controllerId);
        rightHalfCard.getAbilities().setControllerId(controllerId);
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        abilities.setControllerId(ownerId);
        leftHalfCard.getAbilities().setControllerId(ownerId);
        leftHalfCard.setOwnerId(ownerId);
        rightHalfCard.getAbilities().setControllerId(ownerId);
        rightHalfCard.setOwnerId(ownerId);

    }

    @Override
    public List<Watcher> getWatchers() {
        List<Watcher> allWatchers = new ArrayList<Watcher>();
        switch (activeCardHalf) {
            case LEFT:
                allWatchers.addAll(leftHalfCard.getWatchers());
                break;
            case RIGHT:
                allWatchers.addAll(rightHalfCard.getWatchers());
                break;
        }
        return allWatchers;
    }
}

/*
 * The left side card of the split card
 */
class LeftHalfCard  extends CardImpl<LeftHalfCard> {

    public LeftHalfCard(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs);
    }

    public LeftHalfCard(final LeftHalfCard card) {
        super(card);
    }

    @Override
    public LeftHalfCard copy() {
        return new LeftHalfCard(this);
    }
}

/*
 * The right side card of the split card
 */
class RightHalfCard  extends CardImpl<RightHalfCard> {

    public RightHalfCard(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs);
    }

    public RightHalfCard(final RightHalfCard card) {
        super(card);
    }

    @Override
    public RightHalfCard copy() {
        return new RightHalfCard(this);
    }
}
