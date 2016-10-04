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
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrSuspendedCard;

/**
 *
 * @author emerald000
 */
public class JhoirasTimebug extends CardImpl {

    private static final FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard("permanent you control or suspended card you own");
    static {
        filter.getPermanentFilter().add(new ControllerPredicate(TargetController.YOU));
        filter.getCardFilter().add(new OwnerPredicate(TargetController.YOU));
    }

    public JhoirasTimebug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add("Insect");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Choose target permanent you control or suspended card you own. If that permanent or card has a time counter on it, you may remove a time counter from it or put another time counter on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JhoirasTimebugEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanentOrSuspendedCard(filter, false));
        this.addAbility(ability);
    }

    public JhoirasTimebug(final JhoirasTimebug card) {
        super(card);
    }

    @Override
    public JhoirasTimebug copy() {
        return new JhoirasTimebug(this);
    }
}

class JhoirasTimebugEffect extends OneShotEffect {

    JhoirasTimebugEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose target permanent you control or suspended card you own. If that permanent or card has a time counter on it, you may remove a time counter from it or put another time counter on it";
    }

    JhoirasTimebugEffect(final JhoirasTimebugEffect effect) {
        super(effect);
    }

    @Override
    public JhoirasTimebugEffect copy() {
        return new JhoirasTimebugEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null && permanent.getCounters(game).containsKey(CounterType.TIME)) {
                if (controller.chooseUse(Outcome.Benefit, "Add a time counter? (Otherwise remove one)", source, game)) {
                    permanent.addCounters(CounterType.TIME.createInstance(), game);
                }
                else {
                    permanent.removeCounters(CounterType.TIME.createInstance(), game);
                }
            }
            else {
                Card card = game.getExile().getCard(this.getTargetPointer().getFirst(game, source), game);
                if (card != null) {
                    if (controller.chooseUse(Outcome.Detriment, "Add a time counter? (Otherwise remove one)", source, game)) {
                        card.addCounters(CounterType.TIME.createInstance(), game);
                    }
                    else {
                        card.removeCounters(CounterType.TIME.createInstance(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
