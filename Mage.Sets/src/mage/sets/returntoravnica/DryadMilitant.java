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
package mage.sets.returntoravnica;

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
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author LevelX2
 */
public class DryadMilitant extends CardImpl<DryadMilitant> {

    public DryadMilitant(UUID ownerId) {
        super(ownerId, 214, "Dryad Militant", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{G/W}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Dryad");
        this.subtype.add("Soldier");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new DryadMilitantReplacementEffect()));
    }

    public DryadMilitant(final DryadMilitant card) {
        super(card);
    }

    @Override
    public DryadMilitant copy() {
        return new DryadMilitant(this);
    }
}

class DryadMilitantReplacementEffect extends ReplacementEffectImpl<DryadMilitantReplacementEffect> {

    public DryadMilitantReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Exile);
        staticText = "If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead";
    }

    public DryadMilitantReplacementEffect(final DryadMilitantReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DryadMilitantReplacementEffect copy() {
        return new DryadMilitantReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getTargetId());
        if (card != null) {
            return card.moveToExile(null, "", source.getId(), game);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone() == Constants.Zone.GRAVEYARD) {
            Card card = (Card) game.getCard(event.getTargetId());
            if (card != null && (card.getCardType().contains(CardType.SORCERY) || card.getCardType().contains(CardType.INSTANT))) {
                return true;
            }
        }
        return false;
    }
}