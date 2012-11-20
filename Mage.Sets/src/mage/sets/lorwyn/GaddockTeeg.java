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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Plopman
 */
public class GaddockTeeg extends CardImpl<GaddockTeeg> {

    public GaddockTeeg(UUID ownerId) {
        super(ownerId, 248, "Gaddock Teeg", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}{W}");
        this.expansionSetCode = "LRW";
        this.supertype.add("Legendary");
        this.subtype.add("Kithkin");
        this.subtype.add("Advisor");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Noncreature spells with converted mana cost 4 or greater can't be cast.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GaddockTeegReplacementEffect4()));
        // Noncreature spells with {X} in their mana costs can't be cast.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GaddockTeegReplacementEffectX()));
    }

    public GaddockTeeg(final GaddockTeeg card) {
        super(card);
    }

    @Override
    public GaddockTeeg copy() {
        return new GaddockTeeg(this);
    }
}

class GaddockTeegReplacementEffect4 extends ReplacementEffectImpl<GaddockTeegReplacementEffect4> {

    public GaddockTeegReplacementEffect4() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Noncreature spells with converted mana cost 4 or greater can't be cast. Noncreature spells with {X} in their mana costs can't be cast.";
    }

    public GaddockTeegReplacementEffect4(final GaddockTeegReplacementEffect4 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GaddockTeegReplacementEffect4 copy() {
        return new GaddockTeegReplacementEffect4(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && !card.getCardType().contains(CardType.CREATURE) && card.getManaCost().convertedManaCost() >= 4) {  
                return true;
            }
        }
        return false;
    }

}

class GaddockTeegReplacementEffectX extends ReplacementEffectImpl<GaddockTeegReplacementEffectX> {

    public GaddockTeegReplacementEffectX() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Noncreature spells with {X} in their mana costs can't be cast.";
    }

    public GaddockTeegReplacementEffectX(final GaddockTeegReplacementEffectX effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GaddockTeegReplacementEffectX copy() {
        return new GaddockTeegReplacementEffectX(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && !card.getCardType().contains(CardType.CREATURE) && card.getManaCost().getText().contains("X")) {  
                return true;
            }
        }
        return false;
    }

}