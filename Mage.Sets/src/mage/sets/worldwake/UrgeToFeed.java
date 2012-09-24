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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class UrgeToFeed extends CardImpl<UrgeToFeed> {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public UrgeToFeed(UUID ownerId) {
        super(ownerId, 70, "Urge to Feed", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{B}{B}");
        this.expansionSetCode = "WWK";

        this.color.setBlack(true);

        // Target creature gets -3/-3 until end of turn. You may tap any number of untapped Vampire creatures you control. If you do, put a +1/+1 counter on each of those Vampires.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3, Constants.Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new UrgeToFeedEffect());
    }

    public UrgeToFeed(final UrgeToFeed card) {
        super(card);
    }

    @Override
    public UrgeToFeed copy() {
        return new UrgeToFeed(this);
    }
}

class UrgeToFeedEffect extends OneShotEffect<UrgeToFeedEffect> {

    final private static FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped Vampire creatures you control");

    static {
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate("Vampire"));
    }

    public UrgeToFeedEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "You may tap any number of untapped Vampire creatures you control. If you do, put a +1/+1 counter on each of those Vampires";
    }

    public UrgeToFeedEffect(UrgeToFeedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
        while (true) {
            target.clearChosen();
            if (target.canChoose(source.getControllerId(), game) && target.choose(Constants.Outcome.Tap, source.getControllerId(), source.getId(), game)) {
                UUID vampire = target.getFirstTarget();
                if (vampire != null) {
                    game.getPermanent(vampire).tap(game);
                    game.getPermanent(vampire).addCounters(CounterType.P1P1.createInstance(), game);
                }
            } else {
                break;
            }
        }
        return false;
    }

    @Override
    public UrgeToFeedEffect copy() {
        return new UrgeToFeedEffect(this);
    }
}
