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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.counters.Counter;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public class SenseiGoldenTail extends CardImpl<SenseiGoldenTail> {

    public SenseiGoldenTail (UUID ownerId) {
        super(ownerId, 44, "Sensei Golden-Tail", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.supertype.add("Legendary");
        this.expansionSetCode = "CHK";
        this.subtype.add("Fox");
        this.subtype.add("Samurai");
	this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));
        // {1}{W}, {T}: Put a training counter on target creature. 
        Ability ability = new ActivateAsSorceryActivatedAbility(Constants.Zone.BATTLEFIELD, new AddCountersTargetEffect(new TrainingCounter()), new ManaCostsImpl("{1}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        // That creature gains bushido 1 and becomes a Samurai in addition to its other creature types. Activate this ability only any time you could cast a sorcery.
        ability.addEffect(new GainAbilityTargetEffect(new BushidoAbility(1),Duration.WhileOnBattlefield));
        ability.addEffect(new AddCardSubTypeTargetEffect("Samurai",Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    public SenseiGoldenTail (final SenseiGoldenTail card) {
        super(card);
    }

    @Override
    public SenseiGoldenTail copy() {
        return new SenseiGoldenTail(this);
    }

}


class TrainingCounter extends Counter<TrainingCounter> {

    public TrainingCounter() {
        this(1);
    }

    public TrainingCounter(int amount) {
        super("training");
        this.count = amount;
    }
}