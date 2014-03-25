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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattelfieldCondition;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class SowerOfTemptation extends CardImpl<SowerOfTemptation> {

    public SowerOfTemptation(UUID ownerId) {
        super(ownerId, 88, "Sower of Temptation", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");
        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Sower of Temptation enters the battlefield, gain control of target creature for as long as Sower of Temptation remains on the battlefield.
        // 10/1/2007: You retain control of the targeted creature as long as Sower of Temptation
        //            remains on the battlefield, even if a different player gains control of Sower of Temptation itself.
        ConditionalContinousEffect effect = new ConditionalContinousEffect(
                new GainControlTargetEffect(Duration.Custom, true),
                new SourceOnBattelfieldCondition(),
                "gain control of target creature for as long as {this} remains on the battlefield");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public SowerOfTemptation(final SowerOfTemptation card) {
        super(card);
    }

    @Override
    public SowerOfTemptation copy() {
        return new SowerOfTemptation(this);
    }
}

class SowerOfTemptationGainControlEffect extends OneShotEffect<SowerOfTemptationGainControlEffect> {

    public SowerOfTemptationGainControlEffect() {
        super(Outcome.GainControl);
        this.staticText = "gain control of target creature for as long as Sower of Temptation remains on the battlefield";
    }

    public SowerOfTemptationGainControlEffect(final SowerOfTemptationGainControlEffect effect) {
        super(effect);
    }

    @Override
    public SowerOfTemptationGainControlEffect copy() {
        return new SowerOfTemptationGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ConditionalContinousEffect effect = new ConditionalContinousEffect(
                new GainControlTargetEffect(Duration.Custom),
                new SourceOnBattelfieldCondition(),
                "gain control of target creature for as long as Sower of Temptation remains on the battlefield");
        game.addEffect(effect, source);
        return false;
    }
}