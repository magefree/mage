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
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class TormentOfVenom extends CardImpl {

    public TormentOfVenom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Put three -1/-1 counters on target creature. Its controller loses 3 life unless he or she sacrifices another nonland permanent or discards a card.
        this.getSpellAbility().addEffect(new TormentOfVenomEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TormentOfVenom(final TormentOfVenom card) {
        super(card);
    }

    @Override
    public TormentOfVenom copy() {
        return new TormentOfVenom(this);
    }
}

class TormentOfVenomEffect extends OneShotEffect {

    public TormentOfVenomEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Put three -1/-1 counters on target creature. Its controller loses 3 life unless he or she sacrifices another nonland permanent or discards a card";
    }

    public TormentOfVenomEffect(final TormentOfVenomEffect effect) {
        super(effect);
    }

    @Override
    public TormentOfVenomEffect copy() {
        return new TormentOfVenomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            new AddCountersTargetEffect(CounterType.M1M1.createInstance(3)).apply(game, source);
            Player controllingPlayer = game.getPlayer(targetCreature.getControllerId());
            if (controllingPlayer != null) {
                int permanents = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_NON_LAND, controllingPlayer.getId(), game);
                if (permanents > 0 && controllingPlayer.chooseUse(outcome, "Sacrifices a nonland permanent?",
                        "Otherwise you have to discard a card or lose 3 life.", "Sacrifice", "Discard or life loss", source, game)) {
                    FilterPermanent filter = new FilterControlledPermanent("another nonland permanent");
                    filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
                    filter.add(Predicates.not(new PermanentIdPredicate(targetCreature.getId())));
                    Target target = new TargetPermanent(filter);
                    if (controllingPlayer.choose(outcome, target, source.getSourceId(), game)) {
                        Permanent permanent = game.getPermanent(target.getFirstTarget());
                        if (permanent != null) {
                            permanent.sacrifice(source.getSourceId(), game);
                            return true;
                        }
                    }
                }
                if (!controllingPlayer.getHand().isEmpty() && controllingPlayer.chooseUse(outcome, "Discard a card?",
                        "Otherwise you lose 3 life.", "Discard", "Lose 3 life", source, game)) {
                    controllingPlayer.discardOne(false, source, game);
                    return true;
                }
                controllingPlayer.loseLife(3, game, false);
                return true;
            }
        }

        return false;
    }
}
