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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public class PathbreakerIbex extends CardImpl {

    public PathbreakerIbex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add("Goat");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Pathbreaker Ibex attacks, creatures you control gain trample and get +X/+X until end of turn, where X is the greatest power among creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new PathbreakerIbexEffect(), false));
    }

    public PathbreakerIbex(final PathbreakerIbex card) {
        super(card);
    }

    @Override
    public PathbreakerIbex copy() {
        return new PathbreakerIbex(this);
    }
}

class PathbreakerIbexEffect extends OneShotEffect {
    
    public PathbreakerIbexEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "creatures you control gain trample and get +X/+X until end of turn, where X is the greatest power among creatures you control";
    }
    
    public PathbreakerIbexEffect(final PathbreakerIbexEffect effect) {
        super(effect);
    }
    
    @Override
    public PathbreakerIbexEffect copy() {
        return new PathbreakerIbexEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        int maxPower = 0;
        for (Permanent perm: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
            if (perm.getPower().getValue() > maxPower) {
                maxPower = perm.getPower().getValue();
            }
        }
        ContinuousEffect effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfStep, new FilterCreaturePermanent());
        game.addEffect(effect, source);
        if (maxPower != 0) {
            effect = new BoostControlledEffect(maxPower, maxPower, Duration.EndOfTurn);
            game.addEffect(effect, source);
        }
        return true;
    }
}
