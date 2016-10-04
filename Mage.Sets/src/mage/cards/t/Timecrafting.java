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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrSuspendedCard;

/**
 *
 * @author emerald000
 */
public class Timecrafting extends CardImpl {

    private static final FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard("permanent with a time counter on it or suspended card");
    static {
        filter.getPermanentFilter().add(new CounterPredicate(CounterType.TIME));
    }

    public Timecrafting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}");

        // Choose one - Remove X time counters from target permanent or suspended card;
        this.getSpellAbility().addEffect(new TimecraftingRemoveEffect());
        this.getSpellAbility().addTarget(new TargetPermanentOrSuspendedCard());

        // or put X time counters on target permanent with a time counter on it or suspended card.
        Mode mode = new Mode();
        mode.getEffects().add(new TimecraftingAddEffect());
        mode.getTargets().add(new TargetPermanentOrSuspendedCard(filter, false));
        this.getSpellAbility().addMode(mode);
    }

    public Timecrafting(final Timecrafting card) {
        super(card);
    }

    @Override
    public Timecrafting copy() {
        return new Timecrafting(this);
    }
}

class TimecraftingRemoveEffect extends OneShotEffect {

    TimecraftingRemoveEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove X time counters from target permanent or suspended card";
    }

    TimecraftingRemoveEffect(final TimecraftingRemoveEffect effect) {
        super(effect);
    }

    @Override
    public TimecraftingRemoveEffect copy() {
        return new TimecraftingRemoveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xValue = source.getManaCostsToPay().getX();
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                permanent.removeCounters(CounterType.TIME.createInstance(xValue), game);
            }
            else {
                Card card = game.getExile().getCard(this.getTargetPointer().getFirst(game, source), game);
                if (card != null) {
                    card.removeCounters(CounterType.TIME.createInstance(xValue), game);
                }
            }
            return true;
        }
        return false;
    }
}

class TimecraftingAddEffect extends OneShotEffect {

    TimecraftingAddEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put X time counters on target permanent with a time counter on it or suspended card";
    }

    TimecraftingAddEffect(final TimecraftingAddEffect effect) {
        super(effect);
    }

    @Override
    public TimecraftingAddEffect copy() {
        return new TimecraftingAddEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xValue = source.getManaCostsToPay().getX();
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                permanent.addCounters(CounterType.TIME.createInstance(xValue), game);
            }
            else {
                Card card = game.getExile().getCard(this.getTargetPointer().getFirst(game, source), game);
                if (card != null) {
                    card.addCounters(CounterType.TIME.createInstance(xValue), game);
                }
            }
            return true;
        }
        return false;
    }
}
