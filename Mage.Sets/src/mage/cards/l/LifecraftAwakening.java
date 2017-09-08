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
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class LifecraftAwakening extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public LifecraftAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Put X +1/+1 counters on target artifact you control. If it isn't a creature or Vehicle, it becomes a 0/0 Construct artifact creature.
        ManacostVariableValue manaX = new ManacostVariableValue();
        getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(), manaX));
        getSpellAbility().addTarget(new TargetArtifactPermanent(filter));
        getSpellAbility().addEffect(new LifecraftAwakeningEffect());
    }

    public LifecraftAwakening(final LifecraftAwakening card) {
        super(card);
    }

    @Override
    public LifecraftAwakening copy() {
        return new LifecraftAwakening(this);
    }
}

class LifecraftAwakeningEffect extends OneShotEffect {

    public LifecraftAwakeningEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "If it isn't a creature or Vehicle, it becomes a 0/0 Construct artifact creature";
    }

    public LifecraftAwakeningEffect(final LifecraftAwakeningEffect effect) {
        super(effect);
    }

    @Override
    public LifecraftAwakeningEffect copy() {
        return new LifecraftAwakeningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getPermanent(source.getTargets().getFirstTarget());
        if (!permanent.isCreature() && !permanent.getSubtype(game).contains(SubType.VEHICLE)) {
            ContinuousEffect continuousEffect = new BecomesCreatureTargetEffect(new LifecraftAwakeningToken(), false, true, Duration.Custom);
            continuousEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(continuousEffect, source);
            return true;
        }
        return false;
    }
}

class LifecraftAwakeningToken extends Token {

    LifecraftAwakeningToken() {
        super("", "0/0 Construct artifact creature");
        this.cardType.add(CardType.ARTIFACT);
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
    }
}
