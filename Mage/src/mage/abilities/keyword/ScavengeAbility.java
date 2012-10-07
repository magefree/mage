/*
 *  Copyright 2012 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.abilities.keyword;

import mage.Constants;
import mage.Constants.TimingRule;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author magenoxx_at_gmail.com
 */


//
//    702.95. Scavenge
//
//    702.95a Scavenge is an activated ability that functions only while the card
//    with scavenge is in a graveyard. "Scavenge [cost]" means "[Cost], Exile this
//    card from your graveyard: Put a number of +1/+1 counters equal to this card’s
//    power on target creature. Activate this ability only any time you could cast
//    a sorcery."
//

public class ScavengeAbility extends ActivatedAbilityImpl<ScavengeAbility> {

    public ScavengeAbility(ManaCosts costs) {
        super(Zone.GRAVEYARD, new ScavengeEffect(), costs);
        this.timing = TimingRule.SORCERY;
        this.addCost(new ExileSourceFromGraveCost());
        this.addTarget(new TargetCreaturePermanent(true));
    }

    public ScavengeAbility(final ScavengeAbility ability) {
        super(ability);
    }

    @Override
    public ScavengeAbility copy() {
        return new ScavengeAbility(this);
    }

    @Override
    public String getRule() {
        return "Scavenge " + getManaCosts().getText() + " (" + getManaCosts().getText() + ", Exile this card from your graveyard: Put a number of +1/+1 counter's equal to this card's power on target creature. Scavenge only as a sorcery.)";
    }
}

class ScavengeEffect extends OneShotEffect<ScavengeEffect> {
    ScavengeEffect() {
        super(Constants.Outcome.BoostCreature);
    }

    ScavengeEffect(final ScavengeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            int count = card.getPower().getValue();
            if (count > 0) {
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(count));
                effect.setTargetPointer(getTargetPointer());
                return effect.apply(game, source);
            }
        }

        return false;
    }

    @Override
    public ScavengeEffect copy() {
        return new ScavengeEffect(this);
    }
}