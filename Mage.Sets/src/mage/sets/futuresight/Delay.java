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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class Delay extends CardImpl {

    public Delay(UUID ownerId) {
        super(ownerId, 35, "Delay", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "FUT";


        // Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend.
        this.getSpellAbility().addEffect(new DelayEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Delay(final Delay card) {
        super(card);
    }

    @Override
    public Delay copy() {
        return new Delay(this);
    }
}

class DelayEffect extends OneShotEffect {

    public DelayEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend";
    }

    public DelayEffect(final DelayEffect effect) {
        super(effect);
    }

    @Override
    public DelayEffect copy() {
        return new DelayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (controller != null && spell != null) {
            Effect effect = new CounterTargetWithReplacementEffect(Zone.EXILED);
            effect.setTargetPointer(targetPointer);
            Card card = game.getCard(spell.getSourceId());
            if (card != null && effect.apply(game, source) && Zone.EXILED.equals(game.getState().getZone(card.getId()))) {
                boolean hasSuspend = card.getAbilities().containsClass(SuspendAbility.class);
                UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
                if (controller.moveCardToExileWithInfo(card, exileId, "Suspended cards of " + controller.getLogName(), source.getSourceId(), game, Zone.HAND, true)) {
                    card.addCounters(CounterType.TIME.createInstance(3), game);
                    if (!hasSuspend) {
                        game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);
                    }
                    game.informPlayers(controller.getLogName() + " suspends 3 - " + card.getName());
                }                
            }
            return true;
        }
        return false;
    }
}
