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

    public enum ActiveCardHalve {
        NONE, LEFT, RIGHT
    }
    private Card leftHalveCard;
    private Card rightHalveCard;

    private ActiveCardHalve activeCardHalve = ActiveCardHalve.NONE;

    public SplitCard(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs);
        this.splitCard = true;
    }

    public SplitCard(SplitCard card) {
        super(card);
        this.leftHalveCard = card.leftHalveCard;
        this.rightHalveCard = card.rightHalveCard;
        this.activeCardHalve = card.activeCardHalve;
    }

    public Card createLeftHalveCard (String name, String costs) {
        CardType[] cardTypes = new CardType[getCardType().size()];
        this.getCardType().toArray(cardTypes);
        leftHalveCard = new leftHalveCard(this.getOwnerId(), this.getCardNumber(), name, this.rarity, cardTypes, costs);
        leftHalveCard.getAbilities().setSourceId(objectId);
        return leftHalveCard;
    }

    public Card createRightHalveCard (String name, String costs) {
        CardType[] cardTypes = new CardType[getCardType().size()];
        this.getCardType().toArray(cardTypes);
        rightHalveCard = new leftHalveCard(this.getOwnerId(), this.getCardNumber(), name, this.rarity, cardTypes, costs);
        rightHalveCard.getAbilities().setSourceId(objectId);
        return rightHalveCard;
    }

    public Card getLeftHalveCard () {
        return leftHalveCard;
    }

    public Card getRightHalveCard () {
        return rightHalveCard;
    }

    public ActiveCardHalve getActiveCardHalve() {
        return activeCardHalve;
    }

    @Override
    public boolean cast(Game game, Constants.Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (super.cast(game, fromZone, ability, controllerId)) {
            if (leftHalveCard.getAbilities().contains(ability)) {
                activeCardHalve = ActiveCardHalve.LEFT;
            } else if (rightHalveCard.getAbilities().contains(ability)) {
                activeCardHalve = ActiveCardHalve.RIGHT;
            } else {
                activeCardHalve = ActiveCardHalve.NONE;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToZone(Constants.Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        if (super.moveToZone(toZone, sourceId, game, flag, appliedEffects)) {
            if (!toZone.equals(Zone.STACK)) {
                activeCardHalve = ActiveCardHalve.NONE;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        if(super.moveToExile(exileId, name, sourceId, game, appliedEffects)) {
            activeCardHalve = ActiveCardHalve.NONE;
            return true;
        }
        return false;
    }

    @Override
    public Abilities<Ability> getAbilities(){
        Abilities<Ability> allAbilites = new AbilitiesImpl<Ability>();
        if (activeCardHalve.equals(ActiveCardHalve.NONE) || activeCardHalve.equals(ActiveCardHalve.LEFT)) {
            allAbilites.addAll(leftHalveCard.getAbilities());
        }
        if (activeCardHalve.equals(ActiveCardHalve.NONE) || activeCardHalve.equals(ActiveCardHalve.RIGHT)) {
            allAbilites.addAll(rightHalveCard.getAbilities());
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
        switch (activeCardHalve) {
            case LEFT:
                return leftHalveCard.getSpellAbility();
            case RIGHT:
                return rightHalveCard.getSpellAbility();
        }
        return null;
    }

    @Override
    public List<String> getRules() {
        List<String> rules = new ArrayList<String>();
        if (activeCardHalve.equals(ActiveCardHalve.NONE) || activeCardHalve.equals(ActiveCardHalve.LEFT)) {
            rules.add(new StringBuilder("<b>").append(leftHalveCard.getName()).append("<b/>").toString());
            rules.addAll(leftHalveCard.getRules());
        }
        if (activeCardHalve.equals(ActiveCardHalve.NONE)) {
            rules.add("<br/>");
        }
        if (activeCardHalve.equals(ActiveCardHalve.NONE) || activeCardHalve.equals(ActiveCardHalve.RIGHT)) {
            rules.add(new StringBuilder("<b>").append(rightHalveCard.getName()).append("<b/>").toString());
            rules.addAll(rightHalveCard.getRules());
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
        leftHalveCard.getAbilities().setControllerId(controllerId);
        rightHalveCard.getAbilities().setControllerId(controllerId);
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        abilities.setControllerId(ownerId);
        leftHalveCard.getAbilities().setControllerId(ownerId);
        leftHalveCard.setOwnerId(ownerId);
        rightHalveCard.getAbilities().setControllerId(ownerId);
        rightHalveCard.setOwnerId(ownerId);

    }

    @Override
    public List<Watcher> getWatchers() {
        List<Watcher> allWatchers = new ArrayList<Watcher>();
        switch (activeCardHalve) {
            case LEFT:
                allWatchers.addAll(leftHalveCard.getWatchers());
                break;
            case RIGHT:
                allWatchers.addAll(rightHalveCard.getWatchers());
                break;
        }
        return allWatchers;
    }



}

/*
 * The left side card of the split card
 */
class leftHalveCard  extends CardImpl<leftHalveCard> {

    public leftHalveCard(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs);
    }

    public leftHalveCard(final leftHalveCard card) {
        super(card);
    }

    @Override
    public leftHalveCard copy() {
        return new leftHalveCard(this);
    }
}

/*
 * The right side card of the split card
 */
class rightHalveCard  extends CardImpl<rightHalveCard> {

    public rightHalveCard(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs);
    }

    public rightHalveCard(final rightHalveCard card) {
        super(card);
    }

    @Override
    public rightHalveCard copy() {
        return new rightHalveCard(this);
    }
}
