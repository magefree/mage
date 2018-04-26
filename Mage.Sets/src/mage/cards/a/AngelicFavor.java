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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TurnPhase;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public class AngelicFavor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("If you control a Plains");

    static {
        filter.add(new SubtypePredicate(SubType.PLAINS));
    }
    private static final FilterControlledCreaturePermanent untappedCreatureYouControl = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        untappedCreatureYouControl.add(Predicates.not(new TappedPredicate()));
    }

    public AngelicFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // If you control a Plains, you may tap an untapped creature you control rather than pay Angelic Favor's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new TapTargetCost(new TargetControlledPermanent(untappedCreatureYouControl)), new PermanentsOnTheBattlefieldCondition(filter)));

        // Cast Angelic Favor only during combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT));

        // Put a 4/4 white Angel creature token with flying onto the battlefield. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new AngelicFavorEffect());

    }

    public AngelicFavor(final AngelicFavor card) {
        super(card);
    }

    @Override
    public AngelicFavor copy() {
        return new AngelicFavor(this);
    }
}

class AngelicFavorEffect extends OneShotEffect {

    public AngelicFavorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 4/4 white Angel creature token with flying. Exile it at the beginning of the next end step";
    }

    public AngelicFavorEffect(final AngelicFavorEffect effect) {
        super(effect);
    }

    @Override
    public AngelicFavorEffect copy() {
        return new AngelicFavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new AngelToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}
