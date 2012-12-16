/*
 *
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 *
 */

package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.condition.common.HasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.PreventDamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;


/**
 * @author LevelX2
 */
public class FaithfulSquire extends CardImpl<FaithfulSquire> {

    private final static FilterSpiritOrArcaneCard filter = new FilterSpiritOrArcaneCard();

    public FaithfulSquire(UUID ownerId) {
        super(ownerId, 3, "Faithful Squire", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Faithful Squire.
        this.addAbility(new SpellCastTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), filter, true));

        // At the beginning of the end step, if there are two or more ki counters on Faithful Squire, you may flip it
        this.addAbility(new ConditionalTriggeredAbility(
                new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new FlipSourceEffect()),
                new HasCounterCondition(CounterType.KI, 2, Integer.MAX_VALUE),
                "At the beginning of the end step, if there are two or more ki counters on Faithful Squire, you may flip it.", true));

        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new KenzoTheHardhearted()), FlippedCondition.getInstance(), "")));
   }

    public FaithfulSquire(final FaithfulSquire card) {
        super(card);
    }

    @Override
    public FaithfulSquire copy() {
        return new FaithfulSquire(this);
    }
}

class KenzoTheHardhearted extends Token {

    KenzoTheHardhearted() {
        super("Kaiso, Memory of Loyalty", "");
        supertype.add("Legendary");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Spirit");
        power = new MageInt(3);
        toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Remove a ki counter from Kaiso, Memory of Loyalty: Prevent all damage that would be dealt to target creature this turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new PreventDamageTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE),
                new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }
}
