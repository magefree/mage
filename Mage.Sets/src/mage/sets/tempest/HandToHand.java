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
package mage.sets.tempest;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public class HandToHand extends CardImpl {

    public HandToHand(UUID ownerId) {
        super(ownerId, 180, "Hand to Hand", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "TMP";

        // During combat, players can't cast instant spells or activate abilities that aren't mana abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HandToHandEffect()));
    }

    public HandToHand(final HandToHand card) {
        super(card);
    }

    @Override
    public HandToHand copy() {
        return new HandToHand(this);
    }
}

class HandToHandEffect extends ContinuousRuleModifyingEffectImpl {

    public HandToHandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "During combat, players can't cast instant spells or activate abilities that aren't mana abilities";
    }

    public HandToHandEffect(final HandToHandEffect effect) {
        super(effect);
    }

    @Override
    public HandToHandEffect copy() {
        return new HandToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "During combat, players can't cast instant spells or activate abilities that aren't mana abilities (" + mageObject.getLogName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getPhase().getType() == TurnPhase.COMBAT) {
            MageObject object = game.getObject(event.getSourceId());
            if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                if (object.getCardType().contains(CardType.INSTANT)) {
                    return true;
                }
            }
            if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
                Ability ability = game.getAbility(event.getTargetId(), event.getSourceId());
                if (ability != null && !(ability instanceof ManaAbility)) {
                    return true;
                }
            }
        }
        return false;
    }
}
