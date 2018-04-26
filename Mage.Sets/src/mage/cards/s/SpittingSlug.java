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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class SpittingSlug extends CardImpl {

    public SpittingSlug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Spitting Slug blocks or becomes blocked, you may pay {1}{G}. If you do, Spitting Slug gains first strike until end of turn. Otherwise, each creature blocking or blocked by Spitting Slug gains first strike until end of turn.
        this.addAbility(new BlocksOrBecomesBlockedTriggeredAbility(
            new DoIfCostPaid(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), 
                new SpittingSlugEffect(), 
                new ManaCostsImpl("{1}{G}")).setText("you may pay {1}{G}. If you do, {this} gains first strike until end of turn. Otherwise, each creature blocking or blocked by {this} gains first strike until end of turn"), 
            false));
    }

    public SpittingSlug(final SpittingSlug card) {
        super(card);
    }

    @Override
    public SpittingSlug copy() {
        return new SpittingSlug(this);
    }
}

class SpittingSlugEffect extends OneShotEffect {

    public SpittingSlugEffect() {
        super(Outcome.Detriment);
    }

    public SpittingSlugEffect(final SpittingSlugEffect effect) {
        super(effect);
    }

    @Override
    public SpittingSlugEffect copy() {
        return new SpittingSlugEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(Predicates.or(new BlockedByIdPredicate(sourcePermanent.getId()), new BlockingAttackerIdPredicate(sourcePermanent.getId())));
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                ContinuousEffect effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(permanent.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
