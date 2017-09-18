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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureAllEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class WeaverOfLies extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures with a morph ability");

    static {
        filter.add(new AbilityPredicate(MorphAbility.class));
        filter.add(new AnotherPredicate());
    }

    public WeaverOfLies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Morph {4}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{4}{U}")));

        // When Weaver of Lies is turned face up, turn any number of target creatures with a morph ability other than Weaver of Lies face down.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new WeaverOfLiesEffect(), false, false);
        ability.addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, false));
        this.addAbility(ability);
    }

    public WeaverOfLies(final WeaverOfLies card) {
        super(card);
    }

    @Override
    public WeaverOfLies copy() {
        return new WeaverOfLies(this);
    }
}

class WeaverOfLiesEffect extends OneShotEffect {

    WeaverOfLiesEffect() {
        super(Outcome.Benefit);
        this.staticText = "turn any number of target creatures with a morph ability other than {this} face down";
    }

    WeaverOfLiesEffect(final WeaverOfLiesEffect effect) {
        super(effect);
    }

    @Override
    public WeaverOfLiesEffect copy() {
        return new WeaverOfLiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Predicate pred = new PermanentIdPredicate(UUID.randomUUID());
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                pred = Predicates.or(pred, new PermanentIdPredicate(targetId));
            }
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(pred);
        game.addEffect(new BecomesFaceDownCreatureAllEffect(filter), source);
        return true;
    }
}
