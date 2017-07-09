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
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.replacement.DiesReplacementEffect;
import mage.abilities.effects.common.ruleModifying.CantRegenerateTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LoneFox
 */
public class ScorchingLava extends CardImpl {

    public ScorchingLava(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));
        // Scorching Lava deals 2 damage to target creature or player. If Scorching Lava was kicked, that creature can't be regenerated this turn and if it would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new ConditionalContinuousRuleModifyingEffect(
                new CantRegenerateTargetEffect(Duration.EndOfTurn, "that creature"), new LockedInCondition(KickedCondition.instance)));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                (OneShotEffect) new ExileTargetIfDiesEffect().setText("and if it would die this turn, exile it instead"),
                new LockedInCondition(KickedCondition.instance)));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public ScorchingLava(final ScorchingLava card) {
        super(card);
    }

    @Override
    public ScorchingLava copy() {
        return new ScorchingLava(this);
    }
}

class ScorchingLavaEffect extends OneShotEffect {

    public ScorchingLavaEffect() {
        super(Outcome.Exile);
        this.staticText = "and if it would die this turn, exile it instead";
    }

    public ScorchingLavaEffect(final ScorchingLavaEffect effect) {
        super(effect);
    }

    @Override
    public ScorchingLavaEffect copy() {
        return new ScorchingLavaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            game.addEffect(new DiesReplacementEffect(new MageObjectReference(targetCreature, game), Duration.EndOfTurn), source);
        }
        return true;
    }
}
