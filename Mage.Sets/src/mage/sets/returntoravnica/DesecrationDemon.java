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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class DesecrationDemon extends CardImpl<DesecrationDemon> {

    public DesecrationDemon(UUID ownerId) {
        super(ownerId, 63, "Desecration Demon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Demon");
        this.color.setBlack(true);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each combat, any opponent may sacrifice a creature. If a player does, tap Desecration Demon and put a +1/+1 counter on it.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DesecrationDemonEffect(), Constants.TargetController.ANY, false));
    }

    public DesecrationDemon(final DesecrationDemon card) {
        super(card);
    }

    @Override
    public DesecrationDemon copy() {
        return new DesecrationDemon(this);
    }
}

class DesecrationDemonEffect extends OneShotEffect<DesecrationDemonEffect> {
    DesecrationDemonEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "any opponent may sacrifice a creature. If a player does, tap {this} and put a +1/+1 counter on it";
    }

    DesecrationDemonEffect(final DesecrationDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent descrationDemon = game.getPermanent(source.getSourceId());
        if (controller != null && descrationDemon != null) {
            for (UUID opponentId: game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterControlledPermanent filter = new FilterControlledPermanent("creature to sacrifice");
                    filter.add(new CardTypePredicate(CardType.CREATURE));
                    filter.add(new ControllerPredicate(Constants.TargetController.YOU));
                    TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, false);
                    if (target.canChoose(opponent.getId(), game)) {
                        if (opponent.chooseUse(Outcome.Detriment, new StringBuilder("Sacrifice a creature to tap ").append(descrationDemon.getName()).append("and put a +1/+1 counter on it?").toString(), game))
                        {
                            opponent.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                            Permanent permanent = game.getPermanent(target.getFirstTarget());
                            if (permanent != null) {
                                permanent.sacrifice(source.getId(), game);
                                game.informPlayers(new StringBuilder(opponent.getName()).append(" sacrifices ").append(permanent.getName()).append(" to tap ").append(descrationDemon.getName()).append(". A +1/+1 counter was put on it").toString());
                                if (descrationDemon != null) {
                                    descrationDemon.tap(game);
                                    descrationDemon.addCounters(CounterType.P1P1.createInstance(), game);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DesecrationDemonEffect copy() {
        return new DesecrationDemonEffect(this);
    }
}
