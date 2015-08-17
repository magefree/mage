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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.Counter;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class Smokestack extends CardImpl {

    public Smokestack(UUID ownerId) {
        super(ownerId, 309, "Smokestack", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "USG";

        // At the beginning of your upkeep, you may put a soot counter on Smokestack.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(new Counter("Soot")), TargetController.YOU, true));

        // At the beginning of each player's upkeep, that player sacrifices a permanent for each soot counter on Smokestack.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SmokestackEffect(), TargetController.ANY, false));
    }

    public Smokestack(final Smokestack card) {
        super(card);
    }

    @Override
    public Smokestack copy() {
        return new Smokestack(this);
    }
}

class SmokestackEffect extends OneShotEffect {

    public SmokestackEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "that player sacrifices a permanent for each soot counter on Smokestack";
    }

    public SmokestackEffect(final SmokestackEffect effect) {
        super(effect);
    }

    @Override
    public SmokestackEffect copy() {
        return new SmokestackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (activePlayer != null && sourcePermanent != null) {
            int count = sourcePermanent.getCounters().getCount("Soot");
            if (count > 0) {
                int amount = Math.min(count, game.getBattlefield().countAll(new FilterControlledPermanent(), activePlayer.getId(), game));
                Target target = new TargetControlledPermanent(amount, amount, new FilterControlledPermanent(), false);
                //A spell or ability could have removed the only legal target this player
                //had, if thats the case this ability should fizzle.
                if (target.canChoose(activePlayer.getId(), game)) {
                    while (!target.isChosen() && target.canChoose(activePlayer.getId(), game) && activePlayer.canRespond()) {
                        activePlayer.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                    }

                    for (int idx = 0; idx < target.getTargets().size(); idx++) {
                        Permanent permanent = game.getPermanent(target.getTargets().get(idx));

                        if (permanent != null) {
                            permanent.sacrifice(source.getSourceId(), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
