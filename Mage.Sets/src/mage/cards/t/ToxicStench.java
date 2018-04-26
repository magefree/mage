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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class ToxicStench extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public ToxicStench(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target nonblack creature gets -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ToxicStenchEffect(),
                new InvertCondition(new CardsInControllerGraveCondition(7)),
                "Target nonblack creature gets -1/-1 until end of turn."));

        // Threshold - If seven or more cards are in your graveyard, instead destroy that creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DestroyTargetEffect(true),
                new CardsInControllerGraveCondition(7),
                "<br/><br/><i>Threshold</i> - If seven or more cards are in your graveyard, instead destroy that creature. It can't be regenerated."));

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public ToxicStench(final ToxicStench card) {
        super(card);
    }

    @Override
    public ToxicStench copy() {
        return new ToxicStench(this);
    }
}

class ToxicStenchEffect extends OneShotEffect {

    ToxicStenchEffect() {
        super(Outcome.UnboostCreature);
    }

    ToxicStenchEffect(final ToxicStenchEffect effect) {
        super(effect);
    }

    @Override
    public ToxicStenchEffect copy() {
        return new ToxicStenchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ContinuousEffect effect = new BoostTargetEffect(-1, -1, Duration.EndOfTurn);
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
