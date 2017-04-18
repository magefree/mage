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
package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.GainLifeOpponentCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class RoarOfJukai extends CardImpl {

    public RoarOfJukai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.subtype.add("Arcane");


        // If you control a Forest, each blocked creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new RoarOfJukaiEffect());

        // Splice onto Arcane-An opponent gains 5 life.
        this.addAbility(new SpliceOntoArcaneAbility(new GainLifeOpponentCost(5)));
    }

    public RoarOfJukai(final RoarOfJukai card) {
        super(card);
    }

    @Override
    public RoarOfJukai copy() {
        return new RoarOfJukai(this);
    }
}

class RoarOfJukaiEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("Forest");
    private static final FilterCreaturePermanent filterBlocked = new FilterCreaturePermanent("blocked creature");

    static {
        filter.add(new SubtypePredicate("Forest"));
        filterBlocked.add(new BlockedPredicate());
    }


    static {

    }

    public RoarOfJukaiEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If you control a Forest, each blocked creature gets +2/+2 until end of turn";
    }

    public RoarOfJukaiEffect(final RoarOfJukaiEffect effect) {
        super(effect);
    }

    @Override
    public RoarOfJukaiEffect copy() {
        return new RoarOfJukaiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 0).apply(game, source)) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filterBlocked, source.getControllerId(), source.getSourceId(), game)) {
                    ContinuousEffect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
