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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TemptWithGlory extends CardImpl<TemptWithGlory> {

    public TemptWithGlory(UUID ownerId) {
        super(ownerId, 24, "Tempt with Glory", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{W}");
        this.expansionSetCode = "C13";

        this.color.setWhite(true);

        // Tempting offer - Put a +1/+1 counter on each creature you control. Each opponent may put a +1/+1 counter on each creature he or she controls. For each opponent who does, put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addEffect(new TemptWithGloryEffect());
    }

    public TemptWithGlory(final TemptWithGlory card) {
        super(card);
    }

    @Override
    public TemptWithGlory copy() {
        return new TemptWithGlory(this);
    }
}

class TemptWithGloryEffect extends OneShotEffect<TemptWithGloryEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final Counter counter = CounterType.P1P1.createInstance();

    public TemptWithGloryEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "<i>Tempting offer</i> - Put a +1/+1 counter on each creature you control. Each opponent may put a +1/+1 counter on each creature he or she controls. For each opponent who does, put a +1/+1 counter on each creature you control";
    }

    public TemptWithGloryEffect(final TemptWithGloryEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithGloryEffect copy() {
        return new TemptWithGloryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            addCounterToEachCreature(controller.getId(), counter, game);
            int opponentsAddedCounters = 0;
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    if (opponent.chooseUse(outcome, new StringBuilder("Put a +1/+1 counter on each creature you control?").toString(), game)) {
                        opponentsAddedCounters++;
                        addCounterToEachCreature(playerId, counter, game);
                        game.informPlayers(new StringBuilder(opponent.getName()).append(" added a +1/+1 counter on each of its creatures").toString());
                    }
                }
            }
            if (opponentsAddedCounters > 0) {
                addCounterToEachCreature(controller.getId(), CounterType.P1P1.createInstance(opponentsAddedCounters), game);
            }
            return true;
        }

        return false;
    }

    private void addCounterToEachCreature(UUID playerId, Counter counter, Game game) {
        for(Permanent permanent: game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
            permanent.addCounters(counter, game);
        }
    }
}
