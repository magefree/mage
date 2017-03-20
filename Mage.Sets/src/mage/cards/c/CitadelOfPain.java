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
package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author Alexsandr0x
 */
public class CitadelOfPain extends CardImpl {

    public CitadelOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true,
                new CitadelOfPainEffect());
        // At the beginning of each player's end step, Citadel of Pain deals X damage to that player, where X is the number of untapped lands he or she controls.
        this.addAbility(triggered);
    }

    public CitadelOfPain(final CitadelOfPain card) {
        super(card);
    }

    @Override
    public CitadelOfPain copy() {
        return new CitadelOfPain(this);
    }
}

class CitadelOfPainEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public CitadelOfPainEffect() {
        super(Outcome.Damage);
    }

    public CitadelOfPainEffect(Outcome outcome) {
        super(outcome);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = game.getBattlefield().countAll(filter, game.getActivePlayerId(), game);
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new CitadelOfPainEffect();
    }
}