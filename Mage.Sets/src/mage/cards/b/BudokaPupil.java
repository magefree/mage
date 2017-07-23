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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class BudokaPupil extends CardImpl {

    public BudokaPupil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.HUMAN, SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Ichiga, Who Topples Oaks";

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Budoka Pupil.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.SPIRIT_OR_ARCANE_CARD, true));

        // At the beginning of the end step, if there are two or more ki counters on Budoka Pupil, you may flip it.
        this.addAbility(new ConditionalTriggeredAbility(
                new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new FlipSourceEffect(new IchigaWhoTopplesOaks()), true),
                new SourceHasCounterCondition(CounterType.KI, 2, Integer.MAX_VALUE),
                "At the beginning of the end step, if there are two or more ki counters on {this}, you may flip it."));
    }

    public BudokaPupil(final BudokaPupil card) {
        super(card);
    }

    @Override
    public BudokaPupil copy() {
        return new BudokaPupil(this);
    }
}

class IchigaWhoTopplesOaks extends Token {

    IchigaWhoTopplesOaks() {
        super("Ichiga, Who Topples Oaks", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Spirit");
        power = new MageInt(4);
        toughness = new MageInt(3);

        // Trample.
        this.addAbility(TrampleAbility.getInstance());

        // Remove a ki counter from Ichiga, Who Topples Oaks: Target creature gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostTargetEffect(2, 2, Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }
}
