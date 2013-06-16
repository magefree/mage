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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.SplitCard;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GiveTake extends SplitCard<GiveTake> {

    public GiveTake(UUID ownerId) {
        super(ownerId, 129, "Give", "Take", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{G}","{2}{U}", true);
        this.expansionSetCode = "DGM";

        this.color.setGreen(true);
        this.color.setBlue(true);

        // Give
        // Put three +1/+1 counters on target creature.
        getLeftHalfCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(true));
        getLeftHalfCard().getColor().setGreen(true);

        // Take
        // Remove all +1/+1 counters from target creature you control. Draw that many cards.
        getRightHalfCard().getSpellAbility().addEffect(new TakeEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent(true));
        getLeftHalfCard().getColor().setBlue(true);
    }

    public GiveTake(final GiveTake card) {
        super(card);
    }

    @Override
    public GiveTake copy() {
        return new GiveTake(this);
    }
}

class TakeEffect extends OneShotEffect<TakeEffect> {

    public TakeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Remove all +1/+1 counters from target creature you control. Draw that many cards.";
    }

    public TakeEffect(final TakeEffect effect) {
        super(effect);
    }

    @Override
    public TakeEffect copy() {
        return new TakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null) {
            int numberCounters = creature.getCounters().getCount(CounterType.P1P1);
            if (numberCounters > 0) {
                creature.removeCounters(CounterType.P1P1.getName(), numberCounters, game);
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    controller.drawCards(numberCounters, game);
                } else {
                    throw new UnsupportedOperationException("Controller missing");
                }
            }
            return true;
        }
        return false;
    }
}
