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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public class PyromancersSwath extends CardImpl<PyromancersSwath> {

    public PyromancersSwath(UUID ownerId) {
        super(ownerId, 125, "Pyromancer's Swath", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "MMA";

        this.color.setRed(true);

        // If an instant or sorcery source you control would deal damage to a creature or player, it deals that much damage plus 2 to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PyromancersSwathReplacementEffect()));

        // At the beginning of each end step, discard your hand.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE,
                "beginning of each end step", false, new DiscardHandControllerEffect()));

    }

    public PyromancersSwath(final PyromancersSwath card) {
        super(card);
    }

    @Override
    public PyromancersSwath copy() {
        return new PyromancersSwath(this);
    }
}

class PyromancersSwathReplacementEffect extends ReplacementEffectImpl<PyromancersSwathReplacementEffect> {

    PyromancersSwathReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an instant or sorcery source you control would deal damage to a creature or player, it deals that much damage plus 2 to that creature or player instead";
    }

    PyromancersSwathReplacementEffect(final PyromancersSwathReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGE_CREATURE)) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object instanceof Spell) {
                if (((Spell) object).getControllerId().equals(source.getControllerId())
                        && (object.getCardType().contains(CardType.INSTANT)
                         || object.getCardType().contains(CardType.SORCERY))){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 2);
        return false;
    }

    @Override
    public PyromancersSwathReplacementEffect copy() {
        return new PyromancersSwathReplacementEffect(this);
    }

}
